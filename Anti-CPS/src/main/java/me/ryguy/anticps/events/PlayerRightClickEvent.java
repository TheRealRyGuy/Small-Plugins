package me.ryguy.anticps.events;

import lombok.Getter;
import me.ryguy.rycore.event.Event;
import org.bukkit.entity.Player;

@Getter
public class PlayerRightClickEvent extends Event {
    private final Player player;

    public PlayerRightClickEvent(Player player) {
        super(true);
        this.player = player;
    }
}
