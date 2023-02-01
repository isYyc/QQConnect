package org.yyc.qqconnect.bot;

import me.dreamvoid.miraimc.api.MiraiBot;
import net.mamoe.mirai.utils.BotConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.yyc.qqconnect.QQConnect;
import org.yyc.qqconnect.config.ConfigManager;

public class TimeTask {

    private static final BukkitScheduler bukkitScheduler = Bukkit.getScheduler();

    private static int i = 0;

    public static void TaskTimer() {
        bukkitScheduler.runTaskTimerAsynchronously(QQConnect.INSTANCE, () -> {
            if (!Bot.getMiraiBot().isOnline()) {
                QQConnect.INSTANCE.getLogger().info("检查到机器人属于离线状态，正在尝试重新登录...");
                try {
                    MiraiBot.doBotLogin(Bot.getQQ(), Bot.getPassword(), BotConfiguration.MiraiProtocol.ANDROID_PHONE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 1200L, 1200L);
    }


    public static void Announce() {
        bukkitScheduler.runTaskTimerAsynchronously(QQConnect.INSTANCE, () -> {
            if (!ConfigManager.getFunEnable("Announce")) return;
            for (long group : ConfigManager.getGroups()) {
                Bot.getMiraiBot().getGroup(group).sendMessage(ConfigManager.getAnnounceList().get(i));
            }
            i++;
            if (i == ConfigManager.getAnnounceList().size()) i = 0;
        }, 600L, ConfigManager.getAnnouncePeriod());
    }
}
