package com.zandor300.simplebounty.commands;

import com.zandor300.simplebounty.SimpleBounty;
import com.zandor300.zsutilities.commandsystem.Command;
import com.zandor300.zsutilities.utilities.uuid.NameFetcher;
import com.zandor300.zsutilities.utilities.uuid.UUIDFetcher;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by Zandor on 3/27/15.
 */
public class BountyCommand extends Command {

	public BountyCommand() {
		super("bounty", "Bounty command.", "[<user> <reward>]");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length == 0) {
			int i = 0;
			for (String uuid : SimpleBounty.getCustomConfig().getConfigurationSection("bounties").getKeys(false)) {
				try {
					SimpleBounty.getChat().sendMessage(sender, i + ". Wanted " + new NameFetcher(Arrays.asList(UUID.fromString(uuid))).call().toString() + " for $" + SimpleBounty.getCustomConfig().getInt("bounties." + uuid + ".reward"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return;
		} else if (args.length == 2) {
			String uuid = "";
			try {
				uuid = new UUIDFetcher(Arrays.asList(args[0])).call().get(0).toString();
			} catch (Exception e) {
				SimpleBounty.getChat().sendMessage(sender, "Unable to fetch the uuid of this player.");
			}

			int reward = 0;
			try {
				Integer.valueOf(args[1]);
			} catch (Exception e) {
				SimpleBounty.getChat().sendMessage(sender, "Invalid reward.");
			}
			SimpleBounty.getCustomConfig().set("bounties." + uuid + ".reward", reward);
			return;
		} else {
			sendUsageMessage(sender);
			return;
		}
	}
}