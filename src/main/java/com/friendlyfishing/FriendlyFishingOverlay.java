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

    /**
     * Initialise a roll
     */
    public void init() {
        knownDiceCount = config.diceCount();
        init = true;
        Dimension dims = client.getRealDimensions();
        int width = dims.width;
        int height = dims.height;

        for (int i = 0; i < config.diceCount(); i++) {
            dices.add(new Dice(width, height));
        }
    }

    /**
     * Sprite plucking method
     */
    public BufferedImage getSprite(int col, int row, int width, int height) {
        BufferedImage img = spritesheet.getSubimage((col * 16) - 16, (row * 16) -16, width, height);
        return img;
    }

    /**
     * Tint a plucked sprite
     */
    protected BufferedImage tint(float r, float g, float b, float a,
                                 BufferedImage sprite)
    {
        BufferedImage tintedSprite = new BufferedImage(sprite.getWidth(), sprite.
                getHeight(), BufferedImage.TRANSLUCENT);
        Graphics2D graphics = tintedSprite.createGraphics();
        graphics.drawImage(sprite, 0, 0, null);
        graphics.dispose();

        for (int i = 0; i < tintedSprite.getWidth(); i++)
        {
            for (int j = 0; j < tintedSprite.getHeight(); j++)
            {
                int ax = tintedSprite.getColorModel().getAlpha(tintedSprite.getRaster().
                        getDataElements(i, j, null));
                int rx = tintedSprite.getColorModel().getRed(tintedSprite.getRaster().
                        getDataElements(i, j, null));
                int gx = tintedSprite.getColorModel().getGreen(tintedSprite.getRaster().
                        getDataElements(i, j, null));
                int bx = tintedSprite.getColorModel().getBlue(tintedSprite.getRaster().
                        getDataElements(i, j, null));
                rx *= r;
                gx *= g;
                bx *= b;
                ax *= a;
                tintedSprite.setRGB(i, j, (ax << 24) | (rx << 16) | (gx << 8) | (bx));
            }
        }
        return tintedSprite;
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

                if(!config.flashResults() || (config.flashResults() && dice.life > 0) || (config.flashResults() && dice.altFrame)) {
                    Color tint = config.diceColor();
                    float tintR = (tint.getRed() / 255.0f);
                    float tintG = (tint.getGreen() / 255.0f);
                    float tintB = (tint.getBlue() / 255.0f);
                    sprite = tint(tintR, tintG, tintB, 1, sprite);
                }

                g.drawImage(sprite, dice.x, dice.y, 32, 32, null);
                dice.next(dices);
            }
        } else if(!init && plugin.ROLL_DICE) {
                init();
        } else {
            reset();
        }
        return null;
    }
}