package com.github.hornta.racing.commands;

import se.hornta.commando.ICommandHandler;
import com.github.hornta.racing.RacingManager;
import com.github.hornta.racing.Util;
import com.github.hornta.racing.enums.RaceState;
import com.github.hornta.racing.MessageKey;
import se.hornta.messenger.MessageManager;
import com.github.hornta.racing.objects.Race;
import com.github.hornta.racing.objects.RaceCheckpoint;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

public class CommandAddCheckpoint extends RacingCommand implements ICommandHandler {
  public CommandAddCheckpoint(RacingManager racingManager) {
    super(racingManager);
  }

  @Override
  public void handle(CommandSender commandSender, String[] args, int typedArgs) {
    Race race = racingManager.getRace(args[0]);
    int position = Integer.parseInt(args[1]);

    if(race.getState() != RaceState.UNDER_CONSTRUCTION) {
      MessageManager.setValue("race_name", race.getName());
      MessageManager.sendMessage(commandSender, MessageKey.EDIT_NO_EDIT_MODE);
      return;
    }

    Entity entity = (Entity) commandSender;

    if (position > race.getCheckpoints().size() + 1) {
      MessageManager.setValue("max", race.getCheckpoints().size() + 1);
      MessageManager.sendMessage(commandSender, MessageKey.RACE_ADD_CHECKPOINT_POSITION_OUT_OF_BOUNDS);
      return;
    }

    if(race.getCheckpoint(entity.getLocation()) != null) {
      MessageManager.sendMessage(commandSender, MessageKey.RACE_ADD_CHECKPOINT_IS_OCCUPIED);
      return;
    }

    racingManager.addCheckpoint(Util.centerOnBlock(entity.getLocation()), race, position, (RaceCheckpoint checkPoint) -> {
      MessageManager.setValue("race_name", race.getName());
      MessageManager.setValue("position", checkPoint.getPosition());
      MessageManager.sendMessage(entity, MessageKey.RACE_ADD_CHECKPOINT_SUCCESS);
    });
  }
}
