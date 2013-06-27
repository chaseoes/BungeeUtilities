package com.chaseoes.bungeeutilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.chaseoes.bungeeutilities.listeners.InventoryClickListener;
import com.chaseoes.bungeeutilities.listeners.PlayerDropItemListener;
import com.chaseoes.bungeeutilities.listeners.PlayerInteractListener;
import com.chaseoes.bungeeutilities.listeners.PlayerJoinListener;
import com.chaseoes.bungeeutilities.listeners.PlayerLoginListener;
import com.chaseoes.bungeeutilities.listeners.PlayerQuitListener;
import com.chaseoes.bungeeutilities.utilities.GeneralUtilities;
import com.chaseoes.bungeeutilities.utilities.IconMenu;

public class BungeeUtilities extends JavaPlugin {

	private static BungeeUtilities instance;
	public IconMenu serverMenu = null;
	public List<String> hasPlayersHidden = new ArrayList<String>();
	public List<String> cantUseClock = new ArrayList<String>();

	public static BungeeUtilities getInstance() {
		return instance;
	}

	public void onEnable() {
		instance = this;
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		if (getConfig().getBoolean("server-menu.enabled")) {
			loadMenu();
		}

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerDropItemListener(), this);
		pm.registerEvents(new InventoryClickListener(), this);
		pm.registerEvents(new PlayerInteractListener(), this);
		pm.registerEvents(new PlayerJoinListener(), this);
		pm.registerEvents(new PlayerLoginListener(), this);
		pm.registerEvents(new PlayerQuitListener(), this);
		// TODO: Bungee Portals
		// TODO: Status Signs
		// TODO: Anti Chat
		// TODO: Commands
		
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord"); 
	}

	public void onDisable() {
		reloadConfig();
		saveConfig();
		instance = null;
	}

	public void loadMenu() {
		try {
			serverMenu = new IconMenu(GeneralUtilities.format(getConfig().getString("server-menu.title")), GeneralUtilities.roundUp(getConfig().getConfigurationSection("server-menu.servers").getKeys(false).size()), new IconMenu.OptionClickEventHandler() {
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent event) {
					GeneralUtilities.warpToServer(event.getPlayer(), getConfig().getString("server-menu.servers." + (event.getPosition() + 1) + ".server"));
					event.setWillClose(true);
				}
			}, this);

			for (String s : getConfig().getConfigurationSection("server-menu.servers").getKeys(false)) {
				int i = Integer.parseInt(s);
				String it = getConfig().getString("server-menu.servers." + i + ".item");
				ItemStack item = GeneralUtilities.parseItem(it);
				String name = getConfig().getString("server-menu.servers." + i + ".name");
				if (ChatColor.stripColor(GeneralUtilities.format(name)).equalsIgnoreCase(getConfig().getString("current-server"))) {
					name = ChatColor.ITALIC + name;
				}
				serverMenu.setOption(i - 1, item, GeneralUtilities.format(name), GeneralUtilities.format(getConfig().getString("server-menu.servers." + i + ".description")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
		return true;
	}

}
