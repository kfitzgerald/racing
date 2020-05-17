package com.github.hornta.racing.api.migrations;

import com.github.hornta.racing.api.IRaceFileMigration;
import com.github.hornta.racing.enums.RaceSignType;
import com.github.hornta.racing.enums.RaceVersion;

import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;
import java.util.Map;

public class SignTypeMigrationRace implements IRaceFileMigration {
  @Override
  public RaceVersion from() {
    return RaceVersion.V13;
  }

  @Override
  public RaceVersion to() {
    return RaceVersion.V14;
  }

  @Override
  public void migrate(YamlConfiguration yamlConfiguration) {
    @SuppressWarnings("unchecked")
    List<Map<String, Object>> entries = (List<Map<String, Object>>)yamlConfiguration.getList("signs");
    for(Map<String, Object> sign : entries) {
      sign.put("type", RaceSignType.JOIN.name());
    }
    yamlConfiguration.set("signs", entries);
  }
}
