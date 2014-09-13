package com.courter.pandasurvivor;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
    public final Vector2 position;
    public final Rectangle bounds;
    public Rectangle shooting_bounds;

    public GameObject(float x, float y, float width, float height) {
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
    }

    public Rectangle createBoundsRectangle(float x, float y, float width, float height) {
        return new Rectangle(x - width / 2, y - height / 2, width, height);
    }
}
