package com.courter.pandasurvivor;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Tree extends GameObject {
    public static final float TREE_HEIGHT = .8f;
    public static final float TREE_WIDTH = .8f;
    Sprite sprite;

    public Tree(float x, float y, float width, float height, Sprite sprite) {
        super(x, y, TREE_WIDTH, TREE_HEIGHT);
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return this.sprite;
    }
}
