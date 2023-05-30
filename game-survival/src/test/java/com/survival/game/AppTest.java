package com.survival.game;

import com.survival.game.grafika.Font;
import com.survival.game.grafika.Fontf;
import com.survival.game.grafika.SpriteSheet;
import com.survival.game.math.Matrix;
import com.survival.game.math.AABB;
import com.survival.game.math.Vector2f;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import static org.junit.Assert.*;


public class AppTest {
    @Test
    public void testApp() {

        int data = 0;

        int tile = 0;
        int material = 0;

        int tilesetSize = 50;
        int numMaterials = 5;

        for(int i = 0; i < tilesetSize; i++) {
            for(int j = 0; j < numMaterials; j++) {
                data = i + (tilesetSize * (numMaterials + j));
                material = (int) ((data / tilesetSize) % numMaterials);
                tile = data - (tilesetSize * (numMaterials + material));

                assertFalse("tile: " + tile + " i: " + i, tile != i);
                assertFalse("material: " + material + " j: " + j, material != j);
            }
        }

        assertTrue(true);
    }


    @Test
    public void matrixIdentitiyTest() {
        // 2x3
        float[][] m1 = {{1, 2, 3}, {4, 5, 6}};
        Matrix M = new Matrix();
        //5x3
        float[][] m2 = {{1, 2, 3}, {4, 5, 6}, {4, 5, 6}, {4, 5, 6}, {4, 5, 6}};
        float[][] res = M.multiply(m1, m2);
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res.length; j++) {
                assertEquals(1, res[i][i], 0);
            }
        }

    }

    @Test
    public void matrixMultiplicationTest() {
        float[][] m1 = {{1, 2}, {3, 4}};
        Matrix M = new Matrix();
        float[][] res = M.multiply(m1, m1);
        assertEquals(7, res[0][0], 0);
        assertEquals(10, res[0][1], 0);
        assertEquals(15, res[1][0], 0);
        assertEquals(22, res[1][1], 0);
    }

    @Test
    public void vectorDistanceTest() {
        AABB a1 = new AABB(new Vector2f(0, 0), 20, 20);
        Vector2f other = new Vector2f(3, 4);
        assertEquals(5, a1.distance(other), 0);
    }

    @Test
    public void resourceTest() {
        SpriteSheet tileset = new SpriteSheet("tile/overworldOP.png", 32, 32);
        SpriteSheet tileset2 = new SpriteSheet("entity/wizardPlayer.png", 64, 64);
        SpriteSheet tileset3 = new SpriteSheet("entity/enemy/minimonsters.png", 16, 16);
        assertNotNull(tileset);
        assertNotNull(tileset2);
        assertNotNull(tileset3);
    }

    @Test
    public void AABBColCircletest() {
        AABB a1 = new AABB(new Vector2f(0, 0), 32, 32);
        AABB a2 = new AABB(new Vector2f(15, 15), 16);
        boolean hit = a2.colCircleBox(a1);
        assertEquals(true, hit);
    }

    @Test
    public void AABBBoxBoxCollisionTest() {
        AABB a1 = new AABB(new Vector2f(0, 0), 32, 32);
        AABB a2 = new AABB(new Vector2f(30, 30), 24, 24);
        boolean res = a1.collides(a2);
        assertEquals(true, res);
    }

    @Test
    public void FontTest() {
        Font font = new Font("font/font.png", 10, 10);
        assertNotNull(font);
    }

    @Test
    public void FontfTest() {
        Fontf fontf = new Fontf();
        fontf.loadFont("font/Stackedpixel.ttf", "MeatMadness");
        fontf.loadFont("font/GravityBold8.ttf", "GravityBold8");
        assertEquals(2, fontf.fontsLen);
    }

    @Test
    public void UIresourcesTest() {
        SpriteSheet ui = new SpriteSheet("ui/ui.png", 64, 64);
        SpriteSheet button = new SpriteSheet("ui/buttons.png", 122, 57);
        assertNotNull(ui);
        assertNotNull(button);
    }
}