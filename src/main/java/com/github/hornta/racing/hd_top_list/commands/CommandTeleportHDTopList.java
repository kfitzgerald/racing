package com.github.hornta.racing.hd_top_list.commands;

import com.github.hornta.commando.ICommandHandler;
import com.github.hornta.messenger.MessageManager;
import com.github.hornta.racing.MessageKey;
import com.github.hornta.racing.enums.RaceStatType;
import com.github.hornta.racing.hd_top_list.HDTopList;
import com.github.hornta.racing.hd_top_list.HDTopListManager;
import io.papermc.lib.PaperLib;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Locale;

public class CommandTeleportHDTopList implements ICommandHandler {
  @Override
  public void handle(CommandSender commandSender, String[] args, int i) {
    HDTopList topList = HDTopListManager.getTopList(args[0]);
    Player player = (Player) commandSender;
    PaperLib.teleportAsync(player, topList.getHologram().getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
  }
}
