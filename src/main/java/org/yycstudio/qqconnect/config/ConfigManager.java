package org.yycstudio.qqconnect.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.yycstudio.qqconnect.QQConnect;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class ConfigManager {

    private static final HashMap<Long, String> bindUser = new HashMap<>();

    /**
     * 初始化数据
     */
    public static void initData() {
        File file = new File(QQConnect.INSTANCE.getDataFolder() + "/playerData");
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File data : files) {
            Long qq = Long.valueOf(data.getName().replace(".yml", ""));
            YamlConfiguration playerYaml = YamlConfiguration.loadConfiguration(new File(QQConnect.INSTANCE.getDataFolder() + "/playerData", qq + ".yml"));
            bindUser.put(qq, playerYaml.getString("playerName"));
        }
    }

    /**
     * 获取已经绑定了QQ的玩家Map
     *
     * @return bindUserMap
     */
    public static HashMap<Long, String> getBindUser() {
        return bindUser;
    }

    /**
     * 获取功能是否启用
     *
     * @param function 功能名字
     * @return isEnable
     */
    public static boolean getFunEnable(String function) {
        return Config.getFunctionYaml().getBoolean(function + ".Enable");
    }

    /**
     * 获取功能通知格式
     *
     * @param function 功能名字
     * @return Format
     */
    public static List<String> getFunFormat(String function) {
        return Config.getFunctionYaml().getStringList(function + ".Format");
    }

    /**
     * 获取机器人QQ
     *
     * @return qq
     */
    public static long getBotQQ() {
        return Config.getBotYaml().getLong("Bot.QQ");
    }

    /**
     * 获取机器人密码
     *
     * @return password
     */
    public static String getBotPassword() {
        return Config.getBotYaml().getString("Bot.Password");
    }

    /**
     * 获取管理员列表
     *
     * @return 管理员list
     */
    public static List<Long> getAdmins() {
        return Config.getBotYaml().getLongList("Admins");
    }

    /**
     * 获取启用群聊
     *
     * @return 群聊list
     */
    public static List<Long> getGroups() {
        return Config.getBotYaml().getLongList("Groups");
    }

    /**
     * 获取 Server -> QQ 单词过滤表
     *
     * @return 单词list
     */
    public static List<String> getServerToQQWordFilter() {
        return Config.getBotYaml().getStringList("WordFilter.ServerToQQ");
    }

    /**
     * 获取 QQ -> Server 单词过滤表
     *
     * @return 单词list
     */
    public static List<String> getQQToServerWordFilter() {
        return Config.getBotYaml().getStringList("WordFilter.QQToServer");
    }

    /**
     * 获取消息内容
     *
     * @param key 键内容
     * @return 消息内容
     */
    public static String getMessage_QQ(String key) {
        return Config.getMessage_qqYaml().getString(key);
    }

    /**
     * 获取消息内容
     *
     * @param key 键内容
     * @return 消息内容
     */
    public static String getMessage_Server(String key) {
        return Config.getMessage_serverYaml().getString(key).replaceAll("&", "§");
    }

    public static String getPasswordRegex() {
        return Config.getFunctionYaml().getString("ChangePassword.Regex");
    }

    public static int[] getPasswordLength() {
        int num1 = Integer.parseInt(Config.getFunctionYaml().getString("ChangePassword.PasswordLength").split("-")[0]);
        int num2 = Integer.parseInt(Config.getFunctionYaml().getString("ChangePassword.PasswordLength").split("-")[1]);
        return new int[]{num1, num2};
    }

    public static String getChangePasswordCmd() {
        return Config.getFunctionYaml().getString("ChangePassword.Command");
    }

    public static List<String> getAllowCmd() {
        return Config.getFunctionYaml().getStringList("SendCmd.AllowCmd");
    }

    public static String getServerName() {
        return Config.getBotYaml().getString("Server_Name").replaceAll("&", "§");
    }

    public static String getQQToServerFormat() {
        return Config.getFunctionYaml().getString("QQToServer.Format").replaceAll("&", "§");
    }

    public static List<String> getAnnounceList() {
        return Config.getFunctionYaml().getStringList("Announce.List");
    }

    public static long getAnnouncePeriod() {
        return Config.getFunctionYaml().getLong("Announce.Period") * 20;
    }
}
