package com.survival.game.ui;

import com.survival.game.JatekPanel;
import com.survival.game.math.Vector2f;
import com.survival.game.entitas.Jatekos;
import com.survival.game.grafika.SpriteSheet;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class JatekosUI {

    private FillBar healthbar;

    public JatekosUI(Jatekos p) {

        SpriteSheet bars = new SpriteSheet("ui/fillbars.png");
        BufferedImage[] barSprites = { 
            bars.getSubimage(12, 2, 7, 16), 
            bars.getSubimage(39, 0, 7, 14), // red health bar
            bars.getSubimage(0, 0, 12, 20) };
        
        Vector2f pos = new Vector2f(22, JatekPanel.magassag - 52);
        this.healthbar = new FillBar(p, barSprites, pos, 16, 16);
        

    }

    public void render(Graphics2D g) {
        healthbar.render(g);
    }

}