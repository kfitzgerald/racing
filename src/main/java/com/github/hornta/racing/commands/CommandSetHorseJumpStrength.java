package com.github.hornta.racing.commands;

import com.github.hornta.commando.ICommandHandler;
import com.github.hornta.racing.RacingManager;
import com.github.hornta.racing.enums.RaceState;
import com.github.hornta.racing.MessageKey;
import com.github.hornta.messenger.MessageManager;
import com.github.hornta.racing.objects.Race;
import org.bukkit.command.CommandSender;

public class CommandSetHorseJumpStrength extends RacingCommand implements ICommandHandler {
  public CommandSetHorseJumpStrength(RacingManager racingManager) {
    super(racingManager);
  }

  @Override
  public void handle(CommandSender commandSender, String[] args, int typedArgs) {
    Race race = racingManager.getRace(args[0]);
    float jumpStrength = Float.parseFloat(args[1]);

    if(race.getState() != RaceState.UNDER_CONSTRUCTION) {
      MessageManager.setValue("race_name", race.getName());
      MessageManager.sendMessage(commandSender, MessageKey.EDIT_NO_EDIT_MODE);
      return;
    }

    race.setHorseJumpStrength(jumpStrength);

    racingManager.updateRace(race, () -> {
      MessageManager.setValue("jump_strength", jumpStrength);
      MessageManager.sendMessage(commandSender, MessageKey.RACE_SET_HORSE_JUMP_STRENGTH);
    });
  }
}
