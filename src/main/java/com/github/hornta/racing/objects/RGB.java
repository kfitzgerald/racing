package com.github.hornta.racing.objects;

import java.util.Random;

public class RGB {
  private final int r;
  private final int g;
  private final int b;

  private static final Random random = new Random();

  RGB(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  private static float randomWithin() {
    return (float) 0.0 + ((float) 1.0 - (float) 0.0) * random.nextFloat();
  }

  public static RGB randomLightColor() {
    float hue = randomWithin();
    float saturation = 0.9f;
    float lightness = 0.55f;
    return fromHSL(hue, saturation, lightness);
  }

  private static float hue2rgb(float p, float q, float t) {
    if(t < 0) {
      t += 1;
    }
    if(t > 1) {
      t -= 1;
    }
    if(t < 1/6f) {
      return p + (q - p) * 6 * t;
    }
    if(t < 1/2f) {
      return q;
    }
    if(t < 2/3f) {
      return p + (q - p) * (2/3f - t) * 6;
    }
    return p;
  }

  public static RGB fromHSL(float h, float s, float l) {
    float r;
    float g;
    float b;

    if(s == 0) {
      r = l;
      g = l;
      b = l;
    } else {
      float q = l < 0.5 ? l * (1 + s) : l + s - l * s;
      float p = 2 * l - q;
      r = hue2rgb(p, q, h + 1/3f);
      g = hue2rgb(p, q, h);
      b = hue2rgb(p, q, h - 1/3f);
    }

    return new RGB(
      Math.round(r * 255),
      Math.round(g * 255),
      Math.round(b * 255)
    );
  }

  public int getR() {
    return r;
  }

  public int getG() {
    return g;
  }

  public int getB() {
    return b;
  }
}
