package online.fireflower.easy_enchants.enchant_registering;

import online.fireflower.easy_enchants.EasyEnchants;
import online.fireflower.easy_enchants.enchant_execution.IEnchantExecutor;
import online.fireflower.easy_enchants.enchant_types.Enchant;
import online.fireflower.easy_enchants.enchant_parsing.EnchantInfoRetriever;
import online.fireflower.easy_enchants.enchant_execution.BasicEnchantExecutor;
import online.fireflower.easy_enchants.enchant_execution.EnchantEventListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredListener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

public class EnchantRegisterer {

    private EasyEnchants main;
    private HashMap<Class, EnchantEventListener> classesAndExecutors = new HashMap<>();

    IEnchantExecutor enchantExecutor;
    EnchantInfoRetriever enchantInfoRetriever;

    public EnchantRegisterer(IEnchantExecutor enchantExecutor, EnchantInfoRetriever enchantInfoRetriever, EasyEnchants easyEnchants){
        this.main = easyEnchants;
        this.enchantExecutor = enchantExecutor;
        this.enchantInfoRetriever = enchantInfoRetriever;
    }

    public void registerEvent(Enchant enchant, Listener listener){


        EnchantRegisteredListener enchantRegisteredListener = getEnchantRegisteredListener(listener);
        enchantExecutor.addEnchant(enchant, enchantRegisteredListener);

        Class<? extends Event> type = (Class<? extends Event>)enchantRegisteredListener.method.getParameterTypes()[0];

        EnchantEventListener executionWrapper = getExecutionWrapper(type);
        executionWrapper.registerEnchant(enchant);

    }

    private EnchantEventListener getExecutionWrapper(Class<? extends Event> type){

        if (classesAndExecutors.containsKey(type))
            return classesAndExecutors.get(type);

        EnchantEventListener wrapper = new EnchantEventListener(enchantInfoRetriever, enchantExecutor, type);
        classesAndExecutors.put(type, wrapper);
        Bukkit.getPluginManager().registerEvent(type, new DummyListener(), EventPriority.NORMAL, wrapper, main);
        return wrapper;
    }

    //Methods should take in class extending Event and an EnchantInfo.
    private Set<RegisteredListener> getRegisteredListeners(Listener listener){

        //Use new Registered Listener
        return main.getPluginLoader().createRegisteredListeners(listener, main).values().stream().findFirst().get();
    }



    public EnchantRegisteredListener getEnchantRegisteredListener(Listener listener){
        for (Method method : listener.getClass().getMethods()){

            if (method.getAnnotation(EventHandler.class) == null)
                continue;

            if (method.getParameterCount() != 2)
                continue;

            return new EnchantRegisteredListener(listener, method);
        }

        return null;
    }
}
