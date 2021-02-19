package com.github.hornta.racing.hd_top_list.commands;

import com.github.hornta.racing.hd_top_list.HDTopListManager;
import io.papermc.lib.PaperLib;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import se.hornta.commando.ICommandHandler;

public class CommandTeleportHDTopList implements ICommandHandler {
	@Override
	public void handle(CommandSender commandSender, String[] args, int i) {
		var topList = HDTopListManager.getTopList(args[0]);
		var player = (Player) commandSender;
		PaperLib.teleportAsync(player, topList.getHologram().getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
	}
}
