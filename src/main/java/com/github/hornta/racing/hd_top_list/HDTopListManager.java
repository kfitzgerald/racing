package com.github.hornta.racing.hd_top_list;

import com.github.hornta.racing.ConfigKey;
import com.github.hornta.racing.MessageKey;
import com.github.hornta.racing.RacingPlugin;
import com.github.hornta.racing.Util;
import com.github.hornta.racing.enums.RaceStatType;
import com.github.hornta.racing.events.ConfigReloadedEvent;
import com.github.hornta.racing.events.DeleteRaceEvent;
import com.github.hornta.racing.events.RaceChangeNameEvent;
import com.github.hornta.racing.events.RaceResetTopEvent;
import com.github.hornta.racing.events.RaceResultUpdatedEvent;
import com.github.hornta.racing.events.RacesLoadedEvent;
import com.github.hornta.racing.events.UnloadRaceEvent;
import com.github.hornta.racing.objects.Race;
import com.github.hornta.racing.objects.RacePlayerStatistic;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import se.hornta.messenger.MessageManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class HDTopListManager implements Listener {
	private static HDTopListManager instance;
	private final List<HDTopList> topLists;
	private final FileStorage storage;

	public HDTopListManager() {
		instance = this;
		topLists = new ArrayList<>();
		storage = new FileStorage(RacingPlugin.getInstance());
	}

	public static void createTopList(String name, Location location, Race race, RaceStatType statType, int laps, Consumer<Boolean> callback) {
		instance.createTopListInternal(name, location, race, statType, laps, callback);
	}

	public static void deleteTopList(String name, Consumer<Boolean> callback) {
		instance.deleteTopListInternal(name, callback);
	}

	public static List<HDTopList> getTopLists() {
		return instance.topLists;
	}

	public static HDTopList getTopList(String name) {
		for (var topList : instance.topLists) {
			if (topList.getName().equals(name)) {
				return topList;
			}
		}
		return null;
	}

	private static String getFormattedPlayerName(RacePlayerStatistic racePlayerStatistic) {
		var vaultChat = RacingPlugin.getInstance().getVaultChat();
		if (vaultChat != null) {
			var offlinePlayer = Bukkit.getOfflinePlayer(racePlayerStatistic.getPlayerId());
			var prefix = ChatColor.translateAlternateColorCodes('&', vaultChat.getPlayerPrefix(null, offlinePlayer));
			var suffix = ChatColor.translateAlternateColorCodes('&', vaultChat.getPlayerSuffix(null, offlinePlayer));
			return prefix + racePlayerStatistic.getPlayerName() + suffix;
		} else {
			return racePlayerStatistic.getPlayerName();
		}
	}

	private static void updateText(HDTopList topList, List<String> playerNames) {
		var hologram = topList.getHologram();
		hologram.clearLines();
		if (RacingPlugin.getInstance().getConfiguration().get(ConfigKey.HD_TOP_LIST_SHOW_HEADER)) {
			MessageManager.setValue("stat_type", topList.getStatType().getFormattedStat(topList.getLaps()));
			MessageManager.setValue("race_name", topList.getRace().getName());
			var header = MessageManager.getMessage(MessageKey.HD_TOP_LIST_HEADER);
			for (var part : header.split("\n")) {
				hologram.appendTextLine(part);
			}
		}
		var p = 1;
		var stats = topList.getRace().getResults(topList.getStatType(), topList.getLaps());
		var playerNameIndex = 0;
		for (var s : stats) {
			MessageManager.setValue("position", p++);
			MessageManager.setValue("player_name", playerNames.get(playerNameIndex));
			playerNameIndex += 1;
			Util.setTimeUnitValues();
			var value = s.getStatValue(topList.getStatType(), topList.getLaps());
			MessageManager.setValue("value", value);
			var item = MessageManager.getMessage(MessageKey.HD_TOP_LIST_ITEM);
			for (var part : item.split("\n")) {
				hologram.appendTextLine(part);
			}
			if (p > 10) {
				break;
			}
		}
		for (var i = p; i <= 10; ++i) {
			MessageManager.setValue("position", i);
			var none = MessageManager.getMessage(MessageKey.HD_TOP_LIST_NONE);
			for (var part : none.split("\n")) {
				hologram.appendTextLine(part);
			}
		}
		if (RacingPlugin.getInstance().getConfiguration().get(ConfigKey.HD_TOP_LIST_SHOW_FOOTER)) {
			var footer = MessageManager.getMessage(MessageKey.HD_TOP_LIST_FOOTER);
			for (var part : footer.split("\n")) {
				hologram.appendTextLine(part);
			}
		}
	}

	private void updateDirtyTopLists() {
		updateDirtyTopLists(null);
	}

	public void updateDirtyTopLists(Runnable runnable) {
		var dirtyTopLists = topLists.stream().filter(HDTopList::isDirty).collect(Collectors.toList());
		updateToplists(dirtyTopLists);
		for (var topList : dirtyTopLists) {
			storage.write(topList, (Boolean result) -> Bukkit.getScheduler().scheduleSyncDelayedTask(RacingPlugin.getInstance(), () -> {
				if (result) {
					topList.setDirty(false);
					if (runnable != null) {
						runnable.run();
					}
				}
			}));
		}
	}

	@EventHandler
	void onRaceResetTop(RaceResetTopEvent event) {
		updateToplists(topLists.stream().filter((topList -> topList.getRace() == event.getRace())).collect(Collectors.toList()));
	}

	@EventHandler
	void onRacesLoaded(RacesLoadedEvent event) {
		storage.load((topLists) -> {
			this.topLists.addAll(topLists);
			updateToplists(topLists);
		});
	}

	@EventHandler
	void onConfigReloaded(ConfigReloadedEvent event) {
		updateToplists(topLists);
	}

	@EventHandler
	void onRaceChangeName(RaceChangeNameEvent event) {
		updateToplists(topLists.stream().filter((hdTopList -> event.getRace() == hdTopList.getRace())).collect(Collectors.toList()));
	}

	@EventHandler
	void onDeleteRace(DeleteRaceEvent event) {
		Collection<HDTopList> toRemove = new ArrayList<>();
		for (var topList : topLists) {
			if (event.getRace() == topList.getRace()) {
				toRemove.add(topList);
			}
		}
		for (var topList : toRemove) {
			deleteTopList(topList);
		}
	}

	@EventHandler
	void onUnloadRace(UnloadRaceEvent event) {
		for (var topList : topLists) {
			if (event.getRace() == topList.getRace()) {
				topList.getHologram().delete();
			}
		}
		topLists.removeIf((HDTopList l) -> l.getHologram().isDeleted());
	}

	@EventHandler
	void onRaceResultUpdatedEvent(RaceResultUpdatedEvent event) {
		updateToplists(topLists.stream().filter((hdTopList -> event.getRace() == hdTopList.getRace())).collect(Collectors.toList()));
	}

	private void createTopListInternal(String name, Location location, Race race, RaceStatType statType, int laps, Consumer<Boolean> callback) {
		var hologram = HologramsAPI.createHologram(RacingPlugin.getInstance(), location);
		var hdTopList = new HDTopList(UUID.randomUUID(), HDTopListVersion.getLast(), name, hologram, race, statType, laps);
		storage.write(hdTopList, (Boolean result) -> {
			if (result) {
				topLists.add(hdTopList);
				updateDirtyTopLists();
			}
			callback.accept(result);
		});
	}

	private void updateToplists(Iterable<HDTopList> topLists) {
		CompletableFuture.runAsync(() -> {
			Map<HDTopList, List<String>> names = new HashMap<>();
			for (var topList : topLists) {
				var stats = topList.getRace().getResults(topList.getStatType(), topList.getLaps());
				List<String> topListUsernames = new ArrayList<>();
				for (var stat : stats) {
					topListUsernames.add(getFormattedPlayerName(stat));
				}
				names.put(topList, topListUsernames);
			}
			new BukkitRunnable() {
				@Override
				public void run() {
					for (var entry : names.entrySet()) {
						updateText(entry.getKey(), entry.getValue());
					}
				}
			}.runTask(RacingPlugin.getInstance());
		});
	}

	private void deleteTopList(HDTopList topList) {
		deleteTopList(topList, null);
	}

	private void deleteTopList(HDTopList topList, Consumer<Boolean> callback) {
		storage.delete(topList, (Boolean result) -> {
			if (result) {
				topList.getHologram().delete();
				topLists.remove(topList);
			}
			if (callback != null) {
				callback.accept(result);
			}
		});
	}

	private void deleteTopListInternal(String name, Consumer<Boolean> callback) {
		for (var topList : topLists) {
			if (topList.getName().equals(name)) {
				deleteTopList(topList, callback);
				break;
			}
		}
	}
}
