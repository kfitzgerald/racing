package com.github.hornta.racing.api.migrations;

import com.github.hornta.racing.api.FileAPI;
import com.github.hornta.racing.api.IRaceFileMigration;
import com.github.hornta.racing.enums.RaceVersion;
import org.bukkit.configuration.file.YamlConfiguration;

public class EntryFeeMigrationRace implements IRaceFileMigration {
  @Override
  public RaceVersion from() {
    return RaceVersion.V1;
  }

  @Override
  public RaceVersion to() {
    return RaceVersion.V2;
  }

  @Override
  public void migrate(YamlConfiguration yamlConfiguration) {
    yamlConfiguration.set(FileAPI.ENTRY_FEE_FIELD, 0D);
  }
}
