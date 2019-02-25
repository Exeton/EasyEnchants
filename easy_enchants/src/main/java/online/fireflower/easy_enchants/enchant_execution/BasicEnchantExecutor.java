package online.fireflower.easy_enchants.enchant_execution;

import online.fireflower.easy_enchants.enchant_types.Enchant;
import org.bukkit.event.Event;
import org.bukkit.plugin.RegisteredListener;

import java.util.List;
import java.util.Map;

public class BasicEnchantExecutor implements IEnchantExecutor {

    public Map<Enchant, RegisteredListener> enchantsAndListeners;
    IActivatedEnchantCuller enchantCuller;

    public BasicEnchantExecutor(Map<Enchant, RegisteredListener> enchantsAndListeners, IActivatedEnchantCuller enchantCuller){
        this.enchantsAndListeners = enchantsAndListeners;
        this.enchantCuller = enchantCuller;
    }

    @Override
    public void execute(Event event, List<Enchant> enchants) {

        try{
            enchants = enchantCuller.cullEnchants(enchants);
            //Bukkit.getLogger().info("Firing x enchants: " + enchants.size());
            for (Enchant enchant : enchants)
                if (enchant.shouldActivate(event))
                    enchantsAndListeners.get(enchant).callEvent(event);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
