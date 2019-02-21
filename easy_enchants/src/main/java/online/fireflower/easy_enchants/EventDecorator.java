package online.fireflower.easy_enchants;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EventDecorator implements Listener {

    @EventHandler
    public void runEvent(EntityDamageByEntityEvent event){
        Bukkit.getLogger().info("Ran Event");
    }

}
