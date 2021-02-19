package com.github.hornta.racing.objects;

import com.github.hornta.racing.RacingPlugin;
import com.github.hornta.racing.enums.RaceCommandType;
import com.github.hornta.racing.events.ExecuteCommandEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import se.hornta.messenger.MessageManager;

import java.util.Comparator;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class RaceCommandExecutor implements Listener {
	@EventHandler
	void onExecuteCommand(ExecuteCommandEvent event) {
		var race = event.getRaceSession().getRace();
		var commands = race.getCommands().stream().filter((RaceCommand command) -> command.isEnabled() && command.getCommandType() == event.getCommandType()).collect(Collectors.toList());
		for (var command : commands) {
			if (command.getCommandType() == RaceCommandType.ON_RACE_FINISH || command.getCommandType() == RaceCommandType.ON_PLAYER_FINISH) {
				if (command.getRecipient() == Integer.MIN_VALUE) {
					MessageManager.setValue("race", race.getName());
					dispatchCommand(command);
				} else {
					var completedPlayerResults = event.getRaceSession().getResult().getPlayerResults().values().stream().filter(PlayerSessionResult::hasCompletedRace).sorted(Comparator.comparingLong(PlayerSessionResult::getRaceDuration)).collect(Collectors.toList());
					if (command.getRecipient() == 0) {
						var position = 1;
						for (var result : completedPlayerResults) {
							var player = Bukkit.getOfflinePlayer(result.getPlayerId());
							MessageManager.setValue("player_name", player.getName());
							MessageManager.setValue("position", position);
							position += 1;
							MessageManager.setValue("time", result.getRaceDuration());
							MessageManager.setValue("race", race.getName());
							dispatchCommand(command);
						}
					} else {
						var recipientIndex = command.getRecipient() - 1;
						if (recipientIndex < completedPlayerResults.size()) {
							var result = completedPlayerResults.get(recipientIndex);
							var player = Bukkit.getOfflinePlayer(result.getPlayerId());
							MessageManager.setValue("player_name", player.getName());
							MessageManager.setValue("position", recipientIndex + 1);
							MessageManager.setValue("time", result.getRaceDuration());
							MessageManager.setValue("race", race.getName());
							dispatchCommand(command);
						}
					}
				}
			} else {
				MessageManager.setValue("race", race.getName());
				dispatchCommand(command);
			}
		}
	}

	private void dispatchCommand(RaceCommand command) {
		var formattedCommand = MessageManager.getInstance().transformPlaceholders(command.getCommand());
		RacingPlugin.getInstance().getLogger().log(Level.INFO, "Dispatching command: " + formattedCommand);
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), formattedCommand);
	}
}
