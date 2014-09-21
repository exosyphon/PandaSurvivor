package com.courter.pandasurvivor;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
    public final Vector2 position;
    public final Rectangle bounds;
    public Rectangle shooting_bounds;
    public Rectangle enemy_walking_bounds;

    public GameObject(float x, float y, float width, float height) {
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
    }

    public Rectangle createBoundsRectangle(float x, float y, float width, float height) {
        return new Rectangle(x - width / 2, y - height / 2, width, height);
    }

    public void updateBounds() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameObject that = (GameObject) o;

        if (bounds != null ? !bounds.equals(that.bounds) : that.bounds != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null)
            return false;
        if (shooting_bounds != null ? !shooting_bounds.equals(that.shooting_bounds) : that.shooting_bounds != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = position != null ? position.hashCode() : 0;
        result = 31 * result + (bounds != null ? bounds.hashCode() : 0);
        result = 31 * result + (shooting_bounds != null ? shooting_bounds.hashCode() : 0);
        return result;
    }
}
