package com.survival.game.tile.blocks;

import java.awt.Graphics2D;

import com.survival.game.math.AABB;
import com.survival.game.math.Vector2f;
import com.survival.game.grafika.Sprite;

public class NormBlokk extends Blokk {
    public NormBlokk(Sprite img, Vector2f pos, int w, int h) {
        super(img, pos, w, h);

        img.setEffect(Sprite.effect.DECAY);
    }

    public boolean update(AABB p) {
        return false;
    }

    public Sprite getImage() {
        return img;
    }

    public void render(Graphics2D g){
        super.render(g);
    }

    public String toString() {
        return "position: " + pos;
    }
}
