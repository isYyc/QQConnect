package org.yycstudio.qqconnect.bot;

import me.dreamvoid.miraimc.api.MiraiBot;
import net.mamoe.mirai.utils.BotConfiguration;
import org.yycstudio.qqconnect.QQConnect;
import org.yycstudio.qqconnect.config.ConfigManager;

public class Bot {

    private static final long QQ = ConfigManager.getBotQQ();
    private static final String Password = ConfigManager.getBotPassword();
    private static MiraiBot miraiBot;

    /**
     * 初始化机器人
     */
    public static void initBot() {
        try {
            QQConnect.INSTANCE.getLogger().info("机器人 " + QQ + " 正在登录中...");
            MiraiBot.doBotLogin(QQ, Password, BotConfiguration.MiraiProtocol.ANDROID_PHONE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        miraiBot = MiraiBot.getBot(QQ);
        if (!miraiBot.isOnline()) {
            QQConnect.INSTANCE.getLogger().info("机器人 " + QQ + " 登录失败，将在一分钟后重新尝试登录。");
        }
        // 确保机器人在线
        TimeTask.TaskTimer();
    }

    /**
     * 获取机器人
     *
     * @return bot
     */
    public static MiraiBot getMiraiBot() {
        return miraiBot;
    }

    /**
     * 获取机器人ID
     *
     * @return qq
     */
    public static long getQQ() {
        return QQ;
    }

    /**
     * 获取机器人密码
     *
     * @return password
     */
    public static String getPassword() {
        return Password;
    }
}
