package com.github.hornta.racing.events;

import com.github.hornta.racing.objects.RaceSession;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RaceSessionStartEvent extends Event {
  private static final HandlerList handlers = new HandlerList();
  private RaceSession raceSession;

  public RaceSessionStartEvent(RaceSession raceSession) {
    this.raceSession = raceSession;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  public RaceSession getRaceSession() {
    return raceSession;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }
}
