package com.courter.pandasurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by andrew on 10/5/14.
 */
public class Boots extends Item {
    private float magicBonus;
    private float meleeBonus;
    private float attackSpeedBonus;
    private float healthBonus;
    private float extraGoldBonus;
    private float armorBonus;

    public Boots(float x, float y, Sprite sprite) {
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
        //TODO
        return;
    }

    public float getMagicBonus() {
        return magicBonus;
    }

    public void setMagicBonus(float magicBonus) {
        this.magicBonus = magicBonus;
    }

    public float getMeleeBonus() {
        return meleeBonus;
    }

    public void setMeleeBonus(float meleeBonus) {
        this.meleeBonus = meleeBonus;
    }

    public float getAttackSpeedBonus() {
        return attackSpeedBonus;
    }

    public void setAttackSpeedBonus(float attackSpeedBonus) {
        this.attackSpeedBonus = attackSpeedBonus;
    }

    public float getHealthBonus() {
        return healthBonus;
    }

    public void setHealthBonus(float healthBonus) {
        this.healthBonus = healthBonus;
    }

    public float getExtraGoldBonus() {
        return extraGoldBonus;
    }

    public void setExtraGoldBonus(float extraGoldBonus) {
        this.extraGoldBonus = extraGoldBonus;
    }

    public float getArmorBonus() {
        return armorBonus;
    }

    public void setArmorBonus(float armorBonus) {
        this.armorBonus = armorBonus;
    }
}
