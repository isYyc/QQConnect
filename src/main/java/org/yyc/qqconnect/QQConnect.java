package org.yyc.qqconnect;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.yyc.qqconnect.command.Commands;
import org.yyc.qqconnect.config.Config;
import org.yyc.qqconnect.config.ConfigManager;
import org.yyc.qqconnect.event.qq.FriendMessageReceiveEvent;
import org.yyc.qqconnect.event.qq.GroupMemberJoinEvent;
import org.yyc.qqconnect.event.qq.GroupMemberQuitEvent;
import org.yyc.qqconnect.event.qq.GroupMessageReceiveEvent;
import org.yyc.qqconnect.event.server.ChatEvent;
import org.yyc.qqconnect.event.server.JoinEvent;
import org.yyc.qqconnect.event.server.QuitEvent;
import org.yyc.qqconnect.event.server.ServerEvent;
import org.yyc.qqconnect.papi.PAPIHolder;
import org.yyc.qqconnect.bot.Bot;
import org.yyc.qqconnect.bot.TimeTask;

public class QQConnect extends JavaPlugin implements Listener {
    public static QQConnect INSTANCE;

    public static Economy economy = null;

    @Override
    public void onEnable() {
        INSTANCE = this;
        INSTANCE.getLogger().info("────────────────────────");
        INSTANCE.getLogger().info("插件 QQConnect 已完成加载");
        INSTANCE.getLogger().info("作者: 郁冬Yyc");
        INSTANCE.getLogger().info("────────────────────────");
        // 加载配置
        Config.createConfig();
        // 初始化机器人
        Bot.initBot();
        // 初始化缓存数据
        ConfigManager.initData();
        // 输出日志
        Bukkit.getPluginManager().registerEvents(new ChatEvent(), this);
        INSTANCE.getLogger().info("Server > 监听器 ChatEvent 注册完毕");
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
        INSTANCE.getLogger().info("Server > 监听器 JoinEvent 注册完毕");
        Bukkit.getPluginManager().registerEvents(new QuitEvent(), this);
        INSTANCE.getLogger().info("Server > 监听器 QuitEvent 注册完毕");
        Bukkit.getPluginManager().registerEvents(new ServerEvent(), this);
        INSTANCE.getLogger().info("Server > 监听器 ServerEvent 注册完毕");
        Bukkit.getServer().getPluginCommand("qqconnect").setExecutor(new Commands());
        INSTANCE.getLogger().info("Server > 命令注册完毕");
        TimeTask.Announce();
        INSTANCE.getLogger().info("Server > 公告定时器注册完毕");
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            setupEconomy();
        }
        Bukkit.getPluginManager().registerEvents(new FriendMessageReceiveEvent(), this);
        INSTANCE.getLogger().info("QQ > 监听器 FriendMessageReceiveEvent 注册完毕");
        Bukkit.getPluginManager().registerEvents(new GroupMessageReceiveEvent(), this);
        INSTANCE.getLogger().info("QQ > 监听器 GroupMessageReceiveEvent 注册完毕");
        Bukkit.getPluginManager().registerEvents(new GroupMemberJoinEvent(), this);
        INSTANCE.getLogger().info("QQ > 监听器 GroupMemberJoinEvent 注册完毕");
        Bukkit.getPluginManager().registerEvents(new GroupMemberQuitEvent(), this);
        INSTANCE.getLogger().info("QQ > 监听器 GroupMemberQuitEvent 注册完毕");
        INSTANCE.getLogger().info("QQ > 命令注册完毕");
        // Papi变量注册
        new PAPIHolder(this).register();
        INSTANCE.getLogger().info("────────────────────────");
    }

    @Override
    public void onDisable() {
        INSTANCE.getLogger().info("────────────────────────");
        INSTANCE.getLogger().info("插件 QQConnect 已完成卸载");
        INSTANCE.getLogger().info("作者: 郁冬Yyc");
        INSTANCE.getLogger().info("────────────────────────");
    }

    /**
     * 设置eco经济系统
     */
    private void setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
                .getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
            INSTANCE.getLogger().info("Server > 成功获取到 Vault 插件。");
        } else {
            INSTANCE.getLogger().info("Server > 未获取到 Vault插件。经济相关功能可能无法使用。");
        }
    }
}
