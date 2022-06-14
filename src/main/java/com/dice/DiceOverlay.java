package com.dice;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.util.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import net.runelite.client.ui.FontManager;

@Slf4j
public class DiceOverlay extends Overlay {

  // general
  private final Client client;
  private final DicePlugin plugin;
  private final DiceConfig config;

  // relatively unchanging
  private BufferedImage spritesheet;
  FontMetrics metrics;
  private Font font;

  // changing
  private final List<Dice> dices = new LinkedList<>();
  private boolean init = false;

  @Inject
  DiceOverlay(Client client, DicePlugin plugin, DiceConfig config) {
    super(plugin);
    setPosition(OverlayPosition.DYNAMIC);
    setLayer(OverlayLayer.ALWAYS_ON_TOP);
    this.plugin = plugin;
    this.client = client;
    this.config = config;
    spritesheet = ImageUtil.loadImageResource(DicePlugin.class, "/spritesheet.png");
    font = FontManager.getRunescapeBoldFont();
  }

  /**
   * Clear current roll
   */
  public void reset() {
    dices.clear();
    init = false;
  }

  /**
   * Initialise a roll
   */
  public void init(Graphics2D g) {
    g.setFont(font);
    metrics = g.getFontMetrics(font);
    init = true;
    Dimension dims = client.getRealDimensions();
    int width = dims.width;
    int height = dims.height;
    int diceCount = config.diceCount();

    if(config.diceAdvancedNotation().length() > 0) {
      String[] notation = config.diceAdvancedNotation().split("\\s+");
      for (String notedDice : notation) {
        try {
          int magicSides = Integer.valueOf(notedDice.substring(1));
          dices.add(new Dice(width, height, magicSides));
        } catch (Exception e) {
          // fail silent..
        }
      }
    } else { // add normal dice
      for (int i = 0; i < diceCount; i++) {
        dices.add(new Dice(width, height, 0));
      }
    }
  }

  /**
   * Sprite plucking method
   */
  public BufferedImage getSprite(int col, int row, int width, int height) {
    BufferedImage img = spritesheet.getSubimage((col * 16) - 16, (row * 16) - 16, width, height);
    return img;
  }

  /**
   * Tint a plucked sprite
   */
  protected BufferedImage tint(float r, float g, float b, float a,
      BufferedImage sprite) {
    BufferedImage tintedSprite = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TRANSLUCENT);
    Graphics2D graphics = tintedSprite.createGraphics();
    graphics.drawImage(sprite, 0, 0, null);
    graphics.dispose();

    for (int i = 0; i < tintedSprite.getWidth(); i++) {
      for (int j = 0; j < tintedSprite.getHeight(); j++) {
        int ax = tintedSprite.getColorModel().getAlpha(tintedSprite.getRaster().getDataElements(i, j, null));
        int rx = tintedSprite.getColorModel().getRed(tintedSprite.getRaster().getDataElements(i, j, null));
        int gx = tintedSprite.getColorModel().getGreen(tintedSprite.getRaster().getDataElements(i, j, null));
        int bx = tintedSprite.getColorModel().getBlue(tintedSprite.getRaster().getDataElements(i, j, null));
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
   * Draw a String centered in the middle of a Rectangle.
   */
  public void drawCenteredString(Graphics g, String text, Dice dice) {
    int x = dice.x + (32 - metrics.stringWidth(text)) / 2;
    int y = dice.y + ((32 - metrics.getHeight()) / 2) + metrics.getAscent();
    g.drawString(text, x, y);
  }

  /**
   * Render method
   */
  @Override
  public Dimension render(Graphics2D g) {
    g.setFont(font);

    if (init && plugin.ROLL_DICE) {
      for (Dice dice : dices) {
        BufferedImage sprite = getSprite(dice.col, dice.row, 16, 16);

        if (!config.flashResults() || (config.flashResults() && dice.life > 0)
            || (config.flashResults() && dice.altFrame)) {
          Color tint = config.diceColor();
          float tintR = (tint.getRed() / 255.0f);
          float tintG = (tint.getGreen() / 255.0f);
          float tintB = (tint.getBlue() / 255.0f);
          sprite = tint(tintR, tintG, tintB, ((float)config.diceOpacity() / 100), sprite);
        }

        g.drawImage(sprite, dice.x, dice.y, 32, 32, null);

        if(dice.life <= 0 && dice.magicSides > 0) {
          g.setColor(config.diceDigitColor());
          g.drawString("", dice.x, dice.y);
          drawCenteredString(g, ""+dice.result, dice);
        }

        dice.next(dices);
      }
    } else if (!init && plugin.ROLL_DICE) {
      init(g);
    } else {
      reset();
    }
    return null;
  }
}
