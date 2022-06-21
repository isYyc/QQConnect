package org.yycstudio.qqconnect.config;


import org.bukkit.configuration.file.YamlConfiguration;
import org.yycstudio.qqconnect.QQConnect;

import java.io.File;
import java.io.IOException;

public class Config {

    private static final QQConnect INSTANCE = QQConnect.INSTANCE;

    private static final File botFile = new File(INSTANCE.getDataFolder(), "Bot.yml");

    private static final File functionFile = new File(INSTANCE.getDataFolder(), "Function.yml");

    private static final File message_qqFile = new File(INSTANCE.getDataFolder(), "Message-QQ.yml");

    private static final File message_serverFile = new File(INSTANCE.getDataFolder(), "Message-Server.yml");
    private static final String pluginVersion = "1.0";
    private static YamlConfiguration bot;
    private static YamlConfiguration function;
    private static YamlConfiguration message_qq;
    private static YamlConfiguration message_server;

    /**
     * 检查配置存在并创建
     */
    public static void createConfig() {
        File[] allFile = {botFile, functionFile, message_qqFile, message_serverFile};
        for (File file : allFile) {
            if (!file.exists()) {
                QQConnect.INSTANCE.getLogger().info("未找到 " + file.getName() + " 配置，已自动创建。");
                INSTANCE.saveResource(file.getName(), true);
            }
        }
        File dataFile2 = new File(QQConnect.INSTANCE.getDataFolder() + "/playerData");
        if (!dataFile2.exists()) {
            dataFile2.mkdir();
        }
        loadConfig();
        checkVersion();
        loadConfig();
    }

    /**
     * 检查配置版本
     */
    public static void checkVersion() {
        File[] allFile = {botFile, functionFile, message_qqFile, message_serverFile};
        File f;
        for (File file : allFile) {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
            String version = yaml.getString("Version");
            if (!version.equalsIgnoreCase(pluginVersion)) {
                // 保存旧备份
                try {
                    f = new File(INSTANCE.getDataFolder(), file.getName() + ".backup");
                    yaml.save(f);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                INSTANCE.saveResource(file.getName(), true);
                INSTANCE.getLogger().info("配置 " + file.getName() + " 版本错误，为了保证插件正常运行，已重新生成正确版本的配置。");
            }
        }
    }

    /**
     * 读取配置到Yaml
     */
    public static void loadConfig() {
        bot = YamlConfiguration.loadConfiguration(botFile);
        function = YamlConfiguration.loadConfiguration(functionFile);
        message_qq = YamlConfiguration.loadConfiguration(message_qqFile);
        message_server = YamlConfiguration.loadConfiguration(message_serverFile);
    }

    /**
     * 重载配置
     */
    public static void reloadConfig() {
        checkVersion();
        loadConfig();
        QQConnect.INSTANCE.getLogger().info("配置文件已重载");
    }

    /**
     * @return bot.yml
     */
    public static YamlConfiguration getBotYaml() {
        return bot;
    }

    /**
     * @return function.yml
     */
    public static YamlConfiguration getFunctionYaml() {
        return function;
    }

    /**
     * @return message_qq.yml
     */
    public static YamlConfiguration getMessage_qqYaml() {
        return message_qq;
    }

    /**
     * @return message_server.yml
     */
    public static YamlConfiguration getMessage_serverYaml() {
        return message_server;
    }
}
