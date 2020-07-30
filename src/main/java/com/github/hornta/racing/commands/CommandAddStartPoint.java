package com.github.hornta.racing.commands;

import se.hornta.commando.ICommandHandler;
import com.github.hornta.racing.RacingManager;
import com.github.hornta.racing.Util;
import com.github.hornta.racing.enums.RaceState;
import com.github.hornta.racing.MessageKey;
import se.hornta.messenger.MessageManager;
import com.github.hornta.racing.objects.Race;
import com.github.hornta.racing.objects.RaceStartPoint;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

public class CommandAddStartPoint extends RacingCommand implements ICommandHandler {
  public CommandAddStartPoint(RacingManager racingManager) {
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

    Entity player = (Entity) commandSender;

    if (position > race.getStartPoints().size() + 1) {
      MessageManager.setValue("max", race.getStartPoints().size() + 1);
      MessageManager.sendMessage(commandSender, MessageKey.RACE_ADD_STARTPOINT_POSITION_OUT_OF_BOUNDS);
      return;
    }

    if(race.getStartPoint(player.getLocation()) != null) {
      MessageManager.sendMessage(commandSender, MessageKey.RACE_ADD_STARTPOINT_IS_OCCUPIED);
      return;
    }

    racingManager.addStartPoint(Util.centerOnBlockHorizontally(player.getLocation()), race, position, (RaceStartPoint startPoint) -> {
      MessageManager.setValue("race_name", race.getName());
      MessageManager.setValue("position", startPoint.getPosition());
      MessageManager.sendMessage(player, MessageKey.RACE_ADD_STARTPOINT_SUCCESS);
    });
  }
}
