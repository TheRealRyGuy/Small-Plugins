package me.ryguy.anticps.listeners;

import me.ryguy.anticps.CPSPlugin;
import me.ryguy.rycore.event.Listener;

public interface CPSListener extends Listener {
    @Override
    default CPSPlugin plugin() {
        return CPSPlugin.getInstance();
    }
}
