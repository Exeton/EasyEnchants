package online.fireflower.easy_enchants.enchant_parsing;

import online.fireflower.easy_enchants.enchant_parsing.numbers.RomanNumeralParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class BasicLoreParserTest {

    @Test
    public void testGetEnchants(){

        LinkedList<String> lore = new LinkedList<>();
        lore.add("Heyo");
        lore.add("test enchant");
        lore.add("test enchant ");
        lore.add("basic enchant 1");
        lore.add("basic enchant a 1");

        List<EnchantInfo> info = null;//getBasicLoreParser().getEnchants(lore);

        Assert.assertTrue(info.size() == 2);
        Assert.assertTrue(!info.get(0).name.equalsIgnoreCase(info.get(1).name));
    }

    @Test
    public void testParse(){

        String loreLine = "TEST enchant 2";
        EnchantInfo info = null; //getBasicLoreParser().parse(loreLine);

        Assert.assertTrue(info.level == 2);
        Assert.assertTrue(info.name.equals("test enchant"));
    }

    private BasicEnchantInfoParser getBasicLoreParser(){

        List<String> enchants = new LinkedList<>();
        enchants.add("basic enchant");
        enchants.add("basic enchant a");
        enchants.add("TeSt EnChAnT");
        enchants.add("suPer Enchant");

        return null;
//        return new BasicEnchantInfoParser(enchants, );
    }

}