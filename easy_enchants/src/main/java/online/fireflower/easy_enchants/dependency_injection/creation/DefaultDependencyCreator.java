package online.fireflower.easy_enchants.dependency_injection.creation;

import online.fireflower.easy_enchants.EasyEnchants;
import online.fireflower.easy_enchants.enchant_execution.BasicEnchantCuller;
import online.fireflower.easy_enchants.enchant_execution.BasicEnchantExecutor;
import online.fireflower.easy_enchants.enchant_execution.IEnchantExecutor;
import online.fireflower.easy_enchants.enchant_parsing.*;
import online.fireflower.easy_enchants.enchant_parsing.numbers.RomanNumeralParser;
import online.fireflower.easy_enchants.enchant_registering.EnchantRegisterer;

import java.util.HashMap;

public class DefaultDependencyCreator implements IDependencyCreator {

    EasyEnchants main;

    public DefaultDependencyCreator(){
        main = EasyEnchants.getInstance();
    }

    @Override
    public IEnchantInfoParser getEnchantInfoParser() {
        return new BasicEnchantInfoParser(main.enchants, RomanNumeralParser.getInstance());
    }

    @Override
    public IEnchantReadWriter getEnchantReadWriter() {
        return new BasicEnchantReadWriter(main.dependencyRetriever.getEnchantInfoParser());
    }

    @Override
    public EnchantInfoRetriever getEnchantInfoRetriever() {
        return new EnchantInfoRetriever(main.dependencyRetriever.getEnchantReadWriter());
    }

    @Override
    public IEnchantExecutor getEnchantExecutor() {
        return new BasicEnchantExecutor(new HashMap<>(), new BasicEnchantCuller());
    }

    @Override
    public EnchantRegisterer getEnchantRegisterer() {
        return new EnchantRegisterer(main.dependencyRetriever.getEnchantExecutor(), main.dependencyRetriever.getEnchantInfoRetriever(), main);
    }
}
