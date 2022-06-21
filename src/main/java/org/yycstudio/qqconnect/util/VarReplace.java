package org.yycstudio.qqconnect.util;

import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;

import java.util.HashMap;

public class VarReplace {

    /**
     * QQ -> Server 变量替换
     *
     * @param event 监听器
     * @return 被变量替换后的字符串
     */
    public static String varReplace(MiraiGroupMessageEvent event, String message) {
        HashMap<String, String> varMap = new HashMap<>();
        varMap.put("%sender_id%", String.valueOf(event.getSenderID()));
        varMap.put("%sender_name%", String.valueOf(event.getSenderNameCard()));
        varMap.put("%group_id%", String.valueOf(event.getGroupID()));
        varMap.put("%group_name%", event.getGroupName());

        for (String var : varMap.keySet()) {
            message = message.replaceAll(var, varMap.get(var));
        }
        return message;
    }
}
