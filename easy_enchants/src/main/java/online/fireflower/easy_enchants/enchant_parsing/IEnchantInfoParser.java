package online.fireflower.easy_enchants.enchant_parsing;

public interface IEnchantInfoParser {
    EnchantInfo getEnchantInfo(String enchantString);
    String createEnchantString(EnchantInfo enchantInfo);
}
