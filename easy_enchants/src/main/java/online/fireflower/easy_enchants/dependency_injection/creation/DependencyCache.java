package online.fireflower.easy_enchants.dependency_injection.creation;

import online.fireflower.easy_enchants.enchant_execution.IEnchantExecutor;
import online.fireflower.easy_enchants.enchant_parsing.EnchantInfoRetriever;
import online.fireflower.easy_enchants.enchant_parsing.IEnchantInfoParser;
import online.fireflower.easy_enchants.enchant_parsing.IEnchantReadWriter;
import online.fireflower.easy_enchants.enchant_registering.EnchantRegisterer;

public class DependencyCache extends DependencyCreatorDecorator {

    IEnchantInfoParser IEnchantInfoParser;
    IEnchantReadWriter IEnchantReadWriter;
    EnchantInfoRetriever EnchantInfoRetriever;
    IEnchantExecutor IEnchantExecutor;
    EnchantRegisterer EnchantRegisterer;

    public DependencyCache(IDependencyCreator dependencyCreator) {
        super(dependencyCreator);
    }

    @Override
    public IEnchantInfoParser getEnchantInfoParser() {
        if (IEnchantInfoParser == null)
            IEnchantInfoParser = decorated.getEnchantInfoParser();

        return IEnchantInfoParser;
    }

    @Override
    public IEnchantReadWriter getEnchantReadWriter() {
        if (IEnchantReadWriter == null)
            IEnchantReadWriter = decorated.getEnchantReadWriter();

        return IEnchantReadWriter;
    }

    @Override
    public EnchantInfoRetriever getEnchantInfoRetriever() {
        if (EnchantInfoRetriever == null)
            EnchantInfoRetriever = decorated.getEnchantInfoRetriever();

        return EnchantInfoRetriever;
    }

    @Override
    public IEnchantExecutor getEnchantExecutor() {
        if (IEnchantExecutor == null)
            IEnchantExecutor = decorated.getEnchantExecutor();

        return IEnchantExecutor;
    }

    @Override
    public EnchantRegisterer getEnchantRegisterer() {
        if (EnchantRegisterer == null)
            EnchantRegisterer = decorated.getEnchantRegisterer();

        return EnchantRegisterer;
    }
}
