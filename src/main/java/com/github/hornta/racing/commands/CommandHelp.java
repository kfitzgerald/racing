package com.github.hornta.racing.commands;

import se.hornta.commando.ICommandHandler;
import com.github.hornta.racing.RacingPlugin;
import com.github.hornta.racing.MessageKey;
import se.hornta.messenger.MessageManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandHelp implements ICommandHandler {
  @Override
  public void handle(CommandSender commandSender, String[] args, int typedArgs) {
    Player player = null;
    if(commandSender instanceof Player) {
      player = (Player)commandSender;
    }

    List<String> helpTexts = RacingPlugin.getInstance().getCommando().getHelpTexts(player);

    MessageManager.sendMessage(commandSender, MessageKey.RACE_HELP_TITLE);

    for(String helpText : helpTexts) {
      MessageManager.setValue("text", helpText);
      MessageManager.sendMessage(commandSender, MessageKey.RACE_HELP_ITEM);
    }
  }
}
