package com.zandor300.simplebounty.listeners;

import com.zandor300.simplebounty.SimpleBounty;
import com.zandor300.zsutilities.utilities.uuid.UUIDFetcher;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by Zandor on 4/2/15.
 */
public class PlayerListener implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if (event.getEntity().getKiller() == null)
			return;

		Player player = event.getEntity();
		UUID playerUuid;
		Player killer = player.getKiller();
		UUID killerUuid;
		try {
			killerUuid = new UUIDFetcher(Arrays.asList(killer.getName())).call().get(0);
			playerUuid = new UUIDFetcher(Arrays.asList(player.getName())).call().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		if (!SimpleBounty.getCustomConfig().getConfigurationSection("bounties").getKeys(false).contains(playerUuid.toString()))
			return;

		int reward = SimpleBounty.getCustomConfig().getInt("bounties." + playerUuid.toString() + ".reward");

		SimpleBounty.getCustomConfig().set("bounties." + playerUuid, null);

		EconomyResponse transaction = SimpleBounty.getEconomy().withdrawPlayer(player, -reward);
		if (transaction.transactionSuccess()) {
			SimpleBounty.getChat().sendMessage(killer, "You have been rewarded with " +
					SimpleBounty.getChat().bold("$" + reward) + "!");
		} else {
			SimpleBounty.getChat().sendMessage(killer, ChatColor.RED + "Transaction failed.");
		}
	}
}
