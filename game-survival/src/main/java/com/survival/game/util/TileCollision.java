package com.survival.game.util;

import com.survival.game.entitas.Entitas;
import com.survival.game.tile.TileMapObj;


public class TileCollision {

    private Entitas e;
    private int tileId;

    public TileCollision(Entitas e) {
        this.e = e;
    }

    public boolean normalTile(float ax, float ay) {
        int xt;
        int yt;

        xt = (int) ( (e.getPos().x + ax) + e.getBounds().getXOffset()) / 64;
        yt = (int) ( (e.getPos().y + ay) + e.getBounds().getYOffset()) / 64;
        tileId = (xt + (yt * TileMapObj.magassag));

        return false;
    }

    public boolean collisionTile(float ax, float ay) {
        return false;
    }

    public int getTile() { return tileId; }

}
