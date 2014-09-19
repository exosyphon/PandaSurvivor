package com.courter.pandasurvivor;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Enemy extends GameObject {
    public static final float WALKING_BOUNDS_ENEMY_HEIGHT = 100;
    public static final float WALKING_BOUNDS_ENEMY_WIDTH = 100;

    public static final float SHOOTING_BOUNDS_ENEMY_HEIGHT = 70;
    public static final float SHOOTING_BOUNDS_ENEMY_WIDTH = 60;

    private float health;
    float stateTime = 0;
    World.HeroDirections currentDirection;
    Sprite sprite;
    private int xpGain;
    private int touchDamage;

    public Enemy(float x, float y, Sprite sprite) {
        super(x - WALKING_BOUNDS_ENEMY_WIDTH / 6, y + WALKING_BOUNDS_ENEMY_HEIGHT / 6, WALKING_BOUNDS_ENEMY_WIDTH, WALKING_BOUNDS_ENEMY_HEIGHT);
        this.shooting_bounds = createBoundsRectangle(x, y - SHOOTING_BOUNDS_ENEMY_WIDTH / 2, SHOOTING_BOUNDS_ENEMY_WIDTH, SHOOTING_BOUNDS_ENEMY_HEIGHT);
        currentDirection = World.HeroDirections.RIGHT;
        this.sprite = sprite;
        this.health = 20;
        this.xpGain = 5;
        this.touchDamage = 2;
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

    public void setCurrentDirection(World.HeroDirections updatedDirection) {
        currentDirection = updatedDirection;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public World.HeroDirections getCurrentDirection() {
        return currentDirection;
    }

    public int getXpGain() {
        return this.xpGain;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public int getTouchDamage() {
        return touchDamage;
    }

    public void setTouchDamage(int touchDamage) {
        this.touchDamage = touchDamage;
    }
}
