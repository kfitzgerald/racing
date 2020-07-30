package com.github.hornta.racing.commands.argumentHandlers;

import se.hornta.commando.ValidationResult;
import se.hornta.commando.completers.IArgumentHandler;
import se.hornta.messenger.MessageManager;
import com.github.hornta.racing.RacingManager;
import com.github.hornta.racing.MessageKey;
import com.github.hornta.racing.objects.Race;
import org.bukkit.command.CommandSender;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class RaceArgumentHandler implements IArgumentHandler {
  private final RacingManager racingManager;
  private final boolean shouldExist;

  public RaceArgumentHandler(RacingManager racingManager, boolean shouldExist) {
    this.racingManager = racingManager;
    this.shouldExist = shouldExist;
  }

  @Override
  public Set<String> getItems(CommandSender sender, String argument, String[] prevArgs) {
    return racingManager
      .getRaces()
      .stream()
      .filter(race -> race.getName().toLowerCase(Locale.ENGLISH).startsWith(argument.toLowerCase(Locale.ENGLISH)))
      .map(Race::getName)
      .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  @Override
  public boolean test(Set<String> items, String argument) {
    if(shouldExist) {
      return items.contains(argument);
    } else {
      return !items.contains(argument);
    }
  }

  @Override
  public void whenInvalid(ValidationResult result) {
    MessageKey message;
    if(shouldExist) {
      message = MessageKey.RACE_NOT_FOUND;
    } else {
      message = MessageKey.RACE_ALREADY_EXIST;
    }
    MessageManager.setValue("race_name", result.getValue());
    MessageManager.sendMessage(result.getCommandSender(), message);
  }
}
