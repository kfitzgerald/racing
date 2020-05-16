package com.github.hornta.racing.hd_top_list.commands;

import com.github.hornta.commando.ICommandHandler;
import com.github.hornta.messenger.MessageManager;
import com.github.hornta.racing.MessageKey;
import com.github.hornta.racing.enums.RaceStatType;
import com.github.hornta.racing.hd_top_list.HDTopList;
import com.github.hornta.racing.hd_top_list.HDTopListManager;
import org.bukkit.command.CommandSender;

import java.util.Locale;

public class CommandSetLapsHDTopList implements ICommandHandler {
  @Override
  public void handle(CommandSender commandSender, String[] args, int i) {
    HDTopList topList = HDTopListManager.getTopList(args[0]);
    Integer laps = Integer.valueOf(args[1]);
    int oldLaps = topList.getLaps();
    topList.setLaps(laps);
    HDTopListManager.updateTopList(topList, () -> {
      MessageManager.setValue("old_laps", oldLaps);
      MessageManager.setValue("new_laps", laps);
      MessageManager.sendMessage(commandSender, MessageKey.HD_TOP_LIST_SET_LAPS);
    });
  }
}
