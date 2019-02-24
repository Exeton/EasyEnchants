package online.fireflower.easy_enchants.enchant_registering;

import javafx.util.Pair;
import online.fireflower.easy_enchants.Enchant;
import online.fireflower.easy_enchants.EnchantInfoRetriever;
import online.fireflower.easy_enchants.activation.IActivatedEnchantCuller;
import online.fireflower.easy_enchants.enchant_parsing.EnchantInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.RegisteredListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class EnchantEventExecutor implements EventExecutor {

    public List<Pair<RegisteredListener, Enchant>> listenersAndEnchants = new LinkedList<Pair<RegisteredListener, Enchant>>();
    public Class eventType;
    IActivatedEnchantCuller procedEnchantCuller;
    public static EnchantInfoRetriever retriever;

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

        Player player = getPlayer(event);
        if (player != null)
            fireEnchants(procedEnchantCuller.cullEnchants(getActivatedEnchants(event, retriever.getHeldItemEnchants(player))), event);
    }

    public List<Enchant> getActivatedEnchants(Event event, List<EnchantInfo> itemEnchants){

        List<String> enchantNames = new LinkedList<>();
        for (EnchantInfo enchantInfo : itemEnchants)
            enchantNames.add(enchantInfo.name);

        LinkedList<Enchant> procedEnchants = new LinkedList<Enchant>();
        for (Pair<RegisteredListener, Enchant> listenerEnchantPair : listenersAndEnchants){
            Enchant enchant = listenerEnchantPair.getValue();
            if (enchantNames.contains(enchant.name.toLowerCase()) && enchant.shouldActivate(event)){
                procedEnchants.add(listenerEnchantPair.getValue());
                Bukkit.getLogger().info("Firing enchant");
            }
            else{
                Bukkit.getLogger().info("Not firing: " + enchant.name.toLowerCase());
                Bukkit.getLogger().info("Available: " + String.join(" ", enchantNames));
            }
        }


        return procedEnchants;
    }

    public void fireEnchants(List<Enchant> activatedEnchants, Event event) throws EventException {
        for (Pair<RegisteredListener, Enchant> listenerEnchantPair : listenersAndEnchants)
            if (activatedEnchants.contains(listenerEnchantPair.getValue()))
                listenerEnchantPair.getKey().callEvent(event);
    }

    public Player getPlayer(Event event){

        Player player = null;

        try {
            Method method = event.getClass().getMethod("getPlayer", null);
            player = (Player)method.invoke(event, null);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            try{
                Method method = event.getClass().getMethod("getEntity", null);
                Entity entity = (Entity) method.invoke(event, null);

                if (entity instanceof Player)
                    player = (Player)entity;
            }
            catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }

        return player;
    }

}
