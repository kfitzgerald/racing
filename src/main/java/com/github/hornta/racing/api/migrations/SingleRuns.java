package com.github.hornta.racing.api.migrations;

import com.github.hornta.racing.api.FileAPI;
import com.github.hornta.racing.api.IRaceFileMigration;
import com.github.hornta.racing.enums.RaceVersion;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Collection;
import java.util.Map;

public class SingleRuns implements IRaceFileMigration {
  @Override
  public RaceVersion from() {
    return RaceVersion.V14;
  }

  @Override
  public RaceVersion to(){
    return RaceVersion.V15;
  }

  @Override
  public void migrate(YamlConfiguration yamlConfiguration) {
    @SuppressWarnings("unchecked")
    Collection<Map<String, Object>> results = (Collection<Map<String, Object>>) yamlConfiguration.getList(FileAPI.RESULTS_FIELD);
    for(Map<String, Object> r : results) {
      int runs = (int)r.get(FileAPI.RESULTS_FIELD_RUNS);
      r.put(FileAPI.RESULTS_FIELD_SINGLE_RUNS, runs);
    }
    yamlConfiguration.set(FileAPI.RESULTS_FIELD, results);
  }
}
