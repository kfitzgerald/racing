package com.github.hornta.racing.hd_top_list.commands;

import com.github.hornta.racing.MessageKey;
import com.github.hornta.racing.RacingPlugin;
import com.github.hornta.racing.enums.RaceStatType;
import com.github.hornta.racing.hd_top_list.HDTopListManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import se.hornta.commando.ICommandHandler;
import se.hornta.messenger.MessageManager;

import java.util.Locale;

public class CommandCreateHDTopList implements ICommandHandler {
	@Override
	public void handle(CommandSender commandSender, String[] args, int i) {
		var topListName = args[0];
		var race = RacingPlugin.getInstance().getRacingManager().getRace(args[1]);
		var statType = RaceStatType.valueOf(args[2].toUpperCase(Locale.ENGLISH));
		var laps = Integer.parseInt(args[3]);
		var entitySender = (Entity) commandSender;
		HDTopListManager.createTopList(topListName, entitySender.getLocation(), race, statType, laps, (Boolean result) -> {
			if (result) {
				MessageManager.sendMessage(commandSender, MessageKey.HD_TOP_LIST_CREATE_SUCCESS);
			} else {
				MessageManager.sendMessage(commandSender, MessageKey.HD_TOP_LIST_CREATE_ERROR_NO_PERSIST);
			}
		});
	}
}
