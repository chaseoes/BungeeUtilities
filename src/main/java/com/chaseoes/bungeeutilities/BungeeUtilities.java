package com.chaseoes.bungeeutilities;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.chaseoes.bungeeutilities.listeners.PlayerInteractListener;
import com.chaseoes.bungeeutilities.listeners.PlayerLoginListener;

public class BungeeUtilities extends JavaPlugin {
	
	private static BungeeUtilities instance;
	
	public BungeeUtilities getInstance() {
		return instance;
	}
	
	public void onEnable() {
		instance = this;
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerInteractListener(), this);
		pm.registerEvents(new PlayerLoginListener(), this);
	}
	
	public void onDisable() {
		reloadConfig();
		saveConfig();
		instance = null;
	}
	
	public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
		return true;
	}

}
