package com.chaseoes.bungeeutilities.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.chaseoes.bungeeutilities.BungeeUtilities;
import com.chaseoes.bungeeutilities.utilities.GeneralUtilities;

public class PlayerInteractListener implements Listener {

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerInteract(final PlayerInteractEvent event) {
		if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.PHYSICAL) {
			if (event.getPlayer().getItemInHand().getType() == GeneralUtilities.getServerMenuItem().getType()) {
				if (event.hasBlock() && (event.getClickedBlock().getType() == Material.WALL_SIGN || event.getClickedBlock().getType() == Material.SIGN || event.getClickedBlock().getType() == Material.JUKEBOX || event.getClickedBlock().getState() instanceof InventoryHolder)) {
					return;
				}
				BungeeUtilities.getInstance().serverMenu.open(event.getPlayer());
				event.setUseItemInHand(Result.DENY);
				event.setUseInteractedBlock(Result.DENY);
			}

			ItemStack magic = new ItemStack(Material.getMaterial(BungeeUtilities.getInstance().getConfig().getString("magical-clock.item").toUpperCase()), 1);
			if (event.getPlayer().getItemInHand().getType() == magic.getType()) {
				if (BungeeUtilities.getInstance().cantUseClock.contains(event.getPlayer().getName())) {
					event.getPlayer().sendMessage(ChatColor.RED + "Please wait before using that again.");
					return;
				}
				
				if (BungeeUtilities.getInstance().hasPlayersHidden.contains(event.getPlayer().getName())) {
					BungeeUtilities.getInstance().hasPlayersHidden.remove(event.getPlayer().getName());
					for (Player player : BungeeUtilities.getInstance().getServer().getOnlinePlayers()) {
						event.getPlayer().showPlayer(player);
					}
				} else {
					BungeeUtilities.getInstance().hasPlayersHidden.add(event.getPlayer().getName());
					for (Player player : BungeeUtilities.getInstance().getServer().getOnlinePlayers()) {
						event.getPlayer().hidePlayer(player);
					}			
				}
				
				BungeeUtilities.getInstance().cantUseClock.add(event.getPlayer().getName());
				BungeeUtilities.getInstance().getServer().getScheduler().runTaskLater(BungeeUtilities.getInstance(), new Runnable() {
					public void run() {
						BungeeUtilities.getInstance().cantUseClock.remove(event.getPlayer().getName());
					}
				}, BungeeUtilities.getInstance().getConfig().getInt("magical-clock.delay") * 20);
			}
		}
	}

}
