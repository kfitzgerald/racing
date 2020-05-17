package com.github.hornta.racing.events;

import com.github.hornta.racing.enums.RaceSessionState;
import com.github.hornta.racing.objects.RaceSession;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SessionStateChangedEvent extends Event {
  private static final HandlerList handlers = new HandlerList();
  private final RaceSession raceSession;
  private final RaceSessionState oldState;

  public SessionStateChangedEvent(RaceSession raceSession, RaceSessionState oldState) {
    this.raceSession = raceSession;
    this.oldState = oldState;
  }

  public RaceSessionState getOldState() {
    return oldState;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  public RaceSession getRaceSession() {
    return raceSession;
  }

  @Override
  public @NotNull HandlerList getHandlers() {
    return handlers;
  }
}
