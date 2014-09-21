package com.courter.pandasurvivor;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Tree extends GameObject {
    public static final float HERO_WALKING_BOUNDS_TREE_HEIGHT = 70;
    public static final float HERO_WALKING_BOUNDS_TREE_WIDTH = 74;

    public static final float SHOOTING_BOUNDS_TREE_HEIGHT = 26;
    public static final float SHOOTING_BOUNDS_TREE_WIDTH = 30;
    Sprite sprite;

    public Tree(float x, float y, Sprite sprite) {
        super(convertTreeWalkingBoundsX(x), convertTreeWalkingBoundsY(y), HERO_WALKING_BOUNDS_TREE_WIDTH, HERO_WALKING_BOUNDS_TREE_HEIGHT);
        this.shooting_bounds = createBoundsRectangle(x - (SHOOTING_BOUNDS_TREE_WIDTH + 6), y - SHOOTING_BOUNDS_TREE_HEIGHT, SHOOTING_BOUNDS_TREE_WIDTH, SHOOTING_BOUNDS_TREE_HEIGHT);
        this.sprite = sprite;
    }

    public static float convertTreeWalkingBoundsX(float x) {
        return x - HERO_WALKING_BOUNDS_TREE_WIDTH + 8;
    }

    public static float convertTreeWalkingBoundsY(float y) {
        return y - HERO_WALKING_BOUNDS_TREE_HEIGHT/2;
    }
}
