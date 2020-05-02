package com.github.hornta.racing.api.migrations;

import com.github.hornta.racing.api.FileAPI;
import com.github.hornta.racing.api.IFileMigration;
import com.github.hornta.racing.enums.RaceVersion;
import org.bukkit.configuration.file.YamlConfiguration;

public class PotionEffectsMigration implements IFileMigration {
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
