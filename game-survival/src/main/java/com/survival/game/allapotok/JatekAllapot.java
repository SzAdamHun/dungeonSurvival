package com.survival.game.allapotok;

import com.survival.game.util.KeyHandler;
import com.survival.game.util.MouseHandler;

import java.awt.Graphics2D;

public abstract class JatekAllapot {

    protected JatekAllapotManager gsm;

    public JatekAllapot(JatekAllapotManager gsm) {
        this.gsm = gsm;
    }

    public abstract void update(double time);
    public abstract void input(MouseHandler mouse, KeyHandler key);
    public abstract void render(Graphics2D g);
}
