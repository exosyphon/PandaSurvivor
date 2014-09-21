package com.courter.pandasurvivor;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Wall extends GameObject {
    public static final float HERO_WALKING_BOUNDS_WALL_HEIGHT = 100;
    public static final float HERO_WALKING_BOUNDS_WALL_WIDTH = 110;
    public static final float ENEMY_WALKING_BOUNDS_WALL_HEIGHT = 15;
    public static final float ENEMY_WALKING_BOUNDS_WALL_WIDTH = 12;

    public static final float SHOOTING_BOUNDS_WALL_HEIGHT = 64;
    public static final float SHOOTING_BOUNDS_WALL_WIDTH = 52;
    Sprite sprite;

    public Wall(float x, float y, Sprite sprite) {
        super(x - HERO_WALKING_BOUNDS_WALL_WIDTH/6, y + HERO_WALKING_BOUNDS_WALL_HEIGHT/6, HERO_WALKING_BOUNDS_WALL_WIDTH, HERO_WALKING_BOUNDS_WALL_HEIGHT);
        this.shooting_bounds = createBoundsRectangle(x + SHOOTING_BOUNDS_WALL_WIDTH/4, y + SHOOTING_BOUNDS_WALL_HEIGHT/4, SHOOTING_BOUNDS_WALL_WIDTH, SHOOTING_BOUNDS_WALL_HEIGHT);
        this.enemy_walking_bounds = createBoundsRectangle(x - (ENEMY_WALKING_BOUNDS_WALL_WIDTH*4), y + ENEMY_WALKING_BOUNDS_WALL_HEIGHT + 5, ENEMY_WALKING_BOUNDS_WALL_WIDTH, ENEMY_WALKING_BOUNDS_WALL_HEIGHT);
        this.sprite = sprite;
    }
}
