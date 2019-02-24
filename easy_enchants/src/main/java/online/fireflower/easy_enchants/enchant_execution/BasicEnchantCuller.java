package online.fireflower.easy_enchants.enchant_execution;

import online.fireflower.easy_enchants.enchant_types.Enchant;

import java.util.List;

public class BasicEnchantCuller implements IActivatedEnchantCuller {
    @Override
    public List<Enchant> cullEnchants(List<Enchant> clonedProcedEnchants) {
        return clonedProcedEnchants;
    }
}
