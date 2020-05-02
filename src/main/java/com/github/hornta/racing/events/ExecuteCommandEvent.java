package com.github.hornta.racing.events;

import com.github.hornta.racing.enums.RaceCommandType;
import com.github.hornta.racing.objects.RacePlayerSession;
import com.github.hornta.racing.objects.RaceSession;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ExecuteCommandEvent extends Event {
  private static final HandlerList handlers = new HandlerList();
  private RaceCommandType commandType;
  private RaceSession raceSession;
  private RacePlayerSession playerSession;

  public ExecuteCommandEvent(RaceCommandType commandType, RaceSession raceSession) {
    this.commandType = commandType;
    this.raceSession = raceSession;
  }

  public ExecuteCommandEvent(RaceCommandType commandType, RaceSession raceSession, RacePlayerSession playerSession) {
    this.commandType = commandType;
    this.raceSession = raceSession;
    this.playerSession = playerSession;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  public RaceCommandType getCommandType() {
    return commandType;
  }

  public RaceSession getRaceSession() {
    return raceSession;
  }

  public RacePlayerSession getPlayerSession() {
    return playerSession;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }
}
