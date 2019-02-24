package online.fireflower.easy_enchants.enchant_parsing;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface IEnchantReadWriter {

    List<EnchantInfo> readItem(ItemStack item);
    void addEnchant(ItemStack item, EnchantInfo enchantInfo);

}
