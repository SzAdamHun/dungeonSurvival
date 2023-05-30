package com.survival.game.grafika;

import java.awt.GraphicsEnvironment;
import java.awt.Font;
import java.util.HashMap;

public class Fontf {

    private HashMap<String, Font> fonts;
    // for testing
    public int fontsLen;

    public Fontf() {
        fonts = new HashMap<String, Font>();
    }

    public void loadFont(String path, String name) {
        try {
            System.out.println("Loading: " + path + "...");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream(path));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            Font font = new Font(name, Font.PLAIN, 32);

            fonts.put(name, font);
            fontsLen++;
        } catch (Exception e) {
            System.out.println("ERROR: cannot import file " + path + "...");
            e.printStackTrace();
        }
    }

    public Font getFont(String name) {
        return fonts.get(name);
    }

}