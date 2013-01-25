package keepcalm.mods.bukkit.modIntegration;

import java.util.HashMap;

import keepcalm.mods.bukkit.BukkitContainer;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

/**
 * Contains a mapping of mod packages -> their players
 * @author keepcalm
 *
 */
public class ModPlayerHelper {

	private static final HashMap<String, EntityPlayerMP> modPlayers = new HashMap<String, EntityPlayerMP>();
	
	/**
	 * 
	 * 
	 * @param packagePrefix - the prefix of the package containing the mod's classes.
	 * @param name - the name of the fake player.
	 */
	public static void registerModPlayer(String packagePrefix, String modName, String name) {
		BukkitContainer.bukkitLogger.info("Searching for mod " + modName + " with prefix " + packagePrefix + "...");
		if (Package.getPackage(packagePrefix) == null) {
			BukkitContainer.bukkitLogger.info("Skipping mod player " + name + " for mod " + modName + " - mod not loaded.");
			return;
		}
		
		EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().createPlayerForUser(name);
		
		modPlayers.put(packagePrefix, player);
	}
	
	
}
