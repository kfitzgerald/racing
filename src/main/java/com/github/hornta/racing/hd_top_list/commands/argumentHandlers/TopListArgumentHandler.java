package com.github.hornta.racing.hd_top_list.commands.argumentHandlers;

import se.hornta.commando.ValidationResult;
import se.hornta.commando.completers.IArgumentHandler;
import se.hornta.messenger.MessageManager;
import com.github.hornta.racing.MessageKey;
import com.github.hornta.racing.hd_top_list.HDTopList;
import com.github.hornta.racing.hd_top_list.HDTopListManager;
import org.bukkit.command.CommandSender;

import java.util.Set;
import java.util.stream.Collectors;

public class TopListArgumentHandler implements IArgumentHandler {
  private final boolean shouldExist;

  public TopListArgumentHandler(boolean shouldExist) {
    this.shouldExist = shouldExist;
  }

  @Override
  public Set<String> getItems(CommandSender sender, String argument, String[] prevArgs) {
    return HDTopListManager.getTopLists().stream().map(HDTopList::getName).collect(Collectors.toSet());
  }

  @Override
  public void whenInvalid(ValidationResult result) {
    MessageKey message;
    if(shouldExist) {
      message = MessageKey.HD_TOP_LIST_NOT_FOUND;
    } else {
      message = MessageKey.HD_TOP_LIST_CREATE_ALREADY_EXIST;
    }
    MessageManager.setValue("name", result.getValue());
    MessageManager.sendMessage(result.getCommandSender(), message);
  }

  @Override
  public boolean test(Set<String> items, String argument) {
    if(shouldExist) {
      return items.contains(argument);
    }
    return !items.contains(argument);
  }
}
