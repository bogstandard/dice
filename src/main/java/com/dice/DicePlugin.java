package com.dice;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.util.Comparator;
import java.util.List;

@Slf4j
@PluginDescriptor(name = "Dice")
public class DicePlugin extends Plugin {
  @Inject
  private Client client;

  @Inject
  private ClientThread clientThread;

  @Inject
  private DiceConfig config;

  @Inject
  private DiceOverlay overlay;

  @Inject
  private OverlayManager overlayManager;

  public boolean ROLL_DICE = false;
  private Widget button = null;
  private Widget parent = null;

  @Override
  protected void startUp() throws Exception {
    log.info("Dice started!");
    overlayManager.add(overlay);
    clientThread.invokeLater(this::createButton);
  }

  @Override
  protected void shutDown() throws Exception {
    overlayManager.remove(overlay);
    clientThread.invoke(this::hideButton);
    log.info("Dice stopped!");
  }

  @Provides
  DiceConfig provideConfig(ConfigManager configManager) {
    return configManager.getConfig(DiceConfig.class);
  }

  @Subscribe
  public void onWidgetLoaded(WidgetLoaded event) {
    if (event.getGroupId() != WidgetID.EQUIPMENT_GROUP_ID) {
      return;
    }

    createButton();
  }

  protected void trigger() {
    ROLL_DICE = !ROLL_DICE;
    if (ROLL_DICE) {
      button.setSpriteId(SpriteID.OPTIONS_ROUND_CHECK_BOX_CROSSED);
      button.setAction(0, "Put away dice");
    } else {
      button.setSpriteId(573);
      button.setAction(0, "Roll dice");
    }
  }

  protected void sayTotal(List<Dice> dices) {
    int total = 0;
    String message = "";
    int i = 0;

    boolean containsNonNumeric = false;

    dices.sort(Comparator.comparing(Dice::getX));

    for (Dice dice : dices) {
      total += dice.result;

      if (dice.diceType == DiceType.BASIC || dice.diceType == DiceType.MAGIC) {
        message = message + dice.result;
      } else {
        containsNonNumeric = true;
        message = message + dice.specialOutcomes.get(dice.result - 1).getName();
      }

      if(i < dices.size() - 1) {
        message = message + " + ";
      }

      i++;
    }

    if (containsNonNumeric) {
      total = 0;
    }

    client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "You rolled " + (total > 0 ? total : "") + " (" + message + ")", null);
  }

  private void hideButton() {
    if (button == null) {
      return;
    }

    button.setHidden(true);
    try {
      parent.getChildren()[button.getIndex()] = null;
    } catch (NullPointerException e) {
      // .. this shouldn't happen, but catch just incase.
    }
    button = null;
  }

  private void createButton() {
    parent = client.getWidget(WidgetInfo.EQUIPMENT);
    if (parent == null) {
      return;
    }

    hideButton();

    button = parent.createChild(-1, WidgetType.GRAPHIC);
    button.setOriginalHeight(20);
    button.setOriginalWidth(20);
    button.setOriginalX(124);
    button.setOriginalY(14);
    button.setSpriteId(573);
    button.setAction(0, "Roll dice");
    button.setOnOpListener((JavaScriptCallback) (e) -> clientThread.invokeLater(this::trigger));
    button.setHasListener(true);
    button.revalidate();
  }
}
