package com.courter.pandasurvivor;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class World {
    public static final int LEVEL_KILL_THRESHOLD = 100;
    public static float BOSS_COLLISION_Y_BOUNDS_OFFSET;
    public static float ENEMY_COLLISION_X_BOUNDS_OFFSET_2;
    public static float ENEMY_COLLISION_Y_BOUNDS_OFFSET_2;
    public static float BOSS_Y_POSITION_OFFSET;
    public static float BOSS_X_POSITION_OFFSET;
    public static float NINJA_COLLISION_X_BOUNDS_OFFSET;
    public static float NINJA_X_POSITION_OFFSET;
    public static float NINJA_Y_POSITION_OFFSET;
    public static float FIREBALL_Y_BOUNDS_OFFSET;
    public static float FIREBALL_X_BOUNDS_OFFSET;
    public static float EDGE_OF_MAP_Y_OFFSET;
    public static float EDGE_OF_MAP_X_OFFSET;
    public static float BOSS_COLLISION_X_BOUNDS_OFFSET;
    public static float BOSS_COIN_DROP_Y_OFFSET_1;
    public static float BOSS_COIN_DROP_Y_OFFSET_2;
    public static float NINJA_COLLISION_Y_BOUNDS_OFFSET;
    public static float NINJA_POSITION_X_BOUNDS_OFFSET;
    public static float NINJA_POSITION_Y_BOUNDS_OFFSET;
    public static float BOSS_KNOCKBACK_Y;
    public static float BOSS_HERO_KNOCKBACK_Y;
    public static float BOSS_HERO_KNOCKBACK_X;
    public static float BOSS_KNOCKBACK_X;
    public static float NINJA_HERO_KNOCKBACK_X;
    public static float NINJA_KNOCKBACK_X;

    enum HeroDirections {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    enum NinjaTypes {
        RED, PURPLE, BLACK
    }

    enum BossTypes {
        PUMPKIN_BOSS
    }

    enum ItemActions {
        EQUIP, SPAWN_BOSS
    }

    public interface WorldListener {
        public void hit();

        public void kill();

        public void powerup();
    }

    public static final int LAST_TIME_DAMAGED_BUFFER = 500;
    public static final int HERO_MOVE_SPEED = (int) (WorldRenderer.h * .481f);
    public static final int ENEMY_MOVE_SPEED = (int) (WorldRenderer.h * .055f);
    public static final int BOSS_MOVE_SPEED = (int) (WorldRenderer.h * .074f);
    public static final int COIN_DROP_CHANCE = 50;
    public static final float BOSS_KEY_DROP_CHANCE = 1f;
    public static final float COMMON_GEAR_DROP_CHANCE = 5f;
    public static final int ITEMS_VISIBLE_TIME = 7;
    public static final int ITEMS_BLINK_TIME = 5;
    public static final float timeBeforeRegeneration = 25;
    static final int numberOfRedNinjaEnemies = 10;
    static final int numberOfBlackNinjaEnemies = 15;
    static final int numberOfPurpleNinjaEnemies = 15;
    public static List<Fireball> fireballList;
    public static List<Fireball> enemyFireballList;
    public static List<Coins> coinsList;
    public static List<Item> itemsList;
    public static Set<GameObject> treeList;
    public static Set<GameObject> wallList;
    public static List<GameObject> redNinjaList;
    public static List<GameObject> purpleNinjaList;
    public static List<GameObject> blackNinjaList;
    public static List<GameObject> bossList;
    public static List<List<GameObject>> enemyList;
    public static List<Set> worldObjectLists;
    public static LevelPortal levelPortal;
    public static Hero hero;
    public static boolean createNewLevel;
    public final WorldListener listener;
    OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
    WorldRenderer worldRenderer;
    int doHorizontal;
    int bossDoHorizontal;

    public World(WorldListener listener, WorldRenderer worldRenderer) {
        doHorizontal = 0;
        bossDoHorizontal = 0;
        this.listener = listener;
        this.worldRenderer = worldRenderer;
        this.tiledMapRenderer = worldRenderer.tiledMapRenderer;
        createObjects();
        generateLevel();
        setConstants();
    }

    private void setConstants() {
        BOSS_COLLISION_X_BOUNDS_OFFSET = (WorldRenderer.h * .06f);
        BOSS_COLLISION_Y_BOUNDS_OFFSET = (WorldRenderer.w * .036f);
        ENEMY_COLLISION_X_BOUNDS_OFFSET_2 = (WorldRenderer.w * .0055f);
        ENEMY_COLLISION_Y_BOUNDS_OFFSET_2 = (WorldRenderer.h * .0092f);
        BOSS_X_POSITION_OFFSET = Boss.WALKING_BOUNDS_BOSS_WIDTH / 4;
        BOSS_Y_POSITION_OFFSET = Boss.WALKING_BOUNDS_BOSS_HEIGHT / 4;
        NINJA_COLLISION_X_BOUNDS_OFFSET = (WorldRenderer.w * .036f);
        NINJA_X_POSITION_OFFSET = Ninja.WALKING_BOUNDS_ENEMY_WIDTH / 6;
        NINJA_Y_POSITION_OFFSET = Ninja.WALKING_BOUNDS_ENEMY_HEIGHT / 6;
        FIREBALL_Y_BOUNDS_OFFSET = (WorldRenderer.w * .0055f);
        FIREBALL_X_BOUNDS_OFFSET = (WorldRenderer.h * .0092f);
        EDGE_OF_MAP_Y_OFFSET = (WorldRenderer.h * .059f);
        EDGE_OF_MAP_X_OFFSET = (WorldRenderer.w * .035f);
        BOSS_COIN_DROP_Y_OFFSET_1 = (WorldRenderer.h * .0092f);
        BOSS_COIN_DROP_Y_OFFSET_2 = (WorldRenderer.h * .0185f);
        NINJA_COLLISION_Y_BOUNDS_OFFSET = (WorldRenderer.h * .06f);
        NINJA_POSITION_X_BOUNDS_OFFSET = Ninja.WALKING_BOUNDS_ENEMY_WIDTH / 6;
        NINJA_POSITION_Y_BOUNDS_OFFSET = Ninja.WALKING_BOUNDS_ENEMY_HEIGHT / 6;
        BOSS_KNOCKBACK_X = (WorldRenderer.w * .041f);
        BOSS_KNOCKBACK_Y = (WorldRenderer.h * .059f);
        BOSS_HERO_KNOCKBACK_X = (WorldRenderer.w * .033f);
        BOSS_HERO_KNOCKBACK_Y = (WorldRenderer.h * .055f);
        NINJA_KNOCKBACK_X = (WorldRenderer.w * .03f);
        NINJA_HERO_KNOCKBACK_X = (WorldRenderer.w * .022f);
    }

    private void generateLevel() {
        worldRenderer.addWalls();
        for (int i = 0; i <= numberOfRedNinjaEnemies; i++) {
            float x = PandaSurvivor.RIGHT_SIDE_OF_MAP * (float) Math.random();
            float y = PandaSurvivor.TOP_OF_MAP * (float) Math.random();
            worldRenderer.addNinja(x, y, NinjaTypes.RED);
        }
        for (int t = 0; t <= numberOfBlackNinjaEnemies; t++) {
            float x = PandaSurvivor.RIGHT_SIDE_OF_MAP * (float) Math.random();
            float y = PandaSurvivor.TOP_OF_MAP * (float) Math.random();
            worldRenderer.addNinja(x, y, NinjaTypes.BLACK);
        }
        for (int p = 0; p <= numberOfPurpleNinjaEnemies; p++) {
            float x = PandaSurvivor.RIGHT_SIDE_OF_MAP * (float) Math.random();
            float y = PandaSurvivor.TOP_OF_MAP * (float) Math.random();
            worldRenderer.addNinja(x, y, NinjaTypes.PURPLE);
        }
    }

    private void checkEnemyCount() {
        while (redNinjaList.size() < numberOfRedNinjaEnemies) {
            float x = PandaSurvivor.RIGHT_SIDE_OF_MAP * (float) Math.random();
            float y = PandaSurvivor.TOP_OF_MAP * (float) Math.random();
            worldRenderer.addNinja(x, y, NinjaTypes.RED);
        }
        while (blackNinjaList.size() < numberOfBlackNinjaEnemies) {
            float x = PandaSurvivor.RIGHT_SIDE_OF_MAP * (float) Math.random();
            float y = PandaSurvivor.TOP_OF_MAP * (float) Math.random();
            worldRenderer.addNinja(x, y, NinjaTypes.BLACK);
        }
        while (purpleNinjaList.size() < numberOfPurpleNinjaEnemies) {
            float x = PandaSurvivor.RIGHT_SIDE_OF_MAP * (float) Math.random();
            float y = PandaSurvivor.TOP_OF_MAP * (float) Math.random();
            worldRenderer.addNinja(x, y, NinjaTypes.PURPLE);
        }
    }

    public void update(float deltaTime) {
        updateFireballs(deltaTime);
        updateEnemyFireballs(deltaTime);
        moveEnemies(deltaTime);
        moveBosses(deltaTime);
        checkCollisions(deltaTime);
        checkGameOver();
        checkHealthRegen(deltaTime);
        checkEnemyCount();
    }

    public void checkCollisions(float deltaTime) {
        checkEnemyFireballCollisions(deltaTime);
        checkFireballCollisions(deltaTime);
        checkCoinCollisions(deltaTime);
        checkItemCollisions(deltaTime);
    }

    public void checkStaticObjectCollisionsFor(GameObject gameObject, HeroDirections direction, float boundsOffset, boolean checkEnemyCollisionsFlag) {
        checkEdgeOfMapCollisions(gameObject);
        checkWallCollisions(gameObject, direction, boundsOffset, !checkEnemyCollisionsFlag);
        if (checkEnemyCollisionsFlag) {
            checkTreeCollisions(gameObject, direction, boundsOffset, !checkEnemyCollisionsFlag);
            checkEnemyCollisions(direction);
            checkBossCollisions(direction);
            if (levelPortal != null) {
                checkLevelPortalCollisions(gameObject);
            }
        }
    }

    private void checkCoinCollisions(float deltaTime) {
        for (Coins coins : coinsList) {
            if (OverlapTester.overlapRectangles(coins.bounds, hero.bounds)) {
                hero.addMoney(coins.getMoneyValue());
                tiledMapRenderer.removeSprite(coins.getSprite());
                coinsList.remove(coins);
                break;
            }
            coins.update(deltaTime);

            if (coins.getStateTime() > ITEMS_VISIBLE_TIME) {
                tiledMapRenderer.removeSprite(coins.getSprite());
                coinsList.remove(coins);
                break;
            }

            if (coins.getStateTime() > ITEMS_BLINK_TIME) {
                if (coins.isVisible()) {
                    tiledMapRenderer.removeSprite(coins.getSprite());
                } else {
                    tiledMapRenderer.addSprite(coins.getSprite());
                }
                coins.toggleVisible();
            }
        }
    }

    private void checkItemCollisions(float deltaTime) {
        for (Item item : itemsList) {
            if (OverlapTester.overlapRectangles(item.bounds, hero.bounds)) {
                int currentHeroInventorySize = hero.getInventory().size();
                if (currentHeroInventorySize < hero.getMaxInventorySize()) {
                    hero.addItemToInventory(item);
                    worldRenderer.addInventoryUnitBounds(currentHeroInventorySize + 1);
                    tiledMapRenderer.removeSprite(item.getSprite());
                    itemsList.remove(item);
                    break;
                }
            }
            item.update(deltaTime);

            if (item.getStateTime() > ITEMS_VISIBLE_TIME) {
                tiledMapRenderer.removeSprite(item.getSprite());
                itemsList.remove(item);
                break;
            }

            if (item.getStateTime() > ITEMS_BLINK_TIME) {
                if (item.isVisible()) {
                    tiledMapRenderer.removeSprite(item.getSprite());
                } else {
                    tiledMapRenderer.addSprite(item.getSprite());
                }
                item.toggleVisible();
            }
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
                        enemy.position.x = hero.position.x - NINJA_KNOCKBACK_X;
                        hero.position.x = hero.position.x + NINJA_HERO_KNOCKBACK_X;

                        enemy.update(deltaTime);
                        enemy.getSprite().setPosition(enemy.position.x + NINJA_POSITION_X_BOUNDS_OFFSET, enemy.position.y - NINJA_POSITION_Y_BOUNDS_OFFSET);
                        hero.updateBounds();
                        checkStaticObjectCollisionsFor(hero, HeroDirections.RIGHT, ENEMY_COLLISION_X_BOUNDS_OFFSET_2, true);
                        hero.updateBounds();
                        worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, HeroDirections.RIGHT);
                    } else if (direction == HeroDirections.LEFT) {
                        enemy.position.x = hero.position.x + NINJA_KNOCKBACK_X;
                        hero.position.x = hero.position.x - NINJA_HERO_KNOCKBACK_X;

                        enemy.update(deltaTime);
                        enemy.getSprite().setPosition(enemy.position.x + NINJA_POSITION_X_BOUNDS_OFFSET, enemy.position.y - NINJA_POSITION_Y_BOUNDS_OFFSET);
                        hero.updateBounds();
                        checkStaticObjectCollisionsFor(hero, HeroDirections.LEFT, ENEMY_COLLISION_X_BOUNDS_OFFSET_2, true);
                        hero.updateBounds();
                        worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, HeroDirections.LEFT);
                    } else if (direction == HeroDirections.DOWN) {
                        enemy.position.y = hero.position.y + BOSS_KNOCKBACK_Y;
                        hero.position.y = hero.position.y - BOSS_HERO_KNOCKBACK_Y;

                        enemy.update(deltaTime);
                        enemy.getSprite().setPosition(enemy.position.x + NINJA_POSITION_X_BOUNDS_OFFSET, enemy.position.y - NINJA_POSITION_Y_BOUNDS_OFFSET);
                        hero.updateBounds();
                        checkStaticObjectCollisionsFor(hero, HeroDirections.DOWN, ENEMY_COLLISION_Y_BOUNDS_OFFSET_2, true);
                        hero.updateBounds();
                        worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, HeroDirections.DOWN);
                    } else if (direction == HeroDirections.UP) {
                        enemy.position.y = hero.position.y - BOSS_KNOCKBACK_Y;
                        hero.position.y = hero.position.y + BOSS_HERO_KNOCKBACK_Y;

                        enemy.update(deltaTime);
                        enemy.getSprite().setPosition(enemy.position.x + NINJA_POSITION_X_BOUNDS_OFFSET, enemy.position.y - NINJA_POSITION_Y_BOUNDS_OFFSET);
                        hero.updateBounds();
                        checkStaticObjectCollisionsFor(hero, HeroDirections.UP, ENEMY_COLLISION_Y_BOUNDS_OFFSET_2, true);
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

    private void checkBossCollisionsWithHero(HeroDirections direction, float deltaTime) {
        for (int i = 0; i < bossList.size(); i++) {
            Boss boss = (Boss) bossList.get(i);
            float heroOriginalX = hero.position.x;
            float heroOriginalY = hero.position.y;
            if (OverlapTester.overlapRectangles(boss.bounds, hero.bounds)) {
                if (direction == HeroDirections.RIGHT) {
                    boss.position.x = hero.position.x - BOSS_KNOCKBACK_X;
                    hero.position.x = hero.position.x + BOSS_HERO_KNOCKBACK_X;

                    boss.update(deltaTime);
                    boss.getSprite().setPosition(boss.position.x + BOSS_X_POSITION_OFFSET, boss.position.y - BOSS_Y_POSITION_OFFSET);
                    hero.updateBounds();
                    checkStaticObjectCollisionsFor(hero, HeroDirections.RIGHT, ENEMY_COLLISION_X_BOUNDS_OFFSET_2, true);
                    hero.updateBounds();
                    worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, HeroDirections.RIGHT);
                } else if (direction == HeroDirections.LEFT) {
                    boss.position.x = hero.position.x + BOSS_KNOCKBACK_X;
                    hero.position.x = hero.position.x - BOSS_HERO_KNOCKBACK_X;

                    boss.update(deltaTime);
                    boss.getSprite().setPosition(boss.position.x + BOSS_X_POSITION_OFFSET, boss.position.y - BOSS_Y_POSITION_OFFSET);
                    hero.updateBounds();
                    checkStaticObjectCollisionsFor(hero, HeroDirections.LEFT, ENEMY_COLLISION_X_BOUNDS_OFFSET_2, true);
                    hero.updateBounds();
                    worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, HeroDirections.LEFT);
                } else if (direction == HeroDirections.DOWN) {
                    boss.position.y = hero.position.y + BOSS_KNOCKBACK_Y;
                    hero.position.y = hero.position.y - BOSS_HERO_KNOCKBACK_Y;

                    boss.update(deltaTime);
                    boss.getSprite().setPosition(boss.position.x + BOSS_X_POSITION_OFFSET, boss.position.y - BOSS_Y_POSITION_OFFSET);
                    hero.updateBounds();
                    checkStaticObjectCollisionsFor(hero, HeroDirections.DOWN, ENEMY_COLLISION_Y_BOUNDS_OFFSET_2, true);
                    hero.updateBounds();
                    worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, HeroDirections.DOWN);
                } else if (direction == HeroDirections.UP) {
                    boss.position.y = hero.position.y - BOSS_KNOCKBACK_Y;
                    hero.position.y = hero.position.y + BOSS_HERO_KNOCKBACK_Y;

                    boss.update(deltaTime);
                    boss.getSprite().setPosition(boss.position.x + BOSS_X_POSITION_OFFSET, boss.position.y - BOSS_Y_POSITION_OFFSET);
                    hero.updateBounds();
                    checkStaticObjectCollisionsFor(hero, HeroDirections.UP, ENEMY_COLLISION_Y_BOUNDS_OFFSET_2, true);
                    hero.updateBounds();
                    worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, HeroDirections.UP);
                }
                worldRenderer.updatePandaHitSpriteTexture(hero.getCurrentDirection());
                hero.subtractDamage(boss.getTouchDamage());
                hero.setLastTimeDamaged(deltaTime * LAST_TIME_DAMAGED_BUFFER);
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
            hero.setHealth(0);
            fireballList.clear();
            enemyFireballList.clear();
            worldRenderer.addRetryBounds();
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
                        hero.position.x = enemy.position.x - enemy.bounds.getWidth() / 2 - ENEMY_COLLISION_X_BOUNDS_OFFSET_2;
                    else if (direction == HeroDirections.LEFT) {
                        hero.position.x = enemy.position.x + enemy.bounds.getWidth() / 2 + ENEMY_COLLISION_X_BOUNDS_OFFSET_2;
                    } else if (direction == HeroDirections.DOWN) {
                        hero.position.y = enemy.position.y + enemy.bounds.getHeight() / 2 + ENEMY_COLLISION_Y_BOUNDS_OFFSET_2;
                    } else if (direction == HeroDirections.UP) {
                        hero.position.y = enemy.position.y - enemy.bounds.getHeight() / 2 - ENEMY_COLLISION_Y_BOUNDS_OFFSET_2;
                    }
                }
            }
        }
    }

    private void checkBossCollisions(HeroDirections direction) {
        for (GameObject gameObject : bossList) {
            Enemy enemy = (Enemy) gameObject;

            if (OverlapTester.overlapRectangles(enemy.bounds, hero.bounds)) {
                if (direction == HeroDirections.RIGHT)
                    hero.position.x = enemy.position.x - enemy.bounds.getWidth() / 2 - ENEMY_COLLISION_X_BOUNDS_OFFSET_2;
                else if (direction == HeroDirections.LEFT) {
                    hero.position.x = enemy.position.x + enemy.bounds.getWidth() / 2 + ENEMY_COLLISION_X_BOUNDS_OFFSET_2;
                } else if (direction == HeroDirections.DOWN) {
                    hero.position.y = enemy.position.y + enemy.bounds.getHeight() / 2 + ENEMY_COLLISION_Y_BOUNDS_OFFSET_2;
                } else if (direction == HeroDirections.UP) {
                    hero.position.y = enemy.position.y - enemy.bounds.getHeight() / 2 - ENEMY_COLLISION_Y_BOUNDS_OFFSET_2;
                }
            }
        }
    }

    private void checkWallCollisions(GameObject gameObject, HeroDirections direction, float boundsOffset, boolean enemyFlag) {
        checkStaticCollisionsForSet(wallList, gameObject, direction, boundsOffset, enemyFlag);
    }

    private void checkLevelPortalCollisions(GameObject gameObject) {
        if (OverlapTester.overlapRectangles(gameObject.bounds, levelPortal.bounds)) {
            this.createNewLevel = true;
        }
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
        float x = WorldRenderer.camera.position.x - WorldRenderer.w / 2;
        float y = WorldRenderer.camera.position.y - WorldRenderer.h / 2;

        for (int i = 0; i < fireballList.size(); i++) {
            Fireball fireball = fireballList.get(i);
            if (fireball.position.x < PandaSurvivor.LEFT_SIDE_OF_MAP ||
                    fireball.position.x > (PandaSurvivor.RIGHT_SIDE_OF_MAP + EDGE_OF_MAP_X_OFFSET) ||
                    fireball.position.y < PandaSurvivor.BOTTOM_OF_MAP ||
                    fireball.position.y > (PandaSurvivor.TOP_OF_MAP + EDGE_OF_MAP_Y_OFFSET) ||
                    fireball.stateTime > Fireball.FIREBALL_DISTANCE) {
                tiledMapRenderer.removeSprite(fireball.getSprite());
                fireballList.remove(fireball);
                break;
            } else {
                for (Set<GameObject> set : worldObjectLists) {
                    for (GameObject gameObject : set) {
                        if ((gameObject.position.x < (x + WorldRenderer.w) && gameObject.position.x > x) && ((gameObject.position.y < (y + WorldRenderer.h) && gameObject.position.y > y))) {
                            if (OverlapTester.overlapRectangles(gameObject.shooting_bounds, fireball.bounds)) {
                                tiledMapRenderer.removeSprite(fireball.getSprite());
                                fireballList.remove(fireball);
                                break;
                            }
                        }
                    }
                }

                if (fireball != null) {
                    float heroGoldBonus = hero.getGoldBonus();
                    for (List<GameObject> list : enemyList) {
                        for (GameObject gameObject : list) {
                            if ((gameObject.position.x < (x + WorldRenderer.w) && gameObject.position.x > x) && ((gameObject.position.y < (y + WorldRenderer.h) && gameObject.position.y > y))) {
                                Ninja ninja = ((Ninja) gameObject);
                                if (OverlapTester.overlapRectangles(ninja.shooting_bounds, fireball.bounds)) {
                                    ninja.setHealth(ninja.getHealth() - hero.getSpellDmg());
                                    if (ninja.getHealth() <= 0) {
                                        hero.handleXpGain(ninja.getXpGain());
                                        float percentageChance = (float) Math.random() * 100;
                                        if (percentageChance < COIN_DROP_CHANCE) {
                                            worldRenderer.addCoins(ninja.position.x + ninja.WALKING_BOUNDS_ENEMY_WIDTH / 2, ninja.position.y, heroGoldBonus);
                                        }
                                        if (percentageChance < BOSS_KEY_DROP_CHANCE) {
                                            worldRenderer.addBossKey(ninja.position.x + ninja.WALKING_BOUNDS_ENEMY_WIDTH / 2 - (WorldRenderer.w * .0055f), ninja.position.y);
                                        }
                                        if (percentageChance < COMMON_GEAR_DROP_CHANCE) {
                                            worldRenderer.addGear(ninja.position.x + ninja.WALKING_BOUNDS_ENEMY_WIDTH / 2 - (WorldRenderer.w * .0055f), ninja.position.y + (WorldRenderer.h * .0185f));
                                        }
                                        tiledMapRenderer.removeSprite(ninja.getSprite());
                                        list.remove(ninja);
                                        hero.addNinjaKill();
                                        if (hero.getNinjaKillCount() % LEVEL_KILL_THRESHOLD == 0 && levelPortal == null) {
                                            worldRenderer.addLevelPortal();
                                        }
                                        break;
                                    }

                                    if (fireball.fireballDirection == HeroDirections.UP) {
                                        ninja.position.y += (HERO_MOVE_SPEED * deltaTime);
                                        ninja.update(deltaTime);
                                        ninja.getSprite().setPosition(ninja.position.x + NINJA_POSITION_X_BOUNDS_OFFSET, ninja.position.y - NINJA_POSITION_Y_BOUNDS_OFFSET);

                                        checkStaticObjectCollisionsFor(ninja, HeroDirections.UP, NINJA_COLLISION_Y_BOUNDS_OFFSET, false);
//                        worldRenderer.updateNinjaHitSpriteTexture(enemy.getCurrentDirection());
                                    } else if (fireball.fireballDirection == HeroDirections.DOWN) {
                                        ninja.position.y -= (HERO_MOVE_SPEED * deltaTime);
                                        ninja.update(deltaTime);
                                        ninja.getSprite().setPosition(ninja.position.x + NINJA_POSITION_X_BOUNDS_OFFSET, ninja.position.y - NINJA_POSITION_Y_BOUNDS_OFFSET);

                                        checkStaticObjectCollisionsFor(ninja, HeroDirections.DOWN, NINJA_COLLISION_Y_BOUNDS_OFFSET, false);
//                        worldRenderer.updateNinjaHitSpriteTexture(enemy.getCurrentDirection());
                                    } else if (fireball.fireballDirection == HeroDirections.RIGHT) {
                                        ninja.position.x += (HERO_MOVE_SPEED * deltaTime);
                                        ninja.update(deltaTime);
                                        ninja.getSprite().setPosition(ninja.position.x + NINJA_POSITION_X_BOUNDS_OFFSET, ninja.position.y - NINJA_POSITION_Y_BOUNDS_OFFSET);

                                        checkStaticObjectCollisionsFor(ninja, HeroDirections.RIGHT, NINJA_COLLISION_X_BOUNDS_OFFSET, false);
//                        worldRenderer.updateNinjaHitSpriteTexture(enemy.getCurrentDirection());
                                    } else if (fireball.fireballDirection == HeroDirections.LEFT) {
                                        ninja.position.x -= (HERO_MOVE_SPEED * deltaTime);
                                        ninja.update(deltaTime);
                                        ninja.getSprite().setPosition(ninja.position.x + NINJA_POSITION_X_BOUNDS_OFFSET, ninja.position.y - NINJA_POSITION_Y_BOUNDS_OFFSET);

                                        checkStaticObjectCollisionsFor(ninja, HeroDirections.LEFT, NINJA_COLLISION_X_BOUNDS_OFFSET, false);
//                        worldRenderer.updateEnemyHitSpriteTexture(enemy.getCurrentDirection());
                                    }

                                    tiledMapRenderer.removeSprite(fireball.getSprite());
                                    fireballList.remove(fireball);
                                    break;
                                }
                            }
                        }
                    }

                    if (fireball != null) {
                        for (GameObject gameObject : bossList) {
                            if ((gameObject.position.x < (x + WorldRenderer.w) && gameObject.position.x > x) && ((gameObject.position.y < (y + WorldRenderer.h) && gameObject.position.y > y))) {
                                Boss boss = ((Boss) gameObject);
                                if (OverlapTester.overlapRectangles(boss.shooting_bounds, fireball.bounds)) {
                                    boss.setHealth(boss.getHealth() - hero.getSpellDmg());
                                    if (boss.getHealth() <= 0) {
                                        hero.handleXpGain(boss.getXpGain());
                                        worldRenderer.addCoins(boss.position.x + boss.WALKING_BOUNDS_BOSS_WIDTH / 2, boss.position.y, heroGoldBonus);
                                        worldRenderer.addCoins(boss.position.x - boss.WALKING_BOUNDS_BOSS_WIDTH / 2, boss.position.y + BOSS_COIN_DROP_Y_OFFSET_1, heroGoldBonus);
                                        worldRenderer.addCoins(boss.position.x + boss.WALKING_BOUNDS_BOSS_WIDTH / 2, boss.position.y + BOSS_COIN_DROP_Y_OFFSET_2, heroGoldBonus);
                                        worldRenderer.addCoins(boss.position.x - boss.WALKING_BOUNDS_BOSS_WIDTH / 2, boss.position.y - BOSS_COIN_DROP_Y_OFFSET_1, heroGoldBonus);
                                        worldRenderer.addCoins(boss.position.x - boss.WALKING_BOUNDS_BOSS_WIDTH / 2, boss.position.y - BOSS_COIN_DROP_Y_OFFSET_1, heroGoldBonus);
                                        worldRenderer.addCoins(boss.position.x - boss.WALKING_BOUNDS_BOSS_WIDTH / 2, boss.position.y - BOSS_COIN_DROP_Y_OFFSET_2, heroGoldBonus);
                                        worldRenderer.addCoins(boss.position.x - boss.WALKING_BOUNDS_BOSS_WIDTH / 2, boss.position.y - BOSS_COIN_DROP_Y_OFFSET_2, heroGoldBonus);
                                        tiledMapRenderer.removeSprite(boss.getSprite());
                                        bossList.remove(boss);
                                        hero.addBossKill();
                                        break;
                                    }

                                    if (fireball.fireballDirection == HeroDirections.UP) {
                                        boss.position.y += (HERO_MOVE_SPEED * deltaTime);
                                        boss.update(deltaTime);
                                        boss.getSprite().setPosition(boss.position.x, boss.position.y - Boss.WALKING_BOUNDS_BOSS_HEIGHT / 4);

                                        checkStaticObjectCollisionsFor(boss, HeroDirections.UP, BOSS_COLLISION_X_BOUNDS_OFFSET, false);
//                        worldRenderer.updateNinjaHitSpriteTexture(enemy.getCurrentDirection());
                                    } else if (fireball.fireballDirection == HeroDirections.DOWN) {
                                        boss.position.y -= (HERO_MOVE_SPEED * deltaTime);
                                        boss.update(deltaTime);
                                        boss.getSprite().setPosition(boss.position.x, boss.position.y - Boss.WALKING_BOUNDS_BOSS_HEIGHT / 4);

                                        checkStaticObjectCollisionsFor(boss, HeroDirections.DOWN, BOSS_COLLISION_X_BOUNDS_OFFSET, false);
//                        worldRenderer.updateNinjaHitSpriteTexture(enemy.getCurrentDirection());
                                    } else if (fireball.fireballDirection == HeroDirections.RIGHT) {
                                        boss.position.x += (HERO_MOVE_SPEED * deltaTime);
                                        boss.update(deltaTime);
                                        boss.getSprite().setPosition(boss.position.x, boss.position.y - Boss.WALKING_BOUNDS_BOSS_HEIGHT / 4);

                                        checkStaticObjectCollisionsFor(boss, HeroDirections.RIGHT, BOSS_COLLISION_Y_BOUNDS_OFFSET, false);
//                        worldRenderer.updateNinjaHitSpriteTexture(enemy.getCurrentDirection());
                                    } else if (fireball.fireballDirection == HeroDirections.LEFT) {
                                        boss.position.x -= (HERO_MOVE_SPEED * deltaTime);
                                        boss.update(deltaTime);
                                        boss.getSprite().setPosition(boss.position.x, boss.position.y - Boss.WALKING_BOUNDS_BOSS_HEIGHT / 4);

                                        checkStaticObjectCollisionsFor(boss, HeroDirections.LEFT, BOSS_COLLISION_Y_BOUNDS_OFFSET, false);
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
    }

    private void checkEnemyFireballCollisions(float deltaTime) {
        float x = WorldRenderer.camera.position.x - WorldRenderer.w / 2;
        float y = WorldRenderer.camera.position.y - WorldRenderer.h / 2;

        for (int i = 0; i < enemyFireballList.size(); i++) {
            Fireball fireball = enemyFireballList.get(i);
            if ((fireball.position.x < (x + WorldRenderer.w) && fireball.position.x > x) && ((fireball.position.y < (y + WorldRenderer.h) && fireball.position.y > y))) {
                if (fireball.position.x < PandaSurvivor.LEFT_SIDE_OF_MAP ||
                        fireball.position.x > (PandaSurvivor.RIGHT_SIDE_OF_MAP + EDGE_OF_MAP_X_OFFSET) ||
                        fireball.position.y < PandaSurvivor.BOTTOM_OF_MAP ||
                        fireball.position.y > (PandaSurvivor.TOP_OF_MAP + EDGE_OF_MAP_Y_OFFSET) ||
                        fireball.stateTime > Fireball.FIREBALL_DISTANCE) {
                    tiledMapRenderer.removeSprite(fireball.getSprite());
                    enemyFireballList.remove(fireball);
                    break;
                } else {
                    for (Set<GameObject> set : worldObjectLists) {
                        for (GameObject gameObject : set) {
                            if ((gameObject.position.x < (x + WorldRenderer.w) && gameObject.position.x > x) && ((gameObject.position.y < (y + WorldRenderer.h) && gameObject.position.y > y))) {
                                if (OverlapTester.overlapRectangles(gameObject.shooting_bounds, fireball.bounds)) {
                                    tiledMapRenderer.removeSprite(fireball.getSprite());
                                    enemyFireballList.remove(fireball);
                                    break;
                                }
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
                            checkStaticObjectCollisionsFor(hero, HeroDirections.UP, FIREBALL_X_BOUNDS_OFFSET, true);
                            worldRenderer.updatePandaHitSpriteTexture(hero.getCurrentDirection());
                            worldRenderer.updateCameraAndPandaSpritePositionsUp(tmpYPosition);
                        } else if (fireball.fireballDirection == HeroDirections.DOWN) {
                            hero.position.y -= (HERO_MOVE_SPEED * deltaTime) * 2;
                            hero.updateBounds();
                            checkStaticObjectCollisionsFor(hero, HeroDirections.DOWN, FIREBALL_X_BOUNDS_OFFSET, true);
                            worldRenderer.updatePandaHitSpriteTexture(hero.getCurrentDirection());
                            worldRenderer.updateCameraAndPandaSpritePositionsDown(tmpYPosition);
                        } else if (fireball.fireballDirection == HeroDirections.RIGHT) {
                            hero.position.x += (HERO_MOVE_SPEED * deltaTime) * 2;
                            hero.updateBounds();
                            checkStaticObjectCollisionsFor(hero, HeroDirections.RIGHT, FIREBALL_Y_BOUNDS_OFFSET, true);
                            worldRenderer.updatePandaHitSpriteTexture(hero.getCurrentDirection());
                            worldRenderer.updateCameraAndPandaSpritePositionsRight(tmpXPosition);
                        } else if (fireball.fireballDirection == HeroDirections.LEFT) {
                            hero.position.x -= (HERO_MOVE_SPEED * deltaTime) * 2;
                            hero.updateBounds();
                            checkStaticObjectCollisionsFor(hero, HeroDirections.LEFT, FIREBALL_Y_BOUNDS_OFFSET, true);
                            worldRenderer.updatePandaHitSpriteTexture(hero.getCurrentDirection());
                            worldRenderer.updateCameraAndPandaSpritePositionsLeft(tmpXPosition);
                        }
                        tiledMapRenderer.removeSprite(fireball.getSprite());
                        enemyFireballList.remove(fireball);
                        break;
                    }
                }
            } else if (fireball.stateTime > Fireball.FIREBALL_DISTANCE) {
                tiledMapRenderer.removeSprite(fireball.getSprite());
                enemyFireballList.remove(fireball);
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
        worldObjectLists = new ArrayList<Set>();

        fireballList = new ArrayList<Fireball>();
        enemyFireballList = new ArrayList<Fireball>();
        coinsList = new ArrayList<Coins>();
        itemsList = new ArrayList<Item>();
        treeList = new HashSet<GameObject>();
        wallList = new HashSet<GameObject>();
        redNinjaList = new ArrayList<GameObject>();
        blackNinjaList = new ArrayList<GameObject>();
        purpleNinjaList = new ArrayList<GameObject>();
        bossList = new ArrayList<GameObject>();

        List<List<GameObject>> list = new ArrayList<List<GameObject>>();
        list.add(redNinjaList);
        list.add(blackNinjaList);
        list.add(purpleNinjaList);
        enemyList = list;

        ArrayList<Set> input = new ArrayList<Set>();
        input.add(treeList);
        input.add(wallList);
        worldObjectLists = input;

        levelPortal = null;
        createNewLevel = false;
    }

    private void moveEnemies(float deltaTime) {
        for (List<GameObject> gameObjectList : enemyList) {
            for (GameObject gameObject : gameObjectList) {
                Ninja ninja = (Ninja) gameObject;
                HeroDirections direction = null;
                if (doHorizontal < 14) {
                    if (ninja.position.x < hero.position.x - hero.WALKING_BOUNDS_HERO_WIDTH / 3) {
                        direction = HeroDirections.RIGHT;
                        ninja.setCurrentDirection(direction);
                        ninja.position.x = ninja.position.x + (ENEMY_MOVE_SPEED * deltaTime);
                    } else if (ninja.position.x > hero.position.x + hero.WALKING_BOUNDS_HERO_WIDTH) {
                        direction = HeroDirections.LEFT;
                        ninja.setCurrentDirection(direction);
                        ninja.position.x = ninja.position.x - (ENEMY_MOVE_SPEED * deltaTime);
                    }
                } else {
                    if (ninja.position.y < hero.position.y - hero.WALKING_BOUNDS_HERO_HEIGHT / 3) {
                        direction = HeroDirections.UP;
                        ninja.setCurrentDirection(HeroDirections.UP);
                        ninja.position.y = ninja.position.y + (ENEMY_MOVE_SPEED * deltaTime);
                    } else if (ninja.position.y > hero.position.y + hero.WALKING_BOUNDS_HERO_HEIGHT) {
                        direction = HeroDirections.DOWN;
                        ninja.setCurrentDirection(direction);
                        ninja.position.y = ninja.position.y - (ENEMY_MOVE_SPEED * deltaTime);
                    }
                }
                ninja.update(deltaTime);
                ninja.getSprite().setPosition(ninja.position.x + NINJA_X_POSITION_OFFSET, ninja.position.y - NINJA_Y_POSITION_OFFSET);

                checkEnemyCollisionsWithHero(direction, deltaTime);
                checkStaticObjectCollisionsFor(ninja, ninja.getCurrentDirection(), NINJA_COLLISION_X_BOUNDS_OFFSET, false);
                ninja.updateBounds();
                worldRenderer.updateNinjaWalkingSpriteTexture(ninja, ninja.getCurrentDirection(), ninja.getNinjaType());
            }
        }

        if (doHorizontal > 28) {
            doHorizontal = 0;
        } else {
            doHorizontal++;
        }
    }

    private void moveBosses(float deltaTime) {
        for (GameObject gameObject : bossList) {
            Boss boss = (Boss) gameObject;
            HeroDirections direction = null;
            if (bossDoHorizontal < 24) {
                if (boss.position.x < hero.position.x - hero.WALKING_BOUNDS_HERO_WIDTH / 3) {
                    direction = HeroDirections.RIGHT;
                    boss.setCurrentDirection(direction);
                    boss.position.x = boss.position.x + (BOSS_MOVE_SPEED * deltaTime);
                } else if (boss.position.x > hero.position.x + hero.WALKING_BOUNDS_HERO_WIDTH) {
                    direction = HeroDirections.LEFT;
                    boss.setCurrentDirection(direction);
                    boss.position.x = boss.position.x - (BOSS_MOVE_SPEED * deltaTime);
                }
            } else {
                if (boss.position.y < hero.position.y - hero.WALKING_BOUNDS_HERO_HEIGHT / 3) {
                    direction = HeroDirections.UP;
                    boss.setCurrentDirection(HeroDirections.UP);
                    boss.position.y = boss.position.y + (BOSS_MOVE_SPEED * deltaTime);
                } else if (boss.position.y > hero.position.y + hero.WALKING_BOUNDS_HERO_HEIGHT) {
                    direction = HeroDirections.DOWN;
                    boss.setCurrentDirection(direction);
                    boss.position.y = boss.position.y - (BOSS_MOVE_SPEED * deltaTime);
                }
            }
            boss.update(deltaTime);
            boss.getSprite().setPosition(boss.position.x, boss.position.y - BOSS_Y_POSITION_OFFSET);

            checkBossCollisionsWithHero(direction, deltaTime);
            checkStaticObjectCollisionsFor(boss, boss.getCurrentDirection(), BOSS_COLLISION_Y_BOUNDS_OFFSET, false);
            boss.updateBounds();
            worldRenderer.updateBossWalkingSpriteTexture(boss, boss.getCurrentDirection(), boss.getBossType());
        }

        if (bossDoHorizontal > 48) {
            bossDoHorizontal = 0;
        } else {
            bossDoHorizontal++;
        }
    }
}
