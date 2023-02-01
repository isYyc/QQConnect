package org.yyc.qqconnect.event.qq;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public class ConsoleSender implements ConsoleCommandSender {

    private final Server server = Bukkit.getServer();

    public Server getServer() {
        return this.server;
    }

    public String getName() {
        return "CONSOLE";
    }

    public void sendMessage(String message) {
        BotCommands.msgList.add(message);
    }

    public void sendMessage(String[] messages) {
        for (String msg : messages)
            sendMessage(msg);
    }

    public boolean isPermissionSet(String s) {
        return false;
    }

    public boolean isPermissionSet(Permission permission) {
        return false;
    }

    public boolean hasPermission(String s) {
        return true;
    }

    public boolean hasPermission(Permission permission) {
        return true;
    }

    public boolean isOp() {
        return true;
    }

    public void setOp(boolean b) {
        throw new UnsupportedOperationException();
    }

    public CommandSender.Spigot spigot() {
        throw new UnsupportedOperationException();
    }

    public boolean isConversing() {
        throw new UnsupportedOperationException();
    }

    public void acceptConversationInput(String s) {
    }

    public boolean beginConversation(Conversation conversation) {
        throw new UnsupportedOperationException();
    }

    public void abandonConversation(Conversation conversation) {
        throw new UnsupportedOperationException();
    }

    public void abandonConversation(Conversation conversation, ConversationAbandonedEvent conversationAbandonedEvent) {
        throw new UnsupportedOperationException();
    }

    public void sendRawMessage(String s) {
    }

    public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b) {
        BotCommands.msgList.add(s);
        throw new UnsupportedOperationException();
    }

    public PermissionAttachment addAttachment(Plugin plugin) {
        throw new UnsupportedOperationException();
    }

    public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b, int i) {
        throw new UnsupportedOperationException();
    }

    public PermissionAttachment addAttachment(Plugin plugin, int i) {
        throw new UnsupportedOperationException();
    }

    public void removeAttachment(PermissionAttachment permissionAttachment) {
        throw new UnsupportedOperationException();
    }

    public void recalculatePermissions() {
        throw new UnsupportedOperationException();
    }

    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        throw new UnsupportedOperationException();
    }
}
