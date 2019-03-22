package online.fireflower.easy_enchants.enchant_parsing;

import online.fireflower.easy_enchants.enchant_parsing.numbers.INumberParser;

import java.util.List;

public class BasicEnchantInfoParser implements IEnchantInfoParser {

    public List<String> enchants;
    INumberParser numberParser;

    public BasicEnchantInfoParser(List<String> enchants, INumberParser numberParser){
        this.enchants = enchants;
        this.numberParser = numberParser;
    }

    @Override
    public EnchantInfo getEnchantInfo(String enchantString) {

        //ToDO: Make sure to search longest to shortest to prevent errors.
        for (String enchant : enchants){
            if (enchantString.startsWith(enchant)){

                if (enchantString.length() <= enchant.length() + 1)
                    continue;

                String level = enchantString.substring(enchant.length() + 1);
                if (numberParser.canParse(level)){
                    return new EnchantInfo(enchant, numberParser.parse(level));
                }
            }
        }

        return null;
    }

    @Override
    public String createEnchantString(EnchantInfo enchantInfo) {
        return enchantInfo.name + " " + numberParser.toInt(enchantInfo.level);
    }
}
