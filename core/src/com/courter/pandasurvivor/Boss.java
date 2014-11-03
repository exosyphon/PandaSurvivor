package com.courter.pandasurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by andrew on 9/28/14.
 */
public class Boss extends Enemy {
    public static final float WALKING_BOUNDS_BOSS_HEIGHT = (Gdx.graphics.getHeight() * .129f);
    public static final float WALKING_BOUNDS_BOSS_WIDTH = (Gdx.graphics.getWidth() * .089f);

    public static final float SHOOTING_BOUNDS_BOSS_HEIGHT = (Gdx.graphics.getHeight() * .106f);
    public static final float SHOOTING_BOUNDS_BOSS_WIDTH = (Gdx.graphics.getWidth() * .072f);

    public static final float FROZEN_TIME = 3;

    private World.BossTypes bossType;

    public Boss(float x, float y, Sprite sprite, World.BossTypes bossType, int currentLevel) {
        super(
                x - WALKING_BOUNDS_BOSS_WIDTH - (Gdx.graphics.getWidth() * .016f),
                y - WALKING_BOUNDS_BOSS_HEIGHT - (Gdx.graphics.getHeight() * .041f),
                sprite,
                WALKING_BOUNDS_BOSS_WIDTH,
                WALKING_BOUNDS_BOSS_HEIGHT,
                SHOOTING_BOUNDS_BOSS_HEIGHT,
                SHOOTING_BOUNDS_BOSS_WIDTH,
                300,
                150,
                10,
                currentLevel
        );
        this.shooting_bounds = createBoundsRectangle(x - SHOOTING_BOUNDS_BOSS_WIDTH - (Gdx.graphics.getWidth() * .011f), y - SHOOTING_BOUNDS_BOSS_HEIGHT - (Gdx.graphics.getHeight() * .037f), SHOOTING_BOUNDS_BOSS_WIDTH, SHOOTING_BOUNDS_BOSS_HEIGHT);
        this.bossType = bossType;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        frozenStateTime += deltaTime;
        if(frozenStateTime > FROZEN_TIME) {
            this.canMove = true;
        }
        updateBounds();
    }

    @Override
    public void updateBounds() {
        bounds.x = position.x - WALKING_BOUNDS_BOSS_WIDTH / 2;
        bounds.y = position.y - WALKING_BOUNDS_BOSS_HEIGHT / 2;

        shooting_bounds.x = position.x;
        shooting_bounds.y = position.y - SHOOTING_BOUNDS_BOSS_HEIGHT / 2;

        if (frozenSprite != null) {
            frozenSprite.setPosition(position.x + 32, position.y - 32);
        }
    }

    public World.BossTypes getBossType() {
        return bossType;
    }

    public void setBossType(World.BossTypes bossType) {
        this.bossType = bossType;
    }
}
