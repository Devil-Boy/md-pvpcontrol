package pgDev.bukkit.MDPVPControl;

import me.desmin88.mobdisguise.api.MobDisguiseAPI;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class MDPVPListener implements Listener {
	MDPVPCMain plugin;
	
	public MDPVPListener(final MDPVPCMain pluginI) {
		plugin = pluginI;
	}
	
	@EventHandler
	public void damageCatcher(EntityDamageEvent e) {
		if (e instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;
			
			// Disguised cannot attack
			if (event.getDamager() instanceof Player) {
				Player attacker = (Player) event.getDamager();
				if (MobDisguiseAPI.isDisguised(attacker) && !plugin.hasPermissions(attacker, "mdpvpcontrol.candisguiseattack")) {
					event.setCancelled(true);
					attacker.sendMessage(ChatColor.RED + "You cannot attack while disguised!");
				}
			} else if (event.getDamager() instanceof Projectile) {
				if (((Projectile) event.getDamager()).getShooter() instanceof Player) {
					Player attacker = (Player) ((Projectile) event.getDamager()).getShooter();
					if (MobDisguiseAPI.isDisguised(attacker) && !plugin.hasPermissions(attacker, "mdpvpcontrol.candisguiseattack")) {
						event.setCancelled(true);
						attacker.sendMessage(ChatColor.RED + "You cannot attack while disguised!");
					}
				}
			}
			
			// Disguised lose disguise
			if (!event.isCancelled() && event.getEntity() instanceof Player) {
				Player defender = (Player) event.getEntity();
				if (!plugin.hasPermissions(defender, "mdpvpcontrol.nodamagedisguiseloss")) {
					MobDisguiseAPI.undisguisePlayer(defender);
					defender.sendMessage(ChatColor.RED + "Your disguise was blown.");
				}
			}
		}
	}
}
