package com.friendlyfishing;

import net.runelite.client.config.Config;

import java.util.Random;

class Dice {
    private Random r;

    int x;
    int y;
    int life;
    int col;
    int row;
    int finalRow; // row to flash to
    final int minLife = 60;
    final int maxLife = 120;
    private final int bezel = 100; // prevent dice being too near edge at spawn
    int xDrift;
    int yDrift;
    int ticks;
    int speed;
    final int minSpeed = 3;
    final int maxSpeed = 10;
    int canvasWidth;
    int canvasHeight;
    boolean flashResults;

    public Dice(int canvasWidth, int canvasHeight, boolean flashResults) {
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
        this.xDrift = -10 + r.nextInt(1 - -10 + 1);
        this.yDrift = -10 + r.nextInt(1 - -10 + 1);
        this.flashResults = flashResults;
    }

    // drifts dice to mimic movement
    private void drift() {
        if(x < 0 || x > canvasWidth - 32) {
            xDrift = -xDrift;
        }
        x += xDrift;

        if(y < 0 || y > canvasHeight - 32) {
            yDrift = -yDrift;
        }
        y += yDrift;
    }

    // moves to next frame (col in spritesheet)
    public void next() {

        if(life > 0) {
            this.drift();
            life--;

            if (ticks > 0) { // idle on current anim frame
                ticks--;
            } else {

                if(speed < maxSpeed) { // slow gradually
                    speed++;
                }

                ticks = speed;

                col++;
                if (col > 6) {
                    col = 1;
                }
            }
        } else {
            if(flashResults) {
                if (ticks > 0) { // idle on current anim frame
                    ticks--;
                } else {
                    ticks = speed * 4;
                    // its settled, color it randomly, just not white
                    if (row == 1) {
                        row = finalRow;
                    } else {
                        row = 1;
                    }
                }
            } else {
                row = 1;
            }
        }
    }
}