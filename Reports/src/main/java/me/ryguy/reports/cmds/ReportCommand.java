package me.ryguy.reports.cmds;

import me.ryguy.reports.Permissions;
import me.ryguy.reports.ReportsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import me.ryguy.rycore.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ReportCommand extends Command {

    public ReportCommand() {
        super("report");
    }

    @Override
    public boolean onCommand(CommandSender sender, String s, String[] args) {
        ReportsPlugin main = ReportsPlugin.getInstance();
        if(args.length >= 1) {
            if(Permissions.REPORT.hasPermission(sender)) {
                if(Bukkit.getServer().getOnlinePlayers().contains(Bukkit.getServer().getPlayer(args[0]))) {
                    String reason = String.join(", ", Arrays.copyOfRange(args, 1, args.length));
                    for(Player p : Bukkit.getOnlinePlayers()) {
                        if(Permissions.READ.hasPermission(sender)) {
                            p.sendMessage(ChatColor.DARK_AQUA + "[Report] " + ChatColor.GRAY + "New Report from " + ChatColor.GOLD + sender.getName());
                            p.sendMessage(ChatColor.DARK_AQUA + "[Report] " + ChatColor.GOLD + args[0] + ChatColor.GRAY + " was reported for " + ChatColor.GOLD + reason);
                        }
                    }
                    if(main.getConfig().getConfigurationSection("Options").getBoolean("ConsoleLogging")) {
                        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Report] New Report from " + ChatColor.GOLD + sender.getName());
                        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Report] " + ChatColor.GOLD + args[0] + ChatColor.AQUA + " was reported for " + ChatColor.GOLD + reason);
                    }
                    sender.sendMessage(ChatColor.DARK_AQUA + "[Report] " + ChatColor.GRAY + "Thank you so much for your report!");
                }else {
                    sender.sendMessage(ChatColor.DARK_AQUA + "[Report] " + ChatColor.GRAY + "Error: Player " + ChatColor.GOLD + args[0] + ChatColor.GRAY + " is not online!");
                }
            }else {
                sender.sendMessage(ChatColor.DARK_AQUA + "[Report] " + ChatColor.RED + "No permission!");
            }
        }else {
            sender.sendMessage(ChatColor.DARK_AQUA + "[Report] " + ChatColor.GRAY + "Usage: /report [player] [Optional Reason]");
        }
        return true;
    }
}
