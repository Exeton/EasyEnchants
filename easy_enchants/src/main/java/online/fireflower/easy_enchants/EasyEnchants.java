package online.fireflower.easy_enchants;

import online.fireflower.easy_enchants.commands.EnchantApplicationCommand;
import online.fireflower.easy_enchants.enchant_execution.BasicEnchantCuller;
import online.fireflower.easy_enchants.enchant_execution.BasicEnchantExecutor;
import online.fireflower.easy_enchants.enchant_parsing.BasicEnchantInfoParser;
import online.fireflower.easy_enchants.enchant_parsing.BasicEnchantReadWriter;
import online.fireflower.easy_enchants.enchant_parsing.IEnchantReadWriter;
import online.fireflower.easy_enchants.enchant_execution.EnchantEventListener;
import online.fireflower.easy_enchants.enchant_registering.EnchantRegisterer;
import online.fireflower.easy_enchants.test_ingame.EntityDamagedByEntityEnchant;

import org.bukkit.ChatColor;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.RegisteredListener;
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
        EnchantInfoRetriever enchantInfoRetriever = new EnchantInfoRetriever(readWriter);
        BasicEnchantExecutor enchantExecutor = new BasicEnchantExecutor(new HashMap<Enchant, RegisteredListener>(), new BasicEnchantCuller());
        enchantRegisterer = new EnchantRegisterer(enchantExecutor, enchantInfoRetriever, this);

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
