package com.courter.pandasurvivor;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by andrew on 9/8/14.
 */
public class Fireball extends DynamicGameObject{
    public static final float FIREBALL_WIDTH = 1f;
    public static final float FIREBALL_HEIGHT = .5f;
    public static final int FIREBALL_SPEED = 725;
    public static final float FIREBALL_DISTANCE = 1;

    float stateTime = 0;
    Sprite sprite;

    PandaSurvivor.HeroDirections fireballDirection = null;

    public Fireball(float x, float y, Sprite sprite) {
        super(x, y, FIREBALL_WIDTH, FIREBALL_HEIGHT);
        velocity.set(FIREBALL_SPEED, 0);
        this.sprite = sprite;
    }

    public void update(float deltaTime, PandaSurvivor.HeroDirections heroDirection)
    {
        if(fireballDirection == null)
            this.fireballDirection = heroDirection;

        if(fireballDirection == PandaSurvivor.HeroDirections.RIGHT)
            position.add(velocity.x * deltaTime, 1 * deltaTime);
        else if(fireballDirection == PandaSurvivor.HeroDirections.LEFT)
            position.add(-velocity.x * deltaTime, 1 * deltaTime);
        else if(fireballDirection == PandaSurvivor.HeroDirections.DOWN)
            position.add(1 * deltaTime, -velocity.x * deltaTime);
        else if(fireballDirection == PandaSurvivor.HeroDirections.UP)
            position.add(1 * deltaTime, velocity.x * deltaTime);

        bounds.x = position.x - FIREBALL_WIDTH / 2;
        bounds.y = position.y - FIREBALL_HEIGHT / 2;

        stateTime += deltaTime;
    }

    public Sprite getSprite() {
        return this.sprite;
    }
}
