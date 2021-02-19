package com.github.hornta.racing.hd_top_list.commands;

import com.github.hornta.racing.MessageKey;
import com.github.hornta.racing.RacingPlugin;
import com.github.hornta.racing.hd_top_list.HDTopListManager;
import org.bukkit.command.CommandSender;
import se.hornta.commando.ICommandHandler;
import se.hornta.messenger.MessageManager;

public class CommandSetLapsHDTopList implements ICommandHandler {
	@Override
	public void handle(CommandSender commandSender, String[] args, int i) {
		var topList = HDTopListManager.getTopList(args[0]);
		var laps = Integer.parseInt(args[1]);
		var oldLaps = topList.getLaps();
		topList.setLaps(laps);
		RacingPlugin.getHdTopListManager().updateDirtyTopLists(() -> {
			MessageManager.setValue("old_laps", oldLaps);
			MessageManager.setValue("new_laps", laps);
			MessageManager.sendMessage(commandSender, MessageKey.HD_TOP_LIST_SET_LAPS);
		});
	}
}
