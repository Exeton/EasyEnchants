# EasyEnchants

## Intro
EasyEnchants is a Minecraft Spigot plugin that strives to a simple API to for enchant makers. This plugin does not contain a system for applying enchantments to items. Check out [EnchantBooks](https://github.com/Exeton/EnchantBooks) for features like /enchanter. 

## Advantages
1. Automatically checks if an Enchant is activated before calling that Enchant's event.
2. Events are now structured as public void myEvent(EntityDamagedByEntity event, EnchantInfo enchantInfo) which gives you access to information like the Enchant's name and level.
3. You have control over how Enchants are executed. This can be used to do things like prevent two enchants from activating at the same time.
4. You have access to enchant reading and writing. This means you can choose if your enchant levels are in roman numerals, 1-10 numbers, or even if they're in hex.

## How it works

![alttext](https://github.com/Exeton/EasyEnchants/blob/master/Pictures/HowItWorks.png)

In order to handle events, a layer of EnchantEventListeners is created between the SpigotAPI and IEnchantExecutor. This consists of multiple instances of EnchantEventListener (multiple are needed because the SpigotAPI requires you to register a different Listener for every event) which check if a player in the event has custom enchants on their item / armor.

This is done by checking (using reflection) if the Event has a getPlayer() or getEntity() method, and then invoking it and seeing if the result is a player. After getting that player, the EnchantEventListener will parse the Enchants on the players Armor and Item in hand based on if the Enchant  depending on enchant.getType(). All enchants will then be passed on to an IEnchantExecutor which execute events like Spigot does, although @EventHandler methods should accept both an Event and EnchantInfo paramater.

## Developer Information
- [Creating Enchants / IEquipables](https://github.com/Exeton/EasyEnchants/blob/master/EnchantCreation.MD)
- [Advanced Topics](https://github.com/Exeton/EasyEnchants/blob/master/AdvancedTopics.md)

## Commands

- `/EasyEnchant refName level`
