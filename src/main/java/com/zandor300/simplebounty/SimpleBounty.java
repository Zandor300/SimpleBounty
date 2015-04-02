package com.zandor300.simplebounty;

import com.zandor300.simplebounty.commands.BountyCommand;
import com.zandor300.simplebounty.commands.SimpleBountyCommand;
import com.zandor300.simplebounty.listeners.PlayerListener;
import com.zandor300.zsutilities.commandsystem.CommandManager;
import com.zandor300.zsutilities.config.Config;
import com.zandor300.zsutilities.utilities.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.IOException;

/**
 * Created by Zandor on 3/27/15.
 */
public class SimpleBounty extends JavaPlugin {

	private static Chat chat = new Chat("SimpleBounty", ChatColor.GOLD);
	private static SimpleBounty plugin;
	private static Config config;
	private static Economy economy = null;

	public static Chat getChat() {
		return chat;
	}

	public static SimpleBounty getPlugin() {
		return plugin;
	}

	public static Config getCustomConfig() {
		return config;
	}

	public static Economy getEconomy() {
		return economy;
	}

	@Override
	public void onEnable() {
		chat.sendConsoleMessage("Setting things up...");

		config = new Config(this, "config.yml", true);
		plugin = this;
		PluginManager pm = Bukkit.getPluginManager();
		CommandManager cm = new CommandManager();

		chat.sendConsoleMessage("Starting metrics...");
		try {
			new Metrics(this).start();
			chat.sendConsoleMessage("Submitted stats to MCStats.org.");
		} catch (IOException e) {
			chat.sendConsoleMessage("Couldn't submit stats to MCStats.org...");
		}

		chat.sendConsoleMessage("Setting up vault...");
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null)
			economy = economyProvider.getProvider();
		chat.sendConsoleMessage("Vault setup.");

		chat.sendConsoleMessage("Registering events...");
		pm.registerEvents(new PlayerListener(), this);
		chat.sendConsoleMessage("Registered events.");

		chat.sendConsoleMessage("Registering commands...");
		cm.registerCommand(new BountyCommand(), this);
		cm.registerCommand(new SimpleBountyCommand(), this);
		chat.sendConsoleMessage("Registered commands.");

		chat.sendConsoleMessage("Everything is setup!");
		chat.sendConsoleMessage("Enabled.");
	}
}
