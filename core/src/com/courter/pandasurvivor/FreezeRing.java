package com.courter.pandasurvivor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by andrew on 11/2/14.
 */
public class FreezeRing extends GameObject {
    public static final float FREEZE_RING_WIDTH = 1f;
    public static final float FREEZE_RING_HEIGHT = .5f;
    public static final float FREEZE_RING_TOTAL_EXPAND_TIME = 2.5f;

    float stateTime = 0;
    Sprite sprite;

    public FreezeRing(float x, float y, Sprite sprite) {
        super(x, y, FREEZE_RING_WIDTH, FREEZE_RING_HEIGHT);
        this.sprite = sprite;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime * 2;

        Sprite tmpSprite = this.sprite;
        float expandingPositionX = (position.x - (tmpSprite.getRegionWidth() * stateTime) / 2);
        float expandingPositionY = (position.y - (tmpSprite.getRegionHeight() * stateTime) / 2);

        this.bounds = new Rectangle(expandingPositionX, expandingPositionY, (tmpSprite.getRegionWidth() * stateTime), (tmpSprite.getRegionHeight() * stateTime));

        this.sprite.setPosition(expandingPositionX, expandingPositionY);
        this.sprite.setSize((tmpSprite.getRegionWidth() * stateTime), (tmpSprite.getRegionHeight() * stateTime));
    }

    public Sprite getSprite() {
        return this.sprite;
    }
}
