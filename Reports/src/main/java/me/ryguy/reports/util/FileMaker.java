package me.ryguy.reports.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;

public class FileMaker {
    String name;
    String folder;

    public FileMaker(String f, String n) {
        this.name = n;
        this.folder = f;
    }
    public void create() {
        try {
            File file = new File(folder, name);
            file.createNewFile();
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Reports] Error Creating ChatReport File!");
            e.printStackTrace();
        }
    }
    public String getName() {
        return this.name;
    }
    public String getFolder() {
        return this.folder;
    }
}
