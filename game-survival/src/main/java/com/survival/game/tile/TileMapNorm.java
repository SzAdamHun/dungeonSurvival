package com.survival.game.tile;

import java.awt.Graphics2D;

import com.survival.game.grafika.SpriteSheet;
import com.survival.game.math.AABB;
import com.survival.game.math.Vector2f;
import com.survival.game.tile.blocks.Blokk;
import com.survival.game.tile.blocks.NormBlokk;

public class TileMapNorm extends TileMap {

    public Blokk[] blokkok;

    private int tileSzelesseg;
    private int tileMagassag;

    private int magassag;

    public TileMapNorm(String data, SpriteSheet sprite, int width, int magassag, int tileSzelesseg, int tileMagassag, int tileColumns) {
        blokkok = new Blokk[width * magassag];

        this.tileSzelesseg = tileSzelesseg;
        this.tileMagassag = tileMagassag;

        this.magassag = magassag;

        String[] block = data.split(",");

        for(int i = 0; i < (width * magassag); i++) {
            int temp = Integer.parseInt(block[i].replaceAll("\\s+",""));
            if(temp != 0) {
                blokkok[i] = new NormBlokk(sprite.getNewSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns) ), new Vector2f((int) (i % width) * tileSzelesseg, (int) (i / magassag) * tileMagassag), tileSzelesseg, tileMagassag);
            }
        }
    }

    public Blokk[] getBlocks() { return blokkok; }

    public void render(Graphics2D g, AABB cam) {
        int x = (int) ((cam.getPos().x) / tileSzelesseg);
        int y = (int) ((cam.getPos().y) / tileMagassag);

        for(int i = x; i < x + (cam.getWidth() / tileSzelesseg); i++) {
            for(int j = y; j < y + (cam.getHeight() / tileMagassag); j++) {
                if(i + (j * magassag) > -1 && i + (j * magassag) < blokkok.length && blokkok[i + (j * magassag)] != null) {
                    blokkok[i + (j * magassag)].render(g);
                }
            }
        }
    }

}
