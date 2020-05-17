package com.github.hornta.racing.api.migrations;

import com.github.hornta.racing.api.FileAPI;
import com.github.hornta.racing.api.IRaceFileMigration;
import com.github.hornta.racing.enums.RaceVersion;

import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaceDurationMigrationRace implements IRaceFileMigration {
  @Override
  public RaceVersion from() {
    return RaceVersion.V11;
  }

  @Override
  public RaceVersion to() {
    return RaceVersion.V12;
  }

  @Override
  public void migrate(YamlConfiguration yamlConfiguration) {
    @SuppressWarnings("unchecked")
    List<Map<String, Object>> results = (List<Map<String, Object>>)yamlConfiguration.getList(FileAPI.RESULTS_FIELD);
    for(Map<String, Object> player_results : results) {
      Object duration = player_results.get(FileAPI.RESULTS_FIELD_DURATION);
      player_results.put(FileAPI.RESULTS_FIELD_FASTEST_LAP, duration);
      Map<Integer, Object> records = new HashMap<Integer, Object>();
      records.put(1, duration);
      player_results.put(FileAPI.RESULTS_FIELD_RECORDS, records);
      player_results.remove(FileAPI.RESULTS_FIELD_DURATION);
    }
  }
}
