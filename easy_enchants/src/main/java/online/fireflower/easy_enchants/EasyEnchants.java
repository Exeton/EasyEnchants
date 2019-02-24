package online.fireflower.easy_enchants;

import libs.com.codingforcookies.armorequip.ArmorListener;
import online.fireflower.easy_enchants.commands.EnchantApplicationCommand;
import online.fireflower.easy_enchants.enchant_execution.BasicEnchantCuller;
import online.fireflower.easy_enchants.enchant_execution.BasicEnchantExecutor;
import online.fireflower.easy_enchants.enchant_parsing.BasicEnchantInfoParser;
import online.fireflower.easy_enchants.enchant_parsing.BasicEnchantReadWriter;
import online.fireflower.easy_enchants.enchant_parsing.EnchantInfoRetriever;
import online.fireflower.easy_enchants.enchant_parsing.IEnchantReadWriter;
import online.fireflower.easy_enchants.enchant_registering.EnchantRegisterer;
import online.fireflower.easy_enchants.enchant_types.Enchant;
import online.fireflower.easy_enchants.test_ingame.DefenseArmor;
import online.fireflower.easy_enchants.test_ingame.EntityDamagedByEntityEnchant;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.LinkedList;


public class EasyEnchants extends JavaPlugin {

    public static EnchantRegisterer enchantRegisterer;
    private static HashMap<String, String> lowercaseEnchantKeyworkdsAndNames = new HashMap<>();
    private static HashMap<String, Enchant> namesAndEnchants = new HashMap<>();
    private static LinkedList<String> enchants = new LinkedList<>();

    @Override
    public void onEnable() {

        IEnchantReadWriter readWriter = new BasicEnchantReadWriter(new BasicEnchantInfoParser(enchants));
        EnchantInfoRetriever enchantInfoRetriever = new EnchantInfoRetriever(readWriter);
        BasicEnchantExecutor enchantExecutor = new BasicEnchantExecutor(new HashMap<>(), new BasicEnchantCuller());
        enchantRegisterer = new EnchantRegisterer(enchantExecutor, enchantInfoRetriever, this);

        //Code for adding an enchant
        EntityDamagedByEntityEnchant testEnchant = new EntityDamagedByEntityEnchant(ChatColor.GOLD + "EntityDamagedByEntityEnchant");
        addEnchant("Damage",ChatColor.GOLD + "EntityDamagedByEntityEnchant", EntityDamageByEntityEvent.class, testEnchant);
        addEnchant("Defense", ChatColor.RED + "DefensiveEnchant", EntityDamageByEntityEvent.class, new DefenseArmor(ChatColor.RED + "DefensiveEnchant"));

        this.getCommand("EasyEnchant").setExecutor(new EnchantApplicationCommand(lowercaseEnchantKeyworkdsAndNames, readWriter));

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ArmorListener(new LinkedList<>()), this);
        pluginManager.registerEvents(new EnchantedArmorEquipHandeler(readWriter, namesAndEnchants), this);

    }

    public static void addEnchant(String refferenceName, String displayName, Class eventClass, Enchant enchant){
        enchantRegisterer.registerEvent(eventClass, enchant, enchant);
        enchants.add(displayName);
        lowercaseEnchantKeyworkdsAndNames.put(refferenceName.toLowerCase(), displayName);
        namesAndEnchants.put(displayName, enchant);
    }

}
