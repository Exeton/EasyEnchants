package online.fireflower.easy_enchants.test_ingame;

import online.fireflower.easy_enchants.enchant_types.ArmorEnchant;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DefenseArmor extends ArmorEnchant {
    public DefenseArmor(String displayName) {
        super(displayName);
    }

    @EventHandler
    public void onEvent(EntityDamageByEntityEvent event){
        Bukkit.getLogger().info("Event fired");
    }

    @Override
    public void onEquip(Player player) {
        player.sendMessage("Equiped");
    }

    @Override
    public void onUnequip(Player player) {
        player.sendMessage("Unequiped");
    }

    @Override
    public boolean shouldActivate(Event event) {
        return true;
    }
}
