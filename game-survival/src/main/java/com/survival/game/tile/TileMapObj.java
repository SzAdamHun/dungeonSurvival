package com.survival.game.tile;

import java.awt.Graphics2D;

import com.survival.game.grafika.SpriteSheet;
import com.survival.game.math.AABB;
import com.survival.game.tile.blocks.Blokk;

public class TileMapObj extends TileMap {

    public static Blokk[] event_blokkok;

    private int tileSzelesseg;
    private int tileMagassag;

    public static int szelesseg;
    public static int magassag;

    public TileMapObj(String data, SpriteSheet sprite, int szelesseg, int magassag, int tileSzelesseg, int tileMagassag, int tileColumns) {
        event_blokkok = new Blokk[szelesseg * magassag];

        this.tileSzelesseg = tileSzelesseg;
        this.tileMagassag = tileMagassag;

        TileMapObj.szelesseg = szelesseg;
        TileMapObj.magassag = magassag;

    }

    public Blokk[] getBlocks() { return event_blokkok; }

    public void render(Graphics2D g, AABB cam) {
        int x = (int) ((cam.getPos().x) / tileSzelesseg);
        int y = (int) ((cam.getPos().y) / tileMagassag);

        for(int i = x; i < x + (cam.getWidth() / tileSzelesseg); i++) {
            for(int j = y; j < y + (cam.getHeight() / tileMagassag); j++) {
                if(i + (j * magassag) > -1 && i + (j * magassag) < event_blokkok.length && event_blokkok[i + (j * magassag)] != null)
                    event_blokkok[i + (j * magassag)].render(g);
            }
        }
    }
}
