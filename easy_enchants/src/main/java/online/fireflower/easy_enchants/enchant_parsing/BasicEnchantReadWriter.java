package online.fireflower.easy_enchants.enchant_parsing;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BasicEnchantReadWriter implements IEnchantReadWriter {

    IEnchantInfoParser enchantInfoParser;

    public BasicEnchantReadWriter(IEnchantInfoParser enchantInfoParser){
        this.enchantInfoParser = enchantInfoParser;
    }

    @Override
    public List<EnchantInfo> readItem(ItemStack item) {

        if (item == null || item.getItemMeta() == null || item.getItemMeta().getLore() == null)
            return new LinkedList<>();

        List<String> lore = item.getItemMeta().getLore();
        LinkedList<EnchantInfo> enchantInfoList = new LinkedList<>();

        for (String loreLine : lore){
            EnchantInfo info = enchantInfoParser.getEnchantInfo(loreLine);
            if (info != null)
                enchantInfoList.add(info);
        }

        return enchantInfoList;
    }

    @Override
    public void setEnchant(ItemStack item, EnchantInfo enchantInfo) {

        removeEnchant(item, enchantInfo.name);

        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null)
            lore = new ArrayList<>();

        lore.add(enchantInfoParser.createEnchantString(enchantInfo));

        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    @Override
    public void removeEnchant(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null)
            return;

        for (String str : lore)
            if (str.startsWith(name)){
                lore.remove(str);
                break;
            }

        meta.setLore(lore);
        item.setItemMeta(meta);
    }

}
