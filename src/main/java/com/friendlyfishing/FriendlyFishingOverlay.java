package com.friendlyfishing;

import com.google.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;
import net.runelite.client.util.ColorUtil;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Map;

public class FriendlyFishingOverlay extends WidgetItemOverlay
{

    // general
    private final Client client;
    private final FriendlyFishingPlugin plugin;
    private final FriendlyFishingConfig config;

    @Inject
    FriendlyFishingOverlay(Client client, FriendlyFishingPlugin plugin, FriendlyFishingConfig config)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;

        showOnInventory();
        //showOnBank();
    }

    @Override
    public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem widgetItem)
    {
        if (itemId != ItemID.RAW_TROUT)
        {
            return;
        }

        final Rectangle bounds = widgetItem.getCanvasBounds();
        final int drawX = bounds.x;
        final int drawY = bounds.y + bounds.height;
        final String text = "here fishy fishy";
        graphics.setFont(FontManager.getRunescapeSmallFont());
        graphics.setColor(Color.BLACK);
        graphics.drawString(text, drawX + 1, drawY + 1);
        graphics.setColor(Color.WHITE);
        graphics.drawString(text, drawX, drawY);

    }
}