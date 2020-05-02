package com.github.hornta.racing.events;

import com.github.hornta.racing.objects.RaceSessionResult;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RaceSessionResultEvent extends Event {
  private static final HandlerList handlers = new HandlerList();
  private final RaceSessionResult result;

  public RaceSessionResultEvent(RaceSessionResult result) {
    this.result = result;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  public RaceSessionResult getResult() {
    return result;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }
}
