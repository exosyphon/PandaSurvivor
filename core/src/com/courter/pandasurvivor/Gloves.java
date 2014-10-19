package com.courter.pandasurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by andrew on 10/5/14.
 */
public class Gloves extends Item {
    public Gloves(float x, float y, Sprite sprite) {
        super(x, y, sprite);
        generateRandomAttributes();
    }

    @Override
    public float inventoryRenderX() {
        return (Gdx.graphics.getWidth() * .053f);
    }

    @Override
    public float inventoryRenderY() {
        return (Gdx.graphics.getHeight() * .088f);
    }

    private void generateRandomAttributes() {
        float random = (float) Math.random() * 100;
        if(random < 50) {
            magicBonus = 2;
        }
        random = (float) Math.random() * 100;
        if(random < 50) {
            meleeBonus = 2;
        }
        random = (float) Math.random() * 100;
        if(random < 50) {
            attackSpeedBonus = 2;
        }
        random = (float) Math.random() * 100;
        if(random < 50) {
            healthBonus = 2;
        }
        random = (float) Math.random() * 100;
        if(random < 50) {
            extraGoldBonus = 2;
        }
        random = (float) Math.random() * 100;
        if(random < 50) {
            armorBonus = 2;
        }
        return;
    }
}
