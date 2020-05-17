package com.github.hornta.racing.enums;

import com.github.hornta.messenger.MessageManager;
import com.github.hornta.racing.MessageKey;

public enum RaceStatType {
  FASTEST(MessageKey.RACE_TOP_TYPE_FASTEST),
  FASTEST_LAP(MessageKey.RACE_TOP_TYPE_FASTEST_LAP),
  WINS(MessageKey.RACE_TOP_TYPE_MOST_WINS),
  RUNS(MessageKey.RACE_TOP_TYPE_MOST_RUNS),
  WIN_RATIO(MessageKey.RACE_TOP_TYPE_WIN_RATIO);

  private final MessageKey messageKey;

  RaceStatType(MessageKey messageKey) {
    this.messageKey = messageKey;
  }

  public MessageKey getMessageKey() {
    return messageKey;
  }

  public String getFormattedStat(int laps) {
    String lapWord = MessageManager.getMessage((laps > 1) ? MessageKey.LAP_PLURAL : MessageKey.LAP_SINGULAR).toLowerCase();
    MessageManager.setValue("laps", laps);
    MessageManager.setValue("lap_word", lapWord);
    return MessageManager.getMessage(messageKey);
  }
}
