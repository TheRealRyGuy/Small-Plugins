package me.ryguy.reports;

import lombok.AllArgsConstructor;
import org.bukkit.command.CommandSender;

@AllArgsConstructor
public enum Permissions {
    REPORT("reports.report"),
    CHAT_REPORT("reports.chatreport"),
    RELOAD("reports.reload"),
    READ("reports.read"),
    CONFIG("reports.config");

    private String permission;
    private static final String OP_PERMISSION = "reports.op";

    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(permission) || sender.hasPermission(OP_PERMISSION);
    }
}
