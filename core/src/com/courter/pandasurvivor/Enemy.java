package com.courter.pandasurvivor;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Enemy extends GameObject {
    public static final float WALKING_BOUNDS_ENEMY_HEIGHT = 100;
    public static final float WALKING_BOUNDS_ENEMY_WIDTH = 110;

    public static final float SHOOTING_BOUNDS_ENEMY_HEIGHT = 70;
    public static final float SHOOTING_BOUNDS_ENEMY_WIDTH = 60;

    public static float health;
    float stateTime = 0;
    World.HeroDirections currentDirection;
    Sprite sprite;

    public Enemy(float x, float y, Sprite sprite) {
        super(x - WALKING_BOUNDS_ENEMY_WIDTH / 6, y + WALKING_BOUNDS_ENEMY_HEIGHT / 6, WALKING_BOUNDS_ENEMY_WIDTH, WALKING_BOUNDS_ENEMY_HEIGHT);
        this.shooting_bounds = createBoundsRectangle(x, y - SHOOTING_BOUNDS_ENEMY_WIDTH / 2, SHOOTING_BOUNDS_ENEMY_WIDTH, SHOOTING_BOUNDS_ENEMY_HEIGHT);
        currentDirection = World.HeroDirections.RIGHT;
        this.sprite = sprite;
        this.health = 20;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;

        bounds.x = position.x - WALKING_BOUNDS_ENEMY_WIDTH / 2;
        bounds.y = position.y - WALKING_BOUNDS_ENEMY_HEIGHT / 2;

        shooting_bounds.x = position.x;
        shooting_bounds.y = position.y - SHOOTING_BOUNDS_ENEMY_HEIGHT / 2;
    }

    public void setCurrentDirection(World.HeroDirections updatedDirection) {
        currentDirection = updatedDirection;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public World.HeroDirections getCurrentDirection() {
        return currentDirection;
    }
}