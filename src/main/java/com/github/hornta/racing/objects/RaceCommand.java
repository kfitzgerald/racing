package com.github.hornta.racing.objects;

import com.github.hornta.racing.enums.RaceCommandType;

public class RaceCommand {
  private final RaceCommandType commandType;
  private final boolean enabled;
  private final String command;
  private final int recipient;

  public RaceCommand(RaceCommandType commandType, boolean enabled, String command, int recipient) {
    this.commandType = commandType;
    this.enabled = enabled;
    this.command = command;
    this.recipient = recipient;
  }

  public RaceCommandType getCommandType() {
    return commandType;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public String getCommand() {
    return command;
  }

  public int getRecipient() {
    return recipient;
  }
}
