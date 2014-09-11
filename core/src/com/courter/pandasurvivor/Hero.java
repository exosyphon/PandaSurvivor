package com.courter.pandasurvivor;

public class Hero extends GameObject {
    public static final float HERO_HEIGHT = .8f;
    public static final float HERO_WIDTH = .8f;

    float stateTime = 0;
    World.HeroDirections currentDirection;

    public Hero(float x, float y) {
        super(x, y, HERO_WIDTH, HERO_HEIGHT);
        currentDirection = World.HeroDirections.DOWN;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;

        bounds.x = position.x - HERO_WIDTH / 2;
        bounds.y = position.y - HERO_HEIGHT / 2;
    }

    public void setCurrentDirection(World.HeroDirections updatedDirection) {
        currentDirection = updatedDirection;
    }

    public World.HeroDirections getCurrentDirection() {
        return currentDirection;
    }
}
