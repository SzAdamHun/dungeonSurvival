package com.survival.game.util;

import com.survival.game.entitas.JatekObject;
import com.survival.game.math.AABB;

public class JatekObjectKey {

    public float value;
    public JatekObject go;

    public JatekObjectKey(float value, JatekObject go) {
        this.value = value;
        this.go = go;
    }

    public AABB getBounds() { return go.getBounds(); }
}