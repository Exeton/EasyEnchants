# EasyEnchants
Used to create custom enchants

## Creating an Enchant
### Enchant
The abstract Enchant class is used to create enchants which fire on events. You will need to implement shouldActivate(Event event) and getType(). If you don't want to implement getType, you can use GivenTypeEnchant which allows you to pass it in to the enchant as a constructor paramater.
To add an event to the class, add a method with the @EventHandeler annotation. The Enchant class already implements listener so you don't need to.

<br>

Here's an example

```java
package online.fireflower.easy_enchants.test_ingame;

import online.fireflower.easy_enchants.enchant_types.Enchant;
import online.fireflower.easy_enchants.enchant_types.EnchantType;
import online.fireflower.easy_enchants.enchant_types.IEquipable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DefenseArmor extends Enchant implements IEquipable {
    public DefenseArmor(String displayName) {
        super(displayName);
    }
    
    @EventHandler
    public void onEvent(EntityDamageByEntityEvent event){
        Bukkit.getLogger().info("Event fired");
    }
    
    @Override
    public boolean shouldActivate(Event event) {
        return true;
    }

    @Override
    public EnchantType getType() {
        return EnchantType.ARMOR_ENCHANT;
    }

```

### IEquipable
Equipables are enchants that listen for when an armor piece is equipped / unequipped. You can implement this interface to add equip and unequip functionallity to armor. If you want to handle equiping and an event in the same class, you'll need to extend Enchant and implement IEquipable.

<br>

Here's an example

```java
package online.fireflower.easy_enchants.test_ingame;

import online.fireflower.easy_enchants.enchant_types.Enchant;
import online.fireflower.easy_enchants.enchant_types.EnchantType;
import online.fireflower.easy_enchants.enchant_types.IEquipable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DefenseArmor extends Enchant implements IEquipable {
    public DefenseArmor(String displayName) {
        super(displayName);
    }

    @EventHandler
    public void onEvent(EntityDamageByEntityEvent event){
        Bukkit.getLogger().info("Event fired");
    }

    @Override
    public void onEquip(Player player, int level) {
        player.sendMessage("Equiped");
    }

    @Override
    public void onUnequip(Player player) {
        player.sendMessage("Unequiped");
    }

    @Override
    public boolean shouldActivate(Event event) {
        return true;
    }

    @Override
    public EnchantType getType() {
        return EnchantType.ARMOR_ENCHANT;
    }
}

```

## Creating potion effect equipables
To create an equipable which yeilds a potion effect, create a new instance of ArmorEffectEnchant and register it as an equipable.


## Registering an enchant
Now that you've created your enchants, you have to register them.

<br>
Just an enchant
```java
        EntityDamagedByEntityEnchant testEnchant = new EntityDamagedByEntityEnchant(ChatColor.GOLD + "EntityDamagedByEntityEnchant");
        EasyEnchants.registerEnchant("Damage", EntityDamageByEntityEvent.class, testEnchant);
```

Just an equipable
```java
        EasyEnchants.registerEquipable("Res", ChatColor.GREEN + "Resistance", new ArmorEffectEnchant(PotionEffectType.DAMAGE_RESISTANCE));
```

Equipable and enchant
```java
        DefenseArmor defenseArmor = new DefenseArmor(ChatColor.RED + "DefensiveEnchant");
        EasyEnchants.registerEnchantAndEquipable("Defense", EntityDamageByEntityEvent.class, defenseArmor, defenseArmor);
```
