package org.yyc.qqconnect.event.qq;

import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMemberJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.yyc.qqconnect.bot.Bot;
import org.yyc.qqconnect.config.ConfigManager;

public class GroupMemberJoinEvent implements Listener {

    @EventHandler
    public void onGroupMemberJoin(MiraiGroupMemberJoinEvent event) {
        if (!ConfigManager.getGroups().contains(event.getGroupID())) return;
        if (!ConfigManager.getFunEnable("JoinGroupMsg")) return;
        Bot.getMiraiBot().getGroup(event.getGroupID()).sendMessage(
                ConfigManager.getFunFormat("JoinGroupMsg")
                        .get(0)
                        .replaceAll("%joiner_id%", String.valueOf(event.getNewMemberID())));
    }
}
