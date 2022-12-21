package com.adamnagyan.yahoofinancewebapi.model.history;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DividendHistoryTimeFrames {
  Y1("1y", -1), Y3("3y", -3), Y5("5y", -5), Y10("10y", -10), MAX("max", -1000);

  @NotNull
  private final String name;
  private int value;

  DividendHistoryTimeFrames(String name) {
    this.name = name;
  }

  public static DividendHistoryTimeFrames getTimeFrameByName(String name) {
    for (DividendHistoryTimeFrames timeFrame : DividendHistoryTimeFrames.values()) {
      if (timeFrame.name.equals(name)) {
        return timeFrame;
      }
    }
    return null;
  }
}
