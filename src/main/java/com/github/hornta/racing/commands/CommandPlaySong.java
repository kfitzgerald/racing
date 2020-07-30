package com.github.hornta.racing.commands;

import se.hornta.commando.ICommandHandler;
import com.github.hornta.racing.SongManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPlaySong implements ICommandHandler {
  @Override
  public void handle(CommandSender sender, String[] args, int typedArgs) {
    SongManager.playSong(args[0], (Player)sender);
  }
}
