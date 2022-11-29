package com.github.hornta.racing.commands;

import com.github.hornta.racing.MessageKey;
import com.github.hornta.racing.RacingManager;
import com.github.hornta.racing.objects.RaceSession;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import se.hornta.commando.ICommandHandler;
import se.hornta.messenger.MessageManager;

public class CommandRespawn extends RacingCommand implements ICommandHandler {
  public CommandRespawn(RacingManager racingManager) {
    super(racingManager);
  }

  @Override
  public void handle(CommandSender commandSender, String[] args, int typedArgs) {
    Player player = (Player)commandSender;
    RaceSession session = racingManager.getParticipatingRace(player);

    if (session == null) {
      MessageManager.sendMessage(commandSender, MessageKey.RACE_RESPAWN_NOT_PARTICIPATING);
      return;
    }

    // DO IT AGAIN
    session.getPlayerSession(player).respawnInVehicle();
    MessageManager.sendMessage(commandSender, MessageKey.RACE_RESPAWN_SUCCESS);
  }
}

