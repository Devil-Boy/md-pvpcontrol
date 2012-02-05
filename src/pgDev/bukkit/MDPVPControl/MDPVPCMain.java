package pgDev.bukkit.MDPVPControl;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class MDPVPCMain extends JavaPlugin {
	// Listener
	public final MDPVPListener listener = new MDPVPListener(this);
	
	// Permissions Integration
    private static PermissionHandler Permissions;
    
    // File Locations
    static String pluginMainDir = "./plugins/CommandPointsMunificent";
    static String pluginConfigLocation = pluginMainDir + "/CPM.cfg";
    static String dbLocation = pluginMainDir + "/PartialPointDB.ini";
	
	public void onEnable() {
		// Check for the plugin directory (create if it does not exist)
    	File pluginDir = new File(pluginMainDir);
		if(!pluginDir.exists()) {
			boolean dirCreation = pluginDir.mkdirs();
			if (dirCreation) {
				System.out.println("New MobDisguisePCPControl directory created!");
			}
		}
		
		// Setup Permissions
		setupPermissions();
		
		// Register Events
		PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(listener, this);
		
		PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}
	
	public void onDisable() {
		System.out.println("MobDisguisePVPControl disabled!");
	}
	
	// Permissions Methods
    private void setupPermissions() {
        Plugin permissions = this.getServer().getPluginManager().getPlugin("Permissions");

        if (Permissions == null) {
            if (permissions != null) {
                Permissions = ((Permissions)permissions).getHandler();
            } else {
            }
        }
    }
    
    protected boolean hasPermissions(Player player, String node) {
        if (Permissions != null) {
        	return Permissions.has(player, node);
        } else {
            return player.hasPermission(node);
        }
    }
}
