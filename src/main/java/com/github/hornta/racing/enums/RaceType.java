package com.github.hornta.racing.enums;

public enum RaceType {
  PLAYER,
  HORSE,
  PIG,
  ELYTRA,
  BOAT,
  STRIDER,
  MINECART;

  public static RaceType fromString(String string) {
    for(RaceType type : values()) {
      if(type.name().compareToIgnoreCase(string) == 0) {
        return type;
      }
    }
    return null;
  }
}
