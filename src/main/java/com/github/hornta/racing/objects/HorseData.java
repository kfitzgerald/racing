package com.github.hornta.racing.objects;

import org.bukkit.entity.Horse;

public class HorseData {
  private final Horse.Color color;
  private final Horse.Style style;
  private final int age;

  public HorseData(Horse horse) {
    color = horse.getColor();
    style = horse.getStyle();
    age = horse.getAge();
  }

  public Horse.Color getColor() {
    return color;
  }

  public int getAge() {
    return age;
  }

  public Horse.Style getStyle() {
    return style;
  }
}
