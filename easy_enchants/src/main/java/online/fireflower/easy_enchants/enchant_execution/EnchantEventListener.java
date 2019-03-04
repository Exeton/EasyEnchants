package online.fireflower.easy_enchants.enchant_execution;

import javafx.util.Pair;
import online.fireflower.easy_enchants.enchant_types.Enchant;
import online.fireflower.easy_enchants.enchant_types.EnchantType;
import online.fireflower.easy_enchants.enchant_parsing.EnchantInfoRetriever;
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

    boolean checkArmor = false;
    boolean checkItem = false;

    public EnchantEventListener(EnchantInfoRetriever enchantInfoRetriever, IEnchantExecutor enchantExecutor, Class eventType){
        this.enchantInfoRetriever = enchantInfoRetriever;
        this.enchantExecutor = enchantExecutor;
        this.eventType = eventType;
    }

    public void registerEnchant(Enchant enchant){
        namesAndEnchants.put(enchant.displayName, enchant);
        if (enchant.getType() == EnchantType.ARMOR_ENCHANT)
            checkArmor = true;
        if (enchant.getType() == EnchantType.ITEM_ENCHANT)
            checkItem = true;
    }

    //Takes inputted listener is a dummy listener and is not used.
    public void execute(Listener listener, Event event) throws EventException {

        if (!eventType.isAssignableFrom(event.getClass())) {
            return;
        }

        Player player = getPlayer(event);
        if (player == null)
            return;

        List<EnchantInfo> enchantInfoList = new LinkedList<>();
        if (checkItem)
            enchantInfoList.addAll(enchantInfoRetriever.getHeldItemEnchants(player));
        if (checkArmor)
            enchantInfoList.addAll(enchantInfoRetriever.getArmorEnchants(player));

        Pair<List<EnchantInfo>, List<Enchant>> activatedEnchants = getActivatedEnchants(enchantInfoList);
        enchantExecutor.execute(event, activatedEnchants.getValue(), activatedEnchants.getKey());
    }

    public Pair<List<EnchantInfo>, List<Enchant>> getActivatedEnchants(List<EnchantInfo> itemEnchants){

        LinkedList<EnchantInfo> enchantInfoList = new LinkedList<>();
        LinkedList<Enchant> enchantsOnItem = new LinkedList<>();

        for (EnchantInfo enchantInfo : itemEnchants){
            Enchant enchant = namesAndEnchants.get(enchantInfo.name);
            if (enchant != null){
                enchantsOnItem.add(enchant);
                enchantInfoList.add(enchantInfo);
            }
        }
        return new Pair<>(enchantInfoList, enchantsOnItem);
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
