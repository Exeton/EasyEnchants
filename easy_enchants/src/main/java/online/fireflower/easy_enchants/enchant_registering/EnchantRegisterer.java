package online.fireflower.easy_enchants.enchant_registering;

import online.fireflower.easy_enchants.EasyEnchants;
import online.fireflower.easy_enchants.Enchant;
import online.fireflower.easy_enchants.enchant_parsing.EnchantInfoRetriever;
import online.fireflower.easy_enchants.enchant_execution.BasicEnchantExecutor;
import online.fireflower.easy_enchants.enchant_execution.EnchantEventListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredListener;

import java.util.HashMap;
import java.util.Set;

public class EnchantRegisterer {

    private EasyEnchants main;
    private HashMap<Class, EnchantEventListener> classesAndExecutors = new HashMap<>();

    BasicEnchantExecutor enchantExecutor;
    EnchantInfoRetriever enchantInfoRetriever;

    public EnchantRegisterer(BasicEnchantExecutor enchantExecutor, EnchantInfoRetriever enchantInfoRetriever, EasyEnchants easyEnchants){
        this.main = easyEnchants;
        this.enchantExecutor = enchantExecutor;
        this.enchantInfoRetriever = enchantInfoRetriever;
    }

    public void registerEvent(Class<? extends Event> type, Enchant enchant, Listener listener){
        EnchantEventListener executionWrapper = getExecutionWrapper(type);

        for (RegisteredListener registeredListener : getRegisteredListeners(listener)){
            executionWrapper.registerEnchant(enchant);
            enchantExecutor.enchantsAndListeners.put(enchant, registeredListener);
        }
    }

    private Set<RegisteredListener> getRegisteredListeners(Listener listener){

        //Although this is unsafe, only enchants with one method should
        return main.getPluginLoader().createRegisteredListeners(listener, main).values().stream().findFirst().get();

    }

    private EnchantEventListener getExecutionWrapper(Class<? extends Event> type){

        if (classesAndExecutors.containsKey(type))
            return classesAndExecutors.get(type);

        EnchantEventListener wrapper = new EnchantEventListener(enchantInfoRetriever, enchantExecutor, type);
        classesAndExecutors.put(type, wrapper);
        Bukkit.getPluginManager().registerEvent(type, new DummyListener(), EventPriority.NORMAL, wrapper, main);
        return wrapper;
    }

}
