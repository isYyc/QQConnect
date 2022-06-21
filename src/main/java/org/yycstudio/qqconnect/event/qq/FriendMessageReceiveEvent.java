package org.yycstudio.qqconnect.event.qq;

import me.dreamvoid.miraimc.bukkit.event.MiraiFriendMessageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.yycstudio.qqconnect.bot.Bot;
import org.yycstudio.qqconnect.config.ConfigManager;
import org.yycstudio.qqconnect.enumClass.Mode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FriendMessageReceiveEvent implements Listener {

    @EventHandler
    public void onFriendMessageReceive(MiraiFriendMessageEvent event) {
        long senderID = event.getSenderID();
        String message = event.getMessageContent();

        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("#帮助");
        matcher = pattern.matcher(message);
        if (matcher.find()) {
            BotCommands.botHelp(senderID, Mode.FRIEND);
            return;
        }

        if (ConfigManager.getFunEnable("SendCmd")) {
            pattern = Pattern.compile("#执行命令 .*");
            matcher = pattern.matcher(message);
            if (matcher.find()) {
                String cmd = matcher.group().replace("#执行命令 ", "");
                if (!ConfigManager.getAllowCmd().contains(cmd) && !ConfigManager.getAdmins().contains(senderID)) {
                    Bot.getMiraiBot().getFriend(senderID).sendMessage(ConfigManager.getMessage_QQ("NoPerm"));
                    return;
                }
                BotCommands.sendCmd(senderID, Mode.FRIEND, cmd);
                return;
            }
        }

        if (ConfigManager.getFunEnable("PlayerState")) {
            pattern = Pattern.compile("#玩家信息 .*");
            matcher = pattern.matcher(message);
            if (matcher.find()) {
                String playerName = matcher.group().replace("#玩家信息 ", "");
                BotCommands.playerState(senderID, Mode.FRIEND, playerName);
                return;
            }
        }

        if (ConfigManager.getFunEnable("PayMoney")) {
            pattern = Pattern.compile("#转账 [0-9]* [0-9]*");
            matcher = pattern.matcher(message);
            if (matcher.find()) {
                String str = matcher.group().replace("#转账 ", "");
                try {
                    if (str.split(" ").length != 2) {
                        Bot.getMiraiBot().getFriend(senderID).sendMessage(ConfigManager.getMessage_QQ("BotCmdError"));
                    }
                    long reciverId = Long.parseLong(str.split(" ")[0]);
                    double money = Double.parseDouble(str.split(" ")[1]);
                    BotCommands.payMoney(senderID, senderID, Mode.FRIEND, reciverId, money);
                } catch (Exception exception) {
                    Bot.getMiraiBot().getFriend(senderID).sendMessage(ConfigManager.getMessage_QQ("BotCmdError"));
                }
                return;
            }
        }

        // QQ查询
        pattern = Pattern.compile("#玩家ID [0-9]*");
        matcher = pattern.matcher(message);
        if (matcher.find()) {
            long id = Long.parseLong(matcher.group().replace("#玩家ID ", ""));
            BotCommands.FindID(id, Mode.FRIEND, senderID);
            return;
        }

        pattern = Pattern.compile("#玩家QQ .*");
        matcher = pattern.matcher(message);
        if (matcher.find()) {
            String playerName = matcher.group().replace("#玩家QQ ", "");
            BotCommands.FindQQ(playerName, Mode.FRIEND, senderID);
            return;
        }

        if (ConfigManager.getFunEnable("LookMoney")) {
            pattern = Pattern.compile("#余额");
            matcher = pattern.matcher(message);
            if (matcher.find()) {
                BotCommands.lookMoney(senderID, senderID, Mode.FRIEND);
                return;
            }
        }


        if (ConfigManager.getFunEnable("ChangePassword")) {
            pattern = Pattern.compile("#修改密码 .*");
            matcher = pattern.matcher(message);
            if (matcher.find()) {
                String password = matcher.group().replaceAll("#修改密码 ", "");
                pattern = Pattern.compile(ConfigManager.getPasswordRegex());
                matcher = pattern.matcher(password);
                if (matcher.find() && password.length() >= ConfigManager.getPasswordLength()[0] && password.length() <= ConfigManager
                        .getPasswordLength()[1]) {
                    if (password.replace(matcher.group(), "").length() > 0) {
                        Bot.getMiraiBot().getFriend(senderID).sendMessage(ConfigManager.getMessage_QQ("PasswordError"));
                        return;
                    }
                    BotCommands.changePassword(senderID, Mode.FRIEND, password);
                } else {
                    Bot.getMiraiBot().getFriend(senderID).sendMessage(ConfigManager.getMessage_QQ("PasswordError"));
                }
            }
        }
    }
}
