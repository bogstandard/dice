package com.friendlyfishing;

import com.google.inject.Inject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

import net.runelite.client.ui.overlay.tooltip.Tooltip;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;
import net.runelite.client.util.ColorUtil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Slf4j
public class FriendlyFishingOverlay extends WidgetItemOverlay
{

    // general
    private final Client client;
    private final FriendlyFishingPlugin plugin;
    private final FriendlyFishingConfig config;
    private final TooltipManager tooltipManager;

    @Inject
    FriendlyFishingOverlay(Client client, FriendlyFishingPlugin plugin, FriendlyFishingConfig config, TooltipManager tooltipManager)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.tooltipManager = tooltipManager;

        showOnInventory();
    }


    @Override
    public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem widgetItem)
    {

        // check it's a catchable item
        // if not then clear the slots memory
        // we check again here because the interface is faster than ticks
        if (!plugin.catchables.containsKey(itemId))
        {
            return;
        }

        FriendlyFishingPlugin.Fish fish = plugin.catches.get(widgetItem.getIndex());

        // this fish is not worth highlighting
        if (fish == null || !fish.size.show) {
            return;
        }

        final Rectangle bounds = widgetItem.getCanvasBounds();
        final int x = bounds.x - 3;
        final int y = bounds.y + 15;
        final String text = fish.size.label;

        graphics.setFont(FontManager.getRunescapeSmallFont());
        graphics.setColor(Color.BLACK);
        graphics.drawString(text, x, y);
        graphics.drawString(text, x + 2, y + 2);
        graphics.setColor(fish.size.color);
        graphics.drawString(text, x + 1, y + 1);


        // mouse is not over fish
        // done like this because detection is spotty
        if (!widgetItem.getCanvasBounds().contains(client.getMouseCanvasPosition().getX(), client.getMouseCanvasPosition().getY()))
        {
            return;
        }

        // mouse is not over fish
        // done like this because detection is spotty
        tooltipManager.add(new Tooltip(ColorUtil.wrapWithColorTag(fish.size.tip, Color.YELLOW)));
    }
}