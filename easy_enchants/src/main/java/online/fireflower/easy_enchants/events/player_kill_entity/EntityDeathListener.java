package online.fireflower.easy_enchants.events.player_kill_entity;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onEvent(EntityDeathEvent event){

        LivingEntity entity = event.getEntity();
        if (entity.getKiller() == null)
            return;

        Bukkit.getPluginManager().callEvent(new PlayerKillEntityEvent(entity.getKiller(), event));
    }
}
