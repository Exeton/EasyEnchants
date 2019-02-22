package online.fireflower.easy_enchants.enchant_registering;

import javafx.util.Pair;
import online.fireflower.easy_enchants.Enchant;
import online.fireflower.easy_enchants.activation.IActivatedEnchantCuller;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.RegisteredListener;

import java.util.LinkedList;
import java.util.List;

public class EnchantEventExecutor implements EventExecutor {

    public List<Pair<RegisteredListener, Enchant>> listenersAndEnchants = new LinkedList<Pair<RegisteredListener, Enchant>>();
    public Class eventType;
    IActivatedEnchantCuller procedEnchantCuller;

    public EnchantEventExecutor(IActivatedEnchantCuller procedEnchantCuller, Class eventType){
        this.procedEnchantCuller = procedEnchantCuller;
        this.eventType = eventType;
    }

    public void registerEnchant(RegisteredListener listener, Enchant enchant){
        listenersAndEnchants.add(new Pair<>(listener, enchant));
    }


    //Takes inputted listener is a dummy listener and is not used.
    public void execute(Listener listener, Event event) throws EventException {

        if (!eventType.isAssignableFrom(event.getClass())) {
            return;
        }

        fireEnchants(procedEnchantCuller.cullEnchants(getActivatedEnchants(event)), event);
    }

    public List<Enchant> getActivatedEnchants(Event event){
        LinkedList<Enchant> procedEnchants = new LinkedList<Enchant>();
        for (Pair<RegisteredListener, Enchant> listenerEnchantPair : listenersAndEnchants)
            if (listenerEnchantPair.getValue().shouldActivate(event))
                procedEnchants.add(listenerEnchantPair.getValue());

        return procedEnchants;
    }

    public void fireEnchants(List<Enchant> activatedEnchants, Event event) throws EventException {
        for (Pair<RegisteredListener, Enchant> listenerEnchantPair : listenersAndEnchants)
            if (activatedEnchants.contains(listenerEnchantPair.getValue()))
                listenerEnchantPair.getKey().callEvent(event);
    }

}
