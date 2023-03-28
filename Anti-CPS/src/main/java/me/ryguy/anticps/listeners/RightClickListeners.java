package me.ryguy.anticps.listeners;

import me.ryguy.anticps.CPSPlugin;
import me.ryguy.anticps.Permissions;
import me.ryguy.anticps.events.PlayerRightClickEvent;
import me.ryguy.anticps.util.Stopwatch;
import me.ryguy.rycore.scheduler.TaskBuilder;
import me.ryguy.rycore.scheduler.TaskType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.UUID;

public class RightClickListeners implements CPSListener {

    //Handling Clicks
    @EventHandler
    public void RightClick(PlayerRightClickEvent e) {
        new TaskBuilder(CPSPlugin.getInstance()).type(TaskType.ASYNC).name("Right Click Handler").run(() -> {
            HashMap<UUID, Stopwatch> map = plugin().getRightClicks();
            UUID uuid = e.getPlayer().getUniqueId();
            Stopwatch sw = new Stopwatch();
            double cpsMax = plugin().getConfig().getConfigurationSection("Options").getDouble("RightCPSMax");
            if(map.get(uuid) != null) {
                Stopwatch resultant = map.get(uuid);
                resultant.stop();
                if(resultant.getTime() > 1000000000) {
                    map.remove(uuid);
                    return;
                }
                double seconds = (double) resultant.getTime() / 1_000_000_000.0;
                if(seconds < (1/cpsMax)) {
                    for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                        if(Permissions.CHECK.hasPermission(p)) {
                            p.sendMessage(ChatColor.DARK_AQUA + "[Anti-CPS] " + ChatColor.GRAY + "Player " + ChatColor.GOLD + e.getPlayer().getName() + ChatColor.GRAY + " is right-clicking at " + Math.round(1/seconds) + " CPS!");
                        }
                    }
                    if(plugin().getConfig().getConfigurationSection("Options").getBoolean("ConsoleLogging")) {
                        plugin().getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Anti-CPS] Player "  + ChatColor.GOLD + e.getPlayer().getName() + ChatColor.AQUA + " is right-clicking at " + Math.round(1/seconds) + " CPS!");
                    }
                }
                map.remove(uuid);
            }else {
                sw.start();
                map.put(uuid, sw);
            }
        });
    }

    //Computing Clicks
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractAtEntityEvent e) {
        new PlayerRightClickEvent(e.getPlayer()).callEvent();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            new PlayerRightClickEvent(e.getPlayer());
        }
    }
}
