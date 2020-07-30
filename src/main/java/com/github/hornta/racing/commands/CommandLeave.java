package com.github.hornta.racing.commands;

import se.hornta.commando.ICommandHandler;
import com.github.hornta.racing.RacingManager;
import com.github.hornta.racing.MessageKey;
import se.hornta.messenger.MessageManager;
import com.github.hornta.racing.objects.RaceSession;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandLeave extends RacingCommand implements ICommandHandler {
  public CommandLeave(RacingManager racingManager) {
    super(racingManager);
  }

  @Override
  public void handle(CommandSender commandSender, String[] args, int typedArgs) {
    Player player = (Player)commandSender;
    RaceSession session = racingManager.getParticipatingRace(player);

    if (session == null) {
      MessageManager.sendMessage(commandSender, MessageKey.RACE_LEAVE_NOT_PARTICIPATING);
      return;
    }

    session.leave(player);

    MessageManager.setValue("race_name", session.getRace().getName());
    MessageManager.sendMessage(commandSender, MessageKey.RACE_LEAVE_SUCCESS);
  }
}

