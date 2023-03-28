package me.ryguy.reports.cmds;

import me.ryguy.reports.Permissions;
import me.ryguy.reports.ReportsPlugin;
import me.ryguy.rycore.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import me.ryguy.reports.util.FileMaker;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class ChatReportCommand extends Command {

    public ChatReportCommand() {
        super("chatreport");
        setDescription("Log a person's chat history when they break a rule in chat");
        setAliases("crp");
        setUsage("/chatreport [player] [optional reason]");
    }

    @Override
    public boolean onCommand(CommandSender sender, String s, String[] args) {
        ReportsPlugin main = ReportsPlugin.getInstance();
        String timestamp = new SimpleDateFormat("MM.dd.yyyy hh:mm:ss").format(new Date());

        if (!main.isChatReportsCancelled()) {
            if(args.length >= 1) {
                if(Permissions.CHAT_REPORT.hasPermission(sender)) {
                    if(Bukkit.getServer().getOnlinePlayers().contains(Bukkit.getServer().getPlayer(args[0]))) {
                        String reason = String.join(", ", Arrays.copyOfRange(args, 1, args.length));
                        if(main.getMsgs().size() != 0) {
                            FileMaker fm = new FileMaker(ReportsPlugin.getInstance().getChatReportsDirectory().getName(), "[" + timestamp + "] " + args[0] + " reported by " + sender.getName() + ".txt");
                            fm.create();
                            try {
                                FileWriter fw = new FileWriter(fm.getFolder() + File.separator + fm.getName());
                                fw.write("[-- Chat Report --] \n");
                                fw.write("Defendant: " + args[0] + "\n");
                                fw.write("Reported by: " + sender.getName() + "\n");
                                fw.write("Reason: " + reason + "\n");
                                fw.write("Reported on: " + new SimpleDateFormat("MM / dd / yyyy").format(new Date()) + new SimpleDateFormat("hh:mm:ss").format(new Date()) + "\n \n");
                                for(String msg : main.getMsgs()) {
                                    fw.write(msg);
                                }
                                fw.close();

                                sender.sendMessage(ChatColor.DARK_AQUA + "[Reports] " + ChatColor.GRAY + "Thank you for your report!");
                                if(main.getConfig().getConfigurationSection("Options").getBoolean("ConsoleLogging")) {
                                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Report] New Chat Report from " + ChatColor.GOLD + sender.getName());
                                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Report] " + ChatColor.GOLD + args[0] + ChatColor.AQUA + " was chat reported for " + ChatColor.GOLD + reason);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else {
                            sender.sendMessage(ChatColor.DARK_AQUA + "[Reports] " + ChatColor.RED + "Error! No messages have been sent on the server yet!");
                        }
                    }else {
                        sender.sendMessage(ChatColor.DARK_AQUA + "[Report] " + ChatColor.GRAY + "Error: Player " + ChatColor.GOLD + args[0] + ChatColor.GRAY + " is not online!");
                    }
                }else {
                    sender.sendMessage(ChatColor.DARK_AQUA + "[Reports] " + ChatColor.RED + "Error! No permissions!");
                }
            }else {
                sender.sendMessage(ChatColor.DARK_AQUA + "[Reports] " + ChatColor.GRAY + "Usage: /chatreport [player] [optional reason]");
            }
        }else {
            sender.sendMessage(ChatColor.DARK_AQUA + "[Reports] " + ChatColor.RED + "Chat Reports have been disabled! Check your server logs!");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String s, String[] args) {
        if(args.length == 1) {
            return super.onTabComplete(sender, s, args);
        }
        return new ArrayList<>();
    }
}
