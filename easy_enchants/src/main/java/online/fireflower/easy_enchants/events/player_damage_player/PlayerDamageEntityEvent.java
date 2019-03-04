package online.fireflower.easy_enchants.events.player_damage_player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerEvent;

public class PlayerDamageEntityEvent extends PlayerEvent {

    public EntityDamageByEntityEvent event;
    public PlayerDamageEntityEvent(Player who, EntityDamageByEntityEvent event) {
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
