package com.github.hornta.racing.api;

import com.github.hornta.racing.objects.Race;
import com.github.hornta.racing.objects.RaceCheckpoint;
import com.github.hornta.racing.objects.RaceStartPoint;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public interface RacingAPI {
  void fetchAllRaces(Consumer<List<Race>> callback);
  void deleteRace(Race race, Consumer<Boolean> callback);
  void updateRace(Race race, Consumer<Boolean> callback);
  void updateCheckpoints(UUID raceId, List<RaceCheckpoint> checkpoints, Consumer<Boolean> callback);
  void updateStartPoints(UUID raceId, List<RaceStartPoint> checkpoints, Consumer<Boolean> callback);
}
