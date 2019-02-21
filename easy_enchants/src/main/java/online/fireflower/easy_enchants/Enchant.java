package online.fireflower.easy_enchants;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;


//Todo: Move the event state and info like name, etc. out of the same object.

public abstract class Enchant implements Listener {

    public abstract boolean shouldProc(Event event);


}
