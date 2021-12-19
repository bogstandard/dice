package com.friendlyfishing;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;

@Slf4j
public enum CardSuit {
        CLUBS, DIAMONDS, HEARTS, SPADES;

    BufferedImage loadImage(CardSuit cardSuit)
    {
            return ImageUtil.loadImageResource(getClass(), "/" + cardSuit.name().toLowerCase() + ".png");
    }

}
