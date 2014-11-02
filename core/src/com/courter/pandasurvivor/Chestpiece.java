package com.courter.pandasurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.text.DecimalFormat;

/**
 * Created by andrew on 10/5/14.
 */
public class Chestpiece extends Item {
    private int currentLevel;

    public Chestpiece(float x, float y, Sprite sprite, int currentLevel) {
        super(x, y, sprite);
        this.currentLevel = currentLevel;
        generateRandomAttributes();
        this.gearSlot = Hero.GearSlot.CHESTPIECE;
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
        float random = (float) Math.random() * (2.0f * currentLevel);
        magicBonus = Float.parseFloat(df.format(random));
        random = (float) Math.random() * (2.0f * currentLevel);
        physicalBonus = Float.parseFloat(df.format(random));
        random = (float) Math.random() * 2.0f;
        attackSpeedBonus = Float.parseFloat(df.format(random));
        random = (float) Math.random() * (2.0f * currentLevel);
        healthBonus = Float.parseFloat(df.format(random));
        random = (float) Math.random() * (2.0f * currentLevel);
        extraGoldBonus = Float.parseFloat(df.format(random));
        random = (float) Math.random() * (2.0f * currentLevel);
        armorBonus = Float.parseFloat(df.format(random));
        return;
    }
}
