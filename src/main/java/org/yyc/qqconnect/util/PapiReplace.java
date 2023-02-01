package org.yyc.qqconnect.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class PapiReplace {

    /**
     * papi变量替换
     *
     * @param p   玩家实体
     * @param str 原字符串
     * @return 被papi变量替换后的字符串
     */
    public static String papiReplace(Player p, String str) {
        return PlaceholderAPI.setPlaceholders(p, str);
    }
}
