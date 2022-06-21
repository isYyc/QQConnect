package org.yycstudio.qqconnect.event.qq;

import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.yycstudio.qqconnect.bot.Bot;
import org.yycstudio.qqconnect.config.ConfigManager;
import org.yycstudio.qqconnect.enumClass.Mode;
import org.yycstudio.qqconnect.util.VarReplace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupMessageReceiveEvent implements Listener {

    @EventHandler
    public void onGroupMessageReceive(MiraiGroupMessageEvent event) {
        long senderID = event.getSenderID();
        long groupID = event.getGroupID();
        String message = event.getMessageContent();

        if (!ConfigManager.getGroups().contains(event.getGroupID())) return;

        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("#帮助");
        matcher = pattern.matcher(message);
        if (matcher.find()) {
            BotCommands.botHelp(groupID, Mode.GROUP);
            return;
        }

        // 发送命令
        if (ConfigManager.getFunEnable("SendCmd")) {
            pattern = Pattern.compile("#执行命令 .*");
            matcher = pattern.matcher(message);
            if (matcher.find()) {
                String cmd = matcher.group().replace("#执行命令 ", "");
                if (!ConfigManager.getAllowCmd().contains(cmd) && !ConfigManager.getAdmins().contains(senderID)) {
                    Bot.getMiraiBot().getGroup(groupID).sendMessage(ConfigManager.getMessage_QQ("NoPerm"));
                    return;
                }
                BotCommands.sendCmd(groupID, Mode.GROUP, cmd);
                return;
            }
        }

        // 玩家信息
        if (ConfigManager.getFunEnable("PlayerState")) {
            pattern = Pattern.compile("#玩家信息 .*");
            matcher = pattern.matcher(message);
            if (matcher.find()) {
                String playerName = matcher.group().replace("#玩家信息 ", "");
                BotCommands.playerState(groupID, Mode.GROUP, playerName);
                return;
            }
        }

        // QQ查询
        pattern = Pattern.compile("#玩家ID [0-9]*");
        matcher = pattern.matcher(message);
        if (matcher.find()) {
            long id = Long.parseLong(matcher.group().replace("#玩家ID ", ""));
            BotCommands.FindID(id, Mode.GROUP, groupID);
            return;
        }

        pattern = Pattern.compile("#玩家QQ .*");
        matcher = pattern.matcher(message);
        if (matcher.find()) {
            String playerName = matcher.group().replace("#玩家QQ ", "");
            BotCommands.FindQQ(playerName, Mode.GROUP, groupID);
            return;
        }

        // 转账
        if (ConfigManager.getFunEnable("PayMoney")) {
            pattern = Pattern.compile("#转账 [0-9]* [0-9]*");
            matcher = pattern.matcher(message);
            if (matcher.find()) {
                String str = matcher.group().replace("#转账 ", "");
                try {
                    if (str.split(" ").length != 2) {
                        Bot.getMiraiBot().getGroup(groupID).sendMessage(ConfigManager.getMessage_QQ("BotCmdError"));
                    }
                    long reciverId = Long.parseLong(str.split(" ")[0]);
                    double money = Double.parseDouble(str.split(" ")[1]);
                    BotCommands.payMoney(groupID, senderID, Mode.GROUP, reciverId, money);
                } catch (Exception exception) {
                    Bot.getMiraiBot().getGroup(groupID).sendMessage(ConfigManager.getMessage_QQ("BotCmdError"));
                }
                return;
            }
        }

        // 余额
        if (ConfigManager.getFunEnable("LookMoney")) {
            pattern = Pattern.compile("#余额");
            matcher = pattern.matcher(message);
            if (matcher.find()) {
                BotCommands.lookMoney(groupID, senderID, Mode.GROUP);
                return;
            }
        }

        for (String word : ConfigManager.getQQToServerWordFilter()) {
            if (message.contains(word)) return;
        }

        if (ConfigManager.getFunEnable("QQToServer")) {
            // 消息转发
            Bukkit.getServer()
                    .broadcastMessage(VarReplace.varReplace(event, ConfigManager.getQQToServerFormat())
                            .replaceAll("%message%", message));
        }
    }
}
