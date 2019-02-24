package online.fireflower.easy_enchants.enchant_types;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public abstract class Enchant implements Listener {

    public String displayName;

    public Enchant(String displayName){
        this.displayName = displayName;
    }

    public abstract boolean shouldActivate(Event event);

    public abstract EnchantType getType();
}
