package fr.aang.glotery.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.aang.glotery.Main;
import fr.aang.glotery.utils.Utils;

public class Config {
	
	private Location				_loc;
	private String					_world_name;
	private	List<Loot>				_items = new ArrayList<Loot>();
	private	int						_total = 0;
	
	private Main					_main;
	private File					_file;
	private YamlConfiguration		_yaml;
	
	public Config(Main main, String file_name) {
		_main = main;
		_yaml = loadConfig(file_name);
		readConfig();
	}
	
	private YamlConfiguration loadConfig(String file_name) {
		
		if(!_main.getDirectory().exists()) {
			_main.getDirectory().mkdir();
		}
		
		_file = new File(_main.getDataFolder(), file_name);
		
		if (!_file.exists()) {
			_main.saveResource(file_name, false);
		}
		
		return YamlConfiguration.loadConfiguration(_file);
	}
	
	private void	addItem(ConfigurationSection section) {
		
		Loot	loot = new Loot();
			
		if (section.isSet("money")) {
			loot.money = section.getInt("money");
			loot.item = Utils.getItem(Material.SUNFLOWER, "§a" + loot.money + "§6$", null, 1, null);
		}
		else if (section.isSet("item")){
			
			Material		material = Material.getMaterial(section.getString("item"));
			if (material == null)
				return ;
			
			String			name = section.getString("name");
			List<String>	lore = section.getStringList("lore");
			List<String>	enchants = section.getStringList("enchant");
			int				quantity = 1;
			
			if (section.isSet("quantity"))
				quantity = section.getInt("quantity");
			loot.item = Utils.getItem(material, name, lore, quantity, enchants);
		}
		else
			return ;
		
		if (section.isSet("prob"))
			loot.prob = section.getInt("prob");
		_total += loot.prob;
		
		_items.add(loot);
	}
	
	private void	readConfig() {
		
		ConfigurationSection section;
		
		_world_name = _yaml.getString("chest.world");
		
		_loc = Utils.parseStringToLoc(_yaml.getString("chest.world"), _yaml.getString("chest.location"));
		
		int i = 0;
		while ((section = _yaml.getConfigurationSection("items." + i)) != null) {
			addItem(section);
			i++;
		}
		
	}
	
	public List<Loot> getLoots() {
		return _items;
	}
	
	public Loot	getLoot(int n) {
		
		int i = 0;
		int	item_i = 0;
		
		while (i <= n) {
			
			i += _items.get(item_i).prob;
			if (i >= n)
				return _items.get(item_i);
			item_i++;
		}
		return null;
	}
	
	// GETTERS
	
	public Location	getLocation() {
		return _loc;
	}
	
	public String	getWorldName() {
		return _world_name;
	}
	
	public int		getTotal() {
		return _total;
	}
	
}
