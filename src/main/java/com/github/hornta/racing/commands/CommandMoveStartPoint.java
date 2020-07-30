package com.github.hornta.racing.commands;

import se.hornta.commando.ICommandHandler;
import se.hornta.messenger.MessageManager;
import com.github.hornta.racing.MessageKey;
import com.github.hornta.racing.RacingManager;
import com.github.hornta.racing.Util;
import com.github.hornta.racing.enums.RaceState;
import com.github.hornta.racing.objects.Race;
import com.github.hornta.racing.objects.RaceStartPoint;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

public class CommandMoveStartPoint extends RacingCommand implements ICommandHandler {
  public CommandMoveStartPoint(RacingManager racingManager) {
    super(racingManager);
  }

  @Override
  public void handle(CommandSender commandSender, String[] args, int typedArgs) {
    Race race = racingManager.getRace(args[0]);
    RaceStartPoint startPoint = race.getStartPoint(Integer.parseInt(args[1]));
    
    if(race.getState() != RaceState.UNDER_CONSTRUCTION) {
      MessageManager.setValue("race_name", race.getName());
      MessageManager.sendMessage(commandSender, MessageKey.EDIT_NO_EDIT_MODE);
      return;
    }

    Entity player = (Entity) commandSender;

    RaceStartPoint occupied = race.getStartPoint(player.getLocation());
    if (occupied == startPoint) {
      MessageManager.sendMessage(commandSender, MessageKey.RACE_MOVE_STARTPOINT_NO_CHANGE);
      return;
    } else if(occupied != null) {
      MessageManager.sendMessage(commandSender, MessageKey.RACE_MOVE_STARTPOINT_OCCUPIED);
      return;
    }

    racingManager.moveStartPoint(
      Util.centerOnBlockHorizontally(player.getLocation()),
      race,
      startPoint,
      () -> {
        MessageManager.setValue("race_name", race.getName());
        MessageManager.setValue("position", startPoint.getPosition());
        MessageManager.setValue("x", startPoint.getLocation().getX());
        MessageManager.setValue("x", startPoint.getLocation().getY());
        MessageManager.setValue("x", startPoint.getLocation().getZ());
        MessageManager.setValue("world", startPoint.getLocation().getWorld().getName());
        MessageManager.sendMessage(player, MessageKey.RACE_MOVE_STARTPOINT_SUCCESS);
      }
    );
  }
}
