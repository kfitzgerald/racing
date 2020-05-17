package com.github.hornta.racing.hd_top_list;

import com.github.hornta.racing.ConfigKey;
import com.github.hornta.racing.RacingPlugin;
import com.github.hornta.racing.migration.ParseYamlLocationException;
import com.github.hornta.racing.enums.RaceStatType;

import com.github.hornta.racing.objects.Race;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.logging.Level;

public class FileStorage {
  private static final String ID_FIELD = "id";
  private static final String VERSION_FIELD = "version";
  private static final String NAME_FIELD = "name";
  private static final String RACE_ID_FIELD = "race";
  private static final String LOC_FIELD = "loc";
  private static final String STAT_TYPE_FIELD = "stat_type";
  private static final String LAPS_FIELD = "laps";

  private final ExecutorService fileService = Executors.newSingleThreadExecutor();
  private final File directory;
  private final HologramMigrationManager migrationManager = new HologramMigrationManager();

  public FileStorage(Plugin plugin) {
    directory = new File(plugin.getDataFolder(), RacingPlugin.getInstance().getConfiguration().get(ConfigKey.HD_TOP_LIST_DIRECTORY));
  }

  public void load(Consumer<List<HDTopList>> callback) {
    CompletableFuture.supplyAsync(() -> {
      List<YamlConfiguration> cfgFiles = new ArrayList<>();
      File[] files = directory.listFiles();

      if (files == null) {
        return cfgFiles;
      }

      for (File file : files) {
        if (file.isFile()) {
          YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

          try {
            migrationManager.migrate(yaml);
            try {
              yaml.save(file);
            } catch (IOException e) {
              RacingPlugin.getInstance().getLogger().log(Level.SEVERE, "Failed to save hologram top list");
            }
            cfgFiles.add(yaml);
          } catch (Exception ex) {
            RacingPlugin.logger().log(Level.SEVERE, ex.getMessage(), ex);
          }
        }
      }

      return cfgFiles;
    }).thenAccept((Iterable<YamlConfiguration> configurations) -> Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(RacingPlugin.getInstance(), () -> {
      List<HDTopList> topLists = new ArrayList<>();

      for (YamlConfiguration config : configurations) {
        try {
          topLists.add(parse(config));
        } catch (Exception ex) {
          RacingPlugin.logger().log(Level.SEVERE, ex.getMessage());
        }
      }

      callback.accept(topLists);
    }));
  }

  public void delete(HDTopList topList, Consumer<Boolean> callback) {
    File cfgFile = new File(directory, topList.getId() + ".yml");

    CompletableFuture.supplyAsync(() -> {
      boolean success = false;
      try {
        Files.delete(cfgFile.toPath());
        success = true;
      } catch (NoSuchFileException ex) {
        RacingPlugin.logger().log(Level.WARNING, "Failed to delete top list file. File `" + cfgFile.getName() + "` wasn't found.", ex);
      } catch (DirectoryNotEmptyException ex) {
        RacingPlugin.logger().log(Level.SEVERE, "Failed to delete top list file. Expected a file but tried to delete a folder", ex);
      } catch (IOException ex) {
        RacingPlugin.logger().log(Level.SEVERE, ex.getMessage(), ex);
      }

      return success;
    }, fileService).thenAccept((Boolean result) -> Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(RacingPlugin.getInstance(), () -> callback.accept(result)));
  }

  public void write(HDTopList topList, Consumer<Boolean> callback) {
    File cfgFile = new File(directory, topList.getId() + ".yml");

    CompletableFuture.supplyAsync(() -> {
      YamlConfiguration yaml = new YamlConfiguration();
      yaml.set(ID_FIELD, topList.getId().toString());
      yaml.set(VERSION_FIELD, topList.getVersion().name());
      yaml.set(NAME_FIELD, topList.getName());
      yaml.set(RACE_ID_FIELD, topList.getRace().getId().toString());
      writeLocation(topList.getHologram().getLocation(), yaml, LOC_FIELD);
      yaml.set(STAT_TYPE_FIELD, topList.getStatType().name());
      yaml.set(LAPS_FIELD, topList.getLaps());

      try {
        yaml.save(cfgFile);
      } catch (IOException ex) {
        RacingPlugin.logger().log(Level.SEVERE, ex.getMessage(), ex);
        return false;
      }
      return true;
    }, fileService).thenAccept((Boolean result) -> Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(RacingPlugin.getInstance(), () -> callback.accept(result)));
  }

