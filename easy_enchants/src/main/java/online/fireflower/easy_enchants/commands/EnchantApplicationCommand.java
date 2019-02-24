package online.fireflower.easy_enchants.commands;

import online.fireflower.easy_enchants.enchant_parsing.EnchantInfo;
import online.fireflower.easy_enchants.enchant_parsing.IEnchantReadWriter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class EnchantApplicationCommand implements CommandExecutor {

    HashMap<String, String> lowercaseEquipableKeywordsAndNames;
    HashMap<String, String> lowercaseEnchantKeyworkdsAndNames;
    IEnchantReadWriter enchantReadWriter;

    public EnchantApplicationCommand(HashMap<String, String> lowercaseEnchantKeyworkdsAndNames, HashMap<String, String> lowercaseEquipableKeywordsAndNames, IEnchantReadWriter enchantReadWriter){
        this.lowercaseEnchantKeyworkdsAndNames = lowercaseEnchantKeyworkdsAndNames;
        this.enchantReadWriter = enchantReadWriter;
        this.lowercaseEquipableKeywordsAndNames = lowercaseEquipableKeywordsAndNames;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length < 2)
            return false;

        if (!(commandSender instanceof Player)){
            commandSender.sendMessage("You must be a player to use this command");
            return true;
        }

        Player player = (Player)commandSender;
        ItemStack item = player.getItemInHand();

        String name = lowercaseEnchantKeyworkdsAndNames.get(args[0].toLowerCase());
        if (name == null)
            name = lowercaseEquipableKeywordsAndNames.get(args[0].toLowerCase());

        if (name == null){
            player.sendMessage(ChatColor.YELLOW + "Could not find enchant: " + args[0]);
            for (String key : lowercaseEnchantKeyworkdsAndNames.keySet()){
                player.sendMessage(ChatColor.YELLOW + "Available Enchant: " + key);
            }
            for (String key : lowercaseEquipableKeywordsAndNames.keySet()){
                player.sendMessage(ChatColor.YELLOW + "Available Equipable: " + key);
            }
            return true;
        }

        enchantReadWriter.addEnchant(item, new EnchantInfo(name, Integer.parseInt(args[1])));
        player.sendMessage(ChatColor.GREEN + "Enchant applied!");

        return true;
    }
}
