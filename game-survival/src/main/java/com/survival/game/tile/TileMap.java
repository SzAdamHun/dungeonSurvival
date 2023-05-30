package com.survival.game.tile;

import com.survival.game.math.AABB;
import com.survival.game.tile.blocks.Blokk;

import java.awt.Graphics2D;

public abstract class TileMap {

    public abstract Blokk[] getBlocks();
    public abstract void render(Graphics2D g, AABB cam);
}
