package com.chaseoes.bungeeutilities.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.chaseoes.bungeeutilities.BungeeUtilities;
import com.chaseoes.bungeeutilities.utilities.GeneralUtilities;

public class InventoryClickListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getCurrentItem() != null) {
			if (BungeeUtilities.getInstance().getConfig().getBoolean("server-menu.prevent-item-movement")) {
				if (event.getCurrentItem().hasItemMeta()) {
					if (event.getCurrentItem().getItemMeta().hasDisplayName()) {
						if (event.getCurrentItem().getItemMeta().getDisplayName().equals(GeneralUtilities.getServerMenuItem().getItemMeta().getDisplayName())) {
							event.setResult(Result.DENY);
							event.setCursor(new ItemStack(Material.AIR));
							event.setCancelled(true);
						}
					}
				}
			}

			if (BungeeUtilities.getInstance().getConfig().getBoolean("server-menu.prevent-book-movement")) {
				for (ItemStack im : GeneralUtilities.getWrittenBooks()) {
					if (event.getCurrentItem().getItemMeta() instanceof BookMeta) {
						BookMeta meta = (BookMeta) event.getCurrentItem().getItemMeta();
						if (meta.getTitle().equals(((BookMeta) im.getItemMeta()).getTitle())) {
							event.setResult(Result.DENY);
							event.setCursor(new ItemStack(Material.AIR));
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
}
