package com.survival.game.entitas;

import com.survival.game.grafika.SpriteSheet;
import com.survival.game.math.AABB;
import com.survival.game.math.Vector2f;
import com.survival.game.util.Kamera;

import java.awt.Graphics2D;
import java.awt.Color;

public abstract class Ellenseg extends Entitas {

    protected AABB sense;
    protected int r_sense;

    protected AABB attackrange;
    protected int r_attackrange;

    private Kamera kam;

    protected int xOffset;
    protected int yOffset;

    public Ellenseg(Kamera kam, SpriteSheet sprite, Vector2f origin, int size) {
        super(sprite, origin, size);
        this.kam = kam;

        bounds.setWidth(size / 2);
        bounds.setHeight(size / 2 - yOffset);
        bounds.setXOffset(size / 2 - xOffset);
        bounds.setYOffset(size / 2 + yOffset);

        sense = new AABB(new Vector2f(origin.x + size / 2 - r_sense / 2, origin.y + size / 2 - r_sense / 2), r_sense);
        attackrange = new AABB(new Vector2f(origin.x + bounds.getXOffset() + bounds.getWidth() / 2 - r_attackrange / 2 , origin.y + bounds.getYOffset() + bounds.getHeight() / 2 - r_attackrange / 2 ), r_attackrange);
    }

    public void chase(Jatekos jatekos) {
        AABB playerBounds = jatekos.getBounds();
        if (sense.colCircleBox(playerBounds) && !attackrange.colCircleBox(playerBounds)) {
            if (pos.y > jatekos.pos.y + 1) {
                up = true;
            } else {
                up = false;
            }
            if (pos.y < jatekos.pos.y - 1) {
                down = true;
            } else {
                down = false;
            }

            if (pos.x > jatekos.pos.x + 1) {
                left = true;
            } else {
                left = false;
            } 
            if (pos.x < jatekos.pos.x - 1) {
                right = true;
            } else {
                right = false;
            }
        } else {
            up = false;
            down = false;
            left = false;
            right = false;
        }
    }

    public void update(Jatekos jatekos, double time) {
        if(kam.getBounds().collides(this.bounds)) {
            super.update(time);
            chase(jatekos);
            move();

            if(teleported) {
                teleported = false;

                bounds.setWidth(size / 2);
                bounds.setHeight(size / 2 - yOffset);
                bounds.setXOffset(size / 2 - xOffset);
                bounds.setYOffset(size / 2 + yOffset);

                hitBounds = new AABB(pos, size, size);
                hitBounds.setXOffset(size / 2);

                sense = new AABB(new Vector2f(pos.x + size / 2 - r_sense / 2, pos.y + size / 2 - r_sense / 2), r_sense);
                attackrange = new AABB(new Vector2f(pos.x + bounds.getXOffset() + bounds.getWidth() / 2 - r_attackrange / 2 , pos.y + bounds.getYOffset() + bounds.getHeight() / 2 - r_attackrange / 2 ), r_attackrange);
            }

            if(attackrange.colCircleBox(jatekos.getBounds()) && !isInvincible) {
                attack = true;
                jatekos.setHealth(jatekos.getHealth() - ero, 5f * getDirection(), currentDirection == UP || currentDirection == DOWN);
            } else {
                attack = false;
            }

            if (!fallen) {
                if (!tc.collisionTile(dx, 0)) {
                    sense.getPos().x += dx;
                    attackrange.getPos().x += dx;
                    pos.x += dx;
                }
                if (!tc.collisionTile(0, dy)) {
                    sense.getPos().y += dy;
                    attackrange.getPos().y += dy;
                    pos.y += dy;
                }
            } else {
                if(ani.hasPlayedOnce()) {
                    die = true;
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        if(kam.getBounds().collides(this.bounds)) {

            if(useRight && left) {
                g.drawImage(ani.getImage().image, (int) (pos.getWorldVar().x) + size, (int) (pos.getWorldVar().y), -size, size, null);
            } else {
                g.drawImage(ani.getImage().image, (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
            }
            
			
            // Health Bar UI, eloszor a piros reszt, majd arra a zold layert
            g.setColor(Color.red);
			g.fillRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y - 5), 24, 5);
			
			g.setColor(Color.green);
            g.fillRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y - 5), (int) (24 * healthpercent), 5);
            
        }
    }
}