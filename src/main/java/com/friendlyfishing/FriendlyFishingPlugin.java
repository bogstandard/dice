package com.friendlyfishing;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

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

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
		log.info("Friendly Fishing started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		log.info("Friendly Fishing stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Provides
	FriendlyFishingConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(FriendlyFishingConfig.class);
	}


	// catchables
	protected final HashMap<Integer, Catchable> catchables = new HashMap<Integer, Catchable>()
	{{
		put(ItemID.RAW_TROUT, new Catchable());
		put(ItemID.RAW_SALMON, new Catchable());
	}};

	public class Catchable
	{
		// TODO do something with this
	}


	// sizes
	// <---- bias towards left
	private final List<Size> sizes = Arrays.asList(
			new Size(true, "", "Nothing Special", Color.YELLOW),
			new Size(true, "", "Nothing Special", Color.YELLOW),
			new Size(true, "", "Nothing Special", Color.YELLOW),
			new Size(true, "", "Nothing Special", Color.YELLOW),
			new Size(true, "Tiny", "Disappointing", Color.YELLOW),
			new Size(true, "", "Nothing Special", Color.YELLOW),
			new Size(true, "Huge", "What a catch!", Color.YELLOW),
			new Size(true, "", "Nothing Special", Color.YELLOW),
			new Size(true, "Whopper", "Ridiculous!", Color.YELLOW),
			new Size(true, "", "Nothing Special", Color.YELLOW),
			new Size(true, "Rotten", "This stinks!", Color.RED),
			new Size(true, "GIANT", "Absolute unit.", Color.CYAN),
			new Size(true, "GIANT", "Absolute unit.", Color.CYAN)
	);

	public class Size
	{
		public boolean show;
		public String label;
		public String tip;
		public Color color;

		public Size(Boolean show, String label, String tip, Color color) {
			this.show = show;
			this.label = label;
			this.tip = tip;
			this.color = color;
		}
	}

	// fish management
	protected final HashMap<Integer, Fish> catches = new HashMap<Integer, Fish>();

	public class Fish
	{
		protected int itemId;
		protected int index;
		protected Size size;


		public Fish(int itemId, int index) {
			this.itemId = itemId;
			this.index = index;
			int ri = (int) ( Math.abs(Math.random() - Math.random()) * (1 + (sizes.size()-1)) ); // biased random towards 0
			this.size = sizes.get(ri);

		}
	}

	// other
	protected boolean cardsMode = true;
	private String cardFaces = "A23456789JKQ";
	private String cardSuits = "cdhs";


	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event) {

		Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY);

		Collection<WidgetItem> items = inventoryWidget.getWidgetItems();

		for (WidgetItem item: items) {

			int itemId = item.getId();

			if (itemId < 0 || !catchables.containsKey(itemId))
			{
				catches.remove(item.getIndex());
				continue;
			}

			Fish fish = new Fish(itemId, item.getIndex());   // make a new fish

			if (catches.containsKey(fish.index)) {     // check we're not replacing an existing one
				Fish existingFish = catches.get(fish.index);
				if (fish.itemId == existingFish.itemId) { // it's the same, eg. Trout == Trout
					continue; // skip the rest
				}
			}

			// cards modifier
			if (cardsMode) {
				Random r = new Random();
				char face = cardFaces.charAt(r.nextInt(cardFaces.length()));
				char suit = cardSuits.charAt(r.nextInt(cardSuits.length()));
				fish.size.label = ""+face+suit;
				if (suit == 's' || suit == 'c') {
					fish.size.color = Color.LIGHT_GRAY;
				} else {
					fish.size.color = Color.RED;
				}
			}

			// put in the new one
			catches.put(fish.index, fish);


		}

		}
	}
