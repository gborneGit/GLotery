package fr.aang.glotery;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import fr.aang.glotery.config.Config;
import fr.aang.glotery.item.LoteryListener;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	

	public Config		config;
	public Economy		eco;
	
	@Override
	public void onEnable() {
		
		if (!setupEconomy()) {
			System.out.println(ChatColor.RED + "[GLotery] You must have Vault");
			getServer().getPluginManager().disablePlugin(this);
			return ;
		}

		config = new Config(this, "config.yml");
		getServer().getPluginManager().registerEvents(new LoteryListener(this), this);
	}
	
	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economy = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economy != null)
			eco = economy.getProvider();
		return (economy != null);
	}
	
	public File getDirectory() {
		return getDataFolder();
	}
}
