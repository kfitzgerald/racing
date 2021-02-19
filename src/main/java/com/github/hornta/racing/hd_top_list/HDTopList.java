package com.github.hornta.racing.hd_top_list;

import com.github.hornta.racing.enums.RaceStatType;
import com.github.hornta.racing.objects.Race;
import com.gmail.filoghost.holographicdisplays.api.Hologram;

import java.util.UUID;

public class HDTopList {
	private final UUID id;
	private final HDTopListVersion version;
	private final String name;
	private final Hologram hologram;
	private int laps;
	private RaceStatType statType;
	private Race race;
	private boolean isDirty;

	HDTopList(UUID id, HDTopListVersion version, String name, Hologram hologram, Race race, RaceStatType statType, int laps) {
		this.id = id;
		this.version = version;
		this.name = name;
		this.hologram = hologram;
		this.race = race;
		this.statType = statType;
		this.laps = laps;
		isDirty = true;
	}

	boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean dirty) {
		isDirty = dirty;
	}

	public UUID getId() {
		return id;
	}

	public HDTopListVersion getVersion() {
		return version;
	}

	public String getName() {
		return name;
	}

	public Hologram getHologram() {
		return hologram;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
		isDirty = true;
	}

	public RaceStatType getStatType() {
		return statType;
	}

	public void setStatType(RaceStatType statType) {
		this.statType = statType;
		isDirty = true;
	}

	public int getLaps() {
		return laps;
	}

	public void setLaps(int laps) {
		this.laps = laps;
	}
}
