package keepcalm.mods.bukkit.modIntegration.computercraft;

import java.util.HashMap;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import dan200.computer.api.IPeripheral;

import keepcalm.mods.bukkit.BukkitContainer;
import keepcalm.mods.bukkit.bukkitAPI.BukkitChunk;
import keepcalm.mods.bukkit.bukkitAPI.BukkitPlayerCache;
import keepcalm.mods.bukkit.bukkitAPI.block.BukkitBlock;
import keepcalm.mods.bukkit.bukkitAPI.entity.BukkitPlayer;
import keepcalm.mods.events.PlayerBreakBlockEvent;
import keepcalm.mods.events.events.BlockDestroyEvent;


import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class ComputercraftHelper {

	public static HashMap<Integer, String> owners = new HashMap<Integer, String>();
	
	/**
	 * Calls appropriate events for turtles breaking blocks.
	 * The turtle behaves as though its owner broke the block.
	 * 
	 * @param ev
	 * @param turtleX
	 * @param turtleY
	 * @param turtleZ
	 * @param turtle
	 * @return -1 for invalid, 0 for uncancelled, 1 for cancelled.
	 * @throws ClassNotFoundException - if CC is not installed
	 * @throws Exception - if an error occured during ID retrieval.
	 */
	public static int handleTurtleBreakEvent(BlockDestroyEvent ev, int turtleX, int turtleY, int turtleZ, IPeripheral turtle) throws ClassNotFoundException, Exception {
		if (turtle.callMethod(null, 3, null) == null || !turtle.getClass().isAssignableFrom(Class.forName("dan200.turtle.shared.TileEntityTurtle"))) {
			return -1;
		}
		int id;
		try {
			id = (Integer) (turtle.callMethod(null, 3, null))[0];
		} catch (Exception e) {return -1;}
		if (owners.containsKey(id)) {
			EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(owners.get(id));
			if (player != null) {
				//return player;
				BukkitBlock blk = new BukkitBlock(new BukkitChunk(ev.world.getChunkFromBlockCoords(ev.x, ev.z)),ev.x,ev.y,ev.z);
				BlockBreakEvent bev = new BlockBreakEvent(blk, BukkitPlayerCache.getBukkitPlayer(player));
			}
			else {
				player = MinecraftServer.getServer().getConfigurationManager().createPlayerForUser(owners.get(id));
				PlayerJoinEvent bev = new PlayerJoinEvent(BukkitPlayerCache.getBukkitPlayer(player), player.username + " joined the game");
				
			}
		}
		
		return 0;
	}
	
}
