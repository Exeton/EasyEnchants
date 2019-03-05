package online.fireflower.easy_enchants.dependency_injection.creation;

import online.fireflower.easy_enchants.enchant_execution.IEnchantExecutor;
import online.fireflower.easy_enchants.enchant_parsing.EnchantInfoRetriever;
import online.fireflower.easy_enchants.enchant_parsing.IEnchantInfoParser;
import online.fireflower.easy_enchants.enchant_parsing.IEnchantReadWriter;
import online.fireflower.easy_enchants.enchant_registering.EnchantRegisterer;

public class DependencyCreatorDecorator implements IDependencyCreator {

    IDependencyCreator decorated;

    public DependencyCreatorDecorator(IDependencyCreator dependencyCreator){
        decorated = dependencyCreator;
    }

    @Override
    public IEnchantInfoParser getEnchantInfoParser() {
        return decorated.getEnchantInfoParser();
    }

    @Override
    public IEnchantReadWriter getEnchantReadWriter() {
        return decorated.getEnchantReadWriter();
    }

    @Override
    public EnchantInfoRetriever getEnchantInfoRetriever() {
        return decorated.getEnchantInfoRetriever();
    }

    @Override
    public IEnchantExecutor getEnchantExecutor() {
        return decorated.getEnchantExecutor();
    }

    @Override
    public EnchantRegisterer getEnchantRegisterer() {
        return decorated.getEnchantRegisterer();
    }
}
