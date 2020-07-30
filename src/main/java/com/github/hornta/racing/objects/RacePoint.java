package com.github.hornta.racing.objects;

import com.github.hornta.racing.enums.Permission;
import com.github.hornta.racing.RacingPlugin;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.UUID;

public class RacePoint {
  private Location location;
  private int position;
  private final UUID id;
  private Hologram hologram;

  public RacePoint(UUID id, int position, Location location) {
    this.id = id;
    this.position = position;
    this.location = location;
  }

  public Hologram getHologram() {
    return hologram;
  }

  public UUID getId() {
    return id;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public Location getLocation() {
    return location.clone();
  }

  public void setLocation(Location location) {
    this.location = location;

    if(hologram != null) {
      hologram.teleport(location.clone().add(new Vector(0, 1, 0)));
    }
  }

  public void setupHologram() {
    if(!RacingPlugin.getInstance().isHolographicDisplaysLoaded()) {
      return;
    }

    hologram = HologramsAPI.createHologram(RacingPlugin.getInstance(), location.clone().add(new Vector(0, 1, 0)));
    hologram.appendTextLine("§d" + position);
    hologram.getVisibilityManager().setVisibleByDefault(false);
    for(Player player : Bukkit.getOnlinePlayers()) {
      if(player.hasPermission(Permission.RACING_MODIFY.toString())) {
        hologram.getVisibilityManager().showTo(player);
      }
    }
  }

  public void removeHologram() {
    if(!RacingPlugin.getInstance().isHolographicDisplaysLoaded() || hologram == null) {
      return;
    }

    hologram.delete();
    hologram = null;
  }
}
