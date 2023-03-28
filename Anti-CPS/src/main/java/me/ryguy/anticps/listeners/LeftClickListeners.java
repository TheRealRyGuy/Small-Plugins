package me.ryguy.anticps.listeners;

import me.ryguy.anticps.Permissions;
import me.ryguy.anticps.events.PlayerLeftClickEvent;
import me.ryguy.anticps.util.Stopwatch;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.UUID;

public class LeftClickListeners implements CPSListener {

    //Handling Clicks
    @EventHandler
    public void LeftClick(PlayerLeftClickEvent e) {
        HashMap<UUID, Stopwatch> map = plugin().getLeftClicks();
        UUID uuid = e.getPlayer().getUniqueId();
        Stopwatch sw = new Stopwatch();
        double cpsMax = plugin().getConfig().getConfigurationSection("Options").getDouble("LeftCPSMax");
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
                        p.sendMessage(ChatColor.DARK_AQUA + "[Anti-CPS] " + ChatColor.GRAY + "Player " + ChatColor.GOLD + e.getPlayer().getName() + ChatColor.GRAY + " is left-clicking at " + Math.round(1/seconds) + " CPS!");
                    }
                }
                if(plugin().getConfig().getConfigurationSection("Options").getBoolean("ConsoleLogging")) {
                    plugin().getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Anti-CPS] Player "  + ChatColor.GOLD + e.getPlayer().getName() + ChatColor.AQUA + " is left-clicking at " + Math.round(1/seconds) + " CPS!");
                }
            }
            map.remove(uuid);
        }else {
            sw.start();
            map.put(uuid, sw);
        }
    }

    //Computing Clicks

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            new PlayerLeftClickEvent((Player) e.getDamager()).callEvent();
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            new PlayerLeftClickEvent(e.getPlayer()).callEvent();
        }
    }
}
