package org.yycstudio.qqconnect.event.server;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.yycstudio.qqconnect.bot.Bot;
import org.yycstudio.qqconnect.config.ConfigManager;
import org.yycstudio.qqconnect.util.PapiReplace;

import java.util.List;

public class ChatEvent implements Listener {


    private static void serverToQQ(Player player, String message) {
        for (String word : ConfigManager.getServerToQQWordFilter()) {
            if (message.contains(word)) return;
        }
        List<String> formats = ConfigManager.getFunFormat("ServerToQQ");
        String msg;
        if (formats.size() == 1) {
            msg = formats.get(0);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (String format : formats) {
                if (formats.get(formats.size() - 1).equalsIgnoreCase(format)) {
                    stringBuilder.append(format);
                } else {
                    stringBuilder.append(format).append("\n");
                }
            }
            msg = stringBuilder.toString();
        }
        msg = PapiReplace.papiReplace(player, msg.replaceAll("%server_name%", ConfigManager.getServerName()));
        List<Long> groups = ConfigManager.getGroups();
        for (long groupID : groups) {
            Bot.getMiraiBot().getGroup(groupID).sendMessage(msg.replaceAll("§\\S", "").replace("%message%", message));
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (ConfigManager.getFunEnable("ServerToQQ")) {
            serverToQQ(event.getPlayer(), event.getMessage());
        }
    }
}
