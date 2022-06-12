package com.friendlyfishing;

import java.util.Random;

class Dice {
    private Random r;

    int x;
    int y;
    int life;
    int col;
    int row;
    final int minLife = 100;
    final int maxLife = 200;
    private final int bezel = 70; // prevent dice being too near edge at spawn
    int xDrift;
    int yDrift;
    int ticks;
    int speed;
    final int minSpeed = 5;
    final int maxSpeed = 15;
    int canvasWidth;
    int canvasHeight;

    public Dice(int canvasWidth, int canvasHeight) {
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
        this.xDrift = -1 + r.nextInt(1 - -1 + 1);
        this.yDrift = -1 + r.nextInt(1 - -1 + 1);
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
            // its settled
            row = 1;
        }
    }
}