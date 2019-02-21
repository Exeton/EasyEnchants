package online.fireflower.easy_enchants.activation;

import online.fireflower.easy_enchants.Enchant;

import java.util.List;

public interface IActivatedEnchantCuller {

    List<Enchant> cullEnchants(List<Enchant> clonedProcedEnchants);

}
