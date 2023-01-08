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


  // APPEARANCE
  @ConfigSection(name = "Appearance", description = "How the dice will appear on screen", position = 1, closedByDefault = false)
  String AppearanceSettings = "AppearanceSettings";

  @Range(
          min = 1,
          max = 100
  )
  @Units(Units.PERCENT)
  @ConfigItem(section = AppearanceSettings, position = 1, keyName = "diceOpacity", name = "Dice opacity", description = "Do you want the dice see-through?")
  default int diceOpacity() {
    return 100;
  }

  @ConfigItem(section = AppearanceSettings, position = 2, keyName = "flashResults", name = "Flash Results", description = "Flash Dice when they stop?")
  default boolean flashResults() {
    return false;
  }

  @ConfigItem(section = AppearanceSettings, position = 3, keyName = "diceColor", name = "Dice Color", description = "The color of the dice.")
  default Color diceColor() {
    return new Color(255, 205, 55);
  }


  @ConfigItem(section = AppearanceSettings, position = 4, keyName = "autoColorDice", name = "Auto Color Dice", description = "Let the system color the dice differently?")
  default boolean autoColorDice() { return false; }

  @ConfigItem(section = AppearanceSettings, position = 5, keyName = "autoPutAway", name = "Auto tidy", description = "Put away dice after a few moments automatically?")
  default boolean autoPutAway() { return true; }

  @Units(Units.TICKS)
  @ConfigItem(section = AppearanceSettings, position = 6, keyName = "autoPutAwayTicks", name = "Auto tidy delay", description = "How quickly to auto tidy?")
  default int autoPutAwayTicks() {
    return 120;
  }

  @ConfigItem(section = AppearanceSettings, position = 7, keyName = "labelDice", name = "Label Dice", description = "Show labels next to each dice?")
  default boolean labelDice() { return false; }

  @ConfigItem(section = AppearanceSettings, position = 8, keyName = "diceDigitColor", name = "Dice Digit Color", description = "The color of the dice digits if using Advanced Notation.")
  default Color diceDigitColor() {
    return new Color(255, 255, 255);
  }


  // SPECIAL DICE SETTINGS
  @ConfigSection(name = "Special Dice", description = "Special dice you can use, these are divided evenly as possible.", position = 2, closedByDefault = false)
  String SpecialDiceSettings = "SpecialDiceSettings";

  @ConfigItem(section = SpecialDiceSettings, position = 9, keyName = "basicDice", name = "Basic Dice (D6)", description = "Use basic dice?")
  default boolean basicDice() { return false; }

  @ConfigItem(section = SpecialDiceSettings, position = 10, keyName = "fishingDice", name = "Fishing Dice (D19)", description = "Use fishing dice?")
  default boolean fishingDice() { return false; }

  @ConfigItem(section = SpecialDiceSettings, position = 11, keyName = "jesterDice", name = "Jester Dice (D6)", description = "Use jester dice?")
  default boolean jesterDice() { return false; }

  @ConfigItem(section = SpecialDiceSettings, position = 12, keyName = "metalDice", name = "Metal Dice (D9)", description = "Use metal dice?")
  default boolean metalDice() { return false; }

  @ConfigItem(section = SpecialDiceSettings, position = 13, keyName = "skillDice", name = "Skill Dice (D23)", description = "Use skill dice?")
  default boolean skillDice() { return false; }

  @ConfigItem(section = SpecialDiceSettings, position = 14, keyName = "combatSkillDice", name = "Combat Skill Dice (D4)", description = "Use combat skill dice?")
  default boolean combatSkillDice() { return false; }

  @ConfigItem(
          section = SpecialDiceSettings,
          position = 99,
          keyName = "diceAdvancedNotation",
          name = "Advanced Notation (optional)",
          description = "Separate by spaces eg. D6 D6 D10 D20 D20<br><br>This overrides the above number of dice.<br><br>Leave blank if unsure!")
  default String diceAdvancedNotation() {
    return "";
  }

}
