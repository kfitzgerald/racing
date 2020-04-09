package com.github.hornta.race.features;

import com.github.hornta.race.enums.RaceSessionState;
import com.github.hornta.race.events.LeaveEvent;
import io.papermc.lib.PaperLib;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportOnLeave implements Listener {
  @EventHandler
  private void onLeave(LeaveEvent event) {
    if(
      event.getRaceSession().getState() == RaceSessionState.COUNTDOWN ||
      event.getRaceSession().getState() == RaceSessionState.STARTED
    ) {
      PaperLib.teleportAsync(
        event.getPlayerSession().getPlayer(),
        event.getRaceSession().getRace().getSpawn(),
        PlayerTeleportEvent.TeleportCause.PLUGIN
      );
    }
  }
}
