package online.fireflower.easy_enchants.enchant_types;

import online.fireflower.easy_enchants.enchant_parsing.EnchantInfo;
import org.bukkit.event.Event;

import java.util.Random;

public abstract class RandomEnchant extends Enchant {

    public Random random;

    public RandomEnchant(String displayName, Random random) {
        super(displayName);
        this.random = random;
    }

    @Override
    public boolean shouldActivate(Event event, EnchantInfo enchantInfo) {
        return random.nextInt(100) <= calculatePercentActivation(event, enchantInfo);
    }

    public abstract int calculatePercentActivation(Event event, EnchantInfo enchantInfo);
}
