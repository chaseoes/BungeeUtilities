package com.chaseoes.bungeeutilities.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.chaseoes.bungeeutilities.BungeeUtilities;
import com.chaseoes.bungeeutilities.utilities.GeneralUtilities;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		for (String p : BungeeUtilities.getInstance().hasPlayersHidden) {
			Player player = BungeeUtilities.getInstance().getServer().getPlayerExact(p);
			if (player == null) {
				BungeeUtilities.getInstance().hasPlayersHidden.remove(p);
				return;
			}
			player.hidePlayer(event.getPlayer());
		}
		
		if (BungeeUtilities.getInstance().getConfig().getBoolean("server-menu.enabled")) {
			if (!event.getPlayer().getInventory().contains(GeneralUtilities.getServerMenuItem())) {
				event.getPlayer().getInventory().addItem(GeneralUtilities.getServerMenuItem());
			}

			if (!event.getPlayer().getInventory().contains(Material.WRITTEN_BOOK)) {
				for (ItemStack i : GeneralUtilities.getWrittenBooks()) {
					event.getPlayer().getInventory().addItem(i);
				}
			}

			if (BungeeUtilities.getInstance().getConfig().getBoolean("magical-clock.enabled")) {
				ItemStack magic = new ItemStack(Material.getMaterial(BungeeUtilities.getInstance().getConfig().getString("magical-clock.item").toUpperCase()), 1);
				ItemMeta itemMagic = magic.getItemMeta();
				itemMagic.setDisplayName(GeneralUtilities.format(BungeeUtilities.getInstance().getConfig().getString("magical-clock.name")));
				magic.setItemMeta(itemMagic);
				if (!event.getPlayer().getInventory().contains(magic)) {
					event.getPlayer().getInventory().addItem(magic);
				}
			}
		}
	}

}
