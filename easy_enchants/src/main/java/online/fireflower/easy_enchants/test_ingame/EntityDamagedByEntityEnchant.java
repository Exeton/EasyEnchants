package online.fireflower.easy_enchants.test_ingame;

import online.fireflower.easy_enchants.Enchant;
import online.fireflower.easy_enchants.EnchantType;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamagedByEntityEnchant extends Enchant {

    public EntityDamagedByEntityEnchant(String name) {
        super(name);
    }

    @EventHandler
    public void handleEvent(EntityDamageByEntityEvent event){
        Bukkit.getLogger().info("EVENT fired!!");
    }

    @Override
    public boolean shouldActivate(Event event) {
        return true;
    }

    @Override
    public EnchantType getType() {
        return EnchantType.ITEM_ENCHANT;
    }
}
