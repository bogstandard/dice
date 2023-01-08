package com.dice;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.util.ImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import net.runelite.client.ui.FontManager;

@Slf4j
public class DiceOverlay extends Overlay {

  // general
  private final Client client;
  private final DicePlugin plugin;
  private final DiceConfig config;
  private final ItemManager itemManager;

  // relatively unchanging
  private BufferedImage spritesheet;
  FontMetrics metrics;
  private Font font;
  private Font fontSmall;
  Dimension dims;
  Color[] colors = new Color[]{Color.WHITE, Color.ORANGE, Color.RED, Color.BLUE, Color.GREEN};

  // changing
  private final List<Dice> dices = new LinkedList<>();
  private boolean init = false;
  private boolean unannounced = true;
  private int putAwayTimer;

  @Inject
  DiceOverlay(Client client, DicePlugin plugin, DiceConfig config, ItemManager itemManager) {
    super(plugin);
    setPosition(OverlayPosition.DYNAMIC);
    setLayer(OverlayLayer.ALWAYS_ON_TOP);
    this.plugin = plugin;
    this.client = client;
    this.config = config;
    this.itemManager = itemManager;
    spritesheet = ImageUtil.loadImageResource(DicePlugin.class, "/spritesheet.png");
    font = FontManager.getRunescapeBoldFont();
    fontSmall = FontManager.getRunescapeSmallFont();
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
    putAwayTimer = config.autoPutAwayTicks();

    g.setFont(font);
    metrics = g.getFontMetrics(font);
    init = true;
    unannounced = true;
    dims = client.getRealDimensions();
    int diceCount = config.diceCount();

    if (config.fishingDice()) {
      for (int i = 0; i < diceCount; i++) {
        dices.add(new Dice(DiceType.FISHING, dims.width, dims.height, 0));
      }
    }
    else if (config.jesterDice()) {
      for (int i = 0; i < diceCount; i++) {
        dices.add(new Dice(DiceType.JESTER, dims.width, dims.height, 0));
      }
    }
    else if (config.metalDice()) {
      for (int i = 0; i < diceCount; i++) {
        dices.add(new Dice(DiceType.METALS, dims.width, dims.height, 0));
      }
    }
    else if (config.diceAdvancedNotation().length() > 0) {
      String[] notation = config.diceAdvancedNotation().split("\\s+");
      for (String notedDice : notation) {
        try {
          int magicSides = Integer.valueOf(notedDice.substring(1));
          dices.add(new Dice(DiceType.MAGIC, dims.width, dims.height, magicSides));
        } catch (Exception e) {
          // fail silent..
        }
      }
    } else { // add normal dice
      for (int i = 0; i < diceCount; i++) {
        dices.add(new Dice(DiceType.BASIC, dims.width, dims.height, 0));
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
   * Affect alpha of sprite
   */
  protected BufferedImage alpha(float a, BufferedImage sprite) {
    BufferedImage alphaSprite = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TRANSLUCENT);
    Graphics2D graphics = alphaSprite.createGraphics();
    graphics.drawImage(sprite, 0, 0, null);
    graphics.dispose();

    for (int i = 0; i < alphaSprite.getWidth(); i++) {
      for (int j = 0; j < alphaSprite.getHeight(); j++) {
        int ax = alphaSprite.getColorModel().getAlpha(alphaSprite.getRaster().getDataElements(i, j, null));
        int rx = alphaSprite.getColorModel().getRed(alphaSprite.getRaster().getDataElements(i, j, null));
        int gx = alphaSprite.getColorModel().getGreen(alphaSprite.getRaster().getDataElements(i, j, null));
        int bx = alphaSprite.getColorModel().getBlue(alphaSprite.getRaster().getDataElements(i, j, null));
        ax *= a;
        alphaSprite.setRGB(i, j, (ax << 24) | (rx << 16) | (gx << 8) | (bx));
      }
    }
    return alphaSprite;
  }

  /**
   * Draw a String centered in the middle of a Rectangle.
   */
  public void drawCenteredString(Graphics g, String text, Dice dice, int offsetx, int offsety) {
    int x = (dice.x + (32 - metrics.stringWidth(text)) / 2) + offsetx;
    int y = (dice.y + ((32 - metrics.getHeight()) / 2) + metrics.getAscent()) + offsety;
    g.drawString(text, x, y);
  }

  /**
   * Render method
   */
  @Override
  public Dimension render(Graphics2D g) {
    if (init && plugin.ROLL_DICE) {

      boolean allDiceDead = true;
      boolean allDiceFallen = true;
      int i = 0;

      for (Dice dice : dices) {

        dice.next(dices);

        BufferedImage sprite = getSprite(dice.col, dice.row, 16, 16);

        Color tint = config.diceColor();

        if(config.autoColorDice()) {
          tint = colors[i % colors.length];
        }

        float tintR = (tint.getRed() / 255.0f);
        float tintG = (tint.getGreen() / 255.0f);
        float tintB = (tint.getBlue() / 255.0f);

        if (!config.flashResults() || (config.flashResults() && dice.life > 0)
            || (config.flashResults() && dice.altFrame)) {
          sprite = tint(tintR, tintG, tintB, ((float)config.diceOpacity() / 100), sprite);
        }

        g.drawImage(sprite, dice.x, dice.y, 32, 32, null);

        // draw special sides result on dice
        if (dice.life <= 0 && dice.magicSides > 0 && dice.diceType != DiceType.BASIC && dice.diceType != DiceType.MAGIC) {
          if (dice.result > 0) { // prevent flash of -1 sprite (we subtract later)
            BufferedImage spriteImage = itemManager.getImage(dice.specialOutcomes.get(dice.result - 1).getSpecialSpriteId());
            spriteImage = alpha(((float)config.diceOpacity() / 100), spriteImage);
            g.drawImage(spriteImage, dice.x, dice.y, 32, 32, null);
          }
        }
        // draw magic sides result on dice
        else if (dice.life <= 0 && dice.magicSides > 0 && dice.diceType == DiceType.MAGIC) {
          if (dice.result > -1) { // prevent flash of -1
            g.setFont(font);
            g.setColor(Color.BLACK);
            drawCenteredString(g, "" + dice.result, dice, -1, -1);
            drawCenteredString(g, "" + dice.result, dice, -1, 1);
            drawCenteredString(g, "" + dice.result, dice, 1, 1);
            drawCenteredString(g, "" + dice.result, dice, 1, -1);
            g.setColor(config.diceDigitColor());
            drawCenteredString(g, "" + dice.result, dice, 0, 0);
          }
        }

        if (dice.life <= 0 && dice.result > -1 && config.labelDice()) {
          g.setFont(fontSmall);

          if (dice.magicSides > 0 && dice.diceType != DiceType.MAGIC && dice.diceType != DiceType.BASIC && dice.result > 0) {
            g.setColor(Color.BLACK);
            String specialDiceName = dice.specialOutcomes.get(dice.result - 1).getName();
            g.drawString(specialDiceName, dice.x + 35, dice.y + 35);
            g.drawString(specialDiceName, dice.x + 35, dice.y + 37);
            g.drawString(specialDiceName, dice.x + 37, dice.y + 37);
            g.drawString(specialDiceName, dice.x + 37, dice.y + 35);
            g.setColor(Color.WHITE);
            g.drawString(specialDiceName, dice.x + 36, dice.y + 36);
          }
          else if (dice.magicSides > 0 && dice.diceType == DiceType.MAGIC) {
            g.setColor(Color.BLACK);
            g.drawString("D"+dice.magicSides, dice.x + 35, dice.y + 35);
            g.drawString("D"+dice.magicSides, dice.x + 35, dice.y + 37);
            g.drawString("D"+dice.magicSides, dice.x + 37, dice.y + 37);
            g.drawString("D"+dice.magicSides, dice.x + 37, dice.y + 35);
            g.setColor(Color.WHITE);
            g.drawString("D"+dice.magicSides, dice.x + 36, dice.y + 36);
          } else {
            g.setColor(Color.BLACK);
            g.drawString("D6", dice.x + 35, dice.y + 35);
            g.drawString("D6", dice.x + 35, dice.y + 37);
            g.drawString("D6", dice.x + 37, dice.y + 37);
            g.drawString("D6", dice.x + 37, dice.y + 35);
            g.setColor(Color.WHITE);
            g.drawString("D6", dice.x + 36, dice.y + 36);
          }
        }

        if (dice.life > 0 || dice.result == -1) {
          allDiceDead = false;
          allDiceFallen = false;
        } else {
          if (config.autoPutAway() && putAwayTimer < 0) {
            dice.dead = true;

            if (dice.y < dims.height + 100) {
              allDiceFallen = false;
              dice.y += Math.abs(dice.yDrift) + 13; // fall down!
            }
          } else {
            allDiceFallen = false;
          }
        }

        i++;
      }

      if (allDiceDead) {
        putAwayTimer--;

        if(unannounced) {
          unannounced = false;
          plugin.sayTotal(dices);
        }
      }

      if (config.autoPutAway() && allDiceDead && allDiceFallen) {
        plugin.trigger(); // auto reset, like the player toggled manually
      }

    } else if (!init && plugin.ROLL_DICE) {
      init(g);
    } else {
      reset();
    }
    return null;
  }
}
