package com.friendlyfishing;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class FriendlyFishingOverlay extends Overlay
{

    // general
    private final Client client;
    private final FriendlyFishingPlugin plugin;
    private final FriendlyFishingConfig config;

    // relatively unchanging
    private BufferedImage spritesheet;

    // changing
    private final List<Dice> dices = new LinkedList<>();
    private boolean init = false;
    private int knownDiceCount;

    @Inject
    FriendlyFishingOverlay(Client client, FriendlyFishingPlugin plugin, FriendlyFishingConfig config)
    {
        super(plugin);
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ALWAYS_ON_TOP);
        this.plugin = plugin;
        this.client = client;
        this.config = config;

       try {
           spritesheet = ImageIO.read(getClass().getResource("/spritesheet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        dices.clear();
        init = false;
    }

    public void init() {
        knownDiceCount = config.diceCount();
        init = true;
        Dimension dims = client.getRealDimensions();
        int width = dims.width;
        int height = dims.height;

        for (int i = 0; i < config.diceCount(); i++) {
            dices.add(new Dice(width, height, config.flashResults()));
        }
    }

    public BufferedImage getSprite(int col, int row, int width, int height) {
        BufferedImage img = spritesheet.getSubimage((col * 16) - 16, (row * 16) -16, width, height);
        return img;
    }

    /**
     * Render method
     */
    @Override
    public Dimension render(Graphics2D g) {

        // someones changed the config, reset and re-roll
        if(knownDiceCount != config.diceCount()) {
            reset();
            init();
        }

        if(init && plugin.ROLL_DICE) {
            for (Dice dice : dices) {
                BufferedImage sprite = getSprite(dice.col, dice.row, 16, 16);
                g.drawImage(sprite, dice.x, dice.y, 32, 32, null);
                dice.next();
            }
        } else if(!init && plugin.ROLL_DICE) {
                init();
        } else {
            reset();
        }
        return null;
    }
}