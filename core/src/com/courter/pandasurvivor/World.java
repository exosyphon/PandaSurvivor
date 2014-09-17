package com.courter.pandasurvivor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
    public static Set<GameObject> treeList;
    public static Set<GameObject> wallList;
    public static List<GameObject> enemyList;
    public static Set<Set> worldObjectLists;
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
        updateEnemies(deltaTime);
        checkCollisions(deltaTime);
        checkGameOver();
    }

    public void checkCollisions(float deltaTime) {
        checkEnemyFireballCollisions(deltaTime);
        checkFireballCollisions(deltaTime);
    }

    public void checkStaticObjectCollisionsFor(GameObject gameObject, HeroDirections direction, float boundsOffset) {
        checkTreeCollisions(gameObject, direction, boundsOffset);
        checkWallCollisions(gameObject, direction, boundsOffset);
        checkEnemyCollisions(direction);
    }

    private void updateEnemies(float deltaTime) {
        if (enemyList.size() > 0)
            ((Enemy) enemyList.get(0)).update(deltaTime);
    }

    private void checkGameOver() {
        if (hero.health <= 0) {
            fireballList.clear();
            enemyFireballList.clear();
            PandaSurvivor.game_state = PandaSurvivor.GAME_STATES.GAME_OVER;
        }
    }

    private void checkTreeCollisions(GameObject gameObject, HeroDirections direction, float boundsOffset) {
        checkStaticCollisionsForSet(treeList, gameObject, direction, boundsOffset);
    }

    private void checkEnemyCollisions(HeroDirections direction) {
        checkStaticCollisionsForArray(enemyList, hero, direction, 1);
    }

    private void checkWallCollisions(GameObject gameObject, HeroDirections direction, float boundsOffset) {
        checkStaticCollisionsForSet(wallList, gameObject, direction, boundsOffset);
    }

    private void checkStaticCollisionsForArray(List<GameObject> objectList, GameObject gameObject, HeroDirections direction, float boundsOffset) {
        for (int i = 0; i < objectList.size(); i++) {
            GameObject existingWorldGameObject = objectList.get(i);

            if (OverlapTester.overlapRectangles(existingWorldGameObject.bounds, gameObject.bounds)) {
                if (direction == HeroDirections.RIGHT)
                    gameObject.position.x = existingWorldGameObject.position.x - existingWorldGameObject.bounds.getWidth() / 2 - boundsOffset;
                else if (direction == HeroDirections.LEFT) {
                    gameObject.position.x = existingWorldGameObject.position.x + existingWorldGameObject.bounds.getWidth() / 2 + boundsOffset;
                } else if (direction == HeroDirections.DOWN) {
                    gameObject.position.y = existingWorldGameObject.position.y + existingWorldGameObject.bounds.getHeight() / 2 + boundsOffset;
                } else if (direction == HeroDirections.UP) {
                    gameObject.position.y = existingWorldGameObject.position.y - existingWorldGameObject.bounds.getHeight() / 2 - boundsOffset;
                }
            }
        }
    }

    private void checkStaticCollisionsForSet(Set<GameObject> objectSet, GameObject gameObject, HeroDirections direction, float boundsOffset) {
        for (GameObject existingWorldGameObject : objectSet) {
            if (OverlapTester.overlapRectangles(existingWorldGameObject.bounds, gameObject.bounds)) {
                if (direction == HeroDirections.RIGHT)
                    gameObject.position.x = existingWorldGameObject.position.x - existingWorldGameObject.bounds.getWidth() / 2 - boundsOffset;
                else if (direction == HeroDirections.LEFT) {
                    gameObject.position.x = existingWorldGameObject.position.x + existingWorldGameObject.bounds.getWidth() / 2 + boundsOffset;
                } else if (direction == HeroDirections.DOWN) {
                    gameObject.position.y = existingWorldGameObject.position.y + existingWorldGameObject.bounds.getHeight() / 2 + boundsOffset;
                } else if (direction == HeroDirections.UP) {
                    gameObject.position.y = existingWorldGameObject.position.y - existingWorldGameObject.bounds.getHeight() / 2 - boundsOffset;
                }
            }
        }
    }

    private void checkFireballCollisions(float deltaTime) {
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
                for (Set<GameObject> set : worldObjectLists) {
                    for (GameObject gameObject : set) {
                        if (OverlapTester.overlapRectangles(gameObject.shooting_bounds, fireball.bounds)) {
                            tiledMapRenderer.removeSprite(fireball.getSprite());
                            fireballList.remove(fireball);
                            break;
                        }
                    }
                }

                if (fireball != null && enemyList.size() > 0) {
                    Enemy enemy = ((Enemy) enemyList.get(0));
                    if (OverlapTester.overlapRectangles(enemy.shooting_bounds, fireball.bounds)) {
                        enemy.health -= fireball.getDamageValue();

                        if (enemy.health <= 0) {
                            tiledMapRenderer.removeSprite(enemy.getSprite());
                            enemyList.remove(enemy);
                            break;
                        }

                        if (fireball.fireballDirection == HeroDirections.UP) {
                            enemy.position.y += (HERO_MOVE_SPEED * deltaTime);
                            enemy.update(deltaTime);
                            enemy.getSprite().setPosition(enemy.position.x + Enemy.WALKING_BOUNDS_ENEMY_WIDTH / 6, enemy.position.y - Enemy.WALKING_BOUNDS_ENEMY_HEIGHT / 6);

                            checkStaticObjectCollisionsFor(enemy, HeroDirections.UP, 55);
//                        worldRenderer.updatePandaHitSpriteTexture(enemy.getCurrentDirection());
                        } else if (fireball.fireballDirection == HeroDirections.DOWN) {
                            enemy.position.y -= (HERO_MOVE_SPEED * deltaTime);
                            enemy.update(deltaTime);
                            enemy.getSprite().setPosition(enemy.position.x + Enemy.WALKING_BOUNDS_ENEMY_WIDTH / 6, enemy.position.y - Enemy.WALKING_BOUNDS_ENEMY_HEIGHT / 6);

                            checkStaticObjectCollisionsFor(enemy, HeroDirections.DOWN, 55);
//                        worldRenderer.updatePandaHitSpriteTexture(enemy.getCurrentDirection());
                        } else if (fireball.fireballDirection == HeroDirections.RIGHT) {
                            enemy.position.x += (HERO_MOVE_SPEED * deltaTime);
                            enemy.update(deltaTime);
                            enemy.getSprite().setPosition(enemy.position.x + Enemy.WALKING_BOUNDS_ENEMY_WIDTH / 6, enemy.position.y - Enemy.WALKING_BOUNDS_ENEMY_HEIGHT / 6);

                            checkStaticObjectCollisionsFor(enemy, HeroDirections.RIGHT, 55);
//                        worldRenderer.updatePandaHitSpriteTexture(enemy.getCurrentDirection());
                        } else if (fireball.fireballDirection == HeroDirections.LEFT) {
                            enemy.position.x -= (HERO_MOVE_SPEED * deltaTime);
                            enemy.update(deltaTime);
                            enemy.getSprite().setPosition(enemy.position.x + Enemy.WALKING_BOUNDS_ENEMY_WIDTH / 6, enemy.position.y - Enemy.WALKING_BOUNDS_ENEMY_HEIGHT / 6);

                            checkStaticObjectCollisionsFor(enemy, HeroDirections.LEFT, 55);
//                        worldRenderer.updatePandaHitSpriteTexture(enemy.getCurrentDirection());
                        }

                        tiledMapRenderer.removeSprite(fireball.getSprite());
                        fireballList.remove(fireball);
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
                for (Set<GameObject> set : worldObjectLists) {
                    for (GameObject gameObject : set) {
                        if (OverlapTester.overlapRectangles(gameObject.shooting_bounds, fireball.bounds)) {
                            tiledMapRenderer.removeSprite(fireball.getSprite());
                            enemyFireballList.remove(fireball);
                            break;
                        }
                    }
                }

                if (OverlapTester.overlapRectangles(hero.shooting_bounds, fireball.bounds)) {
                    float tmpXPosition = hero.position.x;
                    float tmpYPosition = hero.position.y;

                    hero.health -= fireball.getDamageValue();

                    if (fireball.fireballDirection == HeroDirections.UP) {
                        hero.position.y += (HERO_MOVE_SPEED * deltaTime) * 2;
                        hero.update(deltaTime);
                        checkStaticObjectCollisionsFor(hero, HeroDirections.UP, 1);
                        worldRenderer.updatePandaHitSpriteTexture(hero.getCurrentDirection());
                        worldRenderer.updateCameraAndPandaSpritePositionsUp(tmpYPosition);
                    } else if (fireball.fireballDirection == HeroDirections.DOWN) {
                        hero.position.y -= (HERO_MOVE_SPEED * deltaTime) * 2;
                        hero.update(deltaTime);
                        checkStaticObjectCollisionsFor(hero, HeroDirections.DOWN, 1);
                        worldRenderer.updatePandaHitSpriteTexture(hero.getCurrentDirection());
                        worldRenderer.updateCameraAndPandaSpritePositionsDown(tmpYPosition);
                    } else if (fireball.fireballDirection == HeroDirections.RIGHT) {
                        hero.position.x += (HERO_MOVE_SPEED * deltaTime) * 2;
                        hero.update(deltaTime);
                        checkStaticObjectCollisionsFor(hero, HeroDirections.RIGHT, 1);
                        worldRenderer.updatePandaHitSpriteTexture(hero.getCurrentDirection());
                        worldRenderer.updateCameraAndPandaSpritePositionsRight(tmpXPosition);
                    } else if (fireball.fireballDirection == HeroDirections.LEFT) {
                        hero.position.x -= (HERO_MOVE_SPEED * deltaTime) * 2;
                        hero.update(deltaTime);
                        checkStaticObjectCollisionsFor(hero, HeroDirections.LEFT, 1);
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
        for (int i = 0; i < fireballList.size(); i++) {
            Fireball fireball = fireballList.get(i);
            fireball.update(deltaTime);
        }
    }

    private void updateEnemyFireballs(float deltaTime) {
        for (int i = 0; i < enemyFireballList.size(); i++) {
            Fireball fireball = enemyFireballList.get(i);
            fireball.update(deltaTime);
        }
    }

    private void createObjects() {
        worldObjectLists = new HashSet<Set>();

        fireballList = new ArrayList<Fireball>();
        enemyFireballList = new ArrayList<Fireball>();
        treeList = new HashSet<GameObject>();
        wallList = new HashSet<GameObject>();
        enemyList = new ArrayList<GameObject>();

        Set<Set> input = new HashSet<Set>();
        input.add(treeList);
        input.add(wallList);
        worldObjectLists = input;
    }
}
