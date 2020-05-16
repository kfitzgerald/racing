package com.github.hornta.racing.hd_top_list;

import com.github.hornta.racing.RacingPlugin;
import com.github.hornta.racing.api.ParseRaceException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.Collection;

public class HologramMigrationManager {
  private final Collection<IHologramFileMigration> migrations = new ArrayList<>();

  public void addMigration(IHologramFileMigration migration) {

    if(migration.from().equals(migration.to())) {
      throw new IllegalArgumentException("Migration from() and to() must return different values");
    }

    if(migration.from().isGreater(migration.to())) {
      throw new IllegalArgumentException("Migration from() must not be greater than to()");
    }

    for (IHologramFileMigration iFileMigration : migrations) {
      if (iFileMigration.from().equals(migration.from())) {
        throw new IllegalArgumentException("There is already a migration with the same from()");
      }
      if (iFileMigration.to().equals(migration.to())) {
        throw new IllegalArgumentException("There is already a migration with the same to()");
      }
    }

    migrations.add(migration);
  }

  public void migrate(YamlConfiguration yaml) {
    HDTopListVersion version;

    try {
      version = HDTopListVersion.fromString(yaml.getString("version"));
    } catch (IllegalArgumentException e) {
      throw new ParseRaceException("Couldn't find version");
    }

    HDTopListVersion currentVersion = HDTopListVersion.getLast();

    if(version.isGreater(currentVersion)) {
      throw new ParseRaceException("Hologram top list version greater than plugin hologram top list version not supported.");
    }

    if(version == currentVersion) {
      return;
    }

    for(IHologramFileMigration migration : migrations) {
      HDTopListVersion fromVersion = HDTopListVersion.fromString(yaml.getString("version"));
      if(migration.from().equals(fromVersion)) {
        migration.migrate(yaml);
        yaml.set("version", migration.to().toString());
        RacingPlugin.logger().info("Migrate hologram top list from version " + fromVersion.toString() + " to " + migration.to().toString());
      }
    }
  }
}
