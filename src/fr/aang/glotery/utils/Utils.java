package fr.aang.glotery.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {
	
	public static Location parseStringToLoc(String world, String string) {
		
		String[] parseLoc = string.split(",");
		
		int x = Integer.valueOf(parseLoc[0]);
		int y = Integer.valueOf(parseLoc[1]);
		int z = Integer.valueOf(parseLoc[2]);
		
		Location loc = new Location(Bukkit.getWorld(world), x, y, z);
		
		return loc;
	}
	
	public static ItemStack getItem(Material material, String custom_name, List<String> lore, int amount, List<String> enchants) {
		
		ItemStack item = new ItemStack(material, amount);
		ItemMeta itemM = item.getItemMeta();
		if (custom_name != null)
			itemM.setDisplayName(custom_name);
		if (lore != null && !lore.isEmpty())
			itemM.setLore(lore);
		if (enchants != null) {
			for (int i = 0; i < enchants.size(); i++) {
				String[] enchant = enchants.get(i).split(":");
				Enchantment key = getEnchantment(enchant[0]);
				System.out.println("Enchant OK");
				if (key != null) {
					System.out.println("Enchant OK2");
					itemM.addEnchant(key, Integer.valueOf(enchant[1]), false);
				}
	
			}
		}
		item.setItemMeta(itemM);;
		return item;
	}
	
	public static String getItemName(ItemStack item) {
		if (item.getItemMeta() != null && !item.getItemMeta().getDisplayName().isEmpty()) {
			return item.getItemMeta().getDisplayName();
		}
		else {
			return item.getType().name().replace("_", " ").toLowerCase();
		}
	}

	public static boolean hasAvaliableSlot(Player player,int howmanyclear) {
		
		Inventory inv = player.getInventory();
		int check = 0;
		
		for (int i = 0; i < 36; i++) {
		    ItemStack item = inv.getItem(i);
		    if (item == null || item.getType() == Material.AIR) {
		        check++;
		    }
		}
		if(check >= howmanyclear)
			return true;
		else
			return false;
	}
	
	public static ItemStack addLore(ItemStack item, List<String> lore) {
		
		ItemStack new_item = item.clone();
		
		ItemMeta itemM = new_item.getItemMeta();
		List<String> new_lore = itemM.getLore();
		
		if (new_lore == null)
			new_lore = new ArrayList<String>();
			
		for (int i = 0; i < lore.size(); i++) {
			new_lore.add(lore.get(i));
		}
		itemM.setLore(new_lore);
		new_item.setItemMeta(itemM);
		return new_item;
	}
	
	public static Enchantment getEnchantment(String name) {
	    switch (name.trim()) {
	        case "PROTECTION_ENVIRONMENTAL":
	            return Enchantment.PROTECTION_ENVIRONMENTAL;
	        case "PROTECTION_FIRE":
	            return Enchantment.PROTECTION_FIRE;
	        case "PROTECTION_FALL":
	            return Enchantment.PROTECTION_FALL;
	        case "PROTECTION_EXPLOSIONS":
	            return Enchantment.PROTECTION_EXPLOSIONS;
	        case "PROTECTION_PROJECTILE":
	            return Enchantment.PROTECTION_PROJECTILE;
	        case "OXYGEN":
	            return Enchantment.OXYGEN;
	        case "WATER_WORKER":
	            return Enchantment.WATER_WORKER;
	        case "THORNS":
	            return Enchantment.THORNS;
	        case "DURABILITY":
	            return Enchantment.DURABILITY;
	        case "DAMAGE_ALL":
	            return Enchantment.DAMAGE_ALL;
	        case "DAMAGE_UNDEAD":
	            return Enchantment.DAMAGE_UNDEAD;
	        case "DAMAGE_ARTHROPODS":
	            return Enchantment.DAMAGE_ARTHROPODS;
	        case "KNOCKBACK":
	            return Enchantment.KNOCKBACK;
	        case "FIRE_ASPECT":
	            return Enchantment.FIRE_ASPECT;
	        case "LOOT_BONUS_MOBS":
	            return Enchantment.LOOT_BONUS_MOBS;
	        case "DIG_SPEED":
	            return Enchantment.DIG_SPEED;
	        case "SILK_TOUCH":
	            return Enchantment.SILK_TOUCH;
	        case "LOOT_BONUS_BLOCKS":
	            return Enchantment.LOOT_BONUS_BLOCKS;
	        case "ARROW_DAMAGE":
	            return Enchantment.ARROW_DAMAGE;
	        case "ARROW_KNOCKBACK":
	            return Enchantment.ARROW_KNOCKBACK;
	        case "ARROW_FIRE":
	            return Enchantment.ARROW_FIRE;
	        case "ARROW_INFINITE":
	            return Enchantment.ARROW_INFINITE;
	        case "MULTISHOT":
	        	return Enchantment.MULTISHOT;
	        default:
	            return null;
	    }
	}

}
