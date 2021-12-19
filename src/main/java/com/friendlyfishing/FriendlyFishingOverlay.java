package com.friendlyfishing;

import com.google.inject.Inject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

import net.runelite.client.ui.overlay.components.ImageComponent;
import net.runelite.client.ui.overlay.tooltip.Tooltip;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;
import net.runelite.client.util.ColorUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

@Slf4j
public class FriendlyFishingOverlay extends WidgetItemOverlay
{

    // general
    private final Client client;
    private final FriendlyFishingPlugin plugin;
    private final FriendlyFishingConfig config;
    private final TooltipManager tooltipManager;

    // modifiers
    // buffered images for cards, only load once
    private CardSuit cardSuit = CardSuit.CLUBS;
    private final BufferedImage clubsBufferedImage = cardSuit.loadImage(CardSuit.CLUBS);
    private final BufferedImage diamondsBufferedImage = cardSuit.loadImage(CardSuit.DIAMONDS);
    private final BufferedImage heartsBufferedImage = cardSuit.loadImage(CardSuit.HEARTS);
    private final BufferedImage spadesBufferedImage = cardSuit.loadImage(CardSuit.SPADES);


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
        // check rods first

        if (itemId == ItemID.FISHING_ROD && config.fishingRodName().length() > 0) {
            // done like this because detection is spotty
            if (!widgetItem.getCanvasBounds().contains(client.getMouseCanvasPosition().getX(), client.getMouseCanvasPosition().getY()))
            {
            } else {
                tooltipManager.add(new Tooltip(ColorUtil.wrapWithColorTag(config.fishingRodName(), Color.ORANGE)));
            }
            return;
        }

        if (itemId == ItemID.FLY_FISHING_ROD && config.flyFishingRodName().length() > 0) {
            // done like this because detection is spotty
            if (!widgetItem.getCanvasBounds().contains(client.getMouseCanvasPosition().getX(), client.getMouseCanvasPosition().getY()))
            {
            } else {
                tooltipManager.add(new Tooltip(ColorUtil.wrapWithColorTag(config.flyFishingRodName(), Color.ORANGE)));
            }
            return;
        }

        // check it's a catchable item
        // if not then clear the slots memory
        // we check again here because the interface is faster than ticks
        if (!plugin.catchables.containsKey(itemId) || !plugin.catches.containsKey(widgetItem.getIndex()))
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
        String text = fish.size.label;

        // bug workaround, prevent incorrect mode fish appearing when it's off
        if (!config.cardsMode() && text.length() == 2 || config.cardsMode() && text.length() != 2) {
            return;
        }

        if (config.cardsMode() && text.length() == 2) {
            fish.size.tip = text;
            char suit = text.charAt(1);
            text = ""+text.charAt(0);
            BufferedImage bufferedImage = spadesBufferedImage;

            switch (suit) {
                case 'c':
                    bufferedImage = clubsBufferedImage;
                    break;
                case 'd':
                    bufferedImage = diamondsBufferedImage;
                    break;
                case 'h':
                    bufferedImage = heartsBufferedImage;
                    break;
                case 's':
                    bufferedImage = spadesBufferedImage;
                    break;
            }

            ImageComponent imageComponent = new ImageComponent(bufferedImage);
            final Point point = new Point();
            point.setLocation(x, y);
            imageComponent.setPreferredLocation(point);
            imageComponent.render(graphics);

        }

        // standard behavior here
        graphics.setFont(FontManager.getRunescapeSmallFont());
        graphics.setColor(Color.BLACK);
        graphics.drawString(text, x, y);
        graphics.drawString(text, x + 2, y + 2);
        graphics.setColor(fish.size.color);
        graphics.drawString(text, x + 1, y + 1);

        // mouse is not over fish
        // done like this because detection is spotty
        if (fish.size.tip.length() < 1 || !widgetItem.getCanvasBounds().contains(client.getMouseCanvasPosition().getX(), client.getMouseCanvasPosition().getY()))
        {
            return;
        }

        // mouse is not over fish
        // done like this because detection is spotty
        tooltipManager.add(new Tooltip(ColorUtil.wrapWithColorTag(fish.size.tip, Color.YELLOW)));
    }
}