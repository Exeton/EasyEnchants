package online.fireflower.easy_enchants.enchant_parsing;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface IEnchantReadWriter {

    List<EnchantInfo> readItem(ItemStack item);
    void setEnchant(ItemStack item, EnchantInfo enchantInfo);
    void removeEnchant(ItemStack item, String name);
}
