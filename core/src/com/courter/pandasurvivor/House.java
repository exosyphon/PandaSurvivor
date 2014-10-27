package com.courter.pandasurvivor;

/**
 * Created by andrew on 10/26/14.
 */
public class House extends GameObject {
    public static final float WALKING_BOUNDS_HOUSE_WIDTH = 370;
    public static final float WALKING_BOUNDS_HOUSE_HEIGHT = 340;
    public static final float SHOOTING_BOUNDS_HOUSE_WIDTH = 390;
    public static final float SHOOTING_BOUNDS_HOUSE_HEIGHT = 310;

    public static final float ENEMY_WALKING_BOUNDS_HOUSE_WIDTH = 310;
    public static final float ENEMY_WALKING_BOUNDS_HOUSE_HEIGHT = 240;

    public House(float x, float y) {
        super(x + WALKING_BOUNDS_HOUSE_HEIGHT / 4, y + WALKING_BOUNDS_HOUSE_HEIGHT / 2.75f, WALKING_BOUNDS_HOUSE_WIDTH, WALKING_BOUNDS_HOUSE_HEIGHT);
        this.shooting_bounds = createBoundsRectangle(x + SHOOTING_BOUNDS_HOUSE_HEIGHT / 4, y + SHOOTING_BOUNDS_HOUSE_HEIGHT / 2.3f, SHOOTING_BOUNDS_HOUSE_WIDTH, SHOOTING_BOUNDS_HOUSE_HEIGHT);
        this.enemy_walking_bounds = createBoundsRectangle(x + ENEMY_WALKING_BOUNDS_HOUSE_WIDTH / 4, y + ENEMY_WALKING_BOUNDS_HOUSE_HEIGHT / 1.7f, ENEMY_WALKING_BOUNDS_HOUSE_WIDTH, ENEMY_WALKING_BOUNDS_HOUSE_HEIGHT);
    }
}
