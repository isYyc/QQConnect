package org.yycstudio.qqconnect.api;

import me.dreamvoid.miraimc.api.MiraiBot;
import org.yycstudio.qqconnect.bot.Bot;
import org.yycstudio.qqconnect.config.ConfigManager;
import org.yycstudio.qqconnect.config.PlayerData;

public class QQConnectAPI {

    /**
     * 通过玩家ID获取QQ
     *
     * @param playerName 玩家ID
     * @return 玩家QQ 0->未绑定QQ
     */
    public static long getPlayerQQ(String playerName) {
        if (ConfigManager.getBindUser().containsValue(playerName)) {
            long qq = 0;
            for (long l : ConfigManager.getBindUser().keySet()) {
                if (ConfigManager.getBindUser().get(l).equalsIgnoreCase(playerName)) {
                    qq = l;
                    break;
                }
            }
            return qq;
        } else {
            return 0;
        }
    }

    /**
     * 通过QQ获取玩家ID
     *
     * @param qq 玩家QQ
     * @return 玩家ID null->未绑定
     */
    public static String getPlayerName(long qq) {
        if (ConfigManager.getBindUser().containsKey(qq)) {
            String playerName = null;
            for (long l : ConfigManager.getBindUser().keySet()) {
                if (l == qq) {
                    playerName = ConfigManager.getBindUser().get(l);
                }
            }
            return playerName;
        } else {
            return null;
        }
    }

    /**
     * 获取机器人对象
     *
     * @return 机器人
     */
    public static MiraiBot getMiraiBot() {
        return Bot.getMiraiBot();
    }

    /**
     * 通过旧QQ修改玩家绑定
     *
     * @param oldQQ 旧QQ
     * @param newQQ 新QQ
     * @return true->修改成功 false->旧QQ无法找到
     */
    public static boolean changeBind(long oldQQ, long newQQ) {
        if (ConfigManager.getBindUser().containsKey(oldQQ)) {
            PlayerData.changeBind(ConfigManager.getBindUser().get(oldQQ), newQQ);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 通过旧QQ修改玩家绑定
     *
     * @param playerName 玩家ID
     * @param newQQ      新QQ
     * @return true->修改成功 false->旧QQ无法找到
     */
    public static boolean changeBind(String playerName, long newQQ) {
        if (ConfigManager.getBindUser().containsValue(playerName)) {
            PlayerData.changeBind(playerName, newQQ);
            return true;
        } else {
            return false;
        }
    }
}
