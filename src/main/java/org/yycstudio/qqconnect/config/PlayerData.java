package org.yycstudio.qqconnect.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.yycstudio.qqconnect.QQConnect;

import java.io.File;
import java.io.IOException;

public class PlayerData {

    /**
     * 新增/修改 绑定
     *
     * @param playerName 玩家名字
     * @param QQ         QQ
     */
    public static void changeBind(String playerName, long QQ) {
        long oldQQ = 0;
        if (ConfigManager.getBindUser().containsValue(playerName)) {
            for (long key : ConfigManager.getBindUser().keySet()) {
                if (ConfigManager.getBindUser().get(key).equalsIgnoreCase(playerName)) {
                    oldQQ = key;
                    break;
                }
            }
        }
        // 创建玩家数据
        File dataFile = new File(QQConnect.INSTANCE.getDataFolder() + "/playerData", QQ + ".yml");
        if (!dataFile.exists()) {
            try {
                boolean isSuccess = dataFile.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (oldQQ != 0) {
            File oldDataFile = new File(QQConnect.INSTANCE.getDataFolder() + "/playerData", oldQQ + ".yml");
            boolean isSuccess = oldDataFile.delete();
        }
        // 读写Yaml
        YamlConfiguration dataYaml = YamlConfiguration.loadConfiguration(dataFile);
        dataYaml.set("playerName", playerName);
        // 保存数据
        try {
            dataYaml.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 添加玩家到已绑定数据
        ConfigManager.getBindUser().put(QQ, playerName);
        ConfigManager.getBindUser().remove(oldQQ);
    }
}
