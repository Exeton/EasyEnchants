package online.fireflower.easy_enchants;

import libs.com.codingforcookies.armorequip.ArmorListener;
import online.fireflower.easy_enchants.commands.EnchantApplicationCommand;
import online.fireflower.easy_enchants.dependency_injection.creation.DefaultDependencyCreator;
import online.fireflower.easy_enchants.dependency_injection.creation.DependencyCache;
import online.fireflower.easy_enchants.dependency_injection.creation.DependencyCreatorDecorator;
import online.fireflower.easy_enchants.dependency_injection.creation.IDependencyCreator;
import online.fireflower.easy_enchants.dependency_injection.ordering.DependencyBuilder;
import online.fireflower.easy_enchants.enchant_parsing.EnchantInfo;
import online.fireflower.easy_enchants.enchant_parsing.IEnchantInfoParser;
import online.fireflower.easy_enchants.enchant_registering.EnchantRegisterer;
import online.fireflower.easy_enchants.enchant_types.ArmorEffectEnchant;
import online.fireflower.easy_enchants.enchant_types.Enchant;
import online.fireflower.easy_enchants.enchant_types.IEquipable;
import online.fireflower.easy_enchants.events.player_damage_player.EntityDamagedByEntityListener;
import online.fireflower.easy_enchants.events.player_kill_entity.EntityDeathListener;
import online.fireflower.easy_enchants.test_ingame.DefenseArmor;
import online.fireflower.easy_enchants.test_ingame.AttackEnchant;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class EasyEnchants extends JavaPlugin {

    public static EnchantRegisterer enchantRegisterer;
    public static DependencyBuilder dependencyBuilder = new DependencyBuilder();
    private static EasyEnchants instance;
    private List<Runnable> postDependencyTasks = new LinkedList<>();

    public IDependencyCreator dependencyRetriever;
    private static boolean dependenciesLoaded = false;

    public HashMap<String, String> lowercaseEnchantKeyworkdsAndNames = new HashMap<>();
    public HashMap<String, IEquipable> namesAndEquipables = new HashMap<>();
    public LinkedList<String> enchants = new LinkedList<>();
    public HashMap<String, String> lowercaseEquipableKeyworkdsAndNames = new HashMap<>();

    public static EasyEnchants getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new ArmorListener(new LinkedList<>()), this);
        pluginManager.registerEvents(new EntityDamagedByEntityListener(), this);
        pluginManager.registerEvents(new EntityDeathListener(), this);

        InjectSpecialFormatting();
        registerTestEnchants();

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () ->
        {
            buildDependencies();
            injectDependencies();
            runPostDITasks();
        }, 1);

    }

    private void InjectSpecialFormatting(){

        EasyEnchants easyEnchants = EasyEnchants.getInstance();
        Runnable dependencySwapTask = new Runnable() {
            @Override
            public void run() {

                IEnchantInfoParser enchantInfoParser = new IEnchantInfoParser() {

                    IEnchantInfoParser defaultParser = easyEnchants.dependencyRetriever.getEnchantInfoParser();

                    @Override
                    public EnchantInfo getEnchantInfo(String enchantString) {
                        return defaultParser.getEnchantInfo(enchantString.replace(" - ", " "));
                    }

                    @Override
                    public String createEnchantString(EnchantInfo enchantInfo) {
                        return enchantInfo.name + " - " + enchantInfo.level;
                    }
                };

                class DependencySwapDecorator extends DependencyCreatorDecorator{

                    public DependencySwapDecorator(IDependencyCreator dependencyCreator) {
                        super(dependencyCreator);
                    }

                    @Override
                    public IEnchantInfoParser getEnchantInfoParser() {
                        return enchantInfoParser;
                    }
                }

                easyEnchants.dependencyRetriever = new DependencySwapDecorator(easyEnchants.dependencyRetriever);
            }
        };

        EasyEnchants.dependencyBuilder.addTask(dependencySwapTask, 5);

    }

    private void buildDependencies(){
        dependencyRetriever = new DefaultDependencyCreator();
        dependencyBuilder.runTasks();
        dependencyRetriever = new DependencyCache(dependencyRetriever);
    }

    private void injectDependencies(){
        enchantRegisterer = dependencyRetriever.getEnchantRegisterer();
        this.getCommand("EasyEnchant").setExecutor(new EnchantApplicationCommand(lowercaseEnchantKeyworkdsAndNames, lowercaseEquipableKeyworkdsAndNames, dependencyRetriever.getEnchantReadWriter()));
        Bukkit.getPluginManager().registerEvents(new EnchantedArmorEquipHandeler(dependencyRetriever.getEnchantReadWriter(), namesAndEquipables), this);
        dependenciesLoaded = true;
    }

    private void runPostDITasks(){
        for (Runnable runnable : postDependencyTasks){
            runnable.run();
        }
    }

    private void registerTestEnchants(){
        //Code for adding an enchant
        AttackEnchant testEnchant = new AttackEnchant(ChatColor.GOLD + "AttackEnchant");
        registerEnchant(testEnchant);

        DefenseArmor defenseArmor = new DefenseArmor(ChatColor.RED + "DefensiveEnchant");
        registerEnchantAndEquipable(defenseArmor, defenseArmor);

        registerEquipable(ChatColor.GREEN + "Resistance", new ArmorEffectEnchant(PotionEffectType.DAMAGE_RESISTANCE));
    }


    public static void registerEnchantAndEquipable(Enchant enchant, IEquipable equipable){
        registerEnchantAndEquipable(getRefferenceName(enchant), enchant, equipable);
    }

    //This method can be run without scheduling a runnable because both the methods it calls will schedule a runnable if neccessary.
    public static void registerEnchantAndEquipable(String refferenceName, Enchant enchant, IEquipable equipable){
        registerEnchant(refferenceName, enchant);
        registerEquipable(refferenceName, enchant.displayName, equipable);
    }

    public static void registerEnchant(Enchant enchant){
        registerEnchant(getRefferenceName(enchant), enchant);
    }

    public static void registerEnchant(String refferenceName, Enchant enchant){

        if (dependenciesLoaded)
            actuallyRegisterEnchant(refferenceName, enchant);
        else
            getInstance().postDependencyTasks.add(() -> actuallyRegisterEnchant(refferenceName, enchant));

    }

    public static void registerEquipable(String actualName, IEquipable equipable){
        registerEquipable(getRefferenceName(actualName), actualName, equipable);
    }

    public static void registerEquipable(String refferenceName, String actualName, IEquipable equipable){

        if (dependenciesLoaded)
            actuallyRegisterEquipable(refferenceName, actualName, equipable);
        else
            getInstance().postDependencyTasks.add(() -> actuallyRegisterEquipable(refferenceName, actualName, equipable));
    }

    private static void actuallyRegisterEnchant(String refferenceName, Enchant enchant){
        String displayName = enchant.displayName;
        enchantRegisterer.registerEvent(enchant, enchant);

        getInstance().enchants.add(displayName);
        getInstance().lowercaseEnchantKeyworkdsAndNames.put(refferenceName.toLowerCase(), displayName);
    }

    public static void actuallyRegisterEquipable(String refferenceName, String actualName, IEquipable equipable){
        getInstance().namesAndEquipables.put(actualName, equipable);
        getInstance().enchants.add(actualName);
        getInstance().lowercaseEquipableKeyworkdsAndNames.put(refferenceName.toLowerCase(), actualName);
    }


    private static String getRefferenceName(Enchant enchant){
        return getRefferenceName(enchant.displayName);
    }

    private static String getRefferenceName(String displayName){
        return ChatColor.stripColor(displayName.replace(" ", ""));
    }
}
