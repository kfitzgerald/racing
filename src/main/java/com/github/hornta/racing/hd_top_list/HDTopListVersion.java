package com.github.hornta.racing.hd_top_list;

import java.util.EnumSet;

public enum HDTopListVersion {
  V1;

  private static final HDTopListVersion[] copyOfValues = values();

  public static HDTopListVersion fromString(String name) {
    for (HDTopListVersion value : copyOfValues) {
      if (value.name().equals(name)) {
        return value;
      }
    }
    throw new IllegalArgumentException("Couldn't translate " + name + " into a HDTopListVersion");
  }

  static {
    int counter = 0;
    for(HDTopListVersion v : EnumSet.allOf(HDTopListVersion.class)) {
      v.setOrder(counter);
      counter += 1;
    }
  }

  public static HDTopListVersion getLast() {
    return copyOfValues[copyOfValues.length - 1];
  }

  private int order;

  public void setOrder(int order) {
    this.order = order;
  }

  public int getOrder() {
    return order;
  }

  public boolean isGreater(HDTopListVersion v) {
    return order > v.order;
  }
}
