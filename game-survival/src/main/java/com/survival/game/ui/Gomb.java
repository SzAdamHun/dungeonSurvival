package com.survival.game.ui;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.survival.game.JatekPanel;
import com.survival.game.math.AABB;
import com.survival.game.math.Vector2f;
import com.survival.game.util.KeyHandler;
import com.survival.game.util.MouseHandler;
import com.survival.game.allapotok.JatekAllapotManager;
import com.survival.game.grafika.SpriteSheet;

public class Gomb {

    private String label;
    private int lbSzelesseg;
    private int lbMagassag;

    private BufferedImage kep;
    private BufferedImage hoverKep;
    private BufferedImage pressedKep;

    private Vector2f iPos;
    private Vector2f lbPos;

    private AABB bounds;
    private boolean hovering = false;
    private int hoverMeret;
    private ArrayList<ClickedEvent> events;
    private boolean clicked = false;
    private boolean pressed = false;
    private boolean canHover = true;
    private boolean drawString = true;

    private float pressedtime;

    public Gomb(String label, BufferedImage kep, Font font, Vector2f pos, int buttonWidth, int buttonHeight) {
        JatekAllapotManager.g.setFont(font);
        FontMetrics met = JatekAllapotManager.g.getFontMetrics(font);
        int height = met.getHeight();
        int width = met.stringWidth(label);

        if(buttonWidth == -1) buttonWidth = buttonHeight;

        this.label = label;

        this.kep = createButton(label, kep, font, width + buttonWidth, height + buttonHeight, buttonWidth, buttonHeight);
        this.iPos = new Vector2f(pos.x - this.kep.getWidth() / 2, pos.y - this.kep.getHeight() / 2);
        this.bounds = new AABB(iPos, this.kep.getWidth(), this.kep.getHeight());
        

        events = new ArrayList<ClickedEvent>();
        this.canHover = false;
        this.drawString = false;
    }

    public BufferedImage createButton(String label, BufferedImage image, Font font, int width, int height, int buttonWidth, int buttonHeight) {
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        if(image.getWidth() != width || image.getHeight() != height) {
            image = resizeImage(image, width, height);
        }

        Graphics g = result.getGraphics();
        g.drawImage(image, 0, 0, width, height, null);

        g.setFont(font);
        g.drawString(label, buttonWidth / 2, (height - buttonHeight));

        g.dispose();

        return result;
    }

    private BufferedImage resizeImage(BufferedImage image, int width, int height) {
        Image temp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();

        g.drawImage(temp, 0, 0, null);
        g.dispose();

        return result;
    }


    public Gomb(String label, int lbSzelesseg, int lbMagassag, BufferedImage kep, int iWidth, int iHeight) {
        this.label = label;
        this.lbSzelesseg = lbSzelesseg;
        this.lbMagassag = lbMagassag;
        this.kep = kep;
        this.hoverMeret = 20;

        iPos = new Vector2f((JatekPanel.szelesseg / 2 - iWidth / 2) , (JatekPanel.magassag / 2 - iHeight / 2));
        lbPos = new Vector2f((iPos.x + iWidth / 2 + lbSzelesseg / 2) - ((label.length()) * lbSzelesseg / 2), iPos.y + iHeight / 2 - lbMagassag / 2 - 4);
    
        this.bounds = new AABB(iPos, iWidth, iHeight);

        events = new ArrayList<ClickedEvent>();
    }


    public Gomb(String label, Vector2f lbPos, int lbSzelesseg, int lbMagassag, BufferedImage kep, Vector2f iPos, int iWidth, int iHeight) {
        this(label, lbSzelesseg, lbMagassag, kep, iWidth, iHeight);

        this.iPos = iPos;
        this.lbPos = lbPos;

        this.bounds = new AABB(iPos, iWidth, iHeight);
    }

    public void addHoverImage(BufferedImage image) {
        this.hoverKep = image;
        this.canHover = true;
    }

	public boolean getHovering() { return hovering; }
    public void addEvent(ClickedEvent e) { events.add(e);}

    public int getWidth() { return (int) bounds.getWidth(); }
    public int getHeight() { return (int) bounds.getHeight(); }
    public Vector2f getPos() { return bounds.getPos(); }

    public void update(double time) {
        if(pressedKep != null && pressed && pressedtime + 300 < time / 1000000) {
            pressed = false;
        }
    }

    private void hover(int value) {
        if(hoverKep == null) {
            iPos.x -= value / 2;
            iPos.y -= value / 2;
            float iWidth = value + bounds.getWidth();
            float iHeight = value + bounds.getHeight();
            this.bounds = new AABB(iPos, (int) iWidth, (int) iHeight);

            lbPos.x -= value / 2;
            lbPos.y -= value / 2;
            lbSzelesseg += value / 3;
            lbMagassag += value / 3;
            
        }
        
        hovering = true;
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        if(bounds.inside(mouse.getX(), mouse.getY())) {
            if(canHover && !hovering) {
                hover(hoverMeret);
            }
            if(mouse.getButton() == 1 && !clicked) {
                clicked = true;
                pressed = true;

                pressedtime = System.nanoTime() / 1000000;

                for(int i = 0; i < events.size(); i++) {
                    events.get(i).action(1);
                }
            } else if(mouse.getButton() == -1) {
                clicked = false;
            }
        } else if(canHover && hovering) {
            hover(-hoverMeret);
            hovering = false;
        }
    }

    public void render(Graphics2D g) {
        if(drawString) {
            SpriteSheet.drawArray(g, label, lbPos, lbSzelesseg, lbMagassag);
        }

        if(canHover && hoverKep != null && hovering) {
            g.drawImage(hoverKep, (int) iPos.x, (int) iPos.y, (int) bounds.getWidth(), (int) bounds.getHeight(), null);
        } else if(pressedKep != null && pressed) {
            g.drawImage(pressedKep, (int) iPos.x, (int) iPos.y, (int) bounds.getWidth(), (int) bounds.getHeight(), null);
        } else {
            g.drawImage(kep, (int) iPos.x, (int) iPos.y, (int) bounds.getWidth(), (int) bounds.getHeight(), null);
        }
        
    }

}