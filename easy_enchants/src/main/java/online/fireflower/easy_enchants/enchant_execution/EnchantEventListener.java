package online.fireflower.easy_enchants.enchant_execution;

import online.fireflower.easy_enchants.Enchant;
import online.fireflower.easy_enchants.EnchantInfoRetriever;
import online.fireflower.easy_enchants.enchant_parsing.EnchantInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EnchantEventListener implements EventExecutor {

    private EnchantInfoRetriever enchantInfoRetriever;
    private Map<String, Enchant> namesAndEnchants = new HashMap<>();
    private IEnchantExecutor enchantExecutor;
    private Class eventType;

    public EnchantEventListener(EnchantInfoRetriever enchantInfoRetriever, IEnchantExecutor enchantExecutor, Class eventType){
        this.enchantInfoRetriever = enchantInfoRetriever;
        this.enchantExecutor = enchantExecutor;
        this.eventType = eventType;
    }

    public void registerEnchant(Enchant enchant){
        namesAndEnchants.put(enchant.displayName.toLowerCase(), enchant);
    }

    //Takes inputted listener is a dummy listener and is not used.
    public void execute(Listener listener, Event event) throws EventException {

        if (!eventType.isAssignableFrom(event.getClass())) {
            return;
        }

        Player player = getPlayer(event);
        if (player != null)
            enchantExecutor.execute(event, getActivatedEnchants(enchantInfoRetriever.getHeldItemEnchants(player)));
    }

    public List<Enchant> getActivatedEnchants(List<EnchantInfo> itemEnchants){

        LinkedList<Enchant> enchantsOnItem = new LinkedList<>();
        for (EnchantInfo enchantInfo : itemEnchants){
            Enchant enchant = namesAndEnchants.get(enchantInfo.name);
            if (enchant != null)
                enchantsOnItem.add(enchant);
            else{
                Bukkit.getLogger().info("Could not get enchant: " + enchantInfo.name);
            }
        }
        return enchantsOnItem;
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
