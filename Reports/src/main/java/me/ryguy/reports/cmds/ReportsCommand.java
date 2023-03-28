package me.ryguy.reports.cmds;

import me.ryguy.reports.Permissions;
import me.ryguy.reports.ReportsPlugin;
import me.ryguy.rycore.command.Command;
import me.ryguy.rycore.util.RyUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportsCommand extends Command {

    public ReportsCommand() {
        super("reports");
    }

    @Override
    public boolean onCommand(CommandSender sender, String s, String[] args) {
        ReportsPlugin main = ReportsPlugin.getInstance();
        String header = ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "---" + ChatColor.RESET + ChatColor.BOLD + "" + ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + " Reports Help " + ChatColor.GRAY + "]" + ChatColor.STRIKETHROUGH + "---";
        if(args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (Permissions.RELOAD.hasPermission(sender)) {
                    try {
                        main.reloadConfig();
                        main.checkConfig();
                        sender.sendMessage(ChatColor.DARK_AQUA + "[Reports]" + ChatColor.GRAY + " Config Reloaded!");
                    } catch (Exception e) {
                        sender.sendMessage(ChatColor.DARK_AQUA + "[Reports] " + ChatColor.GRAY + "There was a(n) " + e.getClass().getSimpleName() + " in attempting to reload configuration!");
                        sender.sendMessage(ChatColor.DARK_AQUA + "[Reports] " + ChatColor.GRAY + e.getMessage());
                        sender.sendMessage(ChatColor.DARK_AQUA + "[Reports] " + ChatColor.GRAY + e.getLocalizedMessage());
                        sender.sendMessage(ChatColor.DARK_AQUA + "[Reports] " + ChatColor.GRAY + "Try to use a rich text editor to edit your configuration, and if that shows nothing, and you believe it is a bug, report it!");
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_AQUA + "[Reports]" + ChatColor.GRAY + " No Permission!");
                }
            } else if (args[0].equalsIgnoreCase("getvalue")) {
                if (Permissions.CONFIG.hasPermission(sender)) {
                    sender.sendMessage(ChatColor.DARK_AQUA + "[Reports] " + ChatColor.GRAY + "ChatReports currently hold the previous " + ChatColor.GOLD + main.getConfig().getConfigurationSection("Options").getString("MaxMSGs") + ChatColor.GRAY + " messages in one report!");
                }
            } else {
                sender.sendMessage(header);
                sender.sendMessage(ChatColor.DARK_AQUA + "/reports reload" + ChatColor.GRAY + " - Reload's Reports Config!");
                sender.sendMessage(ChatColor.DARK_AQUA + "/reports getvalue" + ChatColor.GRAY + " - Gets the amount of messages a ChatReport holds!");
                sender.sendMessage(ChatColor.DARK_AQUA + "/reports setvalue" + ChatColor.GRAY + " - Sets the amount of messages a ChatReport holds!");
                sender.sendMessage(ChatColor.DARK_AQUA + "/report [user] [reason]" + ChatColor.GRAY + " - Report a user!");
                sender.sendMessage(ChatColor.DARK_AQUA + "/chatreport [user] [reason]" + ChatColor.GRAY + " - Chatreport a user!");
            }
        }else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("setvalue")) {
                if (Permissions.CONFIG.hasPermission(sender)) {
                    if (RyUtils.isInteger(args[1])) {
                        main.getConfig().getConfigurationSection("Options").set("MaxMSGs", Integer.valueOf(args[1]));
                        main.saveConfig();
                        sender.sendMessage(ChatColor.DARK_AQUA + "[Reports] " + ChatColor.GRAY + "ChatReports now hold the previous " + ChatColor.GOLD + main.getConfig().getConfigurationSection("Options").getString("MaxMSGs") + ChatColor.GRAY + " messages!");
                    } else {
                        sender.sendMessage(ChatColor.DARK_AQUA + "[Reports] " + ChatColor.RED + "You need to set this value to an integer!");
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_AQUA + "[Reports] " + ChatColor.RED + "No Permission!");
                }
            }else {
                sender.sendMessage(header);
                sender.sendMessage(ChatColor.DARK_AQUA + "/reports reload" + ChatColor.GRAY + " - Reload's Reports Config!");
                sender.sendMessage(ChatColor.DARK_AQUA + "/reports getvalue" + ChatColor.GRAY + " - Gets the amount of messages a ChatReport holds!");
                sender.sendMessage(ChatColor.DARK_AQUA + "/reports setvalue" + ChatColor.GRAY + " - Sets the amount of messages a ChatReport holds!");
                sender.sendMessage(ChatColor.DARK_AQUA + "/report [user] [reason]" + ChatColor.GRAY + " - Report a user!");
                sender.sendMessage(ChatColor.DARK_AQUA + "/chatreport [user] [reason]" + ChatColor.GRAY + " - Chatreport a user!");
            }
        }else {
            sender.sendMessage(header);
            sender.sendMessage(ChatColor.DARK_AQUA + "/reports reload" + ChatColor.GRAY + " - Reload's Reports Config!");
            sender.sendMessage(ChatColor.DARK_AQUA + "/reports getvalue" + ChatColor.GRAY + " - Gets the amount of messages a ChatReport holds!");
            sender.sendMessage(ChatColor.DARK_AQUA + "/reports setvalue" + ChatColor.GRAY + " - Sets the amount of messages a ChatReport holds!");
            sender.sendMessage(ChatColor.DARK_AQUA + "/report [user] [reason]" + ChatColor.GRAY + " - Report a user!");
            sender.sendMessage(ChatColor.DARK_AQUA + "/chatreport [user] [reason]" + ChatColor.GRAY + " - Chatreport a user!");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String s, String[] args) {
        if (args.length == 1) {
            return Collections.singletonList("reload, getvalue, setvalue");
        }
        return new ArrayList<>();
    }
}
