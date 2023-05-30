package com.survival.game.allapotok;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.survival.game.JatekPanel;
import com.survival.game.math.Vector2f;
import com.survival.game.ui.Gomb;
import com.survival.game.util.KeyHandler;
import com.survival.game.util.MouseHandler;

public class PauseAllapot extends JatekAllapot {
    
    private Gomb btnResume;
    private Gomb btnExit;
    private Font font;

    public PauseAllapot(JatekAllapotManager gsm) {
        super(gsm);

        BufferedImage imgButton = JatekAllapotManager.button.getSubimage(0, 0, 121, 26);
        BufferedImage imgHover = JatekAllapotManager.button.getSubimage(0, 29, 122, 28);

        font = new Font("MeatMadness", Font.PLAIN, 48);
        btnResume = new Gomb("RESUME", imgButton, font, new Vector2f(JatekPanel.szelesseg / 2, JatekPanel.magassag / 2 - 48), 32, 16);
        btnExit = new Gomb("EXIT", imgButton, font, new Vector2f(JatekPanel.szelesseg / 2, JatekPanel.magassag / 2 + 48), 32, 16);

        btnResume.addHoverImage(btnResume.createButton("RESUME", imgHover, font, btnResume.getWidth(), btnResume.getHeight(), 32, 20));
        btnExit.addHoverImage(btnExit.createButton("EXIT", imgHover, font, btnExit.getWidth(), btnExit.getHeight(), 32, 20));
        
        btnResume.addEvent(e -> {
            gsm.pop(JatekAllapotManager.PAUSE);
        });

        btnExit.addEvent(e -> {
            System.exit(0);
        });
    }

    @Override
    public void update(double time) {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        btnResume.input(mouse, key);
        btnExit.input(mouse, key);

    }

    @Override
    public void render(Graphics2D g) {
        btnResume.render(g);
        btnExit.render(g);
    }
}
