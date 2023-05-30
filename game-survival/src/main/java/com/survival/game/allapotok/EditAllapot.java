package com.survival.game.allapotok;

import com.survival.game.JatekPanel;
import com.survival.game.entitas.JatekObject;
import com.survival.game.entitas.ellenseg.TinyMon;
import com.survival.game.grafika.SpriteSheet;
import com.survival.game.math.Vector2f;
import com.survival.game.ui.Gomb;
import com.survival.game.util.Kamera;
import com.survival.game.util.KeyHandler;
import com.survival.game.util.MouseHandler;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class EditAllapot extends JatekAllapot {

    private BufferedImage imgButton;
    private Gomb btnEnemy1;
    private Gomb btnEnemy2;
    private boolean clicked = false;

    private JatekObject jatekObject = null;
    private PlayAllapot ps;
    private Kamera cam;

    private int selection = 0;
    private JatekObject e_enemy1;
    private JatekObject e_enemy2;
    private JatekObject[] entityList = {jatekObject, e_enemy1, e_enemy2};


    public EditAllapot(JatekAllapotManager gsm, Kamera cam) {
        super(gsm);
        imgButton = JatekAllapotManager.ui.getSprite(0, 0, 128, 64).image;
        this.ps = (PlayAllapot) gsm.getState(JatekAllapotManager.PLAY);
        this.cam = cam;

        SpriteSheet enemySheet = new SpriteSheet("entity/enemy/minimonsters.png", 16, 16);

        btnEnemy1 = new Gomb("TinyMon", new Vector2f(64 + 24, 64 + 24), 32, 24, imgButton, new Vector2f(64, 64), 220, 75);
        btnEnemy1.addEvent(e -> {
            selection = 1;
            entityList[1] = new TinyMon(cam, new SpriteSheet(enemySheet.getSprite(0, 0, 128, 32), "tiny monster", 16, 16),
							new Vector2f((JatekPanel.szelesseg / 2) - 32 + 150, 0 + (JatekPanel.magassag / 2) - 32 + 150), 48);
        });

        btnEnemy2 = new Gomb("TinyBoar", new Vector2f(64 + 24, (64 + 24) * 2), 32, 24, imgButton, new Vector2f(64, 64 + 85), 235, 75);
        btnEnemy2.addEvent(e -> {
            selection = 2;
            entityList[2] = new TinyMon(cam, new SpriteSheet(enemySheet.getSprite(0, 1, 128, 32), "tiny boar", 16, 16), 
							new Vector2f((JatekPanel.szelesseg / 2) - 32 + 150, 0 + (JatekPanel.magassag / 2) - 32 + 150), 48);
        });
    }

    @Override
    public void update(double time) {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        btnEnemy1.input(mouse, key);
        btnEnemy2.input(mouse, key);
		
        if(mouse.getButton() == 1 && !clicked && entityList[selection] != null && !btnEnemy1.getHovering() && !btnEnemy2.getHovering()) {
            JatekObject go = entityList[selection];
            go.setPos(new Vector2f(mouse.getX() - go.getSize() / 2 + cam.getPos().x + 64, 
                                    mouse.getY() - go.getSize() / 2 + cam.getPos().y + 64));

            if(!ps.getGameObjects().contains(go)) {
                ps.getGameObjects().add(go.getBounds().distance(ps.getPlayerPos()), go);
            }

            clicked = true;
        } else if(mouse.getButton() == -1) {
            clicked = false;
        }
    }

    @Override
    public void render(Graphics2D g) {
        btnEnemy1.render(g);
        btnEnemy2.render(g);
    }
}