package com.courter.pandasurvivor;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Wall extends GameObject {
    public static final float WALL_HEIGHT = 110;
    public static final float WALL_WIDTH = 110;
    Sprite sprite;

    public Wall(float x, float y, Sprite sprite) {
        super(x, y, WALL_WIDTH, WALL_HEIGHT);
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return this.sprite;
    }
}
