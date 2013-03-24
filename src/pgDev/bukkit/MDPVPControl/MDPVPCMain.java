package pgDev.bukkit.MDPVPControl;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import pgDev.bukkit.DisguiseCraft.DisguiseCraft;
import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;

public class MDPVPCMain extends JavaPlugin {
	// Listener
	public final MDPVPListener listener = new MDPVPListener(this);
    
    // File Locations
    static String pluginMainDir = "./plugins/CommandPointsMunificent";
    static String pluginConfigLocation = pluginMainDir + "/CPM.cfg";
    static String dbLocation = pluginMainDir + "/PartialPointDB.ini";
    
    // DisguiseCraft
    DisguiseCraftAPI dcAPI = null;
	
	@SuppressWarnings("static-access")
	public void onEnable() {
		// Check for the plugin directory (create if it does not exist)
		/*
    	File pluginDir = new File(pluginMainDir);
		if(!pluginDir.exists()) {
			boolean dirCreation = pluginDir.mkdirs();
			if (dirCreation) {
				System.out.println("New MobDisguisePCPControl directory created!");
			}
		}*/
		
		
		// Setup DisguiseCraft
		Plugin dc = getServer().getPluginManager().getPlugin("DisguiseCraft");
		if (dc != null) {
			dcAPI = ((DisguiseCraft) dc).getAPI();
		}
		
		// Register Events
		PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(listener, this);
		
		PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}
	
	public void onDisable() {
		System.out.println("MobDisguisePVPControl disabled!");
	}
    
    // Dual System Methods
    public boolean isDisguised(Player p) {
    	return dcAPI.isDisguised(p);
    }
    
    public void unDisguise(Player p) {
    	dcAPI.undisguisePlayer(p);
    }
}
