package online.fireflower.easy_enchants.enchant_registering;

import online.fireflower.easy_enchants.EasyEnchants;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.RegisteredListener;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class EnchantRegisterer {

    private EasyEnchants main;

    private Map<Class, List<RegisteredListener>> eventTypesAndListeners;
    private List<Type> types;


    public EnchantRegisterer(EasyEnchants easyEnchants){
        this.main = easyEnchants;

        registerEnchant(EntityDamageEvent.class);

    }


    public void registerEnchant(Type type){

        types.add(type);




    }


}
