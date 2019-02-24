package online.fireflower.easy_enchants.enchant_types;


import org.bukkit.entity.Player;

public abstract class ArmorEnchant extends Enchant {

    public ArmorEnchant(String displayName) {
        super(displayName);
    }

    public void onEquip(Player player){

    }

    public void onUnequip(Player player){

    }

    @Override
    public EnchantType getType() {
        return EnchantType.ARMOR_ENCHANT;
    }
}
