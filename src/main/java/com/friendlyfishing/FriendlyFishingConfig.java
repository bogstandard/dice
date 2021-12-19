package com.friendlyfishing;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("Friendly Fishing")
public interface FriendlyFishingConfig extends Config
{
	// FISHING ROD
	@ConfigItem(
			keyName = "fishingRodName",
			name = "Fishing Rod Name",
			description = "Name your fishing rod."
	)
	default String fishingRodName()
	{
		return "Lucky Charmer";
	}

	// FLY FISHING ROD
	@ConfigItem(
		keyName = "flyFishingRodName",
		name = "Fly Fishing Rod Name",
		description = "Name your fly fishing rod."
	)
	default String flyFishingRodName()
	{
		return "Old Lucky";
	}

	// CARDS MODE
	@ConfigItem(
			position = 11,
			keyName = "cardsMode",
			name = "Cards Mode",
			description = "Fish become random playing cards."
	)
	default boolean cardsMode()
	{
		return false;
	}
}
