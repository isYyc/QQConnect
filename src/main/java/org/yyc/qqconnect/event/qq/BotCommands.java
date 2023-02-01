package org.yyc.qqconnect.event.qq;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.yyc.qqconnect.enumClass.Mode;
import org.yyc.qqconnect.QQConnect;
import org.yyc.qqconnect.api.QQConnectAPI;
import org.yyc.qqconnect.bot.Bot;
import org.yyc.qqconnect.config.ConfigManager;
import org.yyc.qqconnect.util.PapiReplace;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BotCommands {

    public static List<String> msgList = new LinkedList<>();

    /**
     * 发送命令 私聊
     *
     * @param id   发送者id
     * @param mode 发送模式 1 = 群聊、0 = 私聊
     * @param cmd  指令
     */
    public static void sendCmd(long id, Mode mode, String cmd) {
        if (cmd.equalsIgnoreCase("list")) {
            sendMsg(id, mode, listOnlinePlayer());
            return;
        }
        if (cmd.equalsIgnoreCase("tps")) {
            sendMsg(id, mode, tps());
            return;
        }
        CommandSender commandSender = new ConsoleSender();
        Bukkit.dispatchCommand(commandSender, cmd);
        Bukkit.getScheduler().runTaskLaterAsynchronously(QQConnect.INSTANCE, () -> {
            // 读取消息
            StringBuilder stringBuilder = new StringBuilder();
            if (msgList.size() == 0) {
                msgList.add("该命令为核心原版自带实现指令，无法获取对应返回值，请在后台插件是否执行成功。");
            }
            for (String msg : msgList) {
                if (msgList.get(msgList.size() - 1).equalsIgnoreCase(msg)) {
                    stringBuilder.append(msg.replaceAll("§\\S", ""));
                } else {
                    stringBuilder.append(msg.replaceAll("§\\S", "")).append("\n");
                }
            }
            // 发送消息
            sendMsg(id, mode, stringBuilder.toString());
            msgList.clear();
        }, 4L);
        // 执行命令
    }

    /**
     * 获取机器人帮助
     *
     * @param id   发送者id
     * @param mode 发送模式 1 = 群聊、0 = 私聊
     */
    public static void botHelp(long id, Mode mode) {
        List<String> messages = new LinkedList<>();
        StringBuilder stringBuilder = new StringBuilder();
        messages.add("机器人帮助:");
        messages.add("─────────────── ");
        messages.add("#玩家信息 [玩家名字] ");
        messages.add(" - 查询玩家基本信息[群内/私聊]");
        messages.add("");
        messages.add("#修改密码 [新密码]");
        messages.add(" - 修改游戏密码[私聊]");
        messages.add("");
        messages.add("#执行命令 [命令]");
        messages.add(" - 执行命令[群内/私聊]");
        messages.add("");
        messages.add("#转账 [对方QQ] [金额]");
        messages.add(" - 进行转账[群内/私聊]");
        messages.add("");
        messages.add("#余额");
        messages.add(" - 查余额[群内/私聊]");
        messages.add("───────────────");
        for (String message : messages) {
            if (messages.get(messages.size() - 1).equalsIgnoreCase(message)) {
                stringBuilder.append(message.replaceAll("§\\S", ""));
            } else {
                stringBuilder.append(message.replaceAll("§\\S", "")).append("\n");
            }
        }
        // 发送消息
        sendMsg(id, mode, stringBuilder.toString());
    }

    /**
     * 获取玩家信息
     *
     * @param id         发送者id
     * @param mode       发送模式 1 = 群聊、0 = 私聊
     * @param playerName 接受者玩家名字
     */
    public static void playerState(long id, Mode mode, String playerName) {
        List<String> formats = new LinkedList<>();
        StringBuilder stringBuilder = new StringBuilder();
        String msg;
        // 判断玩家是否在线
        if (Bukkit.getPlayer(playerName) == null) {
            formats.add("玩家 " + playerName + " 信息:");
            formats.add("─────────────── ");
            formats.add("状态: 离线");
            formats.add("───────────────");
            for (String format : formats) {
                if (formats.get(formats.size() - 1).equalsIgnoreCase(format)) {
                    stringBuilder.append(format);
                } else {
                    stringBuilder.append(format).append("\n");
                }
            }
            msg = stringBuilder.toString();
        } else {
            formats = ConfigManager.getFunFormat("PlayerState");
            if (formats.size() == 1) {
                msg = formats.get(0);
            } else {
                for (String format : formats) {
                    if (formats.get(formats.size() - 1).equalsIgnoreCase(format)) {
                        stringBuilder.append(format);
                    } else {
                        stringBuilder.append(format).append("\n");
                    }
                }
                msg = stringBuilder.toString();
            }
            msg = PapiReplace.papiReplace(Bukkit.getPlayer(playerName), msg);
        }
        // 发送消息
        sendMsg(id, mode, msg);
    }

    /**
     * 通过QQ号转账
     *
     * @param id        id
     * @param senderId  发送者id
     * @param mode      发送模式 1 = 群聊、0 = 私聊
     * @param reciverId 接受者玩家名字
     */
    public static void payMoney(long id, long senderId, Mode mode, long reciverId, double num) {
        Economy economy = QQConnect.economy;
        if (economy == null) {
            sendMsg(id, mode, ConfigManager.getMessage_QQ("NoVault"));
            return;
        }
        if (!ConfigManager.getBindUser().containsKey(senderId)) {
            sendMsg(id, mode, ConfigManager.getMessage_QQ("NoBind"));
            return;
        }
        if (!ConfigManager.getBindUser().containsKey(reciverId)) {
            sendMsg(id, mode, ConfigManager.getMessage_QQ("ReciverNoBind"));
            return;
        }
        String senderName = ConfigManager.getBindUser().get(senderId);
        String reciverName = ConfigManager.getBindUser().get(reciverId);
        if (economy.getBalance(senderName) < num) {
            sendMsg(id, mode, ConfigManager.getMessage_QQ("NoMoney"));
            return;
        }
        sendMsg(id, mode, ConfigManager.getMessage_QQ("PaySuccess"));
        economy.withdrawPlayer(senderName, num);
        economy.depositPlayer(reciverName, num);
    }

    /**
     * 查看自己余额
     *
     * @param id       id
     * @param senderId 发送者id
     * @param mode     发送模式 1 = 群聊、0 = 私聊
     */
    public static void lookMoney(long id, long senderId, Mode mode) {
        if (!ConfigManager.getBindUser().containsKey(senderId)) {
            sendMsg(id, mode, ConfigManager.getMessage_QQ("NoBind"));
            return;
        }
        Economy economy = QQConnect.economy;
        double money = economy.getBalance(ConfigManager.getBindUser().get(senderId));
        sendMsg(id, mode, ConfigManager.getMessage_QQ("LookMoney").replaceAll("%money%", String.valueOf(money)));
    }

    /**
     * 发送消息方法
     *
     * @param id   发送id
     * @param mode 发送模式
     * @param msg  发送内容
     */
    public static void sendMsg(long id, Mode mode, String msg) {
        // 发送消息
        if (mode == Mode.GROUP) {
            Bot.getMiraiBot().getGroup(id).sendMessage(msg);
        }
        if (mode == Mode.FRIEND) {
            Bot.getMiraiBot().getFriend(id).sendMessage(msg);
        }
    }

    /**
     * 修改玩家密码
     *
     * @param id       QQ号
     * @param mode     聊天模式
     * @param password 修改的密码
     */
    public static void changePassword(long id, Mode mode, String password) {
        if (!ConfigManager.getBindUser().containsKey(id)) {
            sendMsg(id, mode, ConfigManager.getMessage_QQ("NoBind"));
            return;
        }
        String playerName = ConfigManager.getBindUser().get(id);
        String command = ConfigManager.getChangePasswordCmd()
                .replace("%player%", playerName)
                .replace("%password%", password);
        Bukkit.getScheduler().scheduleSyncDelayedTask(QQConnect.INSTANCE, () ->
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command),
                0L);
        sendMsg(id, Mode.FRIEND, ConfigManager.getMessage_QQ("ChangeSuccess").replaceAll("%password%", password));
    }

    private static String listOnlinePlayer() {
        List<String> onlinePlayer = new LinkedList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            onlinePlayer.add(p.getName());
        }
        return "在线玩家 " + onlinePlayer.size() + "/" + Bukkit.getMaxPlayers() + ":" + "\n" +
                Arrays.toString(onlinePlayer.toArray()).replace("\\[|\\]", "");
    }

    private static String tps() {
        try {
            Field console = Bukkit.getServer().getClass().getDeclaredField("console");
            Field currentTPS = console.getType().getDeclaredField("currentTPS");
            return "当前TPS: " + Math.min(20.0D, Math.round(currentTPS.getDouble(Bukkit.getServer()) * 10.0D) / 10.0D);
        } catch (IllegalArgumentException | NoSuchFieldException | IllegalAccessException | SecurityException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void FindQQ(String playerName, Mode mode, long groupId) {
        long qq = QQConnectAPI.getPlayerQQ(playerName);
        if (qq != 0) {
            sendMsg(groupId, mode, String.valueOf(qq));
        } else {
            sendMsg(groupId, mode, "玩家未绑定QQ");
        }
    }

    public static void FindID(long QQid, Mode mode, long groupId) {
        String playerName = QQConnectAPI.getPlayerName(QQid);
        if (playerName != null) {
            sendMsg(groupId, mode, playerName);
        } else {
            sendMsg(groupId, mode, "QQ未绑定在任何ID上");
        }
    }
}
