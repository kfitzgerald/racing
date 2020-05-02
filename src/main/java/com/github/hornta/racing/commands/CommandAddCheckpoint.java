package com.github.hornta.racing.commands;

import com.github.hornta.commando.ICommandHandler;
import com.github.hornta.racing.RacingManager;
import com.github.hornta.racing.Util;
import com.github.hornta.racing.enums.RaceState;
import com.github.hornta.racing.MessageKey;
import com.github.hornta.messenger.MessageManager;
import com.github.hornta.racing.objects.Race;
import com.github.hornta.racing.objects.RaceCheckpoint;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAddCheckpoint extends RacingCommand implements ICommandHandler {
  public CommandAddCheckpoint(RacingManager racingManager) {
    super(racingManager);
  }

  @Override
  public void handle(CommandSender commandSender, String[] args, int typedArgs) {
    Race race = racingManager.getRace(args[0]);

    if(race.getState() != RaceState.UNDER_CONSTRUCTION) {
      MessageManager.setValue("race_name", race.getName());
      MessageManager.sendMessage(commandSender, MessageKey.EDIT_NO_EDIT_MODE);
      return;
    }

    Player player = (Player)commandSender;

    if(race.getCheckpoint(player.getLocation()) != null) {
      MessageManager.sendMessage(commandSender, MessageKey.RACE_ADD_CHECKPOINT_IS_OCCUPIED);
      return;
    }

    racingManager.addCheckpoint(Util.centerOnBlock(player.getLocation()), race, (RaceCheckpoint checkPoint) -> {
      MessageManager.setValue("race_name", race.getName());
      MessageManager.setValue("position", checkPoint.getPosition());
      MessageManager.sendMessage(player, MessageKey.RACE_ADD_CHECKPOINT_SUCCESS);
    });
  }
}
