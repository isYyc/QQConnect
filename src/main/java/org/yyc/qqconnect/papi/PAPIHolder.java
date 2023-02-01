package org.yyc.qqconnect.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.yyc.qqconnect.QQConnect;
import org.yyc.qqconnect.api.QQConnectAPI;
import org.yyc.qqconnect.config.ConfigManager;

public class PAPIHolder extends PlaceholderExpansion {

    private final QQConnect INSTANCE;

    public PAPIHolder(QQConnect INSTANCE) {
        this.INSTANCE = INSTANCE;
    }

    @Override
    public String onPlaceholderRequest(Player p, String s) {
        switch (s) {
            case "qq":
                if (!ConfigManager.getBindUser().containsValue(p.getName())) return "未绑定";
                else return String.valueOf(QQConnectAPI.getPlayerQQ(p.getName()));
        }
        return null;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "qqconnect";
    }

    @Override
    public @NotNull String getAuthor() {
        return INSTANCE.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return INSTANCE.getDescription().getVersion();
    }
}
