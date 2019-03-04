package online.fireflower.easy_enchants.test_ingame;

import online.fireflower.easy_enchants.events.player_damage_player.PlayerDamageEntityEvent;
import online.fireflower.easy_enchants.enchant_parsing.EnchantInfo;
import online.fireflower.easy_enchants.enchant_types.Enchant;
import online.fireflower.easy_enchants.enchant_types.EnchantType;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;

public class AttackEnchant extends Enchant {

    public AttackEnchant(String name) {
        super(name);
    }

    @EventHandler
    public void handleEvent(PlayerDamageEntityEvent event, EnchantInfo enchantInfo){
        event.event.setDamage(10 * enchantInfo.level);
    }

    @Override
    public boolean shouldActivate(Event event, EnchantInfo enchantInfo) {
        return true;
    }

    @Override
    public EnchantType getType() {
        return EnchantType.ITEM_ENCHANT;
    }
}
