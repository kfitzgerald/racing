package com.github.hornta.racing.events;

import com.github.hornta.racing.objects.RaceSession;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RaceSessionStopEvent extends Event {
  private static final HandlerList handlers = new HandlerList();
  private final RaceSession raceSession;
  private final Boolean raceCancelled;

  public RaceSessionStopEvent(RaceSession raceSession, Boolean cancelled) {
    this.raceSession = raceSession;
    this.raceCancelled = cancelled;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  public RaceSession getRaceSession() {
    return raceSession;
  }

  public Boolean getRaceCancelled() { return raceCancelled; }

  @Override
  public @NotNull HandlerList getHandlers() {
    return handlers;
  }

}
