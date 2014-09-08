package com.courter.pandasurvivor;

/**
 * Created by andrew on 9/7/14.
 */
public class Hero extends GameObject{
        public static final float HERO_HEIGHT = .8f;
        public static final float HERO_WIDTH = .8f;

        float stateTime = 0;

        public Hero(float x, float y) {
            super(x, y, HERO_WIDTH, HERO_HEIGHT);
        }

        public void update(float deltaTime)
        {
            stateTime += deltaTime;
        }
}
