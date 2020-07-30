package com.github.hornta.racing.commands.argumentHandlers;

import se.hornta.commando.ValidationResult;
import se.hornta.commando.completers.IArgumentHandler;
import com.github.hornta.racing.SongManager;
import com.github.hornta.racing.MessageKey;
import se.hornta.messenger.MessageManager;
import org.bukkit.command.CommandSender;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class SongArgumentHandler implements IArgumentHandler {

  @Override
  public Set<String> getItems(CommandSender sender, String argument, String[] prevArgs) {
    return SongManager.getSongNames().stream()
      .filter(name -> name.toLowerCase(Locale.ENGLISH).startsWith(argument.toLowerCase(Locale.ENGLISH)))
      .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  @Override
  public boolean test(Set<String> items, String argument) {
    return items.contains(argument.toLowerCase(Locale.ENGLISH));
  }

  @Override
  public void whenInvalid(ValidationResult result) {
    MessageManager.setValue("song_name", result.getValue());
    MessageManager.sendMessage(result.getCommandSender(), MessageKey.SONG_NOT_FOUND);
  }
}
