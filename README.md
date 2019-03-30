# EasyEnchants

## Intro
EasyEnchants strives to a simple API to for enchant makers. This plugin does not contain a system for applying enchantments to items. Check out [EnchantBooks](https://github.com/Exeton/EnchantBooks) for features like /enchanter. 


## How it works

![alttext](https://github.com/Exeton/EasyEnchants/blob/master/Pictures/HowItWorks.png)

In order to handle events, a layer of EnchantEventListeners is created between the SpigotAPI and IEnchantExecutor. This consists of multiple instances of EnchantEventListener (multiple are needed because the SpigotAPI requires you to register a different Listener for every event) which check if a player in the event has custom enchants on their item / armor.

This is done by checking (using reflection) if the Event has a getPlayer() or getEntity(), and if that method returns a player. After getting that player, the EnchantEventListener will parse the Enchants on the players Armor and Item in hand based on if the Enchant  depending on enchant.getType(). All enchants will then be passed on to an IEnchantExecutor which execute events like Spigot does, although @EventHandler methods should accept both an Event and EnchantInfo paramater.

## Developer Information
- [Creating Enchants / IEquipables](https://github.com/Exeton/EasyEnchants/blob/master/EnchantCreation.MD)
- [Advanced Topics](https://github.com/Exeton/EasyEnchants/blob/master/AdvancedTopics.md)

## Commands
- /EasyEnchant refName level
