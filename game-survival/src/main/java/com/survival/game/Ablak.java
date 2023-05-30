package com.survival.game;

import java.awt.BorderLayout;
import java.awt.image.BufferStrategy;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

public class Ablak extends JFrame implements ComponentListener {

    private BufferStrategy bs;
    private JatekPanel gp;

    public Ablak() {
        setTitle("Dungeon Adventures 2D!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIgnoreRepaint(true);
        pack();
        //display kozepe
        setLocationRelativeTo(null);
        setVisible(true);
        getContentPane().addComponentListener(this);
    }

    public void addNotify() {
        super.addNotify();

        createBufferStrategy(2);
        bs = getBufferStrategy();

        setLayout(new BorderLayout());
        gp = new JatekPanel(bs, 1280, 720);
        add(gp);
    }


    public void componentHidden(ComponentEvent ce) {}
    public void componentShown(ComponentEvent ce) {}
    public void componentMoved(ComponentEvent ce) {}
    public void componentResized(ComponentEvent ce) {
        JatekPanel.szelesseg = this.getWidth();
        JatekPanel.magassag = this.getHeight();
    }

}
