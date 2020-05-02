package com.github.hornta.racing.config;

import com.github.hornta.racing.ConfigKey;
import com.github.hornta.racing.api.StorageType;
import com.github.hornta.racing.enums.RespawnType;
import com.github.hornta.racing.enums.TeleportAfterRaceWhen;
import com.github.hornta.versioned_config.Configuration;
import com.github.hornta.versioned_config.IConfigVersion;
import com.github.hornta.versioned_config.Patch;
import com.github.hornta.versioned_config.Type;

import java.util.Arrays;
import java.util.Collections;

public class InitialVersion implements IConfigVersion<ConfigKey> {
  @Override
  public int version() {
    return 1;
  }

  @Override
  public Patch<ConfigKey> migrate(Configuration<ConfigKey> configuration) {
    Patch<ConfigKey> patch = new Patch<>();
    patch.set(ConfigKey.LANGUAGE, "language", "english", Type.STRING);
    // https://www.loc.gov/standards/iso639-2/php/code_list.php
    patch.set(ConfigKey.LOCALE, "locale", "en", Type.STRING);
    patch.set(ConfigKey.SONGS_DIRECTORY, "songs_directory", "songs", Type.STRING);
    patch.set(ConfigKey.STORAGE, "storage.current", StorageType.FILE.name(), Type.STRING);
    patch.set(ConfigKey.FILE_RACE_DIRECTORY, "storage.file.directory", "races", Type.STRING);
    patch.set(ConfigKey.RACE_PREPARE_TIME, "prepare_time", 60, Type.INTEGER);
    patch.set(ConfigKey.RACE_ANNOUNCE_INTERVALS, "race_announce_intervals", Arrays.asList(30, 10), Type.LIST);
    patch.set(ConfigKey.COUNTDOWN, "countdown", 10, Type.INTEGER);
    patch.set(ConfigKey.RESPAWN_PLAYER_DEATH, "respawn.player.death", RespawnType.FROM_LAST_CHECKPOINT, Type.STRING);
    patch.set(ConfigKey.RESPAWN_PLAYER_INTERACT, "respawn.player.interact", RespawnType.NONE, Type.STRING);
    patch.set(ConfigKey.RESPAWN_ELYTRA_DEATH, "respawn.elytra.death", RespawnType.FROM_START, Type.STRING);
    patch.set(ConfigKey.RESPAWN_ELYTRA_INTERACT, "respawn.elytra.interact", RespawnType.FROM_START, Type.STRING);
    patch.set(ConfigKey.RESPAWN_PIG_DEATH, "respawn.pig.death", RespawnType.FROM_LAST_CHECKPOINT, Type.STRING);
    patch.set(ConfigKey.RESPAWN_PIG_INTERACT, "respawn.pig.interact", RespawnType.NONE, Type.STRING);
    patch.set(ConfigKey.RESPAWN_HORSE_DEATH, "respawn.horse.death", RespawnType.FROM_LAST_CHECKPOINT, Type.STRING);
    patch.set(ConfigKey.RESPAWN_HORSE_INTERACT, "respawn.horse.interact", RespawnType.NONE, Type.STRING);
    patch.set(ConfigKey.RESPAWN_BOAT_DEATH, "respawn.boat.death", RespawnType.FROM_LAST_CHECKPOINT, Type.STRING);
    patch.set(ConfigKey.RESPAWN_BOAT_INTERACT, "respawn.boat.interact", RespawnType.NONE, Type.STRING);
    patch.set(ConfigKey.RESPAWN_MINECART_DEATH, "respawn.minecart.death", RespawnType.FROM_LAST_CHECKPOINT, Type.STRING);
    patch.set(ConfigKey.RESPAWN_MINECART_INTERACT, "respawn.minecart.interact", RespawnType.FROM_LAST_CHECKPOINT, Type.STRING);
    patch.set(ConfigKey.DISCORD_ENABLED, "discord.enabled", false, Type.BOOLEAN);
    patch.set(ConfigKey.DISCORD_TOKEN, "discord.bot_token", "", Type.STRING);
    patch.set(ConfigKey.DISCORD_ANNOUNCE_CHANNEL, "discord.announce_channel", "", Type.STRING);
    patch.set(ConfigKey.ADVENTURE_ON_START, "adventure_mode_on_start", true, Type.BOOLEAN);
    patch.set(ConfigKey.FRIENDLY_FIRE_COUNTDOWN, "friendlyfire.countdown", false, Type.BOOLEAN);
    patch.set(ConfigKey.FRIENDLY_FIRE_STARTED, "friendlyfire.started", false, Type.BOOLEAN);
    patch.set(ConfigKey.COLLISION_COUNTDOWN, "collision.countdown", false, Type.BOOLEAN);
    patch.set(ConfigKey.COLLISION_STARTED, "collision.started", false, Type.BOOLEAN);
    patch.set(ConfigKey.ELYTRA_RESPAWN_ON_GROUND, "elytra_respawn_on_ground", true, Type.BOOLEAN);
    patch.set(ConfigKey.BLOCKED_COMMANDS, "blocked_commands", Arrays.asList("spawn", "wild", "wilderness", "rtp", "tpa", "tpo", "tp", "tpahere", "tpaccept", "tpdeny", "tpyes", "tpno", "tppos", "warp", "home", "rc spawn", "racing spawn"), Type.LIST);
    patch.set(ConfigKey.PREVENT_JOIN_FROM_GAME_MODE, "prevent_join_from_game_mode", Collections.emptyList(), Type.LIST);
    patch.set(ConfigKey.START_ON_JOIN_SIGN, "start_on_join.sign", false, Type.BOOLEAN);
    patch.set(ConfigKey.START_ON_JOIN_COMMAND, "start_on_join.command", false, Type.BOOLEAN);
    patch.set(ConfigKey.TELEPORT_AFTER_RACE_ENABLED, "teleport_after_race.enabled", false, Type.BOOLEAN);
    patch.set(ConfigKey.TELEPORT_AFTER_RACE_WHEN, "teleport_after_race.when", TeleportAfterRaceWhen.PARTICIPANT_FINISHES, Type.STRING);
    patch.set(ConfigKey.VERBOSE, "verbose", false, Type.BOOLEAN);
    patch.set(ConfigKey.CHECKPOINT_PARTICLES_DURING_RACE, "checkpoint_particles_during_race", true, Type.BOOLEAN);
    patch.set(ConfigKey.SCOREBOARD_ENABLED, "scoreboard.enabled", true, Type.BOOLEAN);
    patch.set(ConfigKey.SCOREBOARD_DISPLAY_MILLISECONDS, "scoreboard.display_milliseconds", false, Type.BOOLEAN);
    patch.set(ConfigKey.SCOREBOARD_WORLD_RECORD, "scoreboard.display_world_record", true, Type.BOOLEAN);
    patch.set(ConfigKey.SCOREBOARD_WORLD_RECORD_HOLDER, "scoreboard.display_world_record_holder", true, Type.BOOLEAN);
    patch.set(ConfigKey.SCOREBOARD_WORLD_RECORD_FASTEST_LAP, "scoreboard.display_world_record_fastest_lap", false, Type.BOOLEAN);
    patch.set(ConfigKey.SCOREBOARD_WORLD_RECORD_FASTEST_LAP_HOLDER, "scoreboard.display_world_record_fastest_lap_holder", false, Type.BOOLEAN);
    patch.set(ConfigKey.SCOREBOARD_PERSONAL_RECORD, "scoreboard.display_personal_record", true, Type.BOOLEAN);
    patch.set(ConfigKey.SCOREBOARD_PERSONAL_RECORD_FASTEST_LAP, "scoreboard.display_record_fastest_lap", false, Type.BOOLEAN);
    patch.set(ConfigKey.SCOREBOARD_TIME, "scoreboard.display_time", true, Type.BOOLEAN);
    patch.set(ConfigKey.SCOREBOARD_LAP_TIME, "scoreboard.display_lap_time", false, Type.BOOLEAN);
    patch.set(ConfigKey.SCOREBOARD_FASTEST_LAP, "scoreboard.display_fastest_lap", false, Type.BOOLEAN);
    patch.set(ConfigKey.SCOREBOARD_TICKS_PER_UPDATE, "scoreboard.ticks_per_update", 1, Type.INTEGER);
    return patch;
  }
}
