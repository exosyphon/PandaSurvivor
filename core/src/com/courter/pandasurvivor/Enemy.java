package com.courter.pandasurvivor;

public class Enemy extends GameObject {
    public static final float WALKING_BOUNDS_ENEMY_HEIGHT = 100;
    public static final float WALKING_BOUNDS_ENEMY_WIDTH = 110;

    public static final float SHOOTING_BOUNDS_ENEMY_HEIGHT = 64;
    public static final float SHOOTING_BOUNDS_ENEMY_WIDTH = 52;

    float stateTime = 0;
    World.HeroDirections currentDirection;

    public Enemy(float x, float y) {
        super(x - WALKING_BOUNDS_ENEMY_WIDTH/6, y + WALKING_BOUNDS_ENEMY_HEIGHT/6, WALKING_BOUNDS_ENEMY_WIDTH, WALKING_BOUNDS_ENEMY_HEIGHT);
        this.shooting_bounds = createBoundsRectangle(x + SHOOTING_BOUNDS_ENEMY_WIDTH/4, y + SHOOTING_BOUNDS_ENEMY_HEIGHT/4, SHOOTING_BOUNDS_ENEMY_WIDTH, SHOOTING_BOUNDS_ENEMY_HEIGHT);
        currentDirection = World.HeroDirections.DOWN;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;

        bounds.x = position.x - WALKING_BOUNDS_ENEMY_WIDTH / 2;
        bounds.y = position.y - WALKING_BOUNDS_ENEMY_HEIGHT / 2;

        shooting_bounds.x = position.x - SHOOTING_BOUNDS_ENEMY_WIDTH / 2;
        shooting_bounds.y = position.y - SHOOTING_BOUNDS_ENEMY_HEIGHT / 2;
    }

    public void setCurrentDirection(World.HeroDirections updatedDirection) {
        currentDirection = updatedDirection;
    }

    public World.HeroDirections getCurrentDirection() {
        return currentDirection;
    }
}
