package com.courter.pandasurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.text.DecimalFormat;

/**
 * Created by andrew on 10/5/14.
 */
public class Bracers extends Item {

    public Bracers(float x, float y, Sprite sprite) {
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
        DecimalFormat df = new DecimalFormat("0.0");
        float random = (float) Math.random() * 2.0f;
        magicBonus = Float.parseFloat(df.format(random));
        random = (float) Math.random() * 2.0f;
        meleeBonus = Float.parseFloat(df.format(random));
        random = (float) Math.random() * 2.0f;
        attackSpeedBonus = Float.parseFloat(df.format(random));
        random = (float) Math.random() * 2.0f;
        healthBonus = Float.parseFloat(df.format(random));
        random = (float) Math.random() * 2.0f;
        extraGoldBonus = Float.parseFloat(df.format(random));
        random = (float) Math.random() * 2.0f;
        armorBonus = Float.parseFloat(df.format(random));
        return;
    }
}
