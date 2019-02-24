package online.fireflower.easy_enchants.enchant_types;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ArmorEffectEnchant implements IEquipable {

    PotionEffectType potionEffectType;

    public ArmorEffectEnchant(PotionEffectType potionEffectType){
        this.potionEffectType = potionEffectType;
    }

    @Override
    public void onEquip(Player player, int level) {
        player.addPotionEffect(new PotionEffect(potionEffectType, 9999999, level - 1));
    }

    @Override
    public void onUnequip(Player player) {
        player.removePotionEffect(potionEffectType);
    }
}
