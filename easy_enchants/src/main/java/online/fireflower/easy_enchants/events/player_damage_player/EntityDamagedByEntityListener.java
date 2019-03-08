package online.fireflower.easy_enchants.events.player_damage_player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamagedByEntityListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onEvent(EntityDamageByEntityEvent event){

        if (event.isCancelled())
            return;

        if (!(event.getDamager() instanceof Player))
            return;

        Player player = (Player)event.getDamager();
        Bukkit.getPluginManager().callEvent(new PlayerDamageEntityEvent(player, event));

    }

}
