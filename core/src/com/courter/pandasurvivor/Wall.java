package com.courter.pandasurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Wall extends GameObject {
    public static final float HERO_WALKING_BOUNDS_WALL_HEIGHT = (Gdx.graphics.getHeight() * .092f);
    public static final float HERO_WALKING_BOUNDS_WALL_WIDTH = (Gdx.graphics.getWidth() * .061f);
    public static final float ENEMY_WALKING_BOUNDS_WALL_HEIGHT = (Gdx.graphics.getHeight() * .0083f);
    public static final float ENEMY_WALKING_BOUNDS_WALL_WIDTH = (Gdx.graphics.getWidth() * .011f);

    public static final float SHOOTING_BOUNDS_WALL_HEIGHT = (Gdx.graphics.getHeight() * .059f);
    public static final float SHOOTING_BOUNDS_WALL_WIDTH = (Gdx.graphics.getWidth() * .028f);
    Sprite sprite;

    public Wall(float x, float y, Sprite sprite) {
        super(x - HERO_WALKING_BOUNDS_WALL_WIDTH/6, y + HERO_WALKING_BOUNDS_WALL_HEIGHT/6, HERO_WALKING_BOUNDS_WALL_WIDTH, HERO_WALKING_BOUNDS_WALL_HEIGHT);
        this.shooting_bounds = createBoundsRectangle(x + SHOOTING_BOUNDS_WALL_WIDTH/4, y + SHOOTING_BOUNDS_WALL_HEIGHT/4, SHOOTING_BOUNDS_WALL_WIDTH, SHOOTING_BOUNDS_WALL_HEIGHT);
        this.enemy_walking_bounds = createBoundsRectangle(x - (ENEMY_WALKING_BOUNDS_WALL_WIDTH*2), y + ENEMY_WALKING_BOUNDS_WALL_HEIGHT + ENEMY_WALKING_BOUNDS_WALL_HEIGHT/3, ENEMY_WALKING_BOUNDS_WALL_WIDTH, ENEMY_WALKING_BOUNDS_WALL_HEIGHT);
        this.sprite = sprite;
    }
}
