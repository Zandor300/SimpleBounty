package com.zandor300.simplebounty.commands;

import com.zandor300.simplebounty.SimpleBounty;
import com.zandor300.zsutilities.commandsystem.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Zandor on 3/27/15.
 */
public class SimpleBountyCommand extends Command {

	public SimpleBountyCommand() {
		super("simplebounty", "Show info.");
	}

	@Override
	public void execute(CommandSender sender, String[] strings) {
		SimpleBounty.getChat().sendMessage(sender, "SimpleBounty 1.0.0 by Zandor300");
	}
}
