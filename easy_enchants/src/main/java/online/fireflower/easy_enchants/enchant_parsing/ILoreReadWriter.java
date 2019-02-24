package online.fireflower.easy_enchants.enchant_parsing;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface ILoreReadWriter {
    List<EnchantInfo> getEnchants(List<String> lore);
    void applyEnchant(ItemStack itemStack, EnchantInfo enchantInfo);
}
