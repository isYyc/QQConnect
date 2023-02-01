package org.yyc.qqconnect.event.qq;

import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMemberLeaveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.yyc.qqconnect.bot.Bot;
import org.yyc.qqconnect.config.ConfigManager;

public class GroupMemberQuitEvent implements Listener {

    @EventHandler
    public void onGroupMemberQuit(MiraiGroupMemberLeaveEvent event) {
        if (!ConfigManager.getGroups().contains(event.getGroupID())) return;
        if (!ConfigManager.getFunEnable("QuitGroupMsg")) return;
        Bot.getMiraiBot().getGroup(event.getGroupID()).sendMessage(
                ConfigManager.getFunFormat("QuitGroupMsg")
                        .get(0)
                        .replaceAll("%joiner_id%", String.valueOf(event.getTargetID())));
    }
}
