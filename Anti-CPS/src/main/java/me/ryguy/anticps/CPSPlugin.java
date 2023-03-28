package me.ryguy.anticps;
import lombok.Getter;
import me.ryguy.anticps.listeners.*;
import me.ryguy.anticps.util.Stopwatch;
import me.ryguy.rycore.util.RyUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import me.ryguy.anticps.cmd.AntiCPSCommand;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class CPSPlugin extends JavaPlugin {
    private final HashMap<UUID, Stopwatch> leftClicks = new HashMap<>();
    private final HashMap<UUID, Stopwatch> rightClicks = new HashMap<>();
    private static CPSPlugin instance;

    public void onEnable() {
        instance = this;
        //Config
        if(getConfig().getConfigurationSection("Options") == null) {
            getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[XPMiner] Configuration Initializing!");
            initConfiguration();
        }
        //Events and Command
        new AntiCPSCommand().registerCommand();
        new LeftClickListeners().registerListener();
        new RightClickListeners().registerListener();
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Anti-CPS]: Events registered and plugin initialized!");
    }
    public void onDisable() {
        saveConfig();
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Anti-CPS]: Config Saved and Anti-CPS disabled!");
    }
    public void initConfiguration() {
        getConfig().createSection("Options");
        getConfig().getConfigurationSection("Options").addDefault("LeftCPSMax", 10);
        getConfig().getConfigurationSection("Options").addDefault("RightCPSMax", 10);
        getConfig().getConfigurationSection("Options").addDefault("ConsoleLogging", true);
        getConfig().getConfigurationSection("Options").addDefault("MultiThread", true);
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
    public void checkConfig() {
        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Anti-CPS] Checking your Configuration File!");
        for(String o : getConfig().getConfigurationSection("Options").getKeys(false)) {
            if(o.equalsIgnoreCase("ConsoleLogging")) return;
            Object val = getConfig().getConfigurationSection("Options").get(o);
            if(!RyUtils.isInteger(o)) {
                getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Anti-CPS] Option " + o + " was not set to an integer! Setting to a default number of 10!");
                getConfig().getConfigurationSection("Options").set(o, 10);
                saveConfig();
            }
            if(!(val instanceof Integer)) {
                getConfig().getConfigurationSection("Options").set(o, val);
                saveConfig();
            }
        }
    }

    public static synchronized CPSPlugin getInstance() {
        return instance;
    }
}
