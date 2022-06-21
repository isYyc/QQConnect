package org.yycstudio.qqconnect.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.yycstudio.qqconnect.bot.Bot;
import org.yycstudio.qqconnect.config.Config;
import org.yycstudio.qqconnect.config.ConfigManager;
import org.yycstudio.qqconnect.config.PlayerData;

public class Commands implements CommandExecutor {

    // 绑定QQ
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§e§lQQ§6§lConnect §7§l- §e§lQQC");
            sender.sendMessage("§f§l───────────────────────");
            sender.sendMessage("§e§l/§6qqc bind §7[QQ] §e—— §7绑定/修改QQ");
            sender.sendMessage(" ");
            sender.sendMessage("§e§l/§6qqc reload §e—— §7重载插件");
            sender.sendMessage(" ");
            sender.sendMessage("§e§l/§6qqc help §e—— §7获取插件帮助");
            sender.sendMessage("§f§l───────────────────────");
            return true;
        }
        // help
        switch (args[0]) {
            case "bind":
                if (args.length != 2) return true;
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ConfigManager.getMessage_Server("ArgsError"));
                    return true;
                }
                Player player = (Player) sender;
                try {
                    long qq = Long.parseLong(args[1]);
                    if (ConfigManager.getBindUser().containsKey(qq)) {
                        player.sendMessage(ConfigManager.getMessage_Server("QQReapt"));
                        return true;
                    }
                    player.sendMessage(ConfigManager.getMessage_Server("ChangeBind")
                            .replaceAll("%qq%", String.valueOf(qq)));
                    for (long group : ConfigManager.getGroups()) {
                        Bot.getMiraiBot()
                                .getGroup(group)
                                .sendMessage(ConfigManager.getMessage_QQ("ChangeBind")
                                        .replaceAll("%qq%", String.valueOf(qq))
                                        .replaceAll("%player%", player.getName()));
                    }
                    PlayerData.changeBind(player.getName(), qq);
                    return true;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    player.sendMessage(ConfigManager.getMessage_Server("ArgsError"));
                }
                break;
            case "reload":
                if (args.length != 1) return true;
                Config.reloadConfig();
                sender.sendMessage(ConfigManager.getMessage_Server("Reload"));
                break;
            case "help":
                if (args.length != 1) return true;
                sender.sendMessage("§e§lQQ§6§lConnect §7§l- §e§lQQC");
                sender.sendMessage("§f§l───────────────────────");
                sender.sendMessage("§e§l/§6qqc bind §7[QQ] §e—— §7绑定/修改QQ");
                sender.sendMessage(" ");
                sender.sendMessage("§e§l/§6qqc reload §e—— §7重载插件");
                sender.sendMessage(" ");
                sender.sendMessage("§e§l/§6qqc help §e—— §7获取插件帮助");
                sender.sendMessage("§f§l───────────────────────");
                break;
            default:
                break;
        }
        return true;
    }
}
