package com.github.hornta.racing.commands;

import se.hornta.commando.ICommandHandler;
import com.github.hornta.racing.SongManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStopSong implements ICommandHandler {
  @Override
  public void handle(CommandSender sender, String[] args, int typedArgs) {
    SongManager.stopSong((Player)sender);
  }
}
