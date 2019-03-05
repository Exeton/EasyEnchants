package online.fireflower.easy_enchants.enchant_execution;

import online.fireflower.easy_enchants.enchant_parsing.EnchantInfo;
import online.fireflower.easy_enchants.enchant_registering.EnchantRegisteredListener;
import online.fireflower.easy_enchants.enchant_types.Enchant;
import org.bukkit.event.Event;

import java.util.List;
import java.util.Map;

public class BasicEnchantExecutor implements IEnchantExecutor {

    public Map<Enchant, EnchantRegisteredListener> enchantsAndListeners;
    IActivatedEnchantCuller enchantCuller;

    public BasicEnchantExecutor(Map<Enchant, EnchantRegisteredListener> enchantsAndListeners, IActivatedEnchantCuller enchantCuller){
        this.enchantsAndListeners = enchantsAndListeners;
        this.enchantCuller = enchantCuller;
    }

    @Override
    public void execute(Event event, List<Enchant> enchants, List<EnchantInfo> enchantInfoList) {

        try{
            enchants = enchantCuller.cullEnchants(enchants);
            for (int i = 0; i < enchants.size(); i++){
                Enchant enchant = enchants.get(i);
                EnchantInfo enchantInfo = enchantInfoList.get(i);
                if (enchant.shouldActivate(event, enchantInfo))
                    enchantsAndListeners.get(enchant).executeEvent(event, enchantInfo);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void addEnchant(Enchant enchant, EnchantRegisteredListener enchantRegisteredListener) {
        enchantsAndListeners.put(enchant, enchantRegisteredListener);
    }
}
