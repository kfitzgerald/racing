package com.github.hornta.racing.api.migrations;

import com.github.hornta.racing.api.FileAPI;
import com.github.hornta.racing.api.IRaceFileMigration;
import com.github.hornta.racing.enums.RaceVersion;
import org.bukkit.configuration.file.YamlConfiguration;

public class PotionEffectsMigrationRace implements IRaceFileMigration {
  @Override
  public RaceVersion from() {
    return RaceVersion.V3;
  }

  @Override
  public RaceVersion to() {
    return RaceVersion.V4;
  }

  @Override
  public void migrate(YamlConfiguration yamlConfiguration) {
    yamlConfiguration.createSection(FileAPI.POTION_EFFECTS_FIELD);
  }
}
