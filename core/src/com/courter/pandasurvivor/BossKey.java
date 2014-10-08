package com.courter.pandasurvivor;

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
        return 64;
    }

    @Override
    public float inventoryRenderY() {
        return 64;
    }
}
