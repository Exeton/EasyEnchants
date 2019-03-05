package online.fireflower.easy_enchants.dependency_injection.creation;

import online.fireflower.easy_enchants.enchant_execution.IEnchantExecutor;
import online.fireflower.easy_enchants.enchant_parsing.EnchantInfoRetriever;
import online.fireflower.easy_enchants.enchant_parsing.IEnchantInfoParser;
import online.fireflower.easy_enchants.enchant_parsing.IEnchantReadWriter;
import online.fireflower.easy_enchants.enchant_registering.EnchantRegisterer;

public interface IDependencyCreator {

    IEnchantInfoParser getEnchantInfoParser();
    IEnchantReadWriter getEnchantReadWriter();
    EnchantInfoRetriever getEnchantInfoRetriever();
    IEnchantExecutor getEnchantExecutor();
    EnchantRegisterer getEnchantRegisterer();

}
