package me.ryguy.anticps;

import lombok.AllArgsConstructor;
import org.bukkit.command.CommandSender;

@AllArgsConstructor
public enum Permissions {
    CHECK("anticps.check"),
    CONFIG("anticps.config");

    private static final String OP_PERMISSION = "anticps.op";

    private String permission;

    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(permission) || sender.hasPermission(OP_PERMISSION);
    }
}
