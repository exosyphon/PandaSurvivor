package com.courter.pandasurvivor;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class World {
    enum HeroDirections {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    enum NinjaTypes {
        RED, PURPLE, BLACK
    }

    public interface WorldListener {
        public void hit();

        public void kill();

        public void powerup();
    }

    public static final int LAST_TIME_DAMAGED_BUFFER = 500;
    public static final int HERO_MOVE_SPEED = 520;
    public static final int ENEMY_MOVE_SPEED = 60;
    public static final String PANDA_SNOW_MAP_NAME = "panda_snow.tmx";
    public static final float timeBeforeRegeneration = 25;
    static final int numberOfRedNinjaEnemies = 10;
    static final int numberOfBlackNinjaEnemies = 15;
    static final int numberOfPurpleNinjaEnemies = 15;
    public static List<Fireball> fireballList;
    public static List<Fireball> enemyFireballList;
    public static Set<GameObject> treeList;
    public static Set<GameObject> wallList;
    public static List<GameObject> redNinjaList;
    public static List<GameObject> purpleNinjaList;
    public static List<GameObject> blackNinjaList;
    public static List<List<GameObject>> enemyList;
    public static Set<Set> worldObjectLists;
    public static Hero hero;
    public final WorldListener listener;
    OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
    WorldRenderer worldRenderer;
    int doHorizontal;

    public World(WorldListener listener, WorldRenderer worldRenderer) {
        doHorizontal = 0;
        this.listener = listener;
        this.worldRenderer = worldRenderer;
        this.tiledMapRenderer = worldRenderer.tiledMapRenderer;
        createObjects();
        generateLevel();
    }

    private void generateLevel() {
        worldRenderer.addWalls();
        for (int i = 0; i <= numberOfRedNinjaEnemies; i++) {
            float x = PandaSurvivor.RIGHT_SIDE_OF_MAP * (float) Math.random();
            float y = PandaSurvivor.TOP_OF_MAP * (float) Math.random();
            worldRenderer.addEnemy(x, y, NinjaTypes.RED);
        }
        for (int t = 0; t <= numberOfBlackNinjaEnemies; t++) {
            float x = PandaSurvivor.RIGHT_SIDE_OF_MAP * (float) Math.random();
            float y = PandaSurvivor.TOP_OF_MAP * (float) Math.random();
            worldRenderer.addEnemy(x, y, NinjaTypes.BLACK);
        }
        for (int p = 0; p <= numberOfPurpleNinjaEnemies; p++) {
            float x = PandaSurvivor.RIGHT_SIDE_OF_MAP * (float) Math.random();
            float y = PandaSurvivor.TOP_OF_MAP * (float) Math.random();
            worldRenderer.addEnemy(x, y, NinjaTypes.PURPLE);
        }
    }

    private void checkEnemyCount() {
        while (redNinjaList.size() < numberOfRedNinjaEnemies) {
            float x = PandaSurvivor.RIGHT_SIDE_OF_MAP * (float) Math.random();
            float y = PandaSurvivor.TOP_OF_MAP * (float) Math.random();
            worldRenderer.addEnemy(x, y, NinjaTypes.RED);
        }
        while (blackNinjaList.size() < numberOfBlackNinjaEnemies) {
            float x = PandaSurvivor.RIGHT_SIDE_OF_MAP * (float) Math.random();
            float y = PandaSurvivor.TOP_OF_MAP * (float) Math.random();
            worldRenderer.addEnemy(x, y, NinjaTypes.BLACK);
        }
        while (purpleNinjaList.size() < numberOfPurpleNinjaEnemies) {
            float x = PandaSurvivor.RIGHT_SIDE_OF_MAP * (float) Math.random();
            float y = PandaSurvivor.TOP_OF_MAP * (float) Math.random();
            worldRenderer.addEnemy(x, y, NinjaTypes.PURPLE);
        }
    }

    public void update(float deltaTime) {
        updateFireballs(deltaTime);
        updateEnemyFireballs(deltaTime);
        moveEnemies(deltaTime);
        checkCollisions(deltaTime);
        checkGameOver();
        checkHealthRegen(deltaTime);
        checkEnemyCount();
    }

    public void checkCollisions(float deltaTime) {
        checkEnemyFireballCollisions(deltaTime);
        checkFireballCollisions(deltaTime);
    }

    public void checkStaticObjectCollisionsFor(GameObject gameObject, HeroDirections direction, float boundsOffset, boolean checkEnemyCollisionsFlag) {
        checkEdgeOfMapCollisions(gameObject);
        checkWallCollisions(gameObject, direction, boundsOffset, !checkEnemyCollisionsFlag);
        if (checkEnemyCollisionsFlag) {
            checkTreeCollisions(gameObject, direction, boundsOffset, !checkEnemyCollisionsFlag);
            checkEnemyCollisions(direction);
        }
    }

    private void checkEdgeOfMapCollisions(GameObject gameObject) {
        if (gameObject.position.x < PandaSurvivor.LEFT_SIDE_OF_MAP) {
            gameObject.position.x = PandaSurvivor.LEFT_SIDE_OF_MAP;
        } else if (gameObject.position.x > (PandaSurvivor.RIGHT_SIDE_OF_MAP + gameObject.bounds.getWidth())) {
            gameObject.position.x = PandaSurvivor.RIGHT_SIDE_OF_MAP;
        } else if (gameObject.position.y < PandaSurvivor.BOTTOM_OF_MAP) {
            gameObject.position.y = PandaSurvivor.BOTTOM_OF_MAP;
        } else if (gameObject.position.y > (PandaSurvivor.TOP_OF_MAP + gameObject.bounds.getHeight())) {
            gameObject.position.y = PandaSurvivor.TOP_OF_MAP;
        }
        gameObject.updateBounds();
    }

    private void checkEnemyCollisionsWithHero(HeroDirections direction, float deltaTime) {
        for (List<GameObject> list : enemyList) {
            for (int i = 0; i < list.size(); i++) {
                Enemy enemy = (Enemy) list.get(i);
                float heroOriginalX = hero.position.x;
                float heroOriginalY = hero.position.y;
                if (OverlapTester.overlapRectangles(enemy.bounds, hero.bounds)) {
                    if (direction == HeroDirections.RIGHT) {
                        enemy.position.x = hero.position.x - 55;
                        hero.position.x = hero.position.x + 40;

                        enemy.update(deltaTime);
                        enemy.getSprite().setPosition(enemy.position.x + Enemy.WALKING_BOUNDS_ENEMY_WIDTH / 6, enemy.position.y - Enemy.WALKING_BOUNDS_ENEMY_HEIGHT / 6);
                        hero.updateBounds();
                        checkStaticObjectCollisionsFor(hero, HeroDirections.RIGHT, 10, true);
                        hero.updateBounds();
                        worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, HeroDirections.RIGHT);
                    } else if (direction == HeroDirections.LEFT) {
                        enemy.position.x = hero.position.x + 55;
                        hero.position.x = hero.position.x - 40;

                        enemy.update(deltaTime);
                        enemy.getSprite().setPosition(enemy.position.x + Enemy.WALKING_BOUNDS_ENEMY_WIDTH / 6, enemy.position.y - Enemy.WALKING_BOUNDS_ENEMY_HEIGHT / 6);
                        hero.updateBounds();
                        checkStaticObjectCollisionsFor(hero, HeroDirections.LEFT, 10, true);
                        hero.updateBounds();
                        worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, HeroDirections.LEFT);
                    } else if (direction == HeroDirections.DOWN) {
                        enemy.position.y = hero.position.y + 64;
                        hero.position.y = hero.position.y - 60;

                        enemy.update(deltaTime);
                        enemy.getSprite().setPosition(enemy.position.x + Enemy.WALKING_BOUNDS_ENEMY_WIDTH / 6, enemy.position.y - Enemy.WALKING_BOUNDS_ENEMY_HEIGHT / 6);
                        hero.updateBounds();
                        checkStaticObjectCollisionsFor(hero, HeroDirections.DOWN, 10, true);
                        hero.updateBounds();
                        worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, HeroDirections.DOWN);
                    } else if (direction == HeroDirections.UP) {
                        enemy.position.y = hero.position.y - 64;
                        hero.position.y = hero.position.y + 60;

                        enemy.update(deltaTime);
                        enemy.getSprite().setPosition(enemy.position.x + Enemy.WALKING_BOUNDS_ENEMY_WIDTH / 6, enemy.position.y - Enemy.WALKING_BOUNDS_ENEMY_HEIGHT / 6);
                        hero.updateBounds();
                        checkStaticObjectCollisionsFor(hero, HeroDirections.UP, 10, true);
                        hero.updateBounds();
                        worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, HeroDirections.UP);
                    }
                    worldRenderer.updatePandaHitSpriteTexture(hero.getCurrentDirection());
                    hero.subtractDamage(enemy.getTouchDamage());
                    hero.setLastTimeDamaged(deltaTime * LAST_TIME_DAMAGED_BUFFER);
                }
            }
        }
    }

    private void checkHealthRegen(float deltaTime) {
        if (hero.getHealth() < hero.getFullHealth()) {
            if (hero.getLastTimeDamaged() > timeBeforeRegeneration) {
                hero.regenerateHealth();
            } else {
                hero.setLastTimeDamaged(hero.getLastTimeDamaged() + deltaTime);
            }
        }
    }

    private void checkGameOver() {
        if (hero.getHealth() <= 0) {
            fireballList.clear();
            enemyFireballList.clear();
            PandaSurvivor.game_state = PandaSurvivor.GAME_STATES.GAME_OVER;
        }
    }

    private void checkTreeCollisions(GameObject gameObject, HeroDirections direction, float boundsOffset, boolean enemyFlag) {
        checkStaticCollisionsForSet(treeList, gameObject, direction, boundsOffset, enemyFlag);
    }

    private void checkEnemyCollisions(HeroDirections direction) {
        for (List<GameObject> list : enemyList) {
            for (GameObject gameObject : list) {
                Enemy enemy = (Enemy) gameObject;

                if (OverlapTester.overlapRectangles(enemy.bounds, hero.bounds)) {
                    if (direction == HeroDirections.RIGHT)
                        hero.position.x = enemy.position.x - enemy.bounds.getWidth() / 2 - (float) 10;
                    else if (direction == HeroDirections.LEFT) {
                        hero.position.x = enemy.position.x + enemy.bounds.getWidth() / 2 + (float) 10;
                    } else if (direction == HeroDirections.DOWN) {
                        hero.position.y = enemy.position.y + enemy.bounds.getHeight() / 2 + (float) 10;
                    } else if (direction == HeroDirections.UP) {
                        hero.position.y = enemy.position.y - enemy.bounds.getHeight() / 2 - (float) 10;
                    }
                }
            }
        }
    }

    private void checkWallCollisions(GameObject gameObject, HeroDirections direction, float boundsOffset, boolean enemyFlag) {
        checkStaticCollisionsForSet(wallList, gameObject, direction, boundsOffset, enemyFlag);
    }

    private void checkStaticCollisionsForSet(Set<GameObject> objectSet, GameObject gameObject, HeroDirections direction, float boundsOffset, boolean enemyFlag) {
        Rectangle boundsForStaticObject;
        for (GameObject existingWorldStaticObject : objectSet) {
            if (enemyFlag)
                boundsForStaticObject = existingWorldStaticObject.enemy_walking_bounds;
            else
                boundsForStaticObject = existingWorldStaticObject.bounds;

            if (OverlapTester.overlapRectangles(gameObject.bounds, boundsForStaticObject)) {
                if (direction == HeroDirections.RIGHT)
                    gameObject.position.x = existingWorldStaticObject.position.x - boundsForStaticObject.getWidth() / 2 - boundsOffset;
                else if (direction == HeroDirections.LEFT) {
                    gameObject.position.x = existingWorldStaticObject.position.x + boundsForStaticObject.getWidth() / 2 + boundsOffset;
                } else if (direction == HeroDirections.DOWN) {
                    gameObject.position.y = existingWorldStaticObject.position.y + boundsForStaticObject.getHeight() / 2 + boundsOffset;
                } else if (direction == HeroDirections.UP) {
                    gameObject.position.y = existingWorldStaticObject.position.y - boundsForStaticObject.getHeight() / 2 - boundsOffset;
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

                if (fireball != null) {
                    for (List<GameObject> list : enemyList) {
                        for (GameObject gameObject : list) {
                            Enemy enemy = ((Enemy) gameObject);
                            if (OverlapTester.overlapRectangles(enemy.shooting_bounds, fireball.bounds)) {
                                enemy.setHealth(enemy.getHealth() - fireball.getDamageValue());
                                if (enemy.getHealth() <= 0) {
                                    hero.handleXpGain(enemy.getXpGain());
                                    tiledMapRenderer.removeSprite(enemy.getSprite());
                                    list.remove(enemy);
                                    break;
                                }

                                if (fireball.fireballDirection == HeroDirections.UP) {
                                    enemy.position.y += (HERO_MOVE_SPEED * deltaTime);
                                    enemy.update(deltaTime);
                                    enemy.getSprite().setPosition(enemy.position.x + Enemy.WALKING_BOUNDS_ENEMY_WIDTH / 6, enemy.position.y - Enemy.WALKING_BOUNDS_ENEMY_HEIGHT / 6);

                                    checkStaticObjectCollisionsFor(enemy, HeroDirections.UP, 65, false);
//                        worldRenderer.updateEnemyHitSpriteTexture(enemy.getCurrentDirection());
                                } else if (fireball.fireballDirection == HeroDirections.DOWN) {
                                    enemy.position.y -= (HERO_MOVE_SPEED * deltaTime);
                                    enemy.update(deltaTime);
                                    enemy.getSprite().setPosition(enemy.position.x + Enemy.WALKING_BOUNDS_ENEMY_WIDTH / 6, enemy.position.y - Enemy.WALKING_BOUNDS_ENEMY_HEIGHT / 6);

                                    checkStaticObjectCollisionsFor(enemy, HeroDirections.DOWN, 65, false);
//                        worldRenderer.updateEnemyHitSpriteTexture(enemy.getCurrentDirection());
                                } else if (fireball.fireballDirection == HeroDirections.RIGHT) {
                                    enemy.position.x += (HERO_MOVE_SPEED * deltaTime);
                                    enemy.update(deltaTime);
                                    enemy.getSprite().setPosition(enemy.position.x + Enemy.WALKING_BOUNDS_ENEMY_WIDTH / 6, enemy.position.y - Enemy.WALKING_BOUNDS_ENEMY_HEIGHT / 6);

                                    checkStaticObjectCollisionsFor(enemy, HeroDirections.RIGHT, 65, false);
//                        worldRenderer.updateEnemyHitSpriteTexture(enemy.getCurrentDirection());
                                } else if (fireball.fireballDirection == HeroDirections.LEFT) {
                                    enemy.position.x -= (HERO_MOVE_SPEED * deltaTime);
                                    enemy.update(deltaTime);
                                    enemy.getSprite().setPosition(enemy.position.x + Enemy.WALKING_BOUNDS_ENEMY_WIDTH / 6, enemy.position.y - Enemy.WALKING_BOUNDS_ENEMY_HEIGHT / 6);

                                    checkStaticObjectCollisionsFor(enemy, HeroDirections.LEFT, 65, false);
//                        worldRenderer.updateEnemyHitSpriteTexture(enemy.getCurrentDirection());
                                }

                                tiledMapRenderer.removeSprite(fireball.getSprite());
                                fireballList.remove(fireball);
                            }
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

                    hero.subtractDamage(fireball.getDamageValue());
                    hero.setLastTimeDamaged(deltaTime * LAST_TIME_DAMAGED_BUFFER);

                    if (fireball.fireballDirection == HeroDirections.UP) {
                        hero.position.y += (HERO_MOVE_SPEED * deltaTime) * 2;
                        hero.updateBounds();
                        checkStaticObjectCollisionsFor(hero, HeroDirections.UP, 10, true);
                        worldRenderer.updatePandaHitSpriteTexture(hero.getCurrentDirection());
                        worldRenderer.updateCameraAndPandaSpritePositionsUp(tmpYPosition);
                    } else if (fireball.fireballDirection == HeroDirections.DOWN) {
                        hero.position.y -= (HERO_MOVE_SPEED * deltaTime) * 2;
                        hero.updateBounds();
                        checkStaticObjectCollisionsFor(hero, HeroDirections.DOWN, 10, true);
                        worldRenderer.updatePandaHitSpriteTexture(hero.getCurrentDirection());
                        worldRenderer.updateCameraAndPandaSpritePositionsDown(tmpYPosition);
                    } else if (fireball.fireballDirection == HeroDirections.RIGHT) {
                        hero.position.x += (HERO_MOVE_SPEED * deltaTime) * 2;
                        hero.updateBounds();
                        checkStaticObjectCollisionsFor(hero, HeroDirections.RIGHT, 10, true);
                        worldRenderer.updatePandaHitSpriteTexture(hero.getCurrentDirection());
                        worldRenderer.updateCameraAndPandaSpritePositionsRight(tmpXPosition);
                    } else if (fireball.fireballDirection == HeroDirections.LEFT) {
                        hero.position.x -= (HERO_MOVE_SPEED * deltaTime) * 2;
                        hero.updateBounds();
                        checkStaticObjectCollisionsFor(hero, HeroDirections.LEFT, 10, true);
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
        redNinjaList = new ArrayList<GameObject>();
        blackNinjaList = new ArrayList<GameObject>();
        purpleNinjaList = new ArrayList<GameObject>();

        List<List<GameObject>> list = new ArrayList<List<GameObject>>();
        list.add(redNinjaList);
        list.add(blackNinjaList);
        list.add(purpleNinjaList);
        enemyList = list;

        Set<Set> input = new HashSet<Set>();
        input.add(treeList);
        input.add(wallList);
        worldObjectLists = input;
    }

    private void moveEnemies(float deltaTime) {
        for (List<GameObject> gameObjectList : enemyList) {
            for (GameObject gameObject : gameObjectList) {
                Enemy enemy = (Enemy) gameObject;
                HeroDirections direction = null;
                if (doHorizontal < 14) {
                    if (enemy.position.x < hero.position.x - hero.WALKING_BOUNDS_HERO_WIDTH / 3) {
                        direction = HeroDirections.RIGHT;
                        enemy.setCurrentDirection(direction);
                        enemy.position.x = enemy.position.x + (ENEMY_MOVE_SPEED * deltaTime);
                    } else if (enemy.position.x > hero.position.x + hero.WALKING_BOUNDS_HERO_WIDTH) {
                        direction = HeroDirections.LEFT;
                        enemy.setCurrentDirection(direction);
                        enemy.position.x = enemy.position.x - (ENEMY_MOVE_SPEED * deltaTime);
                    }
                } else {
                    if (enemy.position.y < hero.position.y - hero.WALKING_BOUNDS_HERO_HEIGHT / 3) {
                        direction = HeroDirections.UP;
                        enemy.setCurrentDirection(HeroDirections.UP);
                        enemy.position.y = enemy.position.y + (ENEMY_MOVE_SPEED * deltaTime);
                    } else if (enemy.position.y > hero.position.y + hero.WALKING_BOUNDS_HERO_HEIGHT) {
                        direction = HeroDirections.DOWN;
                        enemy.setCurrentDirection(direction);
                        enemy.position.y = enemy.position.y - (ENEMY_MOVE_SPEED * deltaTime);
                    }
                }
                enemy.update(deltaTime);
                enemy.getSprite().setPosition(enemy.position.x + Enemy.WALKING_BOUNDS_ENEMY_WIDTH / 6, enemy.position.y - Enemy.WALKING_BOUNDS_ENEMY_HEIGHT / 6);

                checkEnemyCollisionsWithHero(direction, deltaTime);
                checkStaticObjectCollisionsFor(enemy, enemy.getCurrentDirection(), 65, false);
                enemy.updateBounds();
                worldRenderer.updateNinjaWalkingSpriteTexture(enemy, enemy.getCurrentDirection(), enemy.getNinjaType());
            }
        }

        if (doHorizontal > 28) {
            doHorizontal = 0;
        } else {
            doHorizontal++;
        }
    }
}
