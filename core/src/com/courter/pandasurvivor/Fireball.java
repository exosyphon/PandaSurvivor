package com.courter.pandasurvivor;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Fireball extends DynamicGameObject{
    public static final float FIREBALL_WIDTH = 1f;
    public static final float FIREBALL_HEIGHT = .5f;
    public static final int FIREBALL_SPEED = (int) (WorldRenderer.w * .418f);
    public static final float FIREBALL_DISTANCE = 1;

    float stateTime = 0;
    Sprite sprite;
    float damageValue;
    World.HeroDirections fireballDirection;

    public Fireball(float x, float y, Sprite sprite, World.HeroDirections direction) {
        super(x, y, FIREBALL_WIDTH, FIREBALL_HEIGHT);
        velocity.set(FIREBALL_SPEED, 0);
        this.sprite = sprite;
        this.damageValue = 5;
        this.fireballDirection = direction;
    }

    public void update(float deltaTime)
    {
        stateTime += deltaTime;

        if(this.fireballDirection == World.HeroDirections.RIGHT)
            position.add(velocity.x * deltaTime, 1 * deltaTime);
        else if(this.fireballDirection == World.HeroDirections.LEFT)
            position.add(-velocity.x * deltaTime, 1 * deltaTime);
        else if(this.fireballDirection == World.HeroDirections.DOWN)
            position.add(1 * deltaTime, -velocity.x * deltaTime);
        else if(this.fireballDirection == World.HeroDirections.UP)
            position.add(1 * deltaTime, velocity.x * deltaTime);

        bounds.x = position.x - FIREBALL_WIDTH / 2;
        bounds.y = position.y - FIREBALL_HEIGHT / 2;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public float getDamageValue() {
        return damageValue;
    }
}
