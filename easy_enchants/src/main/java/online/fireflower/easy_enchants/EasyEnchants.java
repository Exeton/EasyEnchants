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
import online.fireflower.easy_enchants.enchant_types.ArmorEffectEnchant;
import online.fireflower.easy_enchants.enchant_types.Enchant;
import online.fireflower.easy_enchants.enchant_types.IEquipable;
import online.fireflower.easy_enchants.test_ingame.DefenseArmor;
import online.fireflower.easy_enchants.test_ingame.AttackEnchant;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.LinkedList;


public class EasyEnchants extends JavaPlugin {

    public static EnchantRegisterer enchantRegisterer;
    private static HashMap<String, String> lowercaseEnchantKeyworkdsAndNames = new HashMap<>();
    private static HashMap<String, Enchant> namesAndEnchants = new HashMap<>();
    private static LinkedList<String> enchants = new LinkedList<>();

    //Equipables
    private static LinkedList<String> equipables = new LinkedList<>();
    private static HashMap<String, IEquipable> namesAndEquipables = new HashMap<>();
    private static HashMap<String, String> lowercaseEquipableKeyworkdsAndNames = new HashMap<>();

    @Override
    public void onEnable() {

        IEnchantReadWriter readWriter = new BasicEnchantReadWriter(new BasicEnchantInfoParser(enchants));
        EnchantInfoRetriever enchantInfoRetriever = new EnchantInfoRetriever(readWriter);
        BasicEnchantExecutor enchantExecutor = new BasicEnchantExecutor(new HashMap<>(), new BasicEnchantCuller());
        enchantRegisterer = new EnchantRegisterer(enchantExecutor, enchantInfoRetriever, this);

        //Code for adding an enchant
        AttackEnchant testEnchant = new AttackEnchant(ChatColor.GOLD + "AttackEnchant");
        registerEnchant("Damage", PlayerDamageEntityEvent.class, testEnchant);

        DefenseArmor defenseArmor = new DefenseArmor(ChatColor.RED + "DefensiveEnchant");
        registerEnchantAndEquipable("Defense", EntityDamageByEntityEvent.class, defenseArmor, defenseArmor);

        registerEquipable("Res", ChatColor.GREEN + "Resistance", new ArmorEffectEnchant(PotionEffectType.DAMAGE_RESISTANCE));

        this.getCommand("EasyEnchant").setExecutor(new EnchantApplicationCommand(lowercaseEnchantKeyworkdsAndNames, lowercaseEquipableKeyworkdsAndNames, readWriter));

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ArmorListener(new LinkedList<>()), this);

        IEnchantReadWriter equipableReadWriter = new BasicEnchantReadWriter(new BasicEnchantInfoParser(equipables));
        pluginManager.registerEvents(new EnchantedArmorEquipHandeler(equipableReadWriter, namesAndEquipables), this);
        pluginManager.registerEvents(new EntityDamagedByEntityListener(), this);

    }

    public static void registerEnchant(String refferenceName, Class eventClass, Enchant enchant){

        String displayName = enchant.displayName;
        enchantRegisterer.registerEvent(eventClass, enchant, enchant);
        enchants.add(displayName);
        lowercaseEnchantKeyworkdsAndNames.put(refferenceName.toLowerCase(), displayName);
        namesAndEnchants.put(displayName, enchant);
    }

    private static void registerEquipable(String refferenceName, String actualName, IEquipable equipable){
        equipables.add(actualName);
        namesAndEquipables.put(actualName, equipable);
        lowercaseEquipableKeyworkdsAndNames.put(refferenceName.toLowerCase(), actualName);

    }

    public static void registerEnchantAndEquipable(String refferenceName, Class eventClass, Enchant enchant, IEquipable equipable){
        registerEnchant(refferenceName, eventClass, enchant);
        registerEquipable(refferenceName, enchant.displayName, equipable);
    }

}
