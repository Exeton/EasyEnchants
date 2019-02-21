package online.fireflower.easy_enchants;

import online.fireflower.easy_enchants.enchant_registering.EnchantRegisterer;
import online.fireflower.easy_enchants.test_ingame.EntityDamagedByEntityEnchant;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class EasyEnchants extends JavaPlugin {

    public static EnchantRegisterer enchantRegisterer;

    @Override
    public void onEnable() {
        enchantRegisterer = new EnchantRegisterer(this);

        EntityDamagedByEntityEnchant testEnchant = new EntityDamagedByEntityEnchant();
        enchantRegisterer.registerEvent(EntityDamageByEntityEvent.class, testEnchant, testEnchant);
    }
}
