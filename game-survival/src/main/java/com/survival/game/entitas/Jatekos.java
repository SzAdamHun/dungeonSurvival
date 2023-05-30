package com.survival.game.entitas;


import com.survival.game.JatekPanel;
import com.survival.game.grafika.Sprite;
import com.survival.game.grafika.SpriteSheet;
import com.survival.game.math.Vector2f;
import com.survival.game.allapotok.PlayAllapot;
import com.survival.game.tile.TileManager;
import com.survival.game.tile.blocks.NormBlokk;
import com.survival.game.util.Kamera;
import com.survival.game.util.KeyHandler;
import com.survival.game.util.MouseHandler;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;

public class Jatekos extends Entitas {

    private Kamera kam;
    private ArrayList<Ellenseg> ellensegek;
    private ArrayList<JatekObject> go;
    private TileManager tm;

    public Jatekos(Kamera kam, SpriteSheet sprite, Vector2f origin, int size, TileManager tm) {
        super(sprite, origin, size);
        this.kam = kam;
        this.tm = tm;
        
        bounds.setWidth(42);
        bounds.setHeight(20);
        bounds.setXOffset(12);
        bounds.setYOffset(40);

        hitBounds.setWidth(42);
        hitBounds.setHeight(42);

        ani.setNumFrames(4, UP);
        ani.setNumFrames(4, DOWN);
        ani.setNumFrames(4, ATTACK + RIGHT);
        ani.setNumFrames(4, ATTACK + LEFT);
        ani.setNumFrames(4, ATTACK + UP);
        ani.setNumFrames(4, ATTACK + DOWN);

        ellensegek = new ArrayList<Ellenseg>();
        go = new ArrayList<JatekObject>();

        for(int i = 0; i < sprite.getSpriteArray2().length; i++) {
            for(int j = 0; j < sprite.getSpriteArray2()[i].length; j++) {
                sprite.getSpriteArray2()[i][j].setEffect(Sprite.effect.NEGATIVE);
                sprite.getSpriteArray2()[i][j].saveColors();
            }
        }

        hasIdle = false;
    }

    public void setTargetEnemy(Ellenseg ellenseg) {
        this.ellensegek.add(ellenseg);
    }

    private void resetPosition() {
        System.out.println("Resetting player ");
        pos.x = JatekPanel.szelesseg / 2 - 32;
        PlayAllapot.map.x = 0;
        kam.getPos().x = 0;

        pos.y = JatekPanel.magassag /2 - 32;
        PlayAllapot.map.y = 0;
        kam.getPos().y = 0;

        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);
    }

    public void update(double time) {
        super.update(time);

        attacking = isAttacking(time);
        for(int i = 0; i < ellensegek.size(); i++) {
            if(attacking) {
                ellensegek.get(i).setHealth(ellensegek.get(i).getHealth() - ero, force * getDirection(), currentDirection == UP || currentDirection == DOWN);
                ellensegek.remove(i);
            }
        }

        if(!fallen) {
            move();
            if(!tc.collisionTile(dx, 0) && !bounds.collides(dx, 0, go)) {
                //PlayState.map.x += dx;
                pos.x += dx;
                xCol = false;
            } else {
                xCol = true;
            }
            if(!tc.collisionTile(0, dy) && !bounds.collides(0, dy, go)) {
                //PlayState.map.y += dy;
                pos.y += dy;
                yCol = false;
            } else {
                yCol = true;
            }

            tc.normalTile(dx, 0);
            tc.normalTile(0, dy);

        } else {
            xCol = true;
            yCol = true;
            if(ani.hasPlayedOnce()) {
                resetPosition();
                dx = 0;
                dy = 0;
                fallen = false;
            }
        }

        NormBlokk block = tm.getNormalTile(tc.getTile());
        if(block != null) {
            block.getImage().restoreDefault();
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.green);
        g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());

        if(attack) {
            g.setColor(Color.red);
            g.drawRect((int) (hitBounds.getPos().getWorldVar().x + hitBounds.getXOffset()), (int) (hitBounds.getPos().getWorldVar().y + hitBounds.getYOffset()), (int) hitBounds.getWidth(), (int) hitBounds.getHeight());
        }

        if(isInvincible) {
            if(JatekPanel.tickCount % 30 >= 15) {
                ani.getImage().setEffect(Sprite.effect.REDISH);
            } else {
                ani.getImage().restoreColors();
            }
        } else {
            ani.getImage().restoreColors();
        }

        if(useRight && left) {
            g.drawImage(ani.getImage().image, (int) (pos.getWorldVar().x) + size, (int) (pos.getWorldVar().y), -size, size, null);
        } else {
            g.drawImage(ani.getImage().image, (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
        }
    }

    public void input(MouseHandler mouse, KeyHandler key) {

        if(!fallen) {
            if(key.up.down) {
                up = true;
            } else {
                up = false;
            }
            if(key.down.down) {
                down = true;
            } else {
                down = false;
            }
            if(key.left.down) {
                left = true;
            } else {
                left = false;
            }
            if(key.right.down) {
                right = true;
            } else {
                right = false;
            }

            if(key.attack.down && canAttack) {
                attack = true;
                attacktime = System.nanoTime();
            } else {
                if(!attacking) {
                    attack = false;
                }
            }

            if(key.shift.down) {
                maxSebesseg = 8;
                kam.setMaxSebesseg(7);
            } else {
                maxSebesseg = 4;
                kam.setMaxSebesseg(4);
            }

            if(up && down) {
                up = false;
                down = false;
            }

            if(right && left) {
                right = false;
                left = false;
            }
        } else {
            up = false;
            down = false;
            right = false;
            left = false;
        }
    }
}
