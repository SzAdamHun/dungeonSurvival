package com.survival.game.math;

import java.util.ArrayList;

import com.survival.game.entitas.JatekObject;

public class AABB {

    private Vector2f pos;
    private float xOffset = 0;
    private float yOffset = 0;
    private float w;
    private float h;
    private float r;
    private int size;

    public AABB(Vector2f pos, int w, int h) {
        this.pos = pos;
        this.w = w;
        this.h = h;

        size = Math.max(w, h);
    }

    public AABB(Vector2f pos, int r) {
        this.pos = pos;
        this.r = r;

        size = r;
    }

    public Vector2f getPos() { return pos; }

    public float getSize() { return size; }
    public float getWidth() { return w; }
    public float getHeight() { return h; }


    public void setWidth(float f) { w = f; }
    public void setHeight(float f) { h = f; }

    public void setXOffset(float f) { xOffset = f; }
    public void setYOffset(float f) { yOffset = f; }
    public float getXOffset() { return xOffset; }
    public float getYOffset() { return yOffset; }

    public boolean collides(AABB bBox) {
        return collides(0, 0, bBox);
    }

    public boolean collides(float dx, float dy, ArrayList<JatekObject> go) {
        boolean collides = false;

        for(int i = 0; i < go.size(); i++) {
            collides = collides(dx, dy, go.get(i).getBounds());
            if(collides) {
                go.get(i).getImage().restoreDefault();
                go.remove(i);
                return collides;
            }
        }

        return collides;
    }

    public boolean collides(float dx, float dy, AABB bBox) {
        float ax = ((pos.x + (xOffset)) + (this.w / 2)) + dx;
        float ay = ((pos.y + (yOffset)) + (this.h / 2)) + dy;
        float bx = ((bBox.getPos().x + (bBox.getXOffset())) + (bBox.getWidth() / 2));
        float by = ((bBox.getPos().y + (bBox.getYOffset())) + (bBox.getHeight() / 2));

        if (Math.abs(ax - bx) < (this.w / 2) + (bBox.getWidth() / 2)) {
            if (Math.abs(ay - by) < (this.h / 2) + (bBox.getHeight() / 2)) {
                return true;
            }
        }

        return false;
    }

    public boolean inside(int xp, int yp) {
        if(xp == -1 || yp == - 1) return false;

        int wTemp = (int) this.w;
        int hTemp = (int) this.h;
        int x = (int) this.pos.x;
        int y = (int) this.pos.y;

        if(xp < x || yp < y) {
            return false;
        }

        wTemp += x;
        hTemp += y;
        return ((wTemp < x || wTemp > xp) && (hTemp < y || hTemp > yp));
    }

    public boolean colCircleBox(AABB aBox) {

        float dx = Math.max(aBox.getPos().x + aBox.getXOffset(), Math.min(pos.x + (r / 2), aBox.getPos().x + aBox.getXOffset() + aBox.getWidth()));
        float dy = Math.max(aBox.getPos().y + aBox.getYOffset(), Math.min(pos.y + (r / 2), aBox.getPos().y + aBox.getYOffset() + aBox.getHeight()));

        dx = pos.x + (r / 2) - dx;
        dy = pos.y + (r / 2) - dy;

        if(Math.sqrt(dx * dx + dy * dy) < r / 2) {
            return true;
        }

        return false;
    }

    public float distance(Vector2f other) {
        float dx = pos.x - other.x;
        float dy = pos.y - other.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

}
