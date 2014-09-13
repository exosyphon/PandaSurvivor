package com.courter.pandasurvivor;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Tree extends GameObject {
    public static final float TREE_HEIGHT = 70;
    public static final float TREE_WIDTH = 70;
    Sprite sprite;

    public Tree(float x, float y, Sprite sprite) {
        super(x - TREE_WIDTH + 5, y - (TREE_HEIGHT/2), TREE_WIDTH, TREE_HEIGHT);
        this.sprite = sprite;
    }
}
