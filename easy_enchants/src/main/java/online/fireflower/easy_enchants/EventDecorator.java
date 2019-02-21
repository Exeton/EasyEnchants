package online.fireflower.easy_enchants;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EventDecorator implements Listener {

    @EventHandler
    public void runEvent(Event event){
        Bukkit.getLogger().info("Ran Event");
    }

}
