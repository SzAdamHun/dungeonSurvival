package com.survival.game.allapotok;

import com.survival.game.JatekPanel;
import com.survival.game.grafika.SpriteSheet;
import com.survival.game.math.Vector2f;
import com.survival.game.ui.Gomb;
import com.survival.game.util.KeyHandler;
import com.survival.game.util.MouseHandler;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GameOverAllapot extends JatekAllapot {

    private String gameover = "GAME OVER";

    private BufferedImage imgButton;
    private BufferedImage imgHover;
    private Gomb btnReset;
    private Gomb btnQuit;
    private Font font;


    public GameOverAllapot(JatekAllapotManager gsm) {
        super(gsm);

        imgButton = JatekAllapotManager.button.getSubimage(0, 0, 121, 26);
        imgHover = JatekAllapotManager.button.getSubimage(0, 29, 122, 28);

        font = new Font("MeatMadness", Font.PLAIN, 48);
        btnReset = new Gomb("RESTART", imgButton, font, new Vector2f(JatekPanel.szelesseg / 2, JatekPanel.magassag / 2 - 48), 32, 16);
        btnQuit = new Gomb("QUIT", imgButton, font, new Vector2f(JatekPanel.szelesseg / 2, JatekPanel.magassag / 2 + 48), 32, 16);

        btnReset.addHoverImage(btnReset.createButton("RESTART", imgHover, font, btnReset.getWidth(), btnReset.getHeight(), 32, 20));
        btnQuit.addHoverImage(btnQuit.createButton("QUIT", imgHover, font, btnQuit.getWidth(), btnQuit.getHeight(), 32, 20));
        
        btnReset.addEvent(e -> {
            gsm.add(JatekAllapotManager.PLAY);
            gsm.pop(JatekAllapotManager.GAMEOVER);
        });

        btnQuit.addEvent(e -> {
            System.exit(0);
        });
    }

    @Override
    public void update(double time) {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        key.escape.tick();

        btnReset.input(mouse, key);
        btnQuit.input(mouse, key);

        if (key.escape.clicked) {
			System.exit(0);
		}
    }

    @Override
    public void render(Graphics2D g) {
        SpriteSheet.drawArray(g, gameover, new Vector2f(JatekPanel.szelesseg / 2 - gameover.length() * (32 / 2), JatekPanel.magassag / 2 - 32 / 2), 32, 32, 32);
        btnReset.render(g);
        btnQuit.render(g);
    }
}
