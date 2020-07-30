package com.github.hornta.racing.commands;

import se.hornta.commando.ICommandHandler;
import com.github.hornta.racing.RacingManager;
import com.github.hornta.racing.enums.RaceState;
import com.github.hornta.racing.MessageKey;
import se.hornta.messenger.MessageManager;
import com.github.hornta.racing.objects.Race;
import com.github.hornta.racing.objects.RaceCheckpoint;
import org.bukkit.command.CommandSender;

public class CommandDeleteCheckpoint extends RacingCommand implements ICommandHandler {
  public CommandDeleteCheckpoint(RacingManager racingManager) {
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

    RaceCheckpoint checkpoint = race.getCheckpoint(Integer.parseInt(args[1]));
    racingManager.deleteCheckpoint(race, checkpoint, () -> {
      MessageManager.setValue("race_name", race.getName());
      MessageManager.setValue("position", checkpoint.getPosition());

      MessageManager.sendMessage(commandSender, MessageKey.RACE_DELETE_CHECKPOINT_SUCCESS);
    });
  }
}
