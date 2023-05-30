package com.survival.game.util;

import java.awt.Color;
import com.survival.game.JatekPanel;
import com.survival.game.allapotok.PlayAllapot;
import com.survival.game.entitas.Entitas;
import com.survival.game.math.Vector2f;
import com.survival.game.math.AABB;

import java.awt.Graphics;

public class Kamera {

    private AABB collisionCam;

    private boolean fel;
    private boolean le;
    private boolean bal;
    private boolean jobb;

    private float dx;
    private float dy;
    private float maxSebesseg = 8f;
    private float gyorsulas = 3f;
    private float lassulas = 0.3f;

    private int szelessegLimit;
    private int magassagLimit;

    private int tileMeret = 64;

    private Entitas e;

    public Kamera(AABB collisionCam) {
        this.collisionCam = collisionCam;
    }

    public void setLimit(int widthLimit, int heightLimit) {
        this.szelessegLimit = widthLimit;
        this.magassagLimit = heightLimit;
    }

    public void setTileMeret(int tileMeret) {
        this.tileMeret = tileMeret;
    }

    public Entitas getTarget() { return e; }

    public Vector2f getPos() {
        return collisionCam.getPos();
    }
    public AABB getBounds() {
        return collisionCam;
    }

    public void update() {
        move();
        if(e != null) {
            if (!e.xCol) {
                if ((e.getPos().getWorldVar().x + dy) < Vector2f.getWorldVarX(szelessegLimit - collisionCam.getWidth() / 2) + tileMeret
                    && (e.getPos().getWorldVar().x + dy) > Vector2f.getWorldVarX(JatekPanel.szelesseg / 2 - tileMeret * 2)) {
                    PlayAllapot.map.x += dx;
                    collisionCam.getPos().x += dx;
                }
            }
            if (!e.yCol) {
                if ((e.getPos().getWorldVar().y + dy) < Vector2f.getWorldVarY(magassagLimit - collisionCam.getHeight() / 2) + tileMeret
                    && (e.getPos().getWorldVar().y + dy) > Vector2f.getWorldVarY(JatekPanel.magassag / 2 - tileMeret * 2)) {
                    PlayAllapot.map.y += dy;
                    collisionCam.getPos().y += dy;
                }
            }
        } else {
            if(collisionCam.getPos().x + dx > 0
            && collisionCam.getPos().getWorldVar().x + dx < Vector2f.getWorldVarX(szelessegLimit - collisionCam.getWidth()) - tileMeret) {
                PlayAllapot.map.x += dx;
                collisionCam.getPos().x += dx;
            }

            if(collisionCam.getPos().y + dy > 0 
            && collisionCam.getPos().getWorldVar().y + dy < Vector2f.getWorldVarY(magassagLimit - collisionCam.getHeight()) - tileMeret) {
                PlayAllapot.map.y += dy;
                collisionCam.getPos().y += dy;
            }
        }
    }

    private void move() {
        if (fel) {
            dy -= gyorsulas;
            if (dy < -maxSebesseg) {
                dy = -maxSebesseg;
            }
        } else {
            if (dy < 0) {
                dy += lassulas;
                if (dy > 0) {
                    dy = 0;
                }
            }
        }
        if (le) {
            dy += gyorsulas;
            if (dy > maxSebesseg) {
                dy = maxSebesseg;
            }
        } else {
            if (dy > 0) {
                dy -= lassulas;
                if (dy < 0) {
                    dy = 0;
                }
            }
        }
        if (bal) {
            dx -= gyorsulas;
            if (dx < -maxSebesseg) {
                dx = -maxSebesseg;
            }
        } else {
            if (dx < 0) {
                dx += lassulas;
                if (dx > 0) {
                    dx = 0;
                }
            }
        }
        if (jobb) {
            dx += gyorsulas;
            if (dx > maxSebesseg) {
                dx = maxSebesseg;
            }
        } else {
            if (dx > 0) {
                dx -= lassulas;
                if (dx < 0) {
                    dx = 0;
                }
            }
        }
    }

    public void target(Entitas e) {
        this.e = e;
        if(e != null) {
            gyorsulas = e.getGyorsulas();
            lassulas = e.getLassulas();
            maxSebesseg = e.getMaxSebesseg();
        } else {
            gyorsulas = 10;
            lassulas = 0.3f;
            maxSebesseg = 8;
        }
    }

    public void setMaxSebesseg(int maxSebesseg) {this.maxSebesseg = maxSebesseg; }

    public void input(MouseHandler mouse, KeyHandler key) {

        if (e == null) {
            if (key.up.down) {
                fel = true;
            } else {
                fel = false;
            }
            if (key.down.down) {
                le = true;
            } else {
                le = false;
            }
            if (key.left.down) {
                bal = true;
            } else {
                bal = false;
            }
            if (key.right.down) {
                jobb = true;
            } else {
                jobb = false;
            }
        } else {
            if (!e.yCol) {
                if (collisionCam.getPos().y + JatekPanel.magassag / 2 - e.getSize() / 2 + dy > e.getPos().y + e.getDy() + 2) {
                    fel = true;
                    le = false;
                } else if (collisionCam.getPos().y + JatekPanel.magassag / 2 - e.getSize() / 2 + dy < e.getPos().y + e.getDy() - 2) {
                    le = true;
                    fel = false;
                } else {
                    dy = 0;
                    fel = false;
                    le = false;
                }
            }

            if (!e.xCol) {
                if (collisionCam.getPos().x + JatekPanel.szelesseg / 2 - e.getSize() / 2 + dx > e.getPos().x + e.getDx() + 2) {
                    bal = true;
                    jobb = false;
                } else if (collisionCam.getPos().x + JatekPanel.szelesseg / 2 - e.getSize() / 2 + dx < e.getPos().x + e.getDx() - 2) {
                    jobb = true;
                    bal = false;
                } else {
                    dx = 0;
                    jobb = false;
                    bal = false;
                }
            }
        }
    }

    public void render(Graphics g) {
         g.setColor(Color.blue);
        g.drawRect((int) collisionCam.getPos().getWorldVar().x, (int) collisionCam.getPos().getWorldVar().y, (int) collisionCam.getWidth(),
                (int) collisionCam.getHeight());
    }
}