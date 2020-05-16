package com.github.hornta.racing.api;

import com.github.hornta.racing.RacingPlugin;
import com.github.hornta.racing.enums.RaceVersion;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.Collection;

public class RaceMigrationManager {
  private final Collection<IRaceFileMigration> migrations = new ArrayList<>();

  public void addMigration(IRaceFileMigration migration) {

    if(migration.from().equals(migration.to())) {
      throw new IllegalArgumentException("Migration from() and to() must return different values");
    }

    if(migration.from().isGreater(migration.to())) {
      throw new IllegalArgumentException("Migration from() must not be greater than to()");
    }

    for (IRaceFileMigration iRaceFileMigration : migrations) {
      if (iRaceFileMigration.from().equals(migration.from())) {
        throw new IllegalArgumentException("There is already a migration with the same from()");
      }
      if (iRaceFileMigration.to().equals(migration.to())) {
        throw new IllegalArgumentException("There is already a migration with the same to()");
      }
    }

    migrations.add(migration);
  }

  public void migrate(YamlConfiguration yaml) {
    RaceVersion version;

    try {
      version = RaceVersion.fromString(yaml.getString("version"));
    } catch (IllegalArgumentException e) {
      throw new ParseRaceException("Couldn't find version");
    }

    RaceVersion currentVersion = RaceVersion.getLast();

    if(version.isGreater(currentVersion)) {
      throw new ParseRaceException("Race version greater than plugin race version not supported.");
    }

    if(version == currentVersion) {
      return;
    }

    for(IRaceFileMigration migration : migrations) {
      RaceVersion fromVersion = RaceVersion.fromString(yaml.getString("version"));
      if(migration.from().equals(fromVersion)) {
        migration.migrate(yaml);
        yaml.set("version", migration.to().toString());
        RacingPlugin.logger().info("Migrate race from version " + fromVersion.toString() + " to " + migration.to().toString());
      }
    }
  }
}
