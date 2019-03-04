package online.fireflower.easy_enchants.test_ingame;

import online.fireflower.easy_enchants.enchant_parsing.EnchantInfo;
import online.fireflower.easy_enchants.enchant_types.Enchant;
import online.fireflower.easy_enchants.enchant_types.EnchantType;
import online.fireflower.easy_enchants.enchant_types.IEquipable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DefenseArmor extends Enchant implements IEquipable {
    public DefenseArmor(String displayName) {
        super(displayName);
    }

    @EventHandler
    public void onEvent(EntityDamageByEntityEvent event, EnchantInfo enchantInfo){
        Bukkit.getLogger().info("Event fired: " + enchantInfo.level);
    }

    @Override
    public void onEquip(Player player, int level) {
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

    @Override
    public EnchantType getType() {
        return EnchantType.ARMOR_ENCHANT;
    }
}
