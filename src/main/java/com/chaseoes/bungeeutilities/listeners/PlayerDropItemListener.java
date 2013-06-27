package com.chaseoes.bungeeutilities.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.chaseoes.bungeeutilities.BungeeUtilities;
import com.chaseoes.bungeeutilities.utilities.GeneralUtilities;

public class PlayerDropItemListener implements Listener {

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if (BungeeUtilities.getInstance().getConfig().getBoolean("server-menu.prevent-item-dropping")) {
			if (event.getItemDrop().getItemStack().getItemMeta().hasDisplayName()) {
				if (event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(GeneralUtilities.getServerMenuItem().getItemMeta().getDisplayName())) {
					event.setCancelled(true);
				}
			}
		}

		if (BungeeUtilities.getInstance().getConfig().getBoolean("server-menu.prevent-book-dropping")) {
			for (ItemStack im : GeneralUtilities.getWrittenBooks()) {
				if (event.getItemDrop().getItemStack().getItemMeta() instanceof BookMeta) {
					BookMeta meta = (BookMeta) event.getItemDrop().getItemStack().getItemMeta();
					if (meta.getTitle().equals(((BookMeta) im.getItemMeta()).getTitle())) {
						event.setCancelled(true);
					}
				}
			}
		}
		
		// TODO: Prevent book dropping.
	}

}
