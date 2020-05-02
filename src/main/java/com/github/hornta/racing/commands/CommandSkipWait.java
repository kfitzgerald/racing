package com.github.hornta.racing.commands;

import com.github.hornta.commando.ICommandHandler;
import com.github.hornta.racing.RacingManager;
import com.github.hornta.racing.enums.RaceSessionState;
import com.github.hornta.racing.MessageKey;
import com.github.hornta.messenger.MessageManager;
import com.github.hornta.racing.objects.Race;
import com.github.hornta.racing.objects.RaceSession;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandSkipWait extends RacingCommand implements ICommandHandler {
  public CommandSkipWait(RacingManager racingManager) {
    super(racingManager);
  }

  @Override
  public void handle(CommandSender commandSender, String[] args, int typedArgs) {
    Race race = racingManager.getRace(args[0]);

    List<RaceSession> sessions = racingManager.getRaceSessions(race, RaceSessionState.PREPARING);

    if(sessions.isEmpty()) {
      MessageManager.sendMessage(commandSender, MessageKey.RACE_SKIP_WAIT_NOT_STARTED);
      return;
    }

    for(RaceSession session : sessions) {
      session.skipToCountdown();
    }
  }
}
