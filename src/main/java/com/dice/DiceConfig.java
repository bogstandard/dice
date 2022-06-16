package com.dice;

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup("Dice")
public interface DiceConfig extends Config {


  @Range(
          min = 1
  )
  @ConfigItem(keyName = "diceCount", name = "Number of Dice", description = "Too many dice may prevent them ever settling!")
  default int diceCount() {
    return 5;
  }

  @Range(
          min = 1,
          max = 100
  )
  @Units(Units.PERCENT)
  @ConfigItem(position = 1, keyName = "diceOpacity", name = "Dice opacity", description = "Do you want the dice see-through?")
  default int diceOpacity() {
    return 100;
  }

  @ConfigItem(position = 2, keyName = "flashResults", name = "Flash Results", description = "Flash Dice when they stop?")
  default boolean flashResults() {
    return false;
  }

  @ConfigItem(position = 3, keyName = "diceColor", name = "Dice Color", description = "The color of the dice.")
  default Color diceColor() {
    return new Color(255, 205, 55);
  }


  @ConfigItem(position = 4, keyName = "autoColorDice", name = "Auto Color Dice", description = "Let the system color the dice differently?")
  default boolean autoColorDice() { return false; }

  @ConfigItem(position = 5, keyName = "autoPutAway", name = "Auto tidy", description = "Put away dice after a few moments automatically?")
  default boolean autoPutAway() { return true; }

  @Units(Units.TICKS)
  @ConfigItem(position = 6, keyName = "autoPutAwayTicks", name = "Auto tidy delay", description = "How quickly to auto tidy?")
  default int autoPutAwayTicks() {
    return 120;
  }

  @ConfigItem(
          position = 7,
          keyName = "diceAdvancedNotation",
          name = "Advanced Notation (optional)",
          description = "Separate by spaces eg. D6 D6 D10 D20 D20<br><br>This overrides the above number of dice.<br><br>Leave blank if unsure!")
  default String diceAdvancedNotation() {
    return "";
  }

  @ConfigItem(position = 8, keyName = "labelDice", name = "Label Dice", description = "Show labels next to each dice?")
  default boolean labelDice() { return false; }

  @ConfigItem(position = 9, keyName = "diceDigitColor", name = "Dice Digit Color", description = "The color of the dice digits if using Advanced Notation.")
  default Color diceDigitColor() {
    return new Color(0, 0, 0);
  }

}
