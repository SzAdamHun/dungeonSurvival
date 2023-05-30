package com.survival.game.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.survival.game.math.Vector2f;
import com.survival.game.entitas.Entitas;

public class FillBar {
    
    private BufferedImage[] bar; // 1: bar, 2: energy, 3: ends

    private Entitas e;

    private Vector2f pos;
    private int meret;
    private int hossz;

    private int energyHossz;

    private int barSzelessegArany;
    private int energySzelessegArany;

    private int barMagassagArany;

    public FillBar(Entitas e, BufferedImage[] sprite, Vector2f pos, int meret, int hossz) {
        this.e = e;
        this.bar = sprite;
        this.pos = pos;
        this.meret = meret;
        this.hossz = hossz;

        this.energyHossz = (int) ((bar[0].getWidth() + meret) * (hossz * e.getHealthPercent()));

        this.barSzelessegArany = (bar[0].getWidth() + meret) * hossz / (bar[0].getWidth());
        this.energySzelessegArany = energyHossz / (bar[0].getWidth());

        this.barMagassagArany = (bar[0].getHeight() + meret) / bar[0].getHeight();
    }

    public void render(Graphics2D g) {
        int endsWidth = 0;
        int centerHeight = (int) (pos.y - barMagassagArany - bar[0].getHeight() / 2);

        this.energyHossz = (int) ((bar[0].getWidth() + meret) * (hossz * e.getHealthPercent()));
        this.energySzelessegArany = this.energyHossz / (bar[0].getWidth());

        if(bar[2] != null) {
            endsWidth = bar[2].getWidth() + meret;

            g.drawImage(bar[2], (int) (pos.x), (int) (pos.y), endsWidth, bar[2].getHeight() + meret, null);
            g.drawImage(bar[2], (int) (pos.x + endsWidth * 2 + (bar[0].getWidth() + meret) * hossz) - this.barSzelessegArany, (int) (pos.y), -(endsWidth), bar[2].getHeight() + meret, null);
            centerHeight += bar[2].getHeight() / 2 + (bar[2].getHeight() - bar[0].getHeight()) / 2;
        }

        g.drawImage(bar[0], (int) (pos.x + endsWidth - this.barSzelessegArany), centerHeight, (bar[0].getWidth() + meret) * hossz, bar[0].getHeight() + meret, null);
        g.drawImage(bar[1], (int) (pos.x + endsWidth - this.energySzelessegArany), centerHeight, this.energyHossz, (int) (bar[0].getHeight() + meret), null);
    }

}