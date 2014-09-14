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
    public static List<Tree> treeList;
    public static List<Wall> wallList;
    public static List<Enemy> enemyList;
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

    public void checkStaticObjectCollisions() {
        checkTreeCollisions();
        checkWallCollisions();
        checkEnemyCollisions();
    }

    private void checkGameOver() {
    }

    private void checkTreeCollisions() {
        for (int i = 0; i < treeList.size(); i++) {
            Tree tree = treeList.get(i);
            if (OverlapTester.overlapRectangles(tree.bounds, hero.bounds)) {
                if (hero.getCurrentDirection() == HeroDirections.RIGHT)
                    hero.position.x = tree.position.x - tree.WALKING_BOUNDS_TREE_WIDTH / 2 - 1;
                else if (hero.getCurrentDirection() == HeroDirections.LEFT) {
                    hero.position.x = tree.position.x + tree.WALKING_BOUNDS_TREE_WIDTH / 2 + 1;
                } else if (hero.getCurrentDirection() == HeroDirections.DOWN) {
                    hero.position.y = tree.position.y + tree.WALKING_BOUNDS_TREE_HEIGHT / 2 + 1;
                } else if (hero.getCurrentDirection() == HeroDirections.UP) {
                    hero.position.y = tree.position.y - tree.WALKING_BOUNDS_TREE_HEIGHT / 2 - 1;
                }
            }
        }
    }

    private void checkEnemyCollisions() {
        for (int i = 0; i < enemyList.size(); i++) {
            Enemy enemy = enemyList.get(i);
            if (OverlapTester.overlapRectangles(enemy.bounds, hero.bounds)) {
                if (hero.getCurrentDirection() == HeroDirections.RIGHT)
                    hero.position.x = enemy.position.x - enemy.WALKING_BOUNDS_ENEMY_WIDTH / 2 - 1;
                else if (hero.getCurrentDirection() == HeroDirections.LEFT) {
                    hero.position.x = enemy.position.x + enemy.WALKING_BOUNDS_ENEMY_WIDTH / 2 + 1;
                } else if (hero.getCurrentDirection() == HeroDirections.DOWN) {
                    hero.position.y = enemy.position.y + enemy.WALKING_BOUNDS_ENEMY_HEIGHT / 2 + 1;
                } else if (hero.getCurrentDirection() == HeroDirections.UP) {
                    hero.position.y = enemy.position.y - enemy.WALKING_BOUNDS_ENEMY_HEIGHT / 2 - 1;
                }
            }
        }
    }

    private void checkWallCollisions() {
        for (int i = 0; i < wallList.size(); i++) {
            Wall wall = wallList.get(i);

            if (OverlapTester.overlapRectangles(wall.bounds, hero.bounds)) {
                if (hero.getCurrentDirection() == HeroDirections.RIGHT)
                    hero.position.x = wall.position.x - wall.WALKING_BOUNDS_WALL_WIDTH / 2 - 1;
                else if (hero.getCurrentDirection() == HeroDirections.LEFT) {
                    hero.position.x = wall.position.x + wall.WALKING_BOUNDS_WALL_WIDTH / 2 + 1;
                } else if (hero.getCurrentDirection() == HeroDirections.DOWN) {
                    hero.position.y = wall.position.y + wall.WALKING_BOUNDS_WALL_HEIGHT / 2 + 1;
                } else if (hero.getCurrentDirection() == HeroDirections.UP) {
                    hero.position.y = wall.position.y - wall.WALKING_BOUNDS_WALL_HEIGHT / 2 - 1;
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
                        hero.position.y += World.HERO_MOVE_SPEED * deltaTime;
                        hero.update(deltaTime);
                        checkStaticObjectCollisions();
                        worldRenderer.updateCameraAndPandaSpritePositionsUp(tmpYPosition);
                    }
                    else if (fireball.fireballDirection == HeroDirections.DOWN) {
                        hero.position.y -= World.HERO_MOVE_SPEED * deltaTime;
                        hero.update(deltaTime);
                        checkStaticObjectCollisions();
                        worldRenderer.updateCameraAndPandaSpritePositionsDown(tmpYPosition);
                    }
                    else if (fireball.fireballDirection == HeroDirections.RIGHT) {
                        hero.position.x += World.HERO_MOVE_SPEED * deltaTime;
                        hero.update(deltaTime);
                        checkStaticObjectCollisions();
                        worldRenderer.updateCameraAndPandaSpritePositionsRight(tmpXPosition);
                    }
                    else if (fireball.fireballDirection == HeroDirections.LEFT) {
                        hero.position.x -= World.HERO_MOVE_SPEED * deltaTime;
                        hero.update(deltaTime);
                        checkStaticObjectCollisions();
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
        HeroDirections enemyDirection = enemyList.get(0).getCurrentDirection();
        for (int i = 0; i < enemyFireballList.size(); i++) {
            Fireball fireball = enemyFireballList.get(i);
            fireball.update(deltaTime, enemyDirection);
        }
    }

    private void createObjects() {
        worldObjectLists = new ArrayList<List>();

        fireballList = new ArrayList<Fireball>();
        enemyFireballList = new ArrayList<Fireball>();
        treeList = new ArrayList<Tree>();
        wallList = new ArrayList<Wall>();
        enemyList = new ArrayList<Enemy>();

        List<List> input = new ArrayList<List>();
        input.add(treeList);
        input.add(wallList);
        worldObjectLists = input;
    }
}
