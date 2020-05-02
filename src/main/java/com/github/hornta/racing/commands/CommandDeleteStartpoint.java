package com.github.hornta.racing.commands;

import com.github.hornta.commando.ICommandHandler;
import com.github.hornta.racing.RacingManager;
import com.github.hornta.racing.enums.RaceState;
import com.github.hornta.racing.MessageKey;
import com.github.hornta.messenger.MessageManager;
import com.github.hornta.racing.objects.Race;
import com.github.hornta.racing.objects.RaceStartPoint;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDeleteStartpoint extends RacingCommand implements ICommandHandler {
  public CommandDeleteStartpoint(RacingManager racingManager) {
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

    Player player = (Player) commandSender;
    RaceStartPoint startPoint = race.getStartPoint(Integer.parseInt(args[1]));

    racingManager.deleteStartPoint(race, startPoint, () -> {
      MessageManager.setValue("race_name", race.getName());
      MessageManager.setValue("position", String.valueOf(startPoint.getPosition()));
      MessageManager.sendMessage(player, MessageKey.RACE_DELETE_STARTPOINT_SUCCESS);
    });
  }
}
