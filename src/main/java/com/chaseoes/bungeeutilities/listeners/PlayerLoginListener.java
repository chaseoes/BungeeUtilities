package com.chaseoes.bungeeutilities.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import com.chaseoes.bungeeutilities.BungeeUtilities;
import com.chaseoes.bungeeutilities.utilities.GeneralUtilities;

public class PlayerLoginListener implements Listener {

	@EventHandler
	public void onPlayerLogin(final PlayerLoginEvent event) {
		if (BungeeUtilities.getInstance().getConfig().getBoolean("overflow.enabled")) {
			if (event.getResult() == Result.KICK_FULL) {
				final String server = BungeeUtilities.getInstance().getConfig().getString("overflow.server");
				event.setResult(Result.ALLOWED);

				BungeeUtilities.getInstance().getServer().getScheduler().runTaskLater(BungeeUtilities.getInstance(), new Runnable() {
					public void run() {
						GeneralUtilities.warpToServer(event.getPlayer(), server);
					}
				}, 40L);
			}
		}

		if (BungeeUtilities.getInstance().getConfig().getBoolean("ip-whitelist.enabled")) {
			String address = event.getAddress().toString().replace("/", "");
			if (!BungeeUtilities.getInstance().getConfig().getStringList("ip-whitelist.allowed-ips").contains(address)) {
				event.setKickMessage("Please join using the server's proper IP address.");
				event.setResult(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_OTHER);
				return;
			}
		}

	}

}
