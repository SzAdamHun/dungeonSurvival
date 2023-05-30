package com.survival.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.survival.game.allapotok.JatekAllapotManager;
import com.survival.game.util.MouseHandler;
import com.survival.game.util.KeyHandler;


public class JatekPanel extends JPanel implements Runnable {

    public static final long serialVersionUID = 1L;

    public static int szelesseg;
    public static int magassag;
    public static int regiFrameCount;
    public static int regiTickCount;
    public static int tickCount;

    private Thread thread;
    private boolean fut = false;

    private BufferStrategy bs;
    private BufferedImage img;
    private Graphics2D g;

    private MouseHandler eger;
    private KeyHandler billentyu;

    private JatekAllapotManager gsm;

    public JatekPanel(BufferStrategy bs, int szelesseg, int magassag) {
        JatekPanel.szelesseg = szelesseg;
        JatekPanel.magassag = magassag;
        this.bs = bs;
        setPreferredSize(new Dimension(szelesseg, magassag));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();

        if (thread == null) {
            thread = new Thread(this, "GameThread");
            thread.start();
        }
    }

    public void init() {
        fut = true;

        img = new BufferedImage(szelesseg, magassag, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        eger = new MouseHandler(this);
        billentyu = new KeyHandler(this);

        gsm = new JatekAllapotManager(g);
    }

    public void run() {
        init();

        final double GAME_HERTZ = 64.0;
        final double TBU = 1000000000 / GAME_HERTZ; // Time Before Update

        final int MUBR = 3; // Must Update before render

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double TARGET_FPS = 1000;
        final double TTBR = 1000000000 / TARGET_FPS; // Total time before render

        int frameCount = 0;
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);
        regiFrameCount = 0;

        tickCount = 0;
        regiTickCount = 0;

        while (fut) {
            double now = System.nanoTime();
            int updateCount = 0;
            while (((now - lastUpdateTime) > TBU) && (updateCount < MUBR)) {
                update(now);
                input(eger, billentyu);
                lastUpdateTime += TBU;
                updateCount++;
                tickCount++;
            }

            if ((now - lastUpdateTime) > TBU) {
                lastUpdateTime = now - TBU;
            }

            input(eger, billentyu);
            render();
            draw();
            lastRenderTime = now;
            frameCount++;

            int thisSecond = (int) (lastUpdateTime / 1000000000);
            if (thisSecond > lastSecondTime) {
                if (frameCount != regiFrameCount) {
                    System.out.println(thisSecond + " " + frameCount);
                    regiFrameCount = frameCount;
                }

                if (tickCount != regiTickCount) {
                    System.out.println(thisSecond + " " + tickCount);
                    regiTickCount = tickCount;
                }
                tickCount = 0;
                frameCount = 0;
                lastSecondTime = thisSecond;
            }

            while (now - lastRenderTime < TTBR && now - lastUpdateTime < TBU) {
                Thread.yield();

                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    System.out.println("ERROR");
                }

                now = System.nanoTime();
            }

        }
    }

    public void update(double time) {
        gsm.update(time);
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        gsm.input(mouse, key);
    }

    public void render() {
        if (g != null) {
            g.setColor(new Color(33, 30, 39));
            g.fillRect(0, 0, szelesseg, magassag);
            gsm.render(g);
        }
    }

    public void draw() {
        do {
            Graphics g2 = (Graphics) bs.getDrawGraphics();
            g2.drawImage(img, 8, 31, szelesseg, magassag, null);
            g2.dispose();
            bs.show();
        } while(bs.contentsLost());
        
    }

}
