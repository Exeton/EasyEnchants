package online.fireflower.easy_enchants;

import online.fireflower.easy_enchants.enchant_parsing.EnchantInfo;
import online.fireflower.easy_enchants.enchant_parsing.IEnchantReadWriter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class EnchantInfoRetriever {

    IEnchantReadWriter enchantReadWriter;

    public EnchantInfoRetriever(IEnchantReadWriter enchantReadWriter){
        this.enchantReadWriter = enchantReadWriter;
    }


    public List<EnchantInfo> getArmorEnchants(Player player){

        List<EnchantInfo> enchants = new LinkedList<>();
        for (ItemStack item : player.getInventory().getArmorContents()){
            enchants.addAll(enchantReadWriter.readItem(item));
        }

        return enchants;
    }

    public List<EnchantInfo> getHeldItemEnchants(Player player){
        return enchantReadWriter.readItem(player.getItemInHand());
    }

}
