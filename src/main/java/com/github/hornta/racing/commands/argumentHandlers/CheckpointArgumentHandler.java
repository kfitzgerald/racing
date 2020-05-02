package com.github.hornta.racing.commands.argumentHandlers;

import com.github.hornta.commando.ValidationResult;
import com.github.hornta.commando.completers.IArgumentHandler;
import com.github.hornta.messenger.MessageManager;
import com.github.hornta.racing.RacingManager;
import com.github.hornta.racing.MessageKey;
import org.bukkit.command.CommandSender;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class CheckpointArgumentHandler implements IArgumentHandler {
  private final RacingManager racingManager;
  private final boolean shouldExist;

  public CheckpointArgumentHandler(RacingManager racingManager, boolean shouldExist) {
    this.racingManager = racingManager;
    this.shouldExist = shouldExist;
  }

  @Override
  public Set<String> getItems(CommandSender sender, String argument, String[] prevArgs) {
    return racingManager
      .getRace(prevArgs[0])
      .getCheckpoints()
      .stream()
      .filter(race -> String.valueOf(race.getPosition()).toLowerCase(Locale.ENGLISH).startsWith(argument.toLowerCase(Locale.ENGLISH)))
      .map(cp -> String.valueOf(cp.getPosition()))
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
    MessageKey key;
    if(this.shouldExist) {
      key = MessageKey.CHECKPOINT_NOT_FOUND;
    } else {
      key = MessageKey.CHECKPOINT_ALREADY_EXIST;
    }

    MessageManager.setValue("race_name", result.getPrevArgs()[0]);
    MessageManager.setValue("position", result.getValue());

    MessageManager.sendMessage(result.getCommandSender(), key);
  }
}
