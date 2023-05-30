package com.survival.game.entitas.ellenseg;

import com.survival.game.math.Vector2f;
import com.survival.game.util.Kamera;
import com.survival.game.entitas.Ellenseg;
import com.survival.game.grafika.SpriteSheet;

public class TinyMon extends Ellenseg {

    public TinyMon(Kamera cam, SpriteSheet sprite, Vector2f origin, int size) {
        super(cam, sprite, origin, size);
        xOffset = size / 4;
        yOffset = size / 4;

        ero = 10;
        gyorsulas = 1f;
        lassulas = 2f;
        maxSebesseg = 2f;
        r_sense = 350;
        r_attackrange = 32;

        ATTACK = 0;
        IDLE = 0;
        FALLEN = 1;
        UP = 1;
        DOWN = 1;
        LEFT = 1;
        RIGHT = 1;

        hasIdle = true;
        useRight = true;

        ani.setNumFrames(3, 0);
        ani.setNumFrames(5, 1);

       aktAnimacio = IDLE;
       right = true;
    }

}