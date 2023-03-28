package me.ryguy.nohitdelay;

import me.ryguy.nohitdelay.command.MainCommand;
import me.ryguy.nohitdelay.listener.ProjectileHitDelayListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class NoHitDelay extends JavaPlugin {

    private static NoHitDelay instance;
    protected int HIT_DELAY = 1;

    public NoHitDelay(int delay) {
        HIT_DELAY = delay;
        System.out.println("ENABLED! " + delay);
    }

    public static NoHitDelay getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Enabling RyGuy's NoHitDelay!");
        Bukkit.getPluginManager().registerEvents(new ProjectileHitDelayListener(), this);
        getCommand("nohitdelay").setExecutor(new MainCommand());
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Registered Command and Listener!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public int getHitDelay() {
        return HIT_DELAY;
    }

    public void setHitDelay(int i) {
        HIT_DELAY = i;
    }
}
