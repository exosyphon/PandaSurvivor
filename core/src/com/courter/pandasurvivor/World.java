package com.courter.pandasurvivor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    enum HeroDirections {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public interface WorldListener {
        public void hit();

        public void kill();

        public void powerup();
    }

    public static final int HERO_MOVE_SPEED = 520;
    public static final String PANDA_SNOW_MAP_NAME = "panda_snow.tmx";
    public static List<Fireball> fireballList;
    public static List<Tree> treeList;
    public static Hero hero;
    public final WorldListener listener;
    public final Random rand;
    OrthogonalTiledMapRendererWithSprites tiledMapRenderer;

    public World(WorldListener listener, OrthogonalTiledMapRendererWithSprites tiledMapRenderer) {
        this.listener = listener;
        this.tiledMapRenderer = tiledMapRenderer;
        rand = new Random();
        generateLevel();
        createObjects();
    }

    private void generateLevel() {
    }

    public void update(float deltaTime) {
        hero.update(deltaTime);
        updateFireballs(deltaTime);
        checkCollisions();
        checkGameOver();
    }

    private void checkGameOver() {
    }

    private void checkCollisions() {
        checkFireballCollisions();
    }

    public void checkTreeCollisions() {
        for (int i = 0; i < treeList.size(); i++) {
            Tree tree = treeList.get(i);
            if (OverlapTester.overlapRectanglesX(tree.bounds, hero.bounds)) {
                System.out.println("help me on x");
                if (hero.getCurrentDirection() == HeroDirections.RIGHT)
                    hero.position.x = tree.position.x + 10;
                else
                    hero.position.x = tree.position.x - 10;
            }
            if (OverlapTester.overlapRectanglesY(tree.bounds, hero.bounds)) {
                System.out.println("help me on yyyyyyy");
                if (hero.getCurrentDirection() == HeroDirections.DOWN)
                    hero.position.y = tree.position.y + 10;
                else
                    hero.position.y = tree.position.y - 10;
            }
        }
    }

    private void checkFireballCollisions() {
        for (int i = 0; i < fireballList.size(); i++) {
            Fireball fireball = fireballList.get(i);
            if (fireball.position.x < PandaSurvivor.LEFT_SIDE_OF_MAP ||
                    fireball.position.x > (PandaSurvivor.RIGHT_SIDE_OF_MAP + 64) ||
                    fireball.position.y < PandaSurvivor.BOTTOM_OF_MAP ||
                    fireball.position.y > (PandaSurvivor.TOP_OF_MAP + 64) ||
                    fireball.stateTime > Fireball.FIREBALL_DISTANCE) {
                tiledMapRenderer.removeSprite(fireball.getSprite());
                fireballList.remove(fireball);
            }
        }
    }

    private void updateFireballs(float deltaTime) {
        HeroDirections heroDirection = hero.getCurrentDirection();
        for (int i = 0; i < fireballList.size(); i++) {
            Fireball fireball = fireballList.get(i);
            fireball.update(deltaTime, heroDirection);
        }
    }

    private void createObjects() {
        fireballList = new ArrayList<Fireball>();
        treeList = new ArrayList<Tree>();
    }
}
