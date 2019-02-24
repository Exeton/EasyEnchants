package online.fireflower.easy_enchants;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamagedByEntityListener implements Listener {

    @EventHandler
    public void onEvent(EntityDamageByEntityEvent event){

        if (!(event.getDamager() instanceof Player))
            return;

        Player player = (Player)event.getDamager();
        Bukkit.getPluginManager().callEvent(new PlayerDamageEntityEvent(player, event));

    }

}