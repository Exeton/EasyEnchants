# Enchant Creation
## Intro
Enchants can listen for Spigot events, and/or run code when armor is equiped and unequiped.

## Creating Enchants
To create an Enchant, extend Enchant, GivenTypeEnchant, or RandomEnchant. Then add a special @EventHandler method. Unlike events in Spigot, EasyEnchant events require an EnchantInfo paramater. Enchant classes can only have one method marked @EventHandler.

```java
public class AttackEnchant extends Enchant {

    public AttackEnchant(String name) {
        super(name);
    }

    @EventHandler
    public void handleEvent(PlayerDamageEntityEvent event, EnchantInfo enchantInfo){
        event.event.setDamage(10 * enchantInfo.level);
    }

    @Override
    public boolean shouldActivate(Event event, EnchantInfo enchantInfo) {
        return true;
    }

    @Override
    public EnchantType getType() {
        return EnchantType.ITEM_ENCHANT;
    }
}
```

## Custom Events
If your enchant listens for the EntityDamagedByEntity or EntityDeathEvent event, the enchant will only activate for the entity taking damage or dying. For example if you have a DamageBoost enchant, which listens for the EntityDamagedByEntity event, damage boost will only activate if the defender has an item with the DamageBoost enchant. This is because EasyEnchants calls event.getEntity and event.getPlayer and then checks if that player has enchants which need to be activated. In the case of the EntityDamagedByEntity event, getEntity returns the damaged entity. To get around this, EasyEnchants provides a PlayerDamageEntityEvent and a PlayerKillEntityEvent.  You can also create your own events and have enchants listen to them.

## Registering Enchants

To register an enchant, simply pass in the refference name (used for the /easyenchant command) and the enchant object.

```java
        AttackEnchant testEnchant = new AttackEnchant(ChatColor.GOLD + "AttackEnchant");
        EasyEnchants.registerEnchant("Damage", testEnchant);
```

## Equipables

Like Enchants, Equipables also take a spot on an item's lore. The only difference is in terms of functionallity. Equipables only provide access to onEquip and onUnequip. These can be used for tasks like adding a potion effect.

[Standalone IEquipable](https://github.com/Exeton/EasyEnchants/blob/master/easy_enchants/src/main/java/online/fireflower/easy_enchants/enchant_types/ArmorEffectEnchant.java) |
[Enchant and IEquipable](https://github.com/Exeton/EasyEnchants/blob/master/easy_enchants/src/main/java/online/fireflower/easy_enchants/test_ingame/DefenseArmor.java)

## Potion Effect Equipables and Registering Equipables

```java
//Standalone IEquipable
registerEquipable("Res", ChatColor.GREEN + "Resistance", new ArmorEffectEnchant(PotionEffectType.DAMAGE_RESISTANCE));

//IEquipable and Enchant
DefenseArmor defenseArmor = new DefenseArmor(ChatColor.RED + "DefensiveEnchant");
registerEnchantAndEquipable("Defense", defenseArmor, defenseArmor);
```

**Note: You can use registerEnchantAndEquipable and pass in two different classes (an enchant class and an equipable class). Doing this allows you to register an equipable using the same name as the enchant causing both the equipable and enchant code to work under the same name on an itemstack. This is useful if you want to create an enchant which has a potion equip effect, which can be done by passing in an enchant class and a ArmorEffectEnchant.**
