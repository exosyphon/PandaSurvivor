package com.courter.pandasurvivor;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Wall extends GameObject {
    public static final float WALKING_BOUNDS_WALL_HEIGHT = 100;
    public static final float WALKING_BOUNDS_WALL_WIDTH = 110;

    public static final float SHOOTING_BOUNDS_WALL_HEIGHT = 64;
    public static final float SHOOTING_BOUNDS_WALL_WIDTH = 52;
    Sprite sprite;

    public Wall(float x, float y, Sprite sprite) {
        super(x - WALKING_BOUNDS_WALL_WIDTH/6, y + WALKING_BOUNDS_WALL_HEIGHT/6, WALKING_BOUNDS_WALL_WIDTH, WALKING_BOUNDS_WALL_HEIGHT);
        this.shooting_bounds = createBoundsRectangle(x + SHOOTING_BOUNDS_WALL_WIDTH/4, y + SHOOTING_BOUNDS_WALL_HEIGHT/4, SHOOTING_BOUNDS_WALL_WIDTH, SHOOTING_BOUNDS_WALL_HEIGHT);
        this.sprite = sprite;
    }
}
