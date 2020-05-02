package com.github.hornta.racing.events;

import com.github.hornta.racing.objects.Race;
import com.github.hornta.racing.objects.RaceStartPoint;
import org.bukkit.event.HandlerList;

public class AddRaceStartPointEvent extends RaceStartPointEvent {
  private static final HandlerList handlers = new HandlerList();

  public AddRaceStartPointEvent(Race race, RaceStartPoint startPoint) {
    super(race, startPoint);
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }
}

