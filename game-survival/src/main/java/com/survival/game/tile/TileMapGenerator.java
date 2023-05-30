package com.survival.game.tile;

import com.survival.game.noise.SimplexNoise;

public class TileMapGenerator {

    public String base;
    private int[] data;
    
    public String onTop;
    private int[] layer;

    private int chuckMeret;

    private int[] grass = {52, 53, 54};
    private int[] dirt = {46, 47, 48};

    private Tile[] tiles = { new Tile(0.6f, 35, grass), new Tile(1f, 29, dirt) };

    public TileMapGenerator(int chuckMeret, int tileSize) {
        this.chuckMeret = chuckMeret;
        this.layer = new int[chuckMeret * chuckMeret];
        this.base = "";
        this.onTop = "";

        int xStart = 0;
        int xEnd = 500;
        int yStart = 0;
        int yEnd = 500;

        this.data = simplexTiles(xStart, xEnd, yStart, yEnd);

        for(int i = 0; i < chuckMeret * chuckMeret; i++) {
            onTop += layer[i] + ",";
            base += data[i] + ",";
        }
    }


    private int[] simplexTiles(int xStart, int xEnd, int yStart, int yEnd) {
        int[] data = new int[chuckMeret * chuckMeret];
        SimplexNoise simplexNoise = new SimplexNoise(300, 0.4, 5000);
        double[][] result = new double[chuckMeret][chuckMeret];

        for(int i = 0; i < chuckMeret; i++){
            for(int j = 0; j < chuckMeret; j++){
                int x = (int) (xStart + i * ((xEnd - xStart) / chuckMeret));
                int y = (int) (yStart + j * ((yEnd - yStart) / chuckMeret));
                result[i][j] = 0.5 * (1 + simplexNoise.getNoise(x, y));

                for(int k = 0; k < tiles.length; k++) {
                    if(result[i][j] < tiles[k].rarity) {
                        data[j + i * chuckMeret] = tiles[k].generate();
                        break;
                    }
                }
            }
        }

        return data;
    }
}

class Tile {
    public float rarity;
    public int spriteIndex;
    public int[] vary;

    public Tile(float rarity, int spriteIndex, int[] vary) {
        this.rarity = rarity;
        this.spriteIndex = spriteIndex;
        this.vary = vary;

    }

    public int generate() {
        double random = Math.random();
        if(vary != null && random > 0.9) {
            return vary[((int) (random * 100)) % (vary.length )];
        }

        return spriteIndex;
    }
}