package online.fireflower.easy_enchants;

import online.fireflower.easy_enchants.enchant_parsing.EnchantInfo;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class EnchantInfoRetriever {

    public List<EnchantInfo> getArmorEnchants(Player player){

        List<EnchantInfo> enchants = new LinkedList<>();
        for (ItemStack item : player.getInventory().getArmorContents()){
            enchants.addAll(getItemInfo(item));
        }

        return enchants;
    }

    public List<EnchantInfo> getHeldItemEnchants(Player player){
        return getItemInfo(player.getItemInHand());
    }

    private List<EnchantInfo> getItemInfo(ItemStack itemStack){

        if (itemStack == null || itemStack.getItemMeta() == null || itemStack.getItemMeta().getLore() == null)
            return new LinkedList<>();

        return EasyEnchants.enchantReadWriter.getEnchants(itemStack.getItemMeta().getLore());
    }

}
