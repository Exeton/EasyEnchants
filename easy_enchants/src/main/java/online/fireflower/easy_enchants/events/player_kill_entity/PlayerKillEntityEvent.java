package online.fireflower.easy_enchants.events.player_kill_entity;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerEvent;

public class PlayerKillEntityEvent extends PlayerEvent {

    public EntityDeathEvent event;
    public PlayerKillEntityEvent(Player who, EntityDeathEvent event) {
        super(who);
        this.event = event;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
