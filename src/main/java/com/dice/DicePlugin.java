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

@Slf4j
@PluginDescriptor(name = "Friendly Fishing")
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

  @Override
  protected void startUp() throws Exception {
    log.info("Friendly Fishing started!");
    overlayManager.add(overlay);
    clientThread.invokeLater(this::createButton);
  }

  @Override
  protected void shutDown() throws Exception {
    overlayManager.remove(overlay);
    clientThread.invoke(this::hideButton);
    log.info("Friendly Fishing stopped!");
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

  private void hideButton() {
    if (button == null) {
      return;
    }

    button.setHidden(true);
    button = null;
  }

  private void createButton() {
    Widget parent = client.getWidget(WidgetInfo.EQUIPMENT);
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
