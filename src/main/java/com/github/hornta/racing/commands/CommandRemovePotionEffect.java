package com.github.hornta.racing.commands;

import se.hornta.commando.ICommandHandler;
import com.github.hornta.racing.RacingManager;
import com.github.hornta.racing.enums.RaceState;
import com.github.hornta.racing.MessageKey;
import se.hornta.messenger.MessageManager;
import com.github.hornta.racing.objects.Race;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffectType;

public class CommandRemovePotionEffect extends RacingCommand implements ICommandHandler {
  public CommandRemovePotionEffect(RacingManager racingManager) {
    super(racingManager);
  }

  @Override
  public void handle(CommandSender commandSender, String[] args, int typedArgs) {
    Race race = racingManager.getRace(args[0]);
    PotionEffectType type = PotionEffectType.getByName(args[1]);

    if(race.getState() != RaceState.UNDER_CONSTRUCTION) {
      MessageManager.setValue("race_name", race.getName());
      MessageManager.sendMessage(commandSender, MessageKey.EDIT_NO_EDIT_MODE);
      return;
    }

    race.removePotionEffect(type);

    racingManager.updateRace(race, () -> {
      MessageManager.setValue("potion_effect", type.getName());
      MessageManager.setValue("race_name", race.getName());
      MessageManager.sendMessage(commandSender, MessageKey.RACE_REMOVE_POTION_EFFECT);
    });
  }
}

