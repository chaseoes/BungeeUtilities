package com.chaseoes.bungeeutilities.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.chaseoes.bungeeutilities.BungeeUtilities;

public class PlayerQuitListener implements Listener {
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		BungeeUtilities.getInstance().hasPlayersHidden.remove(event.getPlayer().getName());
	}

}
