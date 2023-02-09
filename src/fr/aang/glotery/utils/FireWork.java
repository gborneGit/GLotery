package fr.aang.glotery.utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireWork {

	
	public static void voteFirework(Location loc, Color color) {
		Firework firework = loc.getWorld().spawn(loc, Firework.class);
		FireworkMeta data = (FireworkMeta) firework.getFireworkMeta();
		
		FireworkEffect effect = FireworkEffect.builder().withColor(color).with(Type.BALL_LARGE).withFlicker().build();
		
		data.addEffect(effect);
		data.setPower(1);
		firework.setFireworkMeta(data);
	}
}
