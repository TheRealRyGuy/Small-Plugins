package me.ryguy.reports;

import lombok.Getter;
import me.ryguy.rycore.util.ConfigHelper;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

public class ReportsConfig implements ConfigHelper {
    @Getter
    private boolean consoleLogging;
    @Getter
    private int maxMessages;

    @Override
    public boolean isInitialized() {
        return getConfig().getConfigurationSection("Options") != null;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public Plugin getPlugin() {
        return ReportsPlugin.getInstance();
    }

    @Override
    public void checkConfig() {

    }

    @Override
    public void initialize() {
        ConfigurationSection sec = getConfig().createSection("Options");

        sec.addDefault("ConsoleLogging", true);
        sec.addDefault("MaxMessages", 20);
    }
}
