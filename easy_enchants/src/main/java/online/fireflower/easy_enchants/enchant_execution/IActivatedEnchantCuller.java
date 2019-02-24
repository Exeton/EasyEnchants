package online.fireflower.easy_enchants.enchant_execution;

import online.fireflower.easy_enchants.Enchant;

import java.util.List;

public interface IActivatedEnchantCuller {

    List<Enchant> cullEnchants(List<Enchant> clonedProcedEnchants);

}
