package fr.aang.glotery.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.aang.glotery.CrateInventory;
import fr.aang.glotery.Main;
import fr.aang.glotery.utils.FireWork;
import fr.aang.glotery.utils.Utils;

public class LoteryListener implements Listener {

	Main _main;
	
	public LoteryListener(Main main) {
		_main = main;
	}
	
	@EventHandler
	public void onChestClick(PlayerInteractEvent event) {

		if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.ENDER_CHEST) {

			Location	click = event.getClickedBlock().getLocation();
			Location	chest = _main.config.getLocation();

			if (click.getWorld().getName().equals(_main.config.getWorldName())
			&& click.getBlockX() == chest.getBlockX()
			&& click.getBlockY() == chest.getBlockY()
			&&	click.getBlockZ() == chest.getBlockZ()) 
			{
				
				event.setCancelled(true);
				Player player = event.getPlayer();
				
				if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					if (ItemKey.is(player.getInventory().getItemInMainHand())) {
						ItemKey.remove(player);
						openLoterie(player);
					}
					else {
						player.sendMessage("§b[❤] §cVous devez avoir une §bClef de Vote");
					}
				}
				else {
					openListLoot(player);
				}
			}
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		Player player = (Player) event.getWhoClicked();
		ItemStack current = event.getCurrentItem();
		
		// Protection Dans le Menu
		if (current != null && current.getType() != null) {
			if (event.getView().getTitle().equals("§8Lots") || event.getView().getTitle().equals("§8Lotterie")) {
				event.setCancelled(true);
				player.updateInventory();
			}
		}
	}
	
	private void openLoterie(Player player) {

		CrateInventory crate = new CrateInventory(9, _main.config.getLoots(), "§8Lotterie", _main);
		crate.spin(8, player);
		FireWork.voteFirework(player.getLocation(), Color.RED);
	}
	
	private void openListLoot(Player player) {
		
		int slot = 9;
		while (slot < _main.config.getLoots().size())
			slot += 9;

		Inventory inv = Bukkit.createInventory(null, slot, "§8Lots");
		
		for (int i = 0; i < _main.config.getLoots().size(); i++) {
			
			ItemStack item = _main.config.getLoots().get(i).item;
			List<String> lore = new ArrayList<String>();
			double prob = _main.config.getLoots().get(i).prob * 10;
			double total = _main.config.getTotal();
			double percent = Math.round((prob * 100) / total);
			percent = percent / 10;
			
			if (percent > 10)
				lore.add("§a" + percent + "%");
			else if (percent > 5)
				lore.add("§e" + percent + "%");
			else if (percent > 2)
				lore.add("§6" + percent + "%");
			else
				lore.add("§c" + percent + "%");
			
			item = Utils.addLore(item, lore);
			inv.setItem(i, item);
		}
			
		
		player.openInventory(inv);
	}
		
}
