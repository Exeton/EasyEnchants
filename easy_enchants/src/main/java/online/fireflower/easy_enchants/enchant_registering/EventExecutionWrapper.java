package online.fireflower.easy_enchants.enchant_registering;

import javafx.util.Pair;
import online.fireflower.easy_enchants.Enchant;
import online.fireflower.easy_enchants.procing.IProcedEnchantCuller;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.RegisteredListener;

import java.util.LinkedList;
import java.util.List;

public class EventExecutionWrapper implements EventExecutor {

    public List<Pair<RegisteredListener, Enchant>> listenersAndEnchants = new LinkedList<Pair<RegisteredListener, Enchant>>();
    public Class eventType;
    IProcedEnchantCuller procedEnchantCuller;

    public EventExecutionWrapper(IProcedEnchantCuller procedEnchantCuller, Class eventType){
        this.procedEnchantCuller = procedEnchantCuller;
        this.eventType = eventType;
    }

    public void registerEnchant(RegisteredListener listener, Enchant enchant){
        listenersAndEnchants.add(new Pair<>(listener, enchant));
    }

    public void execute(Listener listener, Event event) throws EventException {

        //Takes in a dummy listener
        if (!eventType.isAssignableFrom(event.getClass())) {
            return;
        }
        LinkedList<Enchant> procedEnchants = new LinkedList<Enchant>();
        for (Pair<RegisteredListener, Enchant> listenerEnchantPair : listenersAndEnchants)
            if (listenerEnchantPair.getValue().shouldProc(event))
                procedEnchants.add(listenerEnchantPair.getValue());

        //Bukkit.getLogger().info("Registered Enchants size: " + listenersAndEnchants.size());
        //Bukkit.getLogger().info("Enchants to run size: " + procedEnchants.size());

        List<Enchant> enchantsToExecute = procedEnchantCuller.cullEnchants(procedEnchants);
        for (Pair<RegisteredListener, Enchant> listenerEnchantPair : listenersAndEnchants){

            if (enchantsToExecute.contains(listenerEnchantPair.getValue()))
                listenerEnchantPair.getKey().callEvent(event);
        }
    }
}
