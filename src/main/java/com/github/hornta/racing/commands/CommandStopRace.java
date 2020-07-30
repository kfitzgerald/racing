package com.github.hornta.racing.commands;

import se.hornta.commando.ICommandHandler;
import com.github.hornta.racing.ConfigKey;
import com.github.hornta.racing.RacingManager;
import com.github.hornta.racing.MessageKey;
import se.hornta.messenger.MessageManager;
import com.github.hornta.racing.RacingPlugin;
import com.github.hornta.racing.objects.Race;
import com.github.hornta.racing.objects.RaceSession;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandStopRace extends RacingCommand implements ICommandHandler {
  public CommandStopRace(RacingManager racingManager) {
    super(racingManager);
  }

  @Override
  public void handle(CommandSender commandSender, String[] args, int typedArgs) {
    Race race = racingManager.getRace(args[0]);
    List<RaceSession> sessions = racingManager.getRaceSessions(race);

    if(sessions.isEmpty()) {
      MessageManager.sendMessage(commandSender, MessageKey.STOP_RACE_NOT_STARTED);
      return;
    }

    for(RaceSession session : sessions) {
      session.stop();
    }

    if(RacingPlugin.getInstance().getConfiguration().get(ConfigKey.BROADCAST_STOP_MESSAGE)) {
      MessageManager.broadcast(MessageKey.STOP_RACE_SUCCESS);
    } else {
      MessageManager.sendMessage(commandSender, MessageKey.STOP_RACE_SUCCESS);
    }
  }
}
