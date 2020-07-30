package com.github.hornta.racing.hd_top_list.commands;

import se.hornta.commando.ICommandHandler;
import se.hornta.messenger.MessageManager;
import com.github.hornta.racing.MessageKey;
import com.github.hornta.racing.hd_top_list.HDTopList;
import com.github.hornta.racing.hd_top_list.HDTopListManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

public class CommandMoveHDTopList implements ICommandHandler {
  @Override
  public void handle(CommandSender commandSender, String[] args, int i) {
    Entity player = (Entity) commandSender;
    HDTopList topList = HDTopListManager.getTopList(args[0]);
    topList.getHologram().teleport(player.getLocation());
    HDTopListManager.updateTopList(topList, () -> MessageManager.sendMessage(commandSender, MessageKey.HD_TOP_LIST_MOVE));
  }
}
