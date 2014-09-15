package com.courter.pandasurvivor;

public class Hero extends GameObject {
    public static final float WALKING_BOUNDS_HERO_HEIGHT = 2;
    public static final float WALKING_BOUNDS_HERO_WIDTH = 2;

    public static final float SHOOTING_BOUNDS_HERO_HEIGHT = 115;
    public static final float SHOOTING_BOUNDS_HERO_WIDTH = 80;

    public static float health;
    float stateTime = 0;
    World.HeroDirections currentDirection;

    public Hero(float x, float y) {
        super(x, y, WALKING_BOUNDS_HERO_WIDTH, WALKING_BOUNDS_HERO_HEIGHT);
        this.shooting_bounds = createBoundsRectangle(
                x - (SHOOTING_BOUNDS_HERO_WIDTH / 6),
                y - (SHOOTING_BOUNDS_HERO_HEIGHT / 5.5f),
                SHOOTING_BOUNDS_HERO_WIDTH,
                SHOOTING_BOUNDS_HERO_HEIGHT
        );
        currentDirection = World.HeroDirections.DOWN;
        this.health = 100;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;

        bounds.x = position.x - WALKING_BOUNDS_HERO_WIDTH / 2;
        bounds.y = position.y - WALKING_BOUNDS_HERO_HEIGHT / 2;

        shooting_bounds.x = position.x - (SHOOTING_BOUNDS_HERO_WIDTH / 6);
        shooting_bounds.y = position.y - (SHOOTING_BOUNDS_HERO_HEIGHT / 5.5f);
    }

    public void setCurrentDirection(World.HeroDirections updatedDirection) {
        currentDirection = updatedDirection;
    }

    public World.HeroDirections getCurrentDirection() {
        return currentDirection;
    }


}
