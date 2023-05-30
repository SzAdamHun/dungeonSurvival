package com.survival.game.entitas;

import com.survival.game.grafika.SpriteSheet;
import com.survival.game.grafika.Sprite;
import com.survival.game.math.AABB;
import com.survival.game.math.Vector2f;
import com.survival.game.util.TileCollision;

import java.awt.Graphics2D;

public abstract class JatekObject {

    protected SpriteSheet sprite;
    protected Sprite image;
    protected AABB bounds;
    protected Vector2f pos;
    protected int size;
    protected int spriteX;
    protected int spriteY;
    
    protected float dx;
    protected float dy;

    protected float maxSebesseg = 4f;
    protected float gyorsulas = 2f;
    protected float lassulas = 0.3f;
    protected float force = 25f;
    
    protected boolean teleported = false;
    protected TileCollision tc;

    public JatekObject(SpriteSheet sprite, Vector2f origin, int spriteX, int spriteY, int size) {
        this.bounds = new AABB(origin, size, size);
        this.sprite = sprite;
        this.pos = origin;
        this.size = size;
    }

    public void setPos(Vector2f pos) {
        this.pos = pos;
        this.bounds = new AABB(pos, size, size);
        teleported = true;
    }

    public Sprite getImage() { return image; }
    
    public void setSprite(SpriteSheet sprite) { this.sprite = sprite; }
    public void setSize(int i) { size = i; }

    public float getLassulas() { return lassulas; }
    public float getGyorsulas() { return gyorsulas; }
    public float getMaxSebesseg() { return maxSebesseg; }
    public float getDx() { return dx; }
    public float getDy() { return dy; }
    public AABB getBounds() { return bounds; }
    public Vector2f getPos() { return pos; }
    public int getSize() { return size; }

    public void addForce(float a, boolean vertical) {
        if(!vertical) {
            dx -= a; 
        } else {
            dy -= a;
        }
    }

    public void update() {

    }

    public void render(Graphics2D g) {
        g.drawImage(image.image, (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
    }

}