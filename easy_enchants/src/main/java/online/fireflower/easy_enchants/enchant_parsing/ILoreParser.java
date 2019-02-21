package online.fireflower.easy_enchants.enchant_parsing;

import java.util.List;

public interface ILoreParser {
    List<EnchantInfo> getEnchants(List<String> lore);
}
