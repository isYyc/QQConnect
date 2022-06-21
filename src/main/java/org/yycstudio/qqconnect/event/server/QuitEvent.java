package org.yycstudio.qqconnect.event.server;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.yycstudio.qqconnect.bot.Bot;
import org.yycstudio.qqconnect.config.ConfigManager;
import org.yycstudio.qqconnect.util.PapiReplace;

import java.util.List;

public class QuitEvent implements Listener {

    private static void quitToQQ(Player player) {
        List<String> formats = ConfigManager.getFunFormat("QuitToQQ");
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
    public void onQuit(PlayerQuitEvent event) {
        if (ConfigManager.getFunEnable("QuitToQQ")) {
            quitToQQ(event.getPlayer());
        }
    }
}
