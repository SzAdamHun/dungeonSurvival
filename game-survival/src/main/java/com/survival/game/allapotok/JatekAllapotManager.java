package com.survival.game.allapotok;

import com.survival.game.JatekPanel;
import com.survival.game.grafika.Font;
import com.survival.game.grafika.SpriteSheet;
import com.survival.game.math.AABB;
import com.survival.game.math.Vector2f;
import com.survival.game.util.Kamera;
import com.survival.game.util.KeyHandler;
import com.survival.game.util.MouseHandler;
import com.survival.game.grafika.Fontf;

import java.awt.Graphics2D;

public class JatekAllapotManager {

    private JatekAllapot states[];

    public static Vector2f map;

    public static final int MENU = 0;
    public static final int PLAY = 1;
    public static final int PAUSE = 2;
    public static final int GAMEOVER = 3;
    public static final int EDIT = 4;

    public static Font font;
    public static Fontf fontf;
    public static SpriteSheet ui;
    public static SpriteSheet button;
    public static Kamera cam;
    public static Graphics2D g;

    public JatekAllapotManager(Graphics2D g) {
        JatekAllapotManager.g = g;
        map = new Vector2f(JatekPanel.szelesseg, JatekPanel.magassag);
        Vector2f.setWorldVar(map.x, map.y);

        states = new JatekAllapot[5];

        font = new Font("font/font.png", 10, 10);
        fontf = new Fontf();
        fontf.loadFont("font/Stackedpixel.ttf", "MeatMadness");
        fontf.loadFont("font/GravityBold8.ttf", "GravityBold8");
        SpriteSheet.currentFont = font;

        ui = new SpriteSheet("ui/ui.png", 64, 64);
        button = new SpriteSheet("ui/buttons.png", 122, 57);
        

        cam = new Kamera(new AABB(new Vector2f(-64, -64), JatekPanel.szelesseg + 128, JatekPanel.magassag + 128));

        states[PLAY] = new PlayAllapot(this, cam);
    }

    public boolean isStateActive(int state) {
        return states[state] != null;
    }

    public JatekAllapot getState(int state) {
        return states[state];
    }

    public void pop(int state) {
        states[state] = null;
    }

    public void add(int state) {
        if (states[state] != null)
            return;

        if (state == PLAY) {
            cam = new Kamera(new AABB(new Vector2f(0, 0), JatekPanel.szelesseg + 64, JatekPanel.magassag + 64));
            states[PLAY] = new PlayAllapot(this, cam);
        }
        else if (state == MENU) {
            states[MENU] = new MenuAllapot(this);
        }
        else if (state == PAUSE) {
            states[PAUSE] = new PauseAllapot(this);
        }
        else if (state == GAMEOVER) {
            states[GAMEOVER] = new GameOverAllapot(this);
        }
        else if (state == EDIT) {
            if(states[PLAY] != null) {
                states[EDIT] = new EditAllapot(this, cam);
            }
        }
    }


    public void update(double time) {
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null) {
                states[i].update(time);
            }
        }
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null) {
                states[i].input(mouse, key);
            }
        }        
    }

    public void render(Graphics2D g) {
        g.setFont(JatekAllapotManager.fontf.getFont("MeatMadness"));
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null) {
                states[i].render(g);
            }
        }
    }

}
