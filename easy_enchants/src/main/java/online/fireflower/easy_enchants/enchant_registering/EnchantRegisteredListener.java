package online.fireflower.easy_enchants.enchant_registering;

import online.fireflower.easy_enchants.enchant_parsing.EnchantInfo;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EnchantRegisteredListener {

    Method method;
    Listener listener;

    public EnchantRegisteredListener(Listener listener, Method method){
        this.method = method;
        this.listener = listener;
    }

    public void executeEvent(Event event, EnchantInfo enchantInfo){
        try {
            method.invoke(listener, event, enchantInfo);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
