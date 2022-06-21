package org.yycstudio.qqconnect.event.server;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.yycstudio.qqconnect.bot.Bot;
import org.yycstudio.qqconnect.config.ConfigManager;

public class ServerEvent implements Listener {

    @EventHandler
    public void onOnline(PluginEnableEvent event) {
        if (event.getPlugin().equals(Bukkit.getPluginManager().getPlugin("QQConnect"))) {
            for (long group : ConfigManager.getGroups()) {
                Bot.getMiraiBot()
                        .getGroup(group)
                        .sendMessage(ConfigManager.getMessage_QQ("BotOnline")
                                .replaceAll("%server_name%", ConfigManager.getServerName()));
            }
        }
    }

    @EventHandler
    public void onOffline(PluginDisableEvent event) {
        if (event.getPlugin().equals(Bukkit.getPluginManager().getPlugin("QQConnect"))) {
            for (long group : ConfigManager.getGroups()) {
                Bot.getMiraiBot()
                        .getGroup(group)
                        .sendMessage(ConfigManager.getMessage_QQ("BotOffline")
                                .replaceAll("%server_name%", ConfigManager.getServerName()));
            }
        }
    }
}
