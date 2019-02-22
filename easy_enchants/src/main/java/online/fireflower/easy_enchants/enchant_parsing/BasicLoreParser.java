package online.fireflower.easy_enchants.enchant_parsing;

import java.util.LinkedList;
import java.util.List;

public class BasicLoreParser implements ILoreParser{

    public List<String> enchants;

    public BasicLoreParser(List<String> enchants){
        this.enchants = enchants;
    }

    @Override
    public List<EnchantInfo> getEnchants(List<String> lore) {
        LinkedList<EnchantInfo> enchantInfoList = new LinkedList<>();

        for (String loreLine : lore){
            EnchantInfo info = parse(loreLine);
            if (info != null)
                enchantInfoList.add(info);
        }

        return enchantInfoList;
    }

    public EnchantInfo parse(String loreLine){

        loreLine = loreLine.toLowerCase();

        //ToDO: Make sure to search longest to shortest to prevent errors.
        for (String enchant : enchants){
            enchant = enchant.toLowerCase();
            if (loreLine.startsWith(enchant)){

                if (loreLine.length() <= enchant.length() + 1)
                    continue;

                String level = loreLine.substring(enchant.length() + 1);
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
}
