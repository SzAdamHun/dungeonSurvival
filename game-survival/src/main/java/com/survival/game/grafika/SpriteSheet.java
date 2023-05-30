package com.survival.game.grafika;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.survival.game.math.Vector2f;

public class SpriteSheet {

    private Sprite SPRITESHEET = null;
    private Sprite[][] spriteArray;
    private final int TILE_SIZE = 32;
    public int w;
    public int h;
    private int wSprite;
    private int hSprite;

    public static Font currentFont;

    public SpriteSheet(String file) {
        w = TILE_SIZE;
        h = TILE_SIZE;

        System.out.println("Loading: " + file + "...");
        SPRITESHEET = new Sprite(loadSprite(file));

        wSprite = SPRITESHEET.image.getWidth() / w;
        hSprite = SPRITESHEET.image.getHeight() / h;
        loadSpriteArray();
    }

    public SpriteSheet(Sprite sprite, String name, int w, int h) {
        this.w = w;
        this.h = h;

        System.out.println("Loading: " + name + "...");
        SPRITESHEET = sprite;

        wSprite = SPRITESHEET.image.getWidth() / w;
        hSprite = SPRITESHEET.image.getHeight() / h;
        loadSpriteArray();
        
    }

    public SpriteSheet(String file, int w, int h) {
        this.w = w;
        this.h = h;

        System.out.println("Loading: " + file + "...");
        SPRITESHEET = new Sprite(loadSprite(file));

        wSprite = SPRITESHEET.image.getWidth() / w;
        hSprite = SPRITESHEET.image.getHeight() / h;
        loadSpriteArray();
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    private BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch (Exception e) {
            System.out.println("ERROR: could not load file: " + file);
        }
        return sprite;
    }

    public void loadSpriteArray() {
        spriteArray = new Sprite[hSprite][wSprite];

        for (int y = 0; y < hSprite; y++) {
            for (int x = 0; x < wSprite; x++) {
                spriteArray[y][x] = getSprite(x, y);
            }
        }
    }

    public Sprite getSprite(int x, int y) {
        return SPRITESHEET.getSubimage(x * w, y * h, w, h);
    }

    public Sprite getNewSprite(int x, int y) {
        return SPRITESHEET.getNewSubimage(x * w, y * h, w, h);
    }

    public Sprite getSprite(int x, int y, int w, int h) {
        return SPRITESHEET.getSubimage(x * w, y * h, w, h);
    }

    public BufferedImage getSubimage(int x, int y, int w, int h) {
        return SPRITESHEET.image.getSubimage(x, y, w, h);
    }

    public Sprite[] getSpriteArray(int i) {
        return spriteArray[i];
    }

    public Sprite[][] getSpriteArray2() {
        return spriteArray;
    }

    public static void drawArray(Graphics2D g, String word, Vector2f pos, int size, int xOffset) {
        drawArray(g, currentFont, word, pos, size, size, xOffset, 0);
    }

    public static void drawArray(Graphics2D g, String word, Vector2f pos, int width, int height, int xOffset) {
        drawArray(g, currentFont, word, pos, width, height, xOffset, 0);
    }

    public static void drawArray(Graphics2D g, Font f, String word, Vector2f pos, int width, int height, int xOffset, int yOffset) {
        float x = pos.x;
        float y = pos.y;

        currentFont = f;

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != 32)
                g.drawImage(f.getLetter(word.charAt(i)), (int) x, (int) y, width, height, null);

            x += xOffset;
            y += yOffset;
        }

    }

}
