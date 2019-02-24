package online.fireflower.easy_enchants.enchant_parsing;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BasicEnchantInfoParser implements IEnchantInfoParser {

    public List<String> enchants;

    public BasicEnchantInfoParser(List<String> enchants){
        this.enchants = enchants;
    }

    @Override
    public EnchantInfo getEnchantInfo(String enchantString) {

        enchantString = enchantString.toLowerCase();

        //ToDO: Make sure to search longest to shortest to prevent errors.
        for (String enchant : enchants){
            enchant = enchant.toLowerCase();
            if (enchantString.startsWith(enchant)){

                if (enchantString.length() <= enchant.length() + 1)
                    continue;

                String level = enchantString.substring(enchant.length() + 1);
                if (tryParseInt(level)){
                    return new EnchantInfo(enchant, Integer.parseInt(level));
                }
            }
        }

        return null;
    }

    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String createEnchantString(EnchantInfo enchantInfo) {
        return enchantInfo.name + " " + enchantInfo.level;
    }
}
