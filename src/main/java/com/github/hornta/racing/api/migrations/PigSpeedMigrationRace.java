package com.github.hornta.racing.api.migrations;

import com.github.hornta.racing.api.IRaceFileMigration;
import com.github.hornta.racing.enums.RaceVersion;
import org.bukkit.configuration.file.YamlConfiguration;

public class PigSpeedMigrationRace implements IRaceFileMigration {
  @Override
  public RaceVersion from() {
    return RaceVersion.V7;
  }

  @Override
  public RaceVersion to() {
    return RaceVersion.V8;
  }

  @Override
  public void migrate(YamlConfiguration yamlConfiguration) {
    yamlConfiguration.set("pig_speed", 0.25D);
  }
}
