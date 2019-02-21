package online.fireflower.easy_enchants.procing;

import online.fireflower.easy_enchants.Enchant;

import java.util.List;

public interface IProcedEnchantCuller {

    List<Enchant> cullEnchants(List<Enchant> clonedProcedEnchants);

}
