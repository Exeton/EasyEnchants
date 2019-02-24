package online.fireflower.easy_enchants.enchant_types;

import org.bukkit.entity.Player;

public interface IEquipable {

    void onEquip(Player player, int level);
    void onUnequip(Player player);
}
