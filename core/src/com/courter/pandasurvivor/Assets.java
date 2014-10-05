package com.courter.pandasurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
    public static Texture dpad;
    public static Texture aButton;
    public static Texture bagButton;
    public static Texture pandaSpriteSheet;
    public static Texture ninjaSpriteSheet;
    public static Texture fireball;
    public static Texture wall;
    public static Texture healthBar;
    public static Texture coins;
    public static Texture retryPrompt;
    public static Texture pumpkinBossSpriteSheet;
    public static Texture bossKey;

    public static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load() {
        dpad = loadTexture("dpad.png");
        aButton = loadTexture("a-button.png");
        pandaSpriteSheet = loadTexture("panda3.png");
        ninjaSpriteSheet = loadTexture("ninjas.png");
        fireball = loadTexture("fireball.png");
        wall = loadTexture("wall.png");
        healthBar = loadTexture("healthbar.png");
        coins = loadTexture("gold_coins.png");
        bossKey = loadTexture("boss_key.png");
        retryPrompt = loadTexture("tryagain.png");
        pumpkinBossSpriteSheet = loadTexture("pumpkin_boss.png");
        bagButton = loadTexture("bag_button.png");
    }
}