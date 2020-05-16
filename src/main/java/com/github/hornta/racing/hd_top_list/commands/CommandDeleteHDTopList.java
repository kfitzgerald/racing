package com.github.hornta.racing.hd_top_list.commands;

import com.github.hornta.commando.ICommandHandler;
import com.github.hornta.messenger.MessageManager;
import com.github.hornta.racing.MessageKey;
import com.github.hornta.racing.hd_top_list.HDTopListManager;
import org.bukkit.command.CommandSender;

public class CommandDeleteHDTopList implements ICommandHandler {
  @Override
  public void handle(CommandSender commandSender, String[] args, int i) {
    HDTopListManager.deleteTopList(args[0], (Boolean result) -> {
      if(result) {
        MessageManager.sendMessage(commandSender, MessageKey.HD_TOP_LIST_DELETE_SUCCESS);
      } else {
        MessageManager.sendMessage(commandSender, MessageKey.HD_TOP_LIST_DELETE_ERROR_FAIL_DELETE_FILE);
      }
    });
  }
}
