package online.fireflower.easy_enchants.test_ingame;

import online.fireflower.easy_enchants.Enchant;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamagedByEntityEnchant extends Enchant {

    @EventHandler
    public void handleEvent(EntityDamageByEntityEvent event){
        Bukkit.getLogger().info("EVENT fired!!");
    }

    @Override
    public boolean shouldProc(Event event) {
        return true;
    }
}
