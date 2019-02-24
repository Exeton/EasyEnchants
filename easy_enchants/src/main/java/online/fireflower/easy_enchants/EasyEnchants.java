package online.fireflower.easy_enchants;

import online.fireflower.easy_enchants.enchant_parsing.BasicEnchantInfoParser;
import online.fireflower.easy_enchants.enchant_parsing.BasicEnchantReadWriter;
import online.fireflower.easy_enchants.enchant_parsing.IEnchantReadWriter;
import online.fireflower.easy_enchants.enchant_registering.EnchantEventExecutor;
import online.fireflower.easy_enchants.enchant_registering.EnchantRegisterer;
import online.fireflower.easy_enchants.test_ingame.EntityDamagedByEntityEnchant;

import org.bukkit.ChatColor;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.LinkedList;


public class EasyEnchants extends JavaPlugin {

    public static EnchantRegisterer enchantRegisterer;
    private static HashMap<String, String> enchantKeyworkdsAndNames = new HashMap<>();
    private static LinkedList<String> enchants = new LinkedList<>();

    @Override
    public void onEnable() {

        IEnchantReadWriter readWriter = new BasicEnchantReadWriter(new BasicEnchantInfoParser(enchants));
        EnchantEventExecutor.retriever = new EnchantInfoRetriever(readWriter);
        enchantRegisterer = new EnchantRegisterer(this);

        //Code for adding an enchant
        EntityDamagedByEntityEnchant testEnchant = new EntityDamagedByEntityEnchant(ChatColor.GOLD + "EntityDamagedByEntityEnchant");
        enchantRegisterer.registerEvent(EntityDamageByEntityEvent.class, testEnchant, testEnchant);
        addEnchant("Damage",ChatColor.GOLD + "EntityDamagedByEntityEnchant");

        this.getCommand("EasyEnchant").setExecutor(new EnchantApplicationCommand(enchantKeyworkdsAndNames, readWriter));

    }

    public static void addEnchant(String refferenceName, String name){
        enchants.add(name);
        enchantKeyworkdsAndNames.put(refferenceName.toLowerCase(), name);
    }

}