  private HDTopList parse(ConfigurationSection yaml) {
    String idString = yaml.getString(ID_FIELD);

    if(idString == null) {
      throw new ParseHDTopListException("`" + ID_FIELD + "` is missing");
    }

    UUID id;
    try {
      id = UUID.fromString(idString);
    } catch (IllegalArgumentException ex) {
      throw new ParseHDTopListException("Couldn't convert id to UUID");
    }

    if(!yaml.isSet(VERSION_FIELD)) {
      throw new ParseHDTopListException("`" + VERSION_FIELD + "` is missing");
    }

    HDTopListVersion version = HDTopListVersion.fromString(yaml.getString("version"));

    Location location;
    try {
      location = parseLocation(yaml, LOC_FIELD);
    } catch (ParseYamlLocationException ex) {
      throw new ParseHDTopListException("Couldn't parse location: " + ex.getMessage());
    }

    String name = yaml.getString(NAME_FIELD);
    if(name == null) {
      throw new ParseHDTopListException("`" + NAME_FIELD + "` is missing");
    }

    String stringRaceId = yaml.getString(RACE_ID_FIELD);
    if(stringRaceId == null) {
      throw new ParseHDTopListException("`" + RACE_ID_FIELD + "` is missing");
    }

    Race race;
    try {
      UUID raceId = UUID.fromString(stringRaceId);
      race = RacingPlugin.getInstance().getRacingManager().getRace(raceId);
    } catch (IllegalArgumentException ex) {
      throw new ParseHDTopListException("Couldn't convert race id to UUID");
    }

    if(race == null) {
      throw new ParseHDTopListException("Couldn't find race with id `" + stringRaceId + "`");
    }

    RaceStatType type;
    try {
      type = RaceStatType.valueOf(yaml.getString(STAT_TYPE_FIELD));
    } catch (IllegalArgumentException ex) {
      throw new ParseHDTopListException("`" + STAT_TYPE_FIELD + "` is invalid");
    }

    int laps;
    try {
      laps = yaml.getInt(LAPS_FIELD);
    } catch (IllegalArgumentException ex) {
      throw new ParseHDTopListException("`" + LAPS_FIELD + "` is invalid");
    }

    Hologram hologram = HologramsAPI.createHologram(RacingPlugin.getInstance(), location);
    return new HDTopList(
      id,
      version,
      name,
      hologram,
      race,
      type,
      laps
    );
  }

  private Location parseLocation(ConfigurationSection section, String path) throws ParseYamlLocationException {
    String xPath = path + ".x";
    if(!section.isDouble(xPath)) {
      throw new ParseYamlLocationException("Expected `" + xPath + "` to be an integer");
    }

    String yPath = path + ".y";
    if(!section.isDouble(yPath)) {
      throw new ParseYamlLocationException("Expected `" + yPath + "` to be an integer");
    }

    String zPath = path + ".z";
    if(!section.isDouble(zPath)) {
      throw new ParseYamlLocationException("Expected `" + zPath + "` to be an integer");
    }

    String pitchPath = path + ".pitch";
    if(!section.isDouble(pitchPath)) {
      throw new ParseYamlLocationException("Expected `" + pitchPath + "` to be a double");
    }

    String yawPath = path + ".yaw";
    if(!section.isDouble(yawPath)) {
      throw new ParseYamlLocationException("Expected `" + yawPath + "` to be a double");
    }

    String worldPath = path + ".world";
    String worldName = section.getString(worldPath);
    if(worldName == null) {
      throw new ParseYamlLocationException("Expected `" + worldPath + "` to be a string");
    }

    World world = Bukkit.getWorld(worldName);

    if(world == null) {
      throw new ParseYamlLocationException("Couldn't find world with name `" + section.getString(worldPath) + "`");
    }

    return new Location(
      world,
      section.getDouble(xPath),
      section.getDouble(yPath),
      section.getDouble(zPath),
      (float)section.getDouble(yawPath),
      (float)section.getDouble(pitchPath)
    );
  }

  private void writeLocation(Location location, ConfigurationSection yaml, String path) {
    yaml.set(path + ".x", location.getX());
    yaml.set(path + ".y", location.getY());
    yaml.set(path + ".z", location.getZ());
    yaml.set(path + ".pitch", location.getPitch());
    yaml.set(path + ".yaw", location.getYaw());
    yaml.set(path + ".world", location.getWorld().getName());
  }
}
