package pgDev.bukkit.MDPVPControl;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

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
				if (plugin.isDisguised(attacker)) {
					if (plugin.hasPermissions(attacker, "mdpvpcontrol.candisguiseattack")) {
						if (!plugin.hasPermissions(attacker, "mdpvpcontrol.noattackdisguiseloss")) {
							plugin.unDisguise(attacker);
							attacker.sendMessage(ChatColor.RED + "Your disguise was blown.");
						}
					} else {
						event.setCancelled(true);
						attacker.sendMessage(ChatColor.RED + "You cannot attack while disguised!");
					}
				}
			} else if (event.getDamager() instanceof Projectile) {
				if (((Projectile) event.getDamager()).getShooter() instanceof Player) {
					Player attacker = (Player) ((Projectile) event.getDamager()).getShooter();
					if (plugin.isDisguised(attacker)) {
						if (plugin.hasPermissions(attacker, "mdpvpcontrol.candisguiseattack")) {
							if (!plugin.hasPermissions(attacker, "mdpvpcontrol.noattackdisguiseloss")) {
								plugin.unDisguise(attacker);
								attacker.sendMessage(ChatColor.RED + "Your disguise was blown.");
							}
						} else {
							event.setCancelled(true);
							attacker.sendMessage(ChatColor.RED + "You cannot attack while disguised!");
						}
					}
				}
			}
			
			// Disguised lose disguise
			if (!event.isCancelled() && event.getEntity() instanceof Player) {
				Player defender = (Player) event.getEntity();
				if (plugin.isDisguised(defender) && !plugin.hasPermissions(defender, "mdpvpcontrol.nodamagedisguiseloss")) {
					plugin.unDisguise(defender);
					defender.sendMessage(ChatColor.RED + "Your disguise was blown.");
				}
			}
		}
	}
	
	@EventHandler
	public void deathCatcher(EntityDeathEvent e) {
		if (e instanceof PlayerDeathEvent) {
			PlayerDeathEvent event = (PlayerDeathEvent) e;
			Player zombie = (Player) e.getEntity();
			if (plugin.isDisguised(zombie) && plugin.hasPermissions(zombie, "mdpvpcontrol.hidedisguisedeath")) {
				event.setDeathMessage(null);
			}
		}
	}
}
