package com.github.hornta.racing.commands;

import se.hornta.commando.ICommandHandler;
import com.github.hornta.racing.RacingManager;
import com.github.hornta.racing.Util;
import com.github.hornta.racing.enums.RaceState;
import com.github.hornta.racing.MessageKey;
import se.hornta.messenger.MessageManager;
import com.github.hornta.racing.objects.Race;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

public class CommandRaceSetSpawn extends RacingCommand implements ICommandHandler {
  public CommandRaceSetSpawn(RacingManager racingManager) {
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

    race.setSpawn(Util.centerOnBlockHorizontally(((Entity) commandSender).getLocation()));

    racingManager.updateRace(race, () -> {
      MessageManager.setValue("race_name", race.getName());
      MessageManager.setValue("x", race.getSpawn().getX());
      MessageManager.setValue("y", race.getSpawn().getY());
      MessageManager.setValue("z", race.getSpawn().getZ());
      MessageManager.setValue("world", race.getSpawn().getWorld().getName());
      MessageManager.sendMessage(commandSender, MessageKey.RACE_SET_SPAWN_SUCCESS);
    });
  }
}
