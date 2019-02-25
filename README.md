# EasyEnchants

## Intro
EasyEnchants is a plugin designed to make creating custom enchanchants eaiser. EasyEnchants provides two methods for adding enchant functionallity: An event based system and an IEquipable interface, which provides functionallity for when armor is equiped / unequiped.

## Creating an enchant
Creating an enchant is simple. Extend Enchant or GivenTypeEnchant (the latter allows you to specify the EnchantType upon object construction as opposed to having to implement EnchantType.getType() in Enchant. [ExampleEnchant](https://github.com/Exeton/EasyEnchants/blob/master/easy_enchants/src/main/java/online/fireflower/easy_enchants/test_ingame/AttackEnchant.java).

**Note: You can only use one event per Enchant. This may change in the future.**

## How are enchants handeled
In order to handle events, EasyEnchants will listen for events inside of classes extending Enchant (assuming they're registered). Those events are listened to inside of instances of EnchantEventListener (1 per each type of event being listened to). When an event is called, an EnchantEventListener will attempt to check if there is a player involved in the event. This is done using reflection to try and run event.getPlayer(), and event.getEntity() and casting the result to a player object. The EnchantEventListener will then get all the enchants on that player's weapons and/or armor and call an IEnchantExecutor.

**Important: Because Event.getEntity() is used to get the player, having an enchant listen to EntityDamagedByEntity will cause the event to fire when defending player has those enchants. Use PlayerDamageEntityEvent instead.** 

You cannot currently add your own IEnchantExecutor, however that is a planned feature. This will give you greater control to determine what enchants are activated. More functionallity for culling activated enchants is also planned.



### IEquipable

Using IEquipable, you gain access to an onEquip(Player) and an onUnequip(Player) method. IEquipable can be used in conjunction with extending Enchant. You can also register an IEquipable and Enchant class under the same name, providing more flexibility.

[Standalone IEquipable](https://github.com/Exeton/EasyEnchants/blob/master/easy_enchants/src/main/java/online/fireflower/easy_enchants/enchant_types/ArmorEffectEnchant.java) |
[Enchant and IEquipable](https://github.com/Exeton/EasyEnchants/blob/master/easy_enchants/src/main/java/online/fireflower/easy_enchants/test_ingame/DefenseArmor.java)

## Creating potion effect equipables
To create an equipable which yeilds a potion effect, create a new instance of ArmorEffectEnchant and register it as an equipable.


## Registering an enchant
Now that you've created your enchants, you have to register them.

<br>

```java
//Just an enchant
EntityDamagedByEntityEnchant testEnchant = new EntityDamagedByEntityEnchant(ChatColor.GOLD + "EntityDamagedByEntityEnchant");
EasyEnchants.registerEnchant("Damage", EntityDamageByEntityEvent.class, testEnchant);

//Just an equipable
EasyEnchants.registerEquipable("Res", ChatColor.GREEN + "Resistance", new ArmorEffectEnchant(PotionEffectType.DAMAGE_RESISTANCE));

//Equipable and enchant
DefenseArmor defenseArmor = new DefenseArmor(ChatColor.RED + "DefensiveEnchant");
EasyEnchants.registerEnchantAndEquipable("Defense", EntityDamageByEntityEvent.class, defenseArmor, defenseArmor);
```



Equipable and enchant. **Note: You can use this to pass in two different classes (an enchant class and an equipable class). Doing this allows you to register an equipable using the same name as the enchant causing both the equipable and enchant code to work under the same name on an itemstack. This is useful if you want to create an enchant which has a potion equip effect, which can be done by passing in an enchant class and a ArmorEffectEnchant.**

