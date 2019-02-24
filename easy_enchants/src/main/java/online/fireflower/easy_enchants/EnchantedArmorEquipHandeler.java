package online.fireflower.easy_enchants;

import libs.com.codingforcookies.armorequip.ArmorEquipEvent;
import online.fireflower.easy_enchants.enchant_parsing.EnchantInfo;
import online.fireflower.easy_enchants.enchant_parsing.IEnchantReadWriter;
import online.fireflower.easy_enchants.enchant_types.ArmorEnchant;
import online.fireflower.easy_enchants.enchant_types.Enchant;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class EnchantedArmorEquipHandeler implements Listener {

    IEnchantReadWriter enchantReadWriter;
    HashMap<String, Enchant> enchants;

    public EnchantedArmorEquipHandeler(IEnchantReadWriter readWriter, HashMap<String, Enchant> enchants){
        this.enchantReadWriter = readWriter;
        this.enchants = enchants;
    }

    @EventHandler
    public void onArmorEquip(ArmorEquipEvent event){

        //Todo: Get all armor before and after, find differences, apply

        Player player = event.getPlayer();

        if (event.getOldArmorPiece() != null){
            for (EnchantInfo enchantInfo : enchantReadWriter.readItem(event.getOldArmorPiece())){
                Enchant enchant = enchants.get(enchantInfo.name);
                if (enchant instanceof ArmorEnchant){
                    ArmorEnchant armorEnchant = (ArmorEnchant)enchant;
                    armorEnchant.onUnequip(player);
                }
            }
        }

        ItemStack item = event.getNewArmorPiece();
        if (item != null){
            for (EnchantInfo enchantInfo : enchantReadWriter.readItem(item)){
                Enchant enchant = enchants.get(enchantInfo.name);
                if (enchant instanceof ArmorEnchant){
                    ArmorEnchant armorEnchant = (ArmorEnchant)enchant;
                    armorEnchant.onEquip(player);
                }
            }
        }

    }

}
