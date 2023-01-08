package com.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Dice {
  private Random r;

  DiceType diceType;
  int x;
  int y;
  int life;
  int col;
  int row;
  int finalRow; // row to flash to
  final int minLife = 30;
  final int maxLife = 80;
  private final int bezel = 100; // prevent dice being too near edge at spawn
  int xDrift;
  int yDrift;
  int ticks;
  int speed;
  final int minSpeed = 3;
  final int maxSpeed = 10;
  int canvasWidth;
  int canvasHeight;
  boolean altFrame = false;
  int magicSides;
  List<SpecialDice> specialOutcomes;

  int result = -1;
  boolean dead = false;

  public Dice(DiceType diceType, int canvasWidth, int canvasHeight, int magicSides) {
    this.diceType = diceType;
    this.r = new Random();
    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;
    this.x = canvasWidth / 2;
    this.y = canvasHeight / 2;
    this.x = bezel + r.nextInt((canvasWidth - bezel) - bezel + 1);
    this.y = bezel + r.nextInt((canvasHeight - bezel) - bezel + 1);
    this.life = minLife + r.nextInt(maxLife - minLife + 1);
    this.speed = minSpeed + r.nextInt(maxSpeed - minSpeed + 1);
    this.ticks = speed;
    this.col = 1 + r.nextInt(6 - 1 + 1);
    this.row = 15;
    this.finalRow = 2 + r.nextInt(11 - 1 + 1);
    this.xDrift = -15 + r.nextInt(1 - -15 + 1);
    this.yDrift = -15 + r.nextInt(1 - -15 + 1);
    this.magicSides = magicSides;

    // if it's a special dice, then affect the magicSides count to save code
    this.specialOutcomes = new ArrayList<>();
    if (this.diceType != DiceType.BASIC && this.diceType != DiceType.MAGIC) {
      for (SpecialDice sp: SpecialDice.values()) {
        if (sp.getDiceType() == this.diceType) {
          this.specialOutcomes.add(sp);
        }
      }

      this.magicSides = this.specialOutcomes.size();
    }
  }

  public int getX() {
    return this.x;
  }

  // drifts dice to mimic movement
  private void drift() {
    if (x < 0 || x > canvasWidth - 32) {
      xDrift = -xDrift;
    }
    x += xDrift;

    if (y < 0 || y > canvasHeight - 32) {
      yDrift = -yDrift;
    }
    y += yDrift;
  }

  private void uncollide(List<Dice> siblings) {
    for (Dice sibling : siblings) {
      if (this != sibling && this.life <= 0 && sibling.life <= 0 && overlaps(sibling)) {
        this.life = 10;
        this.speed = 5;
        this.row = 15; // revive the rolling
      }
    }
  }

  public boolean overlaps (Dice r) {
    return x < r.x + 32 && x + 32 > r.x && y < r.y + 32 && y + 32 > r.y;
  }

  // moves to next frame (col in spritesheet)
  public void next(List<Dice> siblings) {
    if (life > 0) {
      drift();
      life--;
    } else if (!dead){
      uncollide(siblings);
    }

    if (ticks > 0) { // idle on current anim frame
      ticks--;
    } else {
      altFrame = !altFrame; // flag an advance

      if (speed < maxSpeed) { // slow gradually
        speed++;
      }

      if (life > 0) {
        ticks = speed;
        col++;
        if (col > 6) {
          col = 1;
        }
      } else {
        ticks = speed * 2; // slow things down now
        if (this.magicSides > 0 && this.diceType != DiceType.BASIC) {  // its not a basic dice, use a blank base
          row = 13;
          col = 1;
          if (result == -1) {
            result = 1 + r.nextInt(magicSides - 1 + 1);
          }
        } else {
          row = 1; // its a normal dice use a dotted base..
          result = col;
        }
      }
    }
  }
}
