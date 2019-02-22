package online.fireflower.easy_enchants.enchant_parsing;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class BasicLoreParserTest {

    @Test
    public void testGetEnchants(){

        LinkedList<String> lore = new LinkedList<>();
        lore.add("Heyo");
        lore.add("test enchant");
        lore.add("test enchant ");
        lore.add("basic enchant 1");
        lore.add("basic enchant a 1");

        List<EnchantInfo> info = getBasicLoreParser().getEnchants(lore);

        Assert.assertTrue(info.size() == 2);
        Assert.assertTrue(!info.get(0).name.equalsIgnoreCase(info.get(1).name));
    }

    @Test
    public void testParse(){

        String loreLine = "TEST enchant 2";
        EnchantInfo info = getBasicLoreParser().parse(loreLine);

        Assert.assertTrue(info.level == 2);
        Assert.assertTrue(info.name.equals("test enchant"));
    }

    private BasicLoreParser getBasicLoreParser(){

        List<String> enchants = new LinkedList<>();
        enchants.add("basic enchant");
        enchants.add("basic enchant a");
        enchants.add("TeSt EnChAnT");
        enchants.add("suPer Enchant");

        return new BasicLoreParser(enchants);
    }

}