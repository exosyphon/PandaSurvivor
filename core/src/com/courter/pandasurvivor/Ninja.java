package com.courter.pandasurvivor;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by andrew on 9/28/14.
 */
public class Ninja extends Enemy {
    public static final float WALKING_BOUNDS_ENEMY_HEIGHT = (WorldRenderer.h * .111f);
    public static final float WALKING_BOUNDS_ENEMY_WIDTH = (WorldRenderer.w * .061f);

    public static final float SHOOTING_BOUNDS_ENEMY_HEIGHT = (WorldRenderer.h * .101f);
    public static final float SHOOTING_BOUNDS_ENEMY_WIDTH = (WorldRenderer.w * .044f);

    private World.NinjaTypes ninjaType;

    public Ninja(float x, float y, Sprite sprite, World.NinjaTypes ninjaType, int currentLevel) {
        super(
                x,
                y + WALKING_BOUNDS_ENEMY_HEIGHT / 4,
                sprite,
                WALKING_BOUNDS_ENEMY_WIDTH,
                WALKING_BOUNDS_ENEMY_HEIGHT,
                SHOOTING_BOUNDS_ENEMY_WIDTH,
                SHOOTING_BOUNDS_ENEMY_HEIGHT,
                20,
                5,
                2,
                currentLevel
        );
        this.shooting_bounds = createBoundsRectangle(x + SHOOTING_BOUNDS_ENEMY_HEIGHT / 2, y - SHOOTING_BOUNDS_ENEMY_WIDTH / 2, SHOOTING_BOUNDS_ENEMY_WIDTH, SHOOTING_BOUNDS_ENEMY_HEIGHT);
        this.ninjaType = ninjaType;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        updateBounds();
    }

    @Override
    public void updateBounds() {
        bounds.x = position.x - WALKING_BOUNDS_ENEMY_WIDTH / 2;
        bounds.y = position.y - WALKING_BOUNDS_ENEMY_HEIGHT / 2;

        shooting_bounds.x = position.x;
        shooting_bounds.y = position.y - SHOOTING_BOUNDS_ENEMY_HEIGHT / 2;
    }

    public World.NinjaTypes getNinjaType() {
        return ninjaType;
    }

    public void setNinjaType(World.NinjaTypes ninjaType) {
        this.ninjaType = ninjaType;
    }
}
