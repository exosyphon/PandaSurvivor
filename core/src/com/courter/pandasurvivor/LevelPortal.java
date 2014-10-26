package com.courter.pandasurvivor;

/**
 * Created by andrew on 10/24/14.
 */
public class LevelPortal extends GameObject {
    public static final float WALKING_BOUNDS_LVL_PORTAL_HEIGHT = (WorldRenderer.h * .131f);
    public static final float WALKING_BOUNDS_LVL_PORTAL_WIDTH = (WorldRenderer.w * .041f);

    public LevelPortal(float x, float y) {
        super(x - WALKING_BOUNDS_LVL_PORTAL_WIDTH / 8, y + WALKING_BOUNDS_LVL_PORTAL_HEIGHT / 4, WALKING_BOUNDS_LVL_PORTAL_WIDTH, WALKING_BOUNDS_LVL_PORTAL_HEIGHT);
        this.shooting_bounds = null;
    }
}
