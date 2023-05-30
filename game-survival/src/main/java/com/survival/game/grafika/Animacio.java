package com.survival.game.grafika;

public class Animacio {

    private Sprite[] framek;
    private int[] allapotok;
    private int aktFrame;
    private int frameDb;

    private int count;
    private int delay;

    private int timesPlayed;

    public Animacio() {
        timesPlayed = 0;
        allapotok = new int[10];
    }

    public void setFrames(int state, Sprite[] frames) {
        this.framek = frames;
        aktFrame = 0;
        count = 0;
        timesPlayed = 0;
        delay = 2;
        if(allapotok[state] == 0) {
            frameDb = frames.length;
        } else {
            frameDb = allapotok[state];
        }
    }

    public void setDelay(int i) { delay = i; }
    public void setNumFrames(int i, int state) { allapotok[state] = i; }

    public void update() {
        if(delay == -1) return;
        
        count++;

        if(count == delay) {
            aktFrame++;
            count = 0;
        }
        if(aktFrame == frameDb) {
            aktFrame = 0;
            timesPlayed++;
        }
    }

    public int getDelay() { return delay; }
    public Sprite getImage() { return framek[aktFrame]; }
    public boolean hasPlayedOnce() { return timesPlayed > 0; }

}
