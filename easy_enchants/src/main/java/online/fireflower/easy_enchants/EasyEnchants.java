package online.fireflower.easy_enchants;

import online.fireflower.easy_enchants.enchant_parsing.BasicLoreParser;
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
    public static BasicLoreParser enchantReadWriter;
    private static HashMap<String, String> enchantKeyworkdsAndNames = new HashMap<>();

    @Override
    public void onEnable() {

        EnchantEventExecutor.retriever = new EnchantInfoRetriever();
        enchantRegisterer = new EnchantRegisterer(this);
        enchantReadWriter = new BasicLoreParser(new LinkedList<>());

        EntityDamagedByEntityEnchant testEnchant = new EntityDamagedByEntityEnchant(ChatColor.GOLD + "EntityDamagedByEntityEnchant");
        enchantRegisterer.registerEvent(EntityDamageByEntityEvent.class, testEnchant, testEnchant);

        addEnchant("Damage",ChatColor.GOLD + "EntityDamagedByEntityEnchant");

        this.getCommand("EasyEnchant").setExecutor(new EnchantApplicationCommand(enchantKeyworkdsAndNames));

    }

    public static void addEnchant(String refferenceName, String name){
        enchantReadWriter.enchants.add(name);
        enchantKeyworkdsAndNames.put(refferenceName.toLowerCase(), name);
    }

}
