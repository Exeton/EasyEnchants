package online.fireflower.easy_enchants.enchant_registering;

import online.fireflower.easy_enchants.EasyEnchants;
import online.fireflower.easy_enchants.Enchant;
import online.fireflower.easy_enchants.procing.IProcedEnchantCuller;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredListener;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class EnchantRegisterer {

    private EasyEnchants main;
    private HashMap<Class, EventExecutionWrapper> classesAndExecutors = new HashMap<>();


    public EnchantRegisterer(EasyEnchants easyEnchants){
        this.main = easyEnchants;
    }

    public void registerEvent(Class<? extends Event> type, Enchant enchant, Listener listener){
        EventExecutionWrapper executionWrapper = getExecutionWrapper(type);

        for (RegisteredListener registeredListener : getRegisteredListeners(listener))
            executionWrapper.registerEnchant(registeredListener, enchant);
    }

    private Set<RegisteredListener> getRegisteredListeners(Listener listener){

        //Although this is unsafe, only enchants with one method should
        return main.getPluginLoader().createRegisteredListeners(listener, main).values().stream().findFirst().get();

    }

    private EventExecutionWrapper getExecutionWrapper(Class<? extends Event> type){

        if (classesAndExecutors.containsKey(type))
            return classesAndExecutors.get(type);

        EventExecutionWrapper wrapper = new EventExecutionWrapper(new IProcedEnchantCuller() {
            public List<Enchant> cullEnchants(List<Enchant> clonedProcedEnchants) {
                return clonedProcedEnchants;
            }
        }, type);
        classesAndExecutors.put(type, wrapper);
        registerExecutionWrapper(type, wrapper);
        return wrapper;
    }

    private void registerExecutionWrapper(Class<? extends Event> type, EventExecutionWrapper wrapper){
        Bukkit.getLogger().info("Registering execution wrapper");
        Bukkit.getPluginManager().registerEvent(type, new DummyListener(), EventPriority.NORMAL, wrapper, main);
    }

}
