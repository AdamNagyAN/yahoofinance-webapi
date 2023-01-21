package com.adamnagyan.yahoofinancewebapi.model.history;

import com.adamnagyan.yahoofinancewebapi.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StockTimeFrames {
  Y1("1y", -1), Y3("3y", -3), Y5("5y", -5), Y10("10y", -10), MAX("max", -1000);

  private final String name;
  private int value;

  StockTimeFrames(String name) {
    this.name = name;
  }

  public static StockTimeFrames getTimeFrameByName(String name) throws BadRequestException {
    for (StockTimeFrames timeFrame : StockTimeFrames.values()) {
      if (timeFrame.name.equals(name)) {
        return timeFrame;
      }
    }
    throw new BadRequestException("timeFrame", "Timeframe is not valid!");
  }
}
