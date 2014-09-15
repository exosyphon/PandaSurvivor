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
    public static List<Fireball> enemyFireballList;
    public static List<GameObject> treeList;
    public static List<GameObject> wallList;
    public static List<GameObject> enemyList;
    public static List<List> worldObjectLists;
    public static Hero hero;
    public final WorldListener listener;
    public final Random rand;
    OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
    WorldRenderer worldRenderer;

    public World(WorldListener listener, WorldRenderer worldRenderer) {
        this.listener = listener;
        this.worldRenderer = worldRenderer;
        this.tiledMapRenderer = worldRenderer.tiledMapRenderer;
        rand = new Random();
        generateLevel();
        createObjects();
    }

    private void generateLevel() {
    }

    public void update(float deltaTime) {
        updateFireballs(deltaTime);
        updateEnemyFireballs(deltaTime);
        checkCollisions(deltaTime);
        checkGameOver();
    }

    public void checkCollisions(float deltaTime) {
        checkEnemyFireballCollisions(deltaTime);
        checkFireballCollisions();
    }

    public void checkStaticObjectCollisions(HeroDirections direction) {
        checkTreeCollisions(direction);
        checkWallCollisions(direction);
        checkEnemyCollisions(direction);
    }

    private void checkGameOver() {
    }

    private void checkTreeCollisions(HeroDirections direction) {
        checkStaticCollisionsForArray(treeList, direction);
    }

    private void checkEnemyCollisions(HeroDirections direction) {
        checkStaticCollisionsForArray(enemyList, direction);
    }

    private void checkWallCollisions(HeroDirections direction) {
        checkStaticCollisionsForArray(wallList, direction);
    }

    private void checkStaticCollisionsForArray(List<GameObject> objectList, HeroDirections direction) {
        for (int i = 0; i < objectList.size(); i++) {
            GameObject gameObject = objectList.get(i);

            if (OverlapTester.overlapRectangles(gameObject.bounds, hero.bounds)) {
                if (direction == HeroDirections.RIGHT)
                    hero.position.x = gameObject.position.x - gameObject.bounds.getWidth() / 2 - 1;
                else if (direction == HeroDirections.LEFT) {
                    hero.position.x = gameObject.position.x + gameObject.bounds.getWidth() / 2 + 1;
                } else if (direction == HeroDirections.DOWN) {
                    hero.position.y = gameObject.position.y + gameObject.bounds.getHeight() / 2 + 1;
                } else if (direction == HeroDirections.UP) {
                    hero.position.y = gameObject.position.y - gameObject.bounds.getHeight() / 2 - 1;
                }
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
                break;
            } else {
                for (List<GameObject> list : worldObjectLists) {
                    for (int listIter = 0; listIter < list.size(); listIter++) {
                        GameObject gameObject = list.get(listIter);
                        if (OverlapTester.overlapRectangles(gameObject.shooting_bounds, fireball.bounds)) {
                            tiledMapRenderer.removeSprite(fireball.getSprite());
                            fireballList.remove(fireball);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void checkEnemyFireballCollisions(float deltaTime) {
        for (int i = 0; i < enemyFireballList.size(); i++) {
            Fireball fireball = enemyFireballList.get(i);
            if (fireball.position.x < PandaSurvivor.LEFT_SIDE_OF_MAP ||
                    fireball.position.x > (PandaSurvivor.RIGHT_SIDE_OF_MAP + 64) ||
                    fireball.position.y < PandaSurvivor.BOTTOM_OF_MAP ||
                    fireball.position.y > (PandaSurvivor.TOP_OF_MAP + 64) ||
                    fireball.stateTime > Fireball.FIREBALL_DISTANCE) {
                tiledMapRenderer.removeSprite(fireball.getSprite());
                enemyFireballList.remove(fireball);
                break;
            } else {
                for (List<GameObject> list : worldObjectLists) {
                    for (int listIter = 0; listIter < list.size(); listIter++) {
                        GameObject gameObject = list.get(listIter);
                        if (OverlapTester.overlapRectangles(gameObject.shooting_bounds, fireball.bounds)) {
                            tiledMapRenderer.removeSprite(fireball.getSprite());
                            enemyFireballList.remove(fireball);
                            break;
                        }
                    }
                }

                if (OverlapTester.overlapRectangles(hero.shooting_bounds, fireball.bounds)) {
                    float tmpYPosition = hero.position.y;
                    float tmpXPosition = hero.position.x;

                    if (fireball.fireballDirection == HeroDirections.UP) {
                        hero.position.y += (HERO_MOVE_SPEED * deltaTime) * 2;
                        hero.update(deltaTime);
                        checkStaticObjectCollisions(HeroDirections.UP);
                        worldRenderer.updatePandaHitSpriteTexture(hero.getCurrentDirection());
                        worldRenderer.updateCameraAndPandaSpritePositionsUp(tmpYPosition);
                    } else if (fireball.fireballDirection == HeroDirections.DOWN) {
                        hero.position.y -= (HERO_MOVE_SPEED * deltaTime) * 2;
                        hero.update(deltaTime);
                        checkStaticObjectCollisions(HeroDirections.DOWN);
                        worldRenderer.updatePandaHitSpriteTexture(hero.getCurrentDirection());
                        worldRenderer.updateCameraAndPandaSpritePositionsDown(tmpYPosition);
                    } else if (fireball.fireballDirection == HeroDirections.RIGHT) {
                        hero.position.x += (HERO_MOVE_SPEED * deltaTime) * 2;
                        hero.update(deltaTime);
                        checkStaticObjectCollisions(HeroDirections.RIGHT);
                        worldRenderer.updatePandaHitSpriteTexture(hero.getCurrentDirection());
                        worldRenderer.updateCameraAndPandaSpritePositionsRight(tmpXPosition);
                    } else if (fireball.fireballDirection == HeroDirections.LEFT) {
                        hero.position.x -= (HERO_MOVE_SPEED * deltaTime) * 2;
                        hero.update(deltaTime);
                        checkStaticObjectCollisions(HeroDirections.LEFT);
                        worldRenderer.updatePandaHitSpriteTexture(hero.getCurrentDirection());
                        worldRenderer.updateCameraAndPandaSpritePositionsLeft(tmpXPosition);
                    }
                    tiledMapRenderer.removeSprite(fireball.getSprite());
                    enemyFireballList.remove(fireball);
                    break;
                }
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

    private void updateEnemyFireballs(float deltaTime) {
        HeroDirections enemyDirection = ((Enemy) enemyList.get(0)).getCurrentDirection();
        for (int i = 0; i < enemyFireballList.size(); i++) {
            Fireball fireball = enemyFireballList.get(i);
            fireball.update(deltaTime, enemyDirection);
        }
    }

    private void createObjects() {
        worldObjectLists = new ArrayList<List>();

        fireballList = new ArrayList<Fireball>();
        enemyFireballList = new ArrayList<Fireball>();
        treeList = new ArrayList<GameObject>();
        wallList = new ArrayList<GameObject>();
        enemyList = new ArrayList<GameObject>();

        List<List> input = new ArrayList<List>();
        input.add(treeList);
        input.add(wallList);
        worldObjectLists = input;
    }
}
