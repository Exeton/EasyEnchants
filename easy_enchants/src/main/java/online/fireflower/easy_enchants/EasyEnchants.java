package online.fireflower.easy_enchants;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EasyEnchants extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();

        EventExecutor executor = new EventExecutor() {
            public void execute(Listener listener, Event event) throws EventException {
                try {
                    if (!EntityDamageEvent.class.isAssignableFrom(event.getClass())) {
                        return;
                    }
                    if (!(event instanceof EntityDamageByEntityEvent))
                        return;

                    EventDecorator decorator = (EventDecorator)listener;
                    decorator.runEvent((EntityDamageByEntityEvent)event);
                } catch (Throwable t) {
                    throw new EventException(t);
                }
            }
        };

        Bukkit.getPluginManager().registerEvent(EntityDamageEvent.class,  new EventDecorator(), EventPriority.NORMAL, executor, this);

    }

    public static void registerEvent(Listener listener){

    }



    public void registerListeners(List<Listener> listeners){


        //for (Map.Entry<Class<? extends Event>, Set<RegisteredListener>> entry : getPluginLoader().createRegisteredListeners(listener, this).entrySet()) {
            //entry.getValue().stream().findFirst().get().
             //Bukkit.getPluginManager().getEventListeners(getRegistrationClass(entry.getKey())).registerAll(entry.getValue());
        //}
    }



}
