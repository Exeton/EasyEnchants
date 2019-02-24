package online.fireflower.easy_enchants.test_ingame;

import online.fireflower.easy_enchants.PlayerDamageEntityEvent;
import online.fireflower.easy_enchants.enchant_types.Enchant;
import online.fireflower.easy_enchants.enchant_types.EnchantType;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;

public class AttackEnchant extends Enchant {

    public AttackEnchant(String name) {
        super(name);
    }

    @EventHandler
    public void handleEvent(PlayerDamageEntityEvent event){
        event.event.setDamage(10);
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
