package com.courter.pandasurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Item extends GameObject {
    public static final float HERO_WALKING_BOUNDS_COINS_HEIGHT = (Gdx.graphics.getHeight() * .083f);
    public static final float HERO_WALKING_BOUNDS_COINS_WIDTH = (Gdx.graphics.getWidth() * .05f);
    public static final int HIGHEST_MONEY_VALUE = 30;
    private Sprite sprite;
    private float stateTime;
    private int sellValue;
    private boolean visible;
    private World.ItemActions itemAction;

    protected float magicBonus;
    protected float physicalBonus;
    protected float attackSpeedBonus;
    protected float healthBonus;
    protected float extraGoldBonus;
    protected float armorBonus;
    protected Hero.GearSlot gearSlot;

    public Item(float x, float y, Sprite sprite) {
        super(x - HERO_WALKING_BOUNDS_COINS_WIDTH / 2, y, HERO_WALKING_BOUNDS_COINS_WIDTH, HERO_WALKING_BOUNDS_COINS_HEIGHT);
        this.sprite = sprite;
        this.stateTime = 0;
        float randomValue = (float) Math.random() * HIGHEST_MONEY_VALUE;
        this.sellValue = (int) randomValue;
        this.visible = true;
        magicBonus = 0;
        physicalBonus = 0;
        attackSpeedBonus = 0;
        healthBonus = 0;
        extraGoldBonus = 0;
        armorBonus = 0;
        gearSlot = null;
    }

    public Hero.GearSlot getGearSlot() {
        return this.gearSlot;
    }

    public float inventoryRenderX() {
        return 0;
    }

    public float inventoryRenderY() {
        return 0;
    }

    public void update(float deltaTime) {
        this.stateTime += deltaTime;
    }

    public void toggleVisible() {
        this.visible = !this.visible;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public int getSellValue() {
        return sellValue;
    }

    public void setSellValue(int sellValue) {
        this.sellValue = sellValue;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public World.ItemActions getItemAction() {
        return itemAction;
    }

    public void setItemAction(World.ItemActions itemAction) {
        this.itemAction = itemAction;
    }

    public float getMagicBonus() {
        return magicBonus;
    }

    public void setMagicBonus(float magicBonus) {
        this.magicBonus = magicBonus;
    }

    public float getPhysicalBonus() {
        return physicalBonus;
    }

    public void setPhysicalBonus(float physicalBonus) {
        this.physicalBonus = physicalBonus;
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
