package com.github.hornta.racing.hd_top_list;

import com.github.hornta.messenger.MessageManager;
import com.github.hornta.racing.ConfigKey;
import com.github.hornta.racing.MessageKey;
import com.github.hornta.racing.RacingPlugin;
import com.github.hornta.racing.Util;
import com.github.hornta.racing.enums.RaceStatType;
import com.github.hornta.racing.objects.Race;
import com.github.hornta.racing.objects.RacePlayerStatistic;
import com.gmail.filoghost.holographicdisplays.api.Hologram;

import java.util.Set;
import java.util.UUID;

public class HDTopList {
  private final UUID id;
  private final HDTopListVersion version;
  private final String name;
  private final Hologram hologram;
  private int laps;
  private RaceStatType statType;
  private Race race;

  HDTopList(
    UUID id,
    HDTopListVersion version,
    String name,
    Hologram hologram,
    Race race,
    RaceStatType statType,
    int laps
  ) {
    this.id = id;
    this.version = version;
    this.name = name;
    this.hologram = hologram;
    this.race = race;
    this.statType = statType;
    this.laps = laps;

    update();
  }

  public UUID getId() {
    return id;
  }

  public HDTopListVersion getVersion() {
    return version;
  }

  public String getName() {
    return name;
  }

  public Hologram getHologram() {
    return hologram;
  }

  public Race getRace() {
    return race;
  }

  public RaceStatType getStatType() {
    return statType;
  }

  public void setStatType(RaceStatType statType) {
    this.statType = statType;
    update();
  }

  public int getLaps() {
    return laps;
  }

  public void setLaps(int laps) {
    this.laps = laps;
  }

  public void setRace(Race race) {
    this.race = race;
    update();
  }

  protected void update() {
    Hologram hologram = this.hologram;
    hologram.clearLines();

    if(RacingPlugin.getInstance().getConfiguration().get(ConfigKey.HD_TOP_LIST_SHOW_HEADER)) {
      MessageManager.setValue("stat_type", statType.getFormattedStat(laps));
      MessageManager.setValue("race_name", race.getName());
      String header = MessageManager.getMessage(MessageKey.HD_TOP_LIST_HEADER);
      for (String part : header.split("\n")) {
        hologram.appendTextLine(part);
      }
    }

    Set<RacePlayerStatistic> stats = race.getResults(statType, laps);
    int p = 1;
    for(RacePlayerStatistic s : stats) {
      MessageManager.setValue("position", p++);
      MessageManager.setValue("player_name", s.getPlayerName());
      Util.setTimeUnitValues();
      String value = s.getStatValue(statType, laps);
      MessageManager.setValue("value", value);
      String item = MessageManager.getMessage(MessageKey.HD_TOP_LIST_ITEM);
      for(String part : item.split("\n")) {
        hologram.appendTextLine(part);
      }

      if(p > 10) {
        break;
      }
    }

    for(int i = p; i <= 10; ++i) {
      MessageManager.setValue("position", i);
      String none = MessageManager.getMessage(MessageKey.HD_TOP_LIST_NONE);
      for(String part : none.split("\n")) {
        hologram.appendTextLine(part);
      }
    }

    if(RacingPlugin.getInstance().getConfiguration().get(ConfigKey.HD_TOP_LIST_SHOW_FOOTER)) {
      String footer = MessageManager.getMessage(MessageKey.HD_TOP_LIST_FOOTER);
      for (String part : footer.split("\n")) {
        hologram.appendTextLine(part);
      }
    }
  }
}
