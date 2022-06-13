package com.friendlyfishing;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ModifierlessKeybind;

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
}
