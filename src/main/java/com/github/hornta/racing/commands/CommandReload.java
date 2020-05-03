package com.github.hornta.racing.commands;

import com.github.hornta.commando.ICommandHandler;
import com.github.hornta.messenger.Translation;
import com.github.hornta.racing.ConfigKey;
import com.github.hornta.racing.RacingPlugin;
import com.github.hornta.racing.api.ParseRaceException;
import com.github.hornta.racing.events.ConfigReloadedEvent;
import com.github.hornta.racing.MessageKey;
import com.github.hornta.messenger.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class CommandReload implements ICommandHandler {
  @Override
  public void handle(CommandSender commandSender, String[] args, int typedArgs) {
    RacingPlugin.getInstance().getConfiguration().reload();
    Translation translation = RacingPlugin.getInstance().getTranslations().createTranslation(RacingPlugin.getInstance().getConfiguration().get(ConfigKey.LANGUAGE));
    MessageManager.getInstance().setTranslation(translation);

    if(!RacingPlugin.getInstance().getRacingManager().getRaceSessions().isEmpty()) {
      MessageManager.sendMessage(commandSender, MessageKey.RELOAD_NOT_RACES);
    } else {
      try {
        RacingPlugin.getInstance().getRacingManager().load();
      } catch (ParseRaceException e) {
        MessageManager.setValue("error", e.getMessage());
        MessageManager.sendMessage(commandSender, MessageKey.RELOAD_RACES_FAILED);
        return;
      }
    }
    Bukkit.getPluginManager().callEvent(new ConfigReloadedEvent());

    MessageManager.sendMessage(commandSender, MessageKey.RELOAD_SUCCESS);
  }
}
