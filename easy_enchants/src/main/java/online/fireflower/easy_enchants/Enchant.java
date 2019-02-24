package online.fireflower.easy_enchants;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;


//Todo: Move the event state and info like name, etc. out of the same object.

public abstract class Enchant implements Listener {

    public String displayName;

    public Enchant(String displayName){
        this.displayName = displayName;
    }

    public abstract boolean shouldActivate(Event event);

    public abstract EnchantType getType();
}
