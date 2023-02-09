package fr.aang.glotery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.BukkitRunnable;

import fr.aang.glotery.config.Loot;
import fr.aang.glotery.utils.Utils;

/**
 * Created by Zombie_Striker.
 * https://bukkit.org/threads/csgo-crates-create-inventorys
 * -the-cycle-through-items.431949
 */

public class CrateInventory {

   private Inventory	_inv;
   private Main			_main;
   private List<Loot>	_contents;

   private int itemIndex = 0;

   public CrateInventory(int size, List<Loot> contents, String name, Main main) {
	   
	   this._inv = Bukkit.createInventory(null, size, name);
	   this._main = main;
	   this._contents = contents;
	   shuffle();
   }
   
   public void shuffle() {
	   
	   int[] ints = new Random().ints(0, _main.config.getTotal() - 1).distinct().limit(_inv.getSize()).toArray();
	   List<Loot> new_content = new ArrayList<Loot>();
	   
	   for (int i = 0; i < _inv.getSize(); i++) {
		   _inv.setItem(i, _main.config.getLoot(ints[i]).item);
		   new_content.add( _main.config.getLoot(ints[i]));
	   }
	   _contents = new_content;
   }

   public void setContents(List<Loot> contents) {
     this._contents = contents;
   }

   public List<Loot> getContents() {
     return this._contents;
   }

   public Inventory getInventory() {
     return _inv;
   }
   
   public void giveTask(Player player, ItemStack item) {
		
		 new BukkitRunnable() {
	        @Override
	        public void run() {
	        	    	
	        	if (Utils.hasAvaliableSlot(Bukkit.getPlayer(player.getName()), 1)) {
					player.getInventory().addItem(item);
					player.sendMessage("§a[⛃] §aVous avez reçu §e" + item.getAmount() + "§a x §e" + Utils.getItemName(item));
					cancel();
	        	}
	        	else {
	        		player.sendMessage("§c[⛃] §cVous avez §e1 récompense §cen attente, inventaire plein");
	        	}
	        }
		}.runTaskTimer(_main, 100, 20L);
		return ;
	}

   public void spin(final double seconds, final Player player) {
	   
	   player.openInventory(this._inv);
	     
	   new BukkitRunnable() {
		   
		   double delay = 0;
		   int ticks = 0;
		   boolean done = false;
		   
		   public void run() {
			   
			   if (done)
				   return;
			   ticks++;
			   delay += 1 / (20 * seconds);
			   
			   if (ticks > delay * 10) {
           
				   ticks = 0;
				   for (int itemstacks = 0; itemstacks < _inv.getSize(); itemstacks++)
					   _inv.setItem(itemstacks, _contents.get((itemstacks + itemIndex) % _contents.size()).item);
				   
				   itemIndex++;
				   
				   if (delay >= 1) {
					   done = true;
					   new BukkitRunnable() {

						   @Override
						   public void run() {
							   
							   player.closeInventory();
							   int money = _contents.get((4 + itemIndex - 1) % _contents.size()).money ;
							   if (money > 0) {
								   _main.eco.depositPlayer(player, money);
								   player.sendMessage("§a[⛃] §aVous avez reçu §6" + money + "⛃");
							   }
							   else {
								   ItemStack item = _contents.get((4 + itemIndex - 1) % _contents.size()).item;
								   
								   if (Utils.hasAvaliableSlot(player, 1)) {
										player.sendMessage("§a[⛃] §aVous avez reçu §e" + item.getAmount() + "§a x §e" + Utils.getItemName(item));
										player.getInventory().addItem(item);
									}
									else {
										giveTask(player, item);
									}
							   }
							   cancel();
						   }
						   
					   }.runTaskLater(_main, 50);
					   cancel();
				   }
			   }
		   }
	   }.runTaskTimer(_main, 0, 1);
   
   }
}