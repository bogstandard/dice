package com.friendlyfishing;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ModifierlessKeybind;

import java.awt.*;
import java.awt.event.KeyEvent;

@ConfigGroup("Friendly Fishing")
public interface FriendlyFishingConfig extends Config
{
    @ConfigItem(
            keyName = "diceCount",
            name = "Number of Dice",
            description = "How many dice?"
    )
    default int diceCount()
    {
        return 7;
    }

    @ConfigItem(
            keyName = "flashResults",
            name = "Flash Results",
            description = "Flash Dice when they stop?"
    )
    default boolean flashResults()
    {
        return false;
    }


    @ConfigItem(
            position = 12,
            keyName = "diceColor",
            name = "Dice Color",
            description = "The color of the dice."
    )
    default Color diceColor()
    {
        return new Color(255, 255, 255);
    }
}
