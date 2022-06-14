package com.dice;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ModifierlessKeybind;

import java.awt.*;
import java.awt.event.KeyEvent;

@ConfigGroup("Dice")
public interface DiceConfig extends Config {
  @ConfigItem(keyName = "diceCount", name = "Number of Dice", description = "Too many dice may prevent them ever settling!")
  default int diceCount() {
    return 5;
  }

  @ConfigItem(keyName = "diceOpacity", name = "Dice opacity (0—1)", description = "Do you want the dice see-through?")
  default double diceOpacity() {
    return 0.8;
  }

  @ConfigItem(keyName = "flashResults", name = "Flash Results", description = "Flash Dice when they stop?")
  default boolean flashResults() {
    return false;
  }

  @ConfigItem(keyName = "diceColor", name = "Dice Color", description = "The color of the dice.")
  default Color diceColor() {
    return new Color(255, 215, 0);
  }
}