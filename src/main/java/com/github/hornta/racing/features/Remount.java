package com.github.hornta.racing.features;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.github.hornta.racing.RacingManager;
import com.github.hornta.racing.RacingPlugin;
import com.github.hornta.racing.objects.RacePlayerSession;
import com.github.hornta.racing.objects.RaceSession;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Remount extends PacketAdapter {
  private final Map<Player, Integer> counter;

  public Remount() {
    super(
      RacingPlugin.getInstance(),
      ListenerPriority.NORMAL,
      PacketType.Play.Client.POSITION,
      PacketType.Play.Client.POSITION_LOOK,
      PacketType.Play.Client.LOOK
    );
    counter = new HashMap<>();
  }

  @Override
  public void onPacketReceiving(PacketEvent event) {
    RacingManager racingManager = RacingPlugin.getInstance().getRacingManager();
    Player player = event.getPlayer();
    RaceSession session = racingManager.getParticipatingRace(player);
    if (session == null || !session.isCurrentlyRacing(player)) {
      return;
    }

    PacketContainer packet = event.getPacket();
    PacketType type = packet.getType();
    if (
      player.isInsideVehicle() &&
      (
        type == PacketType.Play.Client.POSITION ||
        type == PacketType.Play.Client.POSITION_LOOK
      )
    ) {
      int count = counter.getOrDefault(player, 0) + 1;
      if (count >= 2) {
        RacePlayerSession playerSession = session.getPlayerSession(player);
         Bukkit.getServer().getScheduler().runTask(
          RacingPlugin.getInstance(),
          () -> {
            Entity vehicle = player.getVehicle();
            if(vehicle != null) {
              Location pLoc = player.getLocation();
              Location vLoc = vehicle.getLocation().clone();
              vLoc.setYaw(pLoc.getYaw());
              vLoc.setPitch(pLoc.getPitch());
              player.teleport(vLoc);
              playerSession.enterVehicle(vehicle);
            }
          }
        );
        counter.remove(player);
      } else {
        counter.put(player, count);
      }
    } else {
      counter.remove(player);
    }
  }
}
