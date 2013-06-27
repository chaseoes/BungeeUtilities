package com.chaseoes.bungeeutilities.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import com.chaseoes.bungeeutilities.BungeeUtilities;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class GeneralUtilities {

	public static ItemStack getServerMenuItem() {
		ItemStack i = new ItemStack(Material.getMaterial(BungeeUtilities.getInstance().getConfig().getString("server-menu.item").toUpperCase()), 1);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(format(BungeeUtilities.getInstance().getConfig().getString("server-menu.item-name")));
		i.setItemMeta(im);
		return i;
	}

	public static List<ItemStack> getWrittenBooks() {
		List<ItemStack> list = new ArrayList<ItemStack>();
		if (BungeeUtilities.getInstance().getConfig().getBoolean("server-menu.books.enabled")) {
			for (String file : BungeeUtilities.getInstance().getConfig().getStringList("server-menu.books.files")) {
				ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
				BookMeta bm = (BookMeta) book.getItemMeta();
				File f = new File(BungeeUtilities.getInstance().getDataFolder() + "/" + file);
				try {
					BufferedReader br = new BufferedReader(new FileReader(f));
					StringBuilder sb = new StringBuilder();
					String line = br.readLine();

					int i = 0;
					while (line != null) {
						i++;
						if (i != 1 && i != 2) {
							if (line.equalsIgnoreCase("/n")) {
								bm.addPage(sb.toString());
								sb = new StringBuilder();
							} else {
								sb.append(format(line));
								sb.append("\n");
							}
						} else {
							if (i == 1) {
								bm.setTitle(format(line));
							}
							if (i == 2) {
								bm.setAuthor(format(line));
							}
						}
						line = br.readLine();
					}

					br.close();
					bm.addPage(sb.toString());
				} catch (Exception e) {
					e.printStackTrace();
					BungeeUtilities.getInstance().getLogger().log(Level.WARNING, "Error encountered while trying to give a written book! (File " + file + ")");
					BungeeUtilities.getInstance().getLogger().log(Level.WARNING, "Please check that the file exists and is readable.");
				}

				book.setItemMeta(bm);
				list.add(book);
			}
		}
		return list;
	}

	public static ItemStack parseItem(String s) {
		String[] parts = s.split(":");
		ItemStack item = null;

		if (isNumber(parts[0])) {
			item = new ItemStack(Material.getMaterial(Integer.parseInt(parts[0])), 1);
		} else {
			item = new ItemStack(Material.getMaterial(parts[0].toUpperCase()), 1);
		}

		if (parts.length == 2) {
			System.out.println(parts[1]);
			if (isNumber(parts[1])) {
				short data = Short.valueOf(parts[1]);
				item.setDurability(data);
			} else {
				Wool wool = new Wool(DyeColor.valueOf(parts[1].toUpperCase()));
				item.setDurability(wool.getData());
			}
		}

		return item;
	}

	public static void warpToServer(Player player, String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(server);
		player.sendPluginMessage(BungeeUtilities.getInstance(), "BungeeCord", out.toByteArray());
	}

	public static String format(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	public static boolean isNumber(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static int roundUp(int n) {
		return (n + 8) / 9 * 9;
	}

}
