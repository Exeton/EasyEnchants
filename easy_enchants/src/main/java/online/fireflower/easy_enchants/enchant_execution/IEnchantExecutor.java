package online.fireflower.easy_enchants.enchant_execution;

import online.fireflower.easy_enchants.enchant_parsing.EnchantInfo;
import online.fireflower.easy_enchants.enchant_types.Enchant;
import org.bukkit.event.Event;

import java.util.List;

public interface IEnchantExecutor {

    void execute(Event event, List<Enchant> enchants, List<EnchantInfo> enchantInfo);

}
