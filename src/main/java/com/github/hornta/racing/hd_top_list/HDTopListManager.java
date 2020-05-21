package com.github.hornta.racing.hd_top_list;

import com.github.hornta.racing.RacingPlugin;
import com.github.hornta.racing.enums.RaceStatType;
import com.github.hornta.racing.events.ConfigReloadedEvent;
import com.github.hornta.racing.events.DeleteRaceEvent;
import com.github.hornta.racing.events.RaceChangeNameEvent;
import com.github.hornta.racing.events.RaceResultUpdatedEvent;
import com.github.hornta.racing.events.RacesLoadedEvent;
import com.github.hornta.racing.objects.Race;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class HDTopListManager implements Listener {
  private static HDTopListManager instance;
  private final List<HDTopList> topLists;
  private final FileStorage storage;

  public HDTopListManager() {
    instance = this;
    topLists = new ArrayList<>();
    storage = new FileStorage(RacingPlugin.getInstance());
  }

  @EventHandler
  void onRacesLoaded(RacesLoadedEvent event) {
    storage.load(topLists::addAll);
  }

  @EventHandler
  void onConfigReloaded(ConfigReloadedEvent event) {
    for(HDTopList topList : topLists) {
      topList.update();
    }
  }

  @EventHandler
  void onRaceChangeName(RaceChangeNameEvent event) {
    for(HDTopList topList : topLists) {
      if(event.getRace() == topList.getRace()) {
        topList.update();
      }
    }
  }

  @EventHandler
  void onDeleteRace(DeleteRaceEvent event) {
    Collection<HDTopList> toRemove = new ArrayList<>();
    for (HDTopList topList : topLists) {
      if (event.getRace() == topList.getRace()) {
        toRemove.add(topList);
      }
    }

    for(HDTopList topList : toRemove) {
      deleteTopList(topList);
    }
  }

  @EventHandler
  void onRaceResultUpdatedEvent(RaceResultUpdatedEvent event) {
    for(HDTopList topList : topLists) {
      if(event.getRace() == topList.getRace()) {
        topList.update();
      }
    }
  }

  private void createTopListInternal(String name, Location location, Race race, RaceStatType statType, int laps, Consumer<Boolean> callback) {
    Hologram hologram = HologramsAPI.createHologram(RacingPlugin.getInstance(), location);
    HDTopList hdTopList = new HDTopList(UUID.randomUUID(), HDTopListVersion.getLast(), name, hologram, race, statType, laps);
    storage.write(hdTopList, (Boolean result) -> {
      if(result) {
        topLists.add(hdTopList);
      }
      callback.accept(result);
    });
  }

  private void deleteTopList(HDTopList topList) {
    deleteTopList(topList, null);
  }

  private void deleteTopList(HDTopList topList, Consumer<Boolean> callback) {
    storage.delete(topList, (Boolean result) -> {
      if(result) {
        topList.getHologram().delete();
        topLists.remove(topList);
      }
      if(callback != null) {
        callback.accept(result);
      }
    });
  }

  private void deleteTopListInternal(String name, Consumer<Boolean> callback) {
    for (HDTopList topList : topLists) {
      if (topList.getName().equals(name)) {
        deleteTopList(topList, callback);
        break;
      }
    }
  }

  public static void createTopList(String name, Location location, Race race, RaceStatType statType, int laps, Consumer<Boolean> callback) {
    instance.createTopListInternal(name, location, race, statType, laps, callback);
  }

  public static void deleteTopList(String name, Consumer<Boolean> callback) {
    instance.deleteTopListInternal(name, callback);
  }

  public static void updateTopList(HDTopList topList, Runnable runnable) {
    instance.storage.write(topList, (Boolean result) -> Bukkit.getScheduler().scheduleSyncDelayedTask(RacingPlugin.getInstance(), () -> {
      if (result) {
        runnable.run();
      }
    }));
  }

  public static List<HDTopList> getTopLists() {
    return instance.topLists;
  }

  public static HDTopList getTopList(String name) {
    for(HDTopList topList : instance.topLists) {
      if(topList.getName().equals(name)) {
        return topList;
      }
    }
    return null;
  }
}
