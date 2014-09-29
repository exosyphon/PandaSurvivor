package com.courter.pandasurvivor;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Enemy extends GameObject {
    private float health;
    float stateTime = 0;
    World.HeroDirections currentDirection;
    Sprite sprite;
    private int xpGain;
    private int touchDamage;

    public Enemy(float x,
                 float y,
                 Sprite sprite,
                 float walkingBoundsWidth,
                 float walkingBoundsHeight,
                 float shootingBoundsWidth,
                 float shootingBoundsHeight,
                 float health,
                 int xpGain,
                 int touchDamage
    ) {
        super(x, y, walkingBoundsWidth, walkingBoundsHeight);
        this.shooting_bounds = createBoundsRectangle(x, y, shootingBoundsWidth, shootingBoundsHeight);
        currentDirection = World.HeroDirections.RIGHT;
        this.sprite = sprite;
        this.health = health;
        this.xpGain = xpGain;
        this.touchDamage = touchDamage;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        updateBounds();
    }

    @Override
    public void updateBounds() {
        bounds.x = position.x;
        bounds.y = position.y;

        shooting_bounds.x = position.x;
        shooting_bounds.y = position.y;
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
