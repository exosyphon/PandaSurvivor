package com.courter.pandasurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by andrew on 10/5/14.
 */
public class BossKey extends Item {
    public BossKey(float x, float y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public float inventoryRenderX() {
        return (Gdx.graphics.getWidth() * .035f);
    }

    @Override
    public float inventoryRenderY() {
        return (Gdx.graphics.getHeight() * .059f);
    }
}
