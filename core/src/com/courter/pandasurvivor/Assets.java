package com.courter.pandasurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
    public static Texture dpad;
    public static Texture aButton;
    public static Texture pandaSpriteSheet;
    public static Texture fireball;
    public static Texture wall;
    public static Texture enemy;

    public static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load() {
        dpad = loadTexture("dpad.png");
        aButton = loadTexture("a-button.png");
        pandaSpriteSheet = loadTexture("panda3.png");
        fireball = loadTexture("fireball.png");
        wall = loadTexture("wall.png");
        enemy = loadTexture("pik.png");
    }
}