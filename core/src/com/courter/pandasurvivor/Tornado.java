package com.courter.pandasurvivor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Tornado extends DynamicGameObject {
    public static final float TORNADO_WIDTH = 40;
    public static final float TORNADO_HEIGHT = 60;
    public static final int TORNADO_SPEED = (int) (WorldRenderer.w * .118f);
    public static final float TORNADO_DISTANCE = 1;

    float stateTime = 0;
    Sprite sprite;
    float damageValue;
    World.HeroDirections tornadoDirection;

    public Tornado(float x, float y, Sprite sprite, World.HeroDirections direction, int currentLevel) {
        super(x, y, TORNADO_WIDTH, TORNADO_HEIGHT);
        velocity.set(TORNADO_SPEED, 0);
        this.sprite = sprite;
        this.damageValue = 5 * currentLevel;
        this.tornadoDirection = direction;
        this.bounds = new Rectangle(x - TORNADO_WIDTH / 2, y + TORNADO_HEIGHT / 4, TORNADO_WIDTH, TORNADO_HEIGHT);
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;

        if (this.tornadoDirection == World.HeroDirections.RIGHT)
            position.add(velocity.x * deltaTime, 1 * deltaTime);
        else if (this.tornadoDirection == World.HeroDirections.LEFT)
            position.add(-velocity.x * deltaTime, 1 * deltaTime);
        else if (this.tornadoDirection == World.HeroDirections.DOWN)
            position.add(1 * deltaTime, -velocity.x * deltaTime);
        else if (this.tornadoDirection == World.HeroDirections.UP)
            position.add(1 * deltaTime, velocity.x * deltaTime);

        bounds.x = position.x - TORNADO_WIDTH / 2;
        bounds.y = position.y + TORNADO_HEIGHT / 4;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public float getDamageValue() {
        return damageValue;
    }
}
