package com.courter.pandasurvivor;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Coins extends GameObject {
    public static final float HERO_WALKING_BOUNDS_COINS_HEIGHT = 90;
    public static final float HERO_WALKING_BOUNDS_COINS_WIDTH = 90;
    public static final int HIGHEST_MONEY_VALUE = 100;
    private Sprite sprite;
    private float stateTime;
    private int moneyValue;
    private boolean visible;

    public Coins(float x, float y, Sprite sprite) {
        super(x - HERO_WALKING_BOUNDS_COINS_WIDTH / 2, y, HERO_WALKING_BOUNDS_COINS_WIDTH, HERO_WALKING_BOUNDS_COINS_HEIGHT);
        this.sprite = sprite;
        this.stateTime = 0;
        float randomValue = (float) Math.random() * HIGHEST_MONEY_VALUE;
        this.moneyValue = (int) randomValue;
        this.visible = true;
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

    public int getMoneyValue() {
        return moneyValue;
    }

    public void setMoneyValue(int moneyValue) {
        this.moneyValue = moneyValue;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
}
