package online.fireflower.easy_enchants;

import libs.com.codingforcookies.armorequip.ArmorEquipEvent;
import online.fireflower.easy_enchants.enchant_parsing.EnchantInfo;
import online.fireflower.easy_enchants.enchant_parsing.IEnchantReadWriter;
import online.fireflower.easy_enchants.enchant_types.IEquipable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class EnchantedArmorEquipHandeler implements Listener {

    IEnchantReadWriter enchantReadWriter;
    HashMap<String, IEquipable> enchants;

    public EnchantedArmorEquipHandeler(IEnchantReadWriter readWriter, HashMap<String, IEquipable> enchants){
        this.enchantReadWriter = readWriter;
        this.enchants = enchants;
    }

    @EventHandler
    public void onArmorEquip(ArmorEquipEvent event){

        //Todo: Get all armor before and after, find differences, apply

        Player player = event.getPlayer();

        if (event.getOldArmorPiece() != null)
            for (EnchantInfo enchantInfo : enchantReadWriter.readItem(event.getOldArmorPiece()))
                enchants.get(enchantInfo.name).onUnequip(player);

        if (event.getNewArmorPiece() != null)
            for (EnchantInfo enchantInfo : enchantReadWriter.readItem(event.getNewArmorPiece()))
                enchants.get(enchantInfo.name).onEquip(player, enchantInfo.level);

    }

}
