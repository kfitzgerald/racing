package com.github.hornta.racing.enums;

public enum RaceState {
  ENABLED,
  DISABLED,
  UNDER_CONSTRUCTION;

  public static RaceState fromString(String string) {
    for(RaceState state : values()) {
      if(state.name().equalsIgnoreCase(string)) {
        return state;
      }
    }
    return null;
  }
}
