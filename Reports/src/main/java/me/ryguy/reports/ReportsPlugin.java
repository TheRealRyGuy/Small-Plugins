package me.ryguy.reports;

import lombok.Getter;
import me.ryguy.reports.cmds.*;
import me.ryguy.rycore.event.Listener;
import me.ryguy.rycore.util.FixedSizeArrayList;
import me.ryguy.rycore.util.RyUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class ReportsPlugin extends JavaPlugin implements Listener {

    private static ReportsPlugin instance;

    private boolean chatReportsCancelled = false;
    private FixedSizeArrayList<String> msgs;
    private File chatReportsDirectory;

    public static synchronized ReportsPlugin getInstance() {
        return instance;
    }

    public void onEnable() {
        new ReportCommand().registerCommand();
        new ReportsCommand().registerCommand();
        new ChatReportCommand().registerCommand();

        registerListener();

        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[Reports] Events and Commands initialized!");

        instance = this;

        if(getConfig().getConfigurationSection("Options") == null) {
            initConfig();
        }

        msgs = new FixedSizeArrayList<>(getConfig().getConfigurationSection("Options").getInt("MaxMSGS"));
    }

    private void initConfig() {
        getConfig().createSection("Options");
        getConfig().getConfigurationSection("Options").addDefault("ConsoleLogging", true);
        getConfig().getConfigurationSection("Options").addDefault("MaxMSGs", 20);

        getConfig().options().copyDefaults(true);
        saveConfig();
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Reports] Config Created!");

        chatReportsDirectory = new File(this.getDataFolder() + File.separator + "ChatReports");
        if(chatReportsDirectory.mkdir()) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[Reports] Chat Reports directory created!");
        }else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Reports] Error: Could not create ChatReports directory! Disabling Chat Report functionality, please investigate!");
            chatReportsCancelled = true;
        }
    }

    public void checkConfig() {
        if(!RyUtils.isInteger(getConfig().getConfigurationSection("Options").getString("MaxMSGS"))) {
            getConfig().getConfigurationSection("Options").set("MaxMSGs", Integer.valueOf(getConfig().getConfigurationSection("Options").getString("MaxMSGS")));
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[Reports] Config Checked!");
        }
    }

    @Override
    public ReportsPlugin plugin() {
        return instance;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        ReportsPlugin plugin = ReportsPlugin.getInstance();
        String timestamp = new SimpleDateFormat("hh:mm:ss").format(new Date());
        plugin.getMsgs().add(0, "[" + timestamp + "] " + e.getPlayer().getName() + ": " + e.getMessage() + "\n");
    }
}
