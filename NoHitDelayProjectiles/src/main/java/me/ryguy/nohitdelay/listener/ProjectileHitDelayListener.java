package me.ryguy.nohitdelay.listener;

import me.ryguy.nohitdelay.NoHitDelay;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class ProjectileHitDelayListener implements Listener {
    @EventHandler
    public void onProjectileHit(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof LivingEntity))
            return;
        LivingEntity le = (LivingEntity) e.getEntity();
        if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            Projectile proj = (Projectile) e.getDamager();
            if (proj.getShooter() instanceof Player)
                le.setMaximumNoDamageTicks(NoHitDelay.getInstance().getHitDelay());
        } else {
            le.setMaximumNoDamageTicks(20);
        }
    }
}
