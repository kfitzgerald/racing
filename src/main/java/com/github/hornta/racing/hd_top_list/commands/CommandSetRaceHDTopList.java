package com.github.hornta.racing.hd_top_list.commands;

import com.github.hornta.racing.MessageKey;
import com.github.hornta.racing.RacingPlugin;
import com.github.hornta.racing.hd_top_list.HDTopListManager;
import org.bukkit.command.CommandSender;
import se.hornta.commando.ICommandHandler;
import se.hornta.messenger.MessageManager;

public class CommandSetRaceHDTopList implements ICommandHandler {
	@Override
	public void handle(CommandSender commandSender, String[] args, int i) {
		var topList = HDTopListManager.getTopList(args[0]);
		var race = RacingPlugin.getInstance().getRacingManager().getRace(args[1]);
		var oldRace = topList.getRace();
		topList.setRace(race);
		RacingPlugin.getHdTopListManager().updateDirtyTopLists(() -> {
			MessageManager.setValue("old_race", oldRace.getName());
			MessageManager.setValue("new_race", race.getName());
			MessageManager.sendMessage(commandSender, MessageKey.HD_TOP_LIST_SET_RACE);
		});
	}
}
