package online.fireflower.easy_enchants.enchant_types;

public abstract class GivenTypeEnchant extends Enchant{

    private EnchantType enchantType;

    public GivenTypeEnchant(EnchantType enchantType, String displayName) {
        super(displayName);
        this.enchantType = enchantType;
    }

    @Override
    public EnchantType getType() {
        return enchantType;
    }
}
