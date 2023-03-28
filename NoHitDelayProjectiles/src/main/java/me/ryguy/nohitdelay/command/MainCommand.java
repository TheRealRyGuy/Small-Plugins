package me.ryguy.nohitdelay.command;

import me.ryguy.nohitdelay.NoHitDelay;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /nohitdelay [ticks]");
            sender.sendMessage(ChatColor.RED + "HitDelay currently set to " + NoHitDelay.getInstance().getHitDelay() + " ticks!");
            //im too lazy to properly test things
            sender.sendMessage(ChatColor.GOLD + System.getenv("COLORTERM"));
            return false;
        } else {
            int val;
            try {
                val = Integer.valueOf(args[0]);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "You need to use an actual integer!");
                return false;
            }
            NoHitDelay.getInstance().setHitDelay(val);
            sender.sendMessage(ChatColor.GREEN + "Hit delay set to " + val + "!");
            return true;
        }
    }
}
