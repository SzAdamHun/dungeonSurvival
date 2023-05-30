package com.survival.game.tile;

import com.survival.game.grafika.SpriteSheet;
import com.survival.game.tile.blocks.NormBlokk;
import com.survival.game.util.Kamera;

import java.awt.Graphics2D;
import java.util.ArrayList;


public class TileManager {

    public static ArrayList<TileMap> tm;
    public Kamera kam;
    private SpriteSheet spritesheet;
    private int blockSzelesseg;
    private int blockMagassag;


    public TileManager() {
        tm = new ArrayList<TileMap>();
    }

    public TileManager(SpriteSheet spritesheet, int chuckSize, Kamera kam) {
        this();
        addTileMap(spritesheet, 64, 64, chuckSize, kam);

    }
    
    private void addTileMap(SpriteSheet spritesheet, int blockWidth, int blockHeight, int chuckSize, Kamera cam) {
        this.kam = cam;
        this.spritesheet = spritesheet;
        this.blockSzelesseg =  blockWidth;
        this.blockMagassag = blockHeight;

        cam.setTileMeret(blockWidth);
        String[] data = new String[3];
        TileMapGenerator tmg = new TileMapGenerator(chuckSize, blockWidth);
        

        data[0] = "";

        for(int i = 0; i < chuckSize; i++){
            for(int j = 0; j < chuckSize; j++){
                data[0] += "0,";
            }
        }

        tm.add(new TileMapObj(data[0], spritesheet, chuckSize, chuckSize, blockWidth, blockHeight, 9));

        tm.add(new TileMapNorm(tmg.base, spritesheet, chuckSize, chuckSize, blockWidth, blockHeight, 9));
        tm.add(new TileMapNorm(tmg.onTop, spritesheet, chuckSize, chuckSize, blockWidth, blockHeight, 9));

        cam.setLimit(chuckSize * blockWidth, chuckSize * blockHeight);

    }

    public NormBlokk getNormalTile(int id) {
        int normMap = 1;
        if(tm.size() < 2) normMap = 0; 
        NormBlokk block = (NormBlokk) tm.get(normMap).getBlocks()[id];
        if(block != null) {
            return block;
        }

        return null;
    }

    public void render(Graphics2D g) {
        if(kam == null)
            return;
            
        for(int i = 0; i < tm.size(); i++) {
            tm.get(i).render(g, kam.getBounds());
        }
    }
}
