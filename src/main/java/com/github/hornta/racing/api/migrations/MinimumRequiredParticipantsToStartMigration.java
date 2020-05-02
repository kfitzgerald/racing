package com.github.hornta.racing.api.migrations;

import com.github.hornta.racing.api.IFileMigration;
import com.github.hornta.racing.enums.RaceVersion;
import org.bukkit.configuration.file.YamlConfiguration;

public class MinimumRequiredParticipantsToStartMigration implements IFileMigration {
  @Override
  public RaceVersion from() {
    return RaceVersion.V6;
  }

  @Override
  public RaceVersion to() {
    return RaceVersion.V7;
  }

  @Override
  public void migrate(YamlConfiguration yamlConfiguration) {
    yamlConfiguration.set("min_required_participants_to_start", 1);
  }
}
