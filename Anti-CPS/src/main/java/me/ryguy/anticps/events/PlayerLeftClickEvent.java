package me.ryguy.anticps.events;

import lombok.Getter;
import me.ryguy.rycore.event.Event;
import org.bukkit.entity.Player;

@Getter
public class PlayerLeftClickEvent extends Event {

    private final Player player;

    public PlayerLeftClickEvent(Player player) {
        super(true);
        this.player = player;
    }
}
