package com.github.hornta.racing.objects;

import com.github.hornta.racing.Util;
import com.github.hornta.racing.enums.RaceStatType;

import java.util.Map;
import java.util.UUID;

public class RacePlayerStatistic {
  private final UUID playerId;
  private String playerName;
  private int wins;
  private int runs;
  private long fastestLap;
  private final Map<Integer, Long> records;

  public RacePlayerStatistic(UUID playerId, String playerName, int wins, int runs, long fastestLap, Map<Integer, Long> records) {
    this.playerId = playerId;
    this.playerName = playerName;
    this.wins = wins;
    this.runs = runs;
    this.fastestLap = fastestLap;
    this.records = records;
  }

  public RacePlayerStatistic clone() {
    return new RacePlayerStatistic(playerId, playerName, wins, runs, fastestLap, records);
  }

  public UUID getPlayerId() {
    return playerId;
  }

  public String getPlayerName() {
    return playerName;
  }

  public long getFastestLap() {
    return fastestLap;
  }

  public int getRuns() {
    return runs;
  }

  public int getWins() {
    return wins;
  }

  public long getRecord(int laps)
  {
    if(records.containsKey(laps))
    {
      return ((Number)records.get(laps)).longValue();
    }
    else
    {
      return Long.MAX_VALUE;
    }
  }

  public Map<Integer, Long> getRecords()
  {
    return records;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public void setRuns(int runs) {
    this.runs = runs;
  }

  public void setFastestLap(long time) {
    this.fastestLap = time;
  }

  public void setWins(int wins) {
    this.wins = wins;
  }

  public void setRecord(int laps, long time) {
    if(!records.containsKey(laps) || ((Number) records.get(laps)).longValue() > time) {
      records.put(laps, time);
    }
  }

  public String getStatValue(RaceStatType statType, int laps) {
    String value = "";
    switch (statType) {
      case WIN_RATIO:
        value = (int)((float) wins / runs * 100) + "%";
        break;
      case FASTEST:
        value = Util.getTimeLeft(getRecord(laps));
        break;
      case FASTEST_LAP:
        value = Util.getTimeLeft(fastestLap);
        break;
      case WINS:
        value = wins + "";
        break;
      case RUNS:
        value = runs + "";
        break;
      default:
    }
    return value;
  }
}
