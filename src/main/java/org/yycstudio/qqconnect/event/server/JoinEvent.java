package org.yycstudio.qqconnect.event.server;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.yycstudio.qqconnect.bot.Bot;
import org.yycstudio.qqconnect.config.ConfigManager;
import org.yycstudio.qqconnect.util.PapiReplace;

import java.util.List;

public class JoinEvent implements Listener {

    private static void joinToQQ(Player player) {
        List<String> formats = ConfigManager.getFunFormat("JoinToQQ");
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
        msg = PapiReplace.papiReplace(player, msg);
        List<Long> groups = ConfigManager.getGroups();
        for (long groupID : groups) {
            Bot.getMiraiBot().getGroup(groupID).sendMessage(msg);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (ConfigManager.getFunEnable("JoinToQQ")) {
            joinToQQ(event.getPlayer());
        }
    }
}
