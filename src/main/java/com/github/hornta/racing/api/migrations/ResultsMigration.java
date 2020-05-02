package com.github.hornta.racing.api.migrations;

import com.github.hornta.racing.api.FileAPI;
import com.github.hornta.racing.api.IFileMigration;
import com.github.hornta.racing.enums.RaceVersion;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Collections;

public class ResultsMigration implements IFileMigration {
  @Override
  public RaceVersion from() {
    return RaceVersion.V5;
  }

  @Override
  public RaceVersion to() {
    return RaceVersion.V6;
  }

  @Override
  public void migrate(YamlConfiguration yamlConfiguration) {
    yamlConfiguration.set(FileAPI.RESULTS_FIELD, Collections.emptyList());
  }
}
