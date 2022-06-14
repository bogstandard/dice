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
    return 80;
  }

  @ConfigItem(position = 2, keyName = "flashResults", name = "Flash Results", description = "Flash Dice when they stop?")
  default boolean flashResults() {
    return false;
  }

  @ConfigItem(position = 3, keyName = "diceColor", name = "Dice Color", description = "The color of the dice.")
  default Color diceColor() {
    return new Color(255, 215, 0);
  }

  @ConfigItem(
          position = 4,
          keyName = "diceAdvancedNotation",
          name = "Advanced Notation (optional)",
          description = "Separate desired dice by spaces eg. D6 D6 D10 D20 D20<br>NOTE: This overrides the above number of dice.<br>Leave blank if unsure!")
  default String diceAdvancedNotation() {
    return "";
  }

  @ConfigItem(position = 5, keyName = "diceDigitColor", name = "Dice Digit Color", description = "The color of the dice digits if using Advanced Notation.")
  default Color diceDigitColor() {
    return new Color(0, 0, 0);
  }

}
