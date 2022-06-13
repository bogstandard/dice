package com.friendlyfishing;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;


@Slf4j
@PluginDescriptor(
	name = "Friendly Fishing"
)
public class FriendlyFishingPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private FriendlyFishingConfig config;

	@Inject
	private FriendlyFishingOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	public boolean ROLL_DICE = true;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Friendly Fishing started!");
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		log.info("Friendly Fishing stopped!");
	}

	@Provides
	FriendlyFishingConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(FriendlyFishingConfig.class);
	}

	public void trigger() {
		ROLL_DICE = !ROLL_DICE;
	}
}
