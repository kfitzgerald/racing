package com.github.hornta.racing.api.migrations;

import com.github.hornta.racing.api.FileAPI;
import com.github.hornta.racing.api.IRaceFileMigration;
import com.github.hornta.racing.enums.RaceVersion;
import com.github.hornta.racing.enums.StartOrder;

import org.bukkit.configuration.file.YamlConfiguration;

public class StartOrderMigrationRace implements IRaceFileMigration {
  @Override
  public RaceVersion from() {
    return RaceVersion.V12;
  }

  @Override
  public RaceVersion to() {
    return RaceVersion.V13;
  }

  @Override
  public void migrate(YamlConfiguration yamlConfiguration) {
    yamlConfiguration.set(FileAPI.START_ORDER_FIELD, StartOrder.RANDOM.name());
  }
}
