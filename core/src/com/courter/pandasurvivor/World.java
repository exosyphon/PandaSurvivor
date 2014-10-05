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

    enum BossTypes {
        PUMPKIN_BOSS
    }

    public interface WorldListener {
        public void hit();

        public void kill();

        public void powerup();
    }

    public static final int LAST_TIME_DAMAGED_BUFFER = 500;
    public static final int HERO_MOVE_SPEED = 520;
    public static final int ENEMY_MOVE_SPEED = 60;
    public static final int BOSS_MOVE_SPEED = 80;
    public static final int COIN_DROP_CHANCE = 50;
    public static final int BOSS_KEY_DROP_CHANCE = 200;
    public static final int ITEMS_VISIBLE_TIME = 7;
    public static final int ITEMS_BLINK_TIME = 5;
    public static final float timeBeforeRegeneration = 25;
    public static final int ninjaKillCountThreshold = 2;
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
    public static Set<Set> worldObjectLists;
    public static Hero hero;
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
        checkBossSpawn();
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
        }
    }

    private void checkBossSpawn() {
        if ((hero.getNinjaKillCount() % ninjaKillCountThreshold) == 0 && bossList.size() == 0 && hero.getNinjaKillCount() > 0)
            worldRenderer.addPumpkinBoss(hero.position.x, hero.position.y);
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
                if (hero.getInventory().size() < hero.getMaxInventorySize()) {
                    hero.addItemToInventory(item);
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
                        enemy.position.x = hero.position.x - 55;
                        hero.position.x = hero.position.x + 40;

                        enemy.update(deltaTime);
                        enemy.getSprite().setPosition(enemy.position.x + Ninja.WALKING_BOUNDS_ENEMY_WIDTH / 6, enemy.position.y - Ninja.WALKING_BOUNDS_ENEMY_HEIGHT / 6);
                        hero.updateBounds();
                        checkStaticObjectCollisionsFor(hero, HeroDirections.RIGHT, 10, true);
                        hero.updateBounds();
                        worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, HeroDirections.RIGHT);
                    } else if (direction == HeroDirections.LEFT) {
                        enemy.position.x = hero.position.x + 55;
                        hero.position.x = hero.position.x - 40;

                        enemy.update(deltaTime);
                        enemy.getSprite().setPosition(enemy.position.x + Ninja.WALKING_BOUNDS_ENEMY_WIDTH / 6, enemy.position.y - Ninja.WALKING_BOUNDS_ENEMY_HEIGHT / 6);
                        hero.updateBounds();
                        checkStaticObjectCollisionsFor(hero, HeroDirections.LEFT, 10, true);
                        hero.updateBounds();
                        worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, HeroDirections.LEFT);
                    } else if (direction == HeroDirections.DOWN) {
                        enemy.position.y = hero.position.y + 64;
                        hero.position.y = hero.position.y - 60;

                        enemy.update(deltaTime);
                        enemy.getSprite().setPosition(enemy.position.x + Ninja.WALKING_BOUNDS_ENEMY_WIDTH / 6, enemy.position.y - Ninja.WALKING_BOUNDS_ENEMY_HEIGHT / 6);
                        hero.updateBounds();
                        checkStaticObjectCollisionsFor(hero, HeroDirections.DOWN, 10, true);
                        hero.updateBounds();
                        worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, HeroDirections.DOWN);
                    } else if (direction == HeroDirections.UP) {
                        enemy.position.y = hero.position.y - 64;
                        hero.position.y = hero.position.y + 60;

                        enemy.update(deltaTime);
                        enemy.getSprite().setPosition(enemy.position.x + Ninja.WALKING_BOUNDS_ENEMY_WIDTH / 6, enemy.position.y - Ninja.WALKING_BOUNDS_ENEMY_HEIGHT / 6);
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

    private void checkBossCollisionsWithHero(HeroDirections direction, float deltaTime) {
        for (int i = 0; i < bossList.size(); i++) {
            Boss boss = (Boss) bossList.get(i);
            float heroOriginalX = hero.position.x;
            float heroOriginalY = hero.position.y;
            if (OverlapTester.overlapRectangles(boss.bounds, hero.bounds)) {
                if (direction == HeroDirections.RIGHT) {
                    boss.position.x = hero.position.x - 75;
                    hero.position.x = hero.position.x + 60;

                    boss.update(deltaTime);
                    boss.getSprite().setPosition(boss.position.x + Boss.WALKING_BOUNDS_BOSS_WIDTH / 4, boss.position.y - Boss.WALKING_BOUNDS_BOSS_HEIGHT / 4);
                    hero.updateBounds();
                    checkStaticObjectCollisionsFor(hero, HeroDirections.RIGHT, 10, true);
                    hero.updateBounds();
                    worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, HeroDirections.RIGHT);
                } else if (direction == HeroDirections.LEFT) {
                    boss.position.x = hero.position.x + 75;
                    hero.position.x = hero.position.x - 60;

                    boss.update(deltaTime);
                    boss.getSprite().setPosition(boss.position.x + Boss.WALKING_BOUNDS_BOSS_WIDTH / 4, boss.position.y - Boss.WALKING_BOUNDS_BOSS_HEIGHT / 4);
                    hero.updateBounds();
                    checkStaticObjectCollisionsFor(hero, HeroDirections.LEFT, 10, true);
                    hero.updateBounds();
                    worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, HeroDirections.LEFT);
                } else if (direction == HeroDirections.DOWN) {
                    boss.position.y = hero.position.y + 64;
                    hero.position.y = hero.position.y - 60;

                    boss.update(deltaTime);
                    boss.getSprite().setPosition(boss.position.x + Boss.WALKING_BOUNDS_BOSS_WIDTH / 4, boss.position.y - Boss.WALKING_BOUNDS_BOSS_HEIGHT / 4);
                    hero.updateBounds();
                    checkStaticObjectCollisionsFor(hero, HeroDirections.DOWN, 10, true);
                    hero.updateBounds();
                    worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, HeroDirections.DOWN);
                } else if (direction == HeroDirections.UP) {
                    boss.position.y = hero.position.y - 64;
                    hero.position.y = hero.position.y + 60;

                    boss.update(deltaTime);
                    boss.getSprite().setPosition(boss.position.x + Boss.WALKING_BOUNDS_BOSS_WIDTH / 4, boss.position.y - Boss.WALKING_BOUNDS_BOSS_HEIGHT / 4);
                    hero.updateBounds();
                    checkStaticObjectCollisionsFor(hero, HeroDirections.UP, 10, true);
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

    private void checkBossCollisions(HeroDirections direction) {
        for (GameObject gameObject : bossList) {
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
                            Ninja ninja = ((Ninja) gameObject);
                            if (OverlapTester.overlapRectangles(ninja.shooting_bounds, fireball.bounds)) {
                                ninja.setHealth(ninja.getHealth() - fireball.getDamageValue());
                                if (ninja.getHealth() <= 0) {
                                    hero.handleXpGain(ninja.getXpGain());
                                    float percentageChance = (float) Math.random() * 100;
                                    if (percentageChance < COIN_DROP_CHANCE) {
                                        worldRenderer.addCoins(ninja.position.x + ninja.WALKING_BOUNDS_ENEMY_WIDTH / 2, ninja.position.y);
                                    }
                                    if (percentageChance < BOSS_KEY_DROP_CHANCE) {
                                        worldRenderer.addBossKey(ninja.position.x + ninja.WALKING_BOUNDS_ENEMY_WIDTH / 2 - 10, ninja.position.y);
                                    }
                                    tiledMapRenderer.removeSprite(ninja.getSprite());
                                    list.remove(ninja);
                                    hero.addNinjaKill();
                                    break;
                                }

                                if (fireball.fireballDirection == HeroDirections.UP) {
                                    ninja.position.y += (HERO_MOVE_SPEED * deltaTime);
                                    ninja.update(deltaTime);
                                    ninja.getSprite().setPosition(ninja.position.x + Ninja.WALKING_BOUNDS_ENEMY_WIDTH / 6, ninja.position.y - Ninja.WALKING_BOUNDS_ENEMY_HEIGHT / 6);

                                    checkStaticObjectCollisionsFor(ninja, HeroDirections.UP, 65, false);
//                        worldRenderer.updateNinjaHitSpriteTexture(enemy.getCurrentDirection());
                                } else if (fireball.fireballDirection == HeroDirections.DOWN) {
                                    ninja.position.y -= (HERO_MOVE_SPEED * deltaTime);
                                    ninja.update(deltaTime);
                                    ninja.getSprite().setPosition(ninja.position.x + Ninja.WALKING_BOUNDS_ENEMY_WIDTH / 6, ninja.position.y - Ninja.WALKING_BOUNDS_ENEMY_HEIGHT / 6);

                                    checkStaticObjectCollisionsFor(ninja, HeroDirections.DOWN, 65, false);
//                        worldRenderer.updateNinjaHitSpriteTexture(enemy.getCurrentDirection());
                                } else if (fireball.fireballDirection == HeroDirections.RIGHT) {
                                    ninja.position.x += (HERO_MOVE_SPEED * deltaTime);
                                    ninja.update(deltaTime);
                                    ninja.getSprite().setPosition(ninja.position.x + Ninja.WALKING_BOUNDS_ENEMY_WIDTH / 6, ninja.position.y - Ninja.WALKING_BOUNDS_ENEMY_HEIGHT / 6);

                                    checkStaticObjectCollisionsFor(ninja, HeroDirections.RIGHT, 65, false);
//                        worldRenderer.updateNinjaHitSpriteTexture(enemy.getCurrentDirection());
                                } else if (fireball.fireballDirection == HeroDirections.LEFT) {
                                    ninja.position.x -= (HERO_MOVE_SPEED * deltaTime);
                                    ninja.update(deltaTime);
                                    ninja.getSprite().setPosition(ninja.position.x + Ninja.WALKING_BOUNDS_ENEMY_WIDTH / 6, ninja.position.y - Ninja.WALKING_BOUNDS_ENEMY_HEIGHT / 6);

                                    checkStaticObjectCollisionsFor(ninja, HeroDirections.LEFT, 65, false);
//                        worldRenderer.updateEnemyHitSpriteTexture(enemy.getCurrentDirection());
                                }

                                tiledMapRenderer.removeSprite(fireball.getSprite());
                                fireballList.remove(fireball);
                                break;
                            }
                        }
                    }

                    if (fireball != null) {
                        for (GameObject gameObject : bossList) {
                            Boss boss = ((Boss) gameObject);
                            if (OverlapTester.overlapRectangles(boss.shooting_bounds, fireball.bounds)) {
                                boss.setHealth(boss.getHealth() - fireball.getDamageValue());
                                if (boss.getHealth() <= 0) {
                                    hero.handleXpGain(boss.getXpGain());
                                    worldRenderer.addCoins(boss.position.x + boss.WALKING_BOUNDS_BOSS_WIDTH / 2, boss.position.y);
                                    worldRenderer.addCoins(boss.position.x - boss.WALKING_BOUNDS_BOSS_WIDTH / 2, boss.position.y + 10);
                                    worldRenderer.addCoins(boss.position.x + boss.WALKING_BOUNDS_BOSS_WIDTH / 2, boss.position.y + 20);
                                    worldRenderer.addCoins(boss.position.x - boss.WALKING_BOUNDS_BOSS_WIDTH / 2, boss.position.y - 10);
                                    worldRenderer.addCoins(boss.position.x - boss.WALKING_BOUNDS_BOSS_WIDTH / 2, boss.position.y - 10);
                                    worldRenderer.addCoins(boss.position.x - boss.WALKING_BOUNDS_BOSS_WIDTH / 2, boss.position.y - 20);
                                    worldRenderer.addCoins(boss.position.x - boss.WALKING_BOUNDS_BOSS_WIDTH / 2, boss.position.y - 20);
                                    tiledMapRenderer.removeSprite(boss.getSprite());
                                    bossList.remove(boss);
                                    hero.addBossKill();
                                    break;
                                }

                                if (fireball.fireballDirection == HeroDirections.UP) {
                                    boss.position.y += (HERO_MOVE_SPEED * deltaTime);
                                    boss.update(deltaTime);
                                    boss.getSprite().setPosition(boss.position.x, boss.position.y - Boss.WALKING_BOUNDS_BOSS_HEIGHT / 4);

                                    checkStaticObjectCollisionsFor(boss, HeroDirections.UP, 65, false);
//                        worldRenderer.updateNinjaHitSpriteTexture(enemy.getCurrentDirection());
                                } else if (fireball.fireballDirection == HeroDirections.DOWN) {
                                    boss.position.y -= (HERO_MOVE_SPEED * deltaTime);
                                    boss.update(deltaTime);
                                    boss.getSprite().setPosition(boss.position.x, boss.position.y - Boss.WALKING_BOUNDS_BOSS_HEIGHT / 4);

                                    checkStaticObjectCollisionsFor(boss, HeroDirections.DOWN, 65, false);
//                        worldRenderer.updateNinjaHitSpriteTexture(enemy.getCurrentDirection());
                                } else if (fireball.fireballDirection == HeroDirections.RIGHT) {
                                    boss.position.x += (HERO_MOVE_SPEED * deltaTime);
                                    boss.update(deltaTime);
                                    boss.getSprite().setPosition(boss.position.x, boss.position.y - Boss.WALKING_BOUNDS_BOSS_HEIGHT / 4);

                                    checkStaticObjectCollisionsFor(boss, HeroDirections.RIGHT, 65, false);
//                        worldRenderer.updateNinjaHitSpriteTexture(enemy.getCurrentDirection());
                                } else if (fireball.fireballDirection == HeroDirections.LEFT) {
                                    boss.position.x -= (HERO_MOVE_SPEED * deltaTime);
                                    boss.update(deltaTime);
                                    boss.getSprite().setPosition(boss.position.x, boss.position.y - Boss.WALKING_BOUNDS_BOSS_HEIGHT / 4);

                                    checkStaticObjectCollisionsFor(boss, HeroDirections.LEFT, 65, false);
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

        Set<Set> input = new HashSet<Set>();
        input.add(treeList);
        input.add(wallList);
        worldObjectLists = input;
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
                ninja.getSprite().setPosition(ninja.position.x + Ninja.WALKING_BOUNDS_ENEMY_WIDTH / 6, ninja.position.y - Ninja.WALKING_BOUNDS_ENEMY_HEIGHT / 6);

                checkEnemyCollisionsWithHero(direction, deltaTime);
                checkStaticObjectCollisionsFor(ninja, ninja.getCurrentDirection(), 65, false);
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
            boss.getSprite().setPosition(boss.position.x, boss.position.y - Boss.WALKING_BOUNDS_BOSS_HEIGHT / 4);

            checkBossCollisionsWithHero(direction, deltaTime);
            checkStaticObjectCollisionsFor(boss, boss.getCurrentDirection(), 65, false);
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
