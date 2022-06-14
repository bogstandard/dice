package com.dice;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class DicePluginTest {
  public static void main(String[] args) throws Exception {
    ExternalPluginManager.loadBuiltin(DicePlugin.class);
    RuneLite.main(args);
  }
}
