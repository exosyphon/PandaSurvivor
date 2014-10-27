package com.courter.pandasurvivor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class PandaSurvivor extends ApplicationAdapter {
    public static final String PANDA_SNOW_MAP_NAME = "panda_snow.tmx";
    public static final String PANDA_GRASS_MAP_NAME = "panda_grass.tmx";
    public static final String INSIDE_HOUSE_FILENAME = "inside_house.tmx";
    public static ArrayList<String> mapNames = new ArrayList<String>();

    static {
        mapNames.add(PANDA_GRASS_MAP_NAME);
        mapNames.add(PANDA_SNOW_MAP_NAME);
    }

    public static int mapNameIndex = 0;
    public static final int RIGHT_SIDE_OF_MAP = 7584;
    public static final int LEFT_SIDE_OF_MAP = 0;
    public static final int BOTTOM_OF_MAP = 0;
    public static final int TOP_OF_MAP = 7616;
    public static final float BUTTON_ACTION_BUFFER = 15;
    public static final float BASE_ATTACK_SPEED = 30;
    public static float DPAD_EXTRA_X_SPACE;
    public static float DPAD_EXTRA_Y_SPACE;
    public static float DPAD_X_MOVE_OFFSET;
    public static float DPAD_MOVE_SIDEWAYS_Y_UPPER_OFFSET;
    public static float DPAD_MOVE_SIDEWAYS_Y_LOWER_OFFSET;
    public static float DPAD_Y_MOVE_DOWN_OFFSET;
    public static float DPAD_Y_MOVE_UP_OFFSET;
    public static float HERO_X_BOUNDS_OFFSET;
    public static float HERO_Y_BOUNDS_OFFSET;
    float lastActionTime = 0;
    float testActionTime = 0;
    float deltaTime;
    public static int currentLevel = 1;
    World world;
    WorldRenderer worldRenderer;
    public static GAME_STATES game_state;
    boolean firstFingerAlreadyDown;
    boolean secondFingerAlreadyDown;
    boolean shouldRemoveBounds;
    int xpPoints;
    WorldRenderer storedWorldRenderer;

    enum GAME_STATES {
        RUNNING, GAME_OVER, PAUSED
    }


    @Override
    public void create() {
        Assets.load();
        worldRenderer = new WorldRenderer(mapNames.get(mapNameIndex), 100, 100);
        world = new World(null, worldRenderer, true);

        setConstants();

        game_state = GAME_STATES.RUNNING;
        firstFingerAlreadyDown = false;
        secondFingerAlreadyDown = false;
        xpPoints = 0;
    }

    private void setConstants() {
        DPAD_X_MOVE_OFFSET = (WorldRenderer.w * .075f);
        DPAD_MOVE_SIDEWAYS_Y_UPPER_OFFSET = (WorldRenderer.h * .166f);
        DPAD_MOVE_SIDEWAYS_Y_LOWER_OFFSET = (WorldRenderer.h * .054f);
        DPAD_Y_MOVE_DOWN_OFFSET = (WorldRenderer.h * .117f);
        DPAD_Y_MOVE_UP_OFFSET = (WorldRenderer.h * .174f);
        HERO_X_BOUNDS_OFFSET = (WorldRenderer.w * .0066f);
        HERO_Y_BOUNDS_OFFSET = (WorldRenderer.h * .011f);
        DPAD_EXTRA_X_SPACE = (WorldRenderer.w * .156f);
        DPAD_EXTRA_Y_SPACE = (WorldRenderer.h * .212f);
    }

    @Override
    public void render() {
        if (game_state == GAME_STATES.RUNNING) {
            if (world.createNewLevel) {
                currentLevel++;
                mapNameIndex++;
                if (mapNameIndex > mapNames.size() - 1) {
                    mapNameIndex = 0;
                }
                Hero tmpHero = world.hero;
                worldRenderer = worldRenderer.createNewRenderer(mapNames.get(mapNameIndex), 100, 100);
                world = new World(null, worldRenderer, true);
                tmpHero.position.x = world.hero.position.x;
                tmpHero.position.y = world.hero.position.y;
                world.hero = tmpHero;
                world.hero.updateBounds();
                worldRenderer.repopulateInventoryUnitBounds(world.hero.getInventory().size());
            } else if (world.switchToInsideHouse) {
                boolean showinglevelPortal = worldRenderer.showPortalMessage;
                Hero tmpHero = world.hero;
                worldRenderer = worldRenderer.createNewRenderer(INSIDE_HOUSE_FILENAME, 450, 0);
                world = new World(null, worldRenderer, false);
                tmpHero.position.x = world.hero.position.x;
                tmpHero.position.y = world.hero.position.y;
                worldRenderer.showPortalMessage = showinglevelPortal;
                world.hero = tmpHero;
                world.hero.updateBounds();
                worldRenderer.repopulateInventoryUnitBounds(world.hero.getInventory().size());
            } else if (world.leaveHouse) {
                boolean showinglevelPortal = worldRenderer.showPortalMessage;
                Hero tmpHero = world.hero;
                worldRenderer = worldRenderer.createNewRenderer(mapNames.get(mapNameIndex), 100, 290);
                world = new World(null, worldRenderer, true);
                tmpHero.position.x = world.hero.position.x;
                tmpHero.position.y = world.hero.position.y;
                if (showinglevelPortal) {
                    worldRenderer.addLevelPortal();
                }
                world.hero = tmpHero;
                world.hero.updateBounds();
                worldRenderer.repopulateInventoryUnitBounds(world.hero.getInventory().size());
            }

            deltaTime = Gdx.graphics.getDeltaTime();

            world.update(deltaTime);
            worldRenderer.render(World.hero.getCurrentDirection());

            if (Gdx.input.isTouched(0)) {
                if (Gdx.input.justTouched()) {
                    if (!firstFingerAlreadyDown) {
                        tapTouchDown(Gdx.input.getX(0), Gdx.input.getY(0));
                        firstFingerAlreadyDown = true;
                    }
                }

                continualTouchDown(Gdx.input.getX(0), Gdx.input.getY(0));
                if (Gdx.input.isTouched(1)) {
                    continualTouchDown(Gdx.input.getX(1), Gdx.input.getY(1));
                    if (Gdx.input.justTouched()) {
                        if (!secondFingerAlreadyDown) {
                            tapTouchDown(Gdx.input.getX(1), Gdx.input.getY(1));
                            secondFingerAlreadyDown = true;
                        }
                    }
                } else {
                    secondFingerAlreadyDown = false;
                }
            } else if (Gdx.input.isTouched(1)) {
                continualTouchDown(Gdx.input.getX(1), Gdx.input.getY(1));
                firstFingerAlreadyDown = false;
            } else {
                firstFingerAlreadyDown = false;
                secondFingerAlreadyDown = false;
            }

            if ((testActionTime == 0 || testActionTime > (deltaTime * BUTTON_ACTION_BUFFER)) && world.outsideHouse) {
//        worldRenderer.updatePandaShootingSpriteTexture(World.hero.getCurrentDirection());
                float x = WorldRenderer.camera.position.x - WorldRenderer.w / 2;
                float y = WorldRenderer.camera.position.y - WorldRenderer.h / 2;
                for (GameObject gameObject : World.redNinjaList) {
                    Ninja ninja = (Ninja) gameObject;
                    if ((ninja.position.x < (x + WorldRenderer.w) && ninja.position.x > x) && ((ninja.position.y < (y + WorldRenderer.h) && ninja.position.y > y))) {
                        if (ninja.getCurrentDirection() == World.HeroDirections.RIGHT || ninja.getCurrentDirection() == World.HeroDirections.LEFT)
                            worldRenderer.addEnemyFireballSprite(ninja.position.x, ninja.position.y, ninja.getCurrentDirection());
                    }
                }
                testActionTime = deltaTime;
            }
            testActionTime += deltaTime;

            lastActionTime += deltaTime;
        } else if (game_state == GAME_STATES.GAME_OVER) {
            worldRenderer.render(World.hero.getCurrentDirection());

            if (Gdx.input.isTouched(0)) {
                if (Gdx.input.justTouched()) {
                    tapTouchDown(Gdx.input.getX(0), Gdx.input.getY(0));
                }
            }
        }
    }

    public boolean tapTouchDown(float screenX, float screenY) {
        Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
        Vector3 clickPosition = WorldRenderer.camera.unproject(clickCoordinates);

        if (game_state == GAME_STATES.RUNNING) {
            xpPoints = world.hero.getXpPointsToUse();
            if (OverlapTester.pointInRectangle(WorldRenderer.bagButtonBounds, clickPosition.x, clickPosition.y)) {
                worldRenderer.toggleInventory();
            }
            if (OverlapTester.pointInRectangle(WorldRenderer.armorButtonBounds, clickPosition.x, clickPosition.y)) {
                worldRenderer.toggleArmorView();
            }
            if (xpPoints > 0) {
                if (OverlapTester.pointInRectangle(WorldRenderer.showLevelStatsButtonBounds, clickPosition.x, clickPosition.y)) {
                    worldRenderer.openLevelStatsView();
                }
            }

            ArrayList<Item> inventory = World.hero.getInventory();
            if (worldRenderer.showInventory) {
                if (WorldRenderer.showInventoryOptions) {
                    if (OverlapTester.pointInRectangle(WorldRenderer.currentUseItemBounds, clickPosition.x, clickPosition.y)) {
                        Item item = inventory.get(WorldRenderer.currentlySelectedItemIndex);
                        World.ItemActions itemAction = item.getItemAction();
                        if (itemAction == World.ItemActions.SPAWN_BOSS) {
                            worldRenderer.addPumpkinBoss(World.hero.position.x, World.hero.position.y);
                            inventory.remove(WorldRenderer.currentlySelectedItemIndex);
                            worldRenderer.currentInventoryUnitBoundsList.remove(worldRenderer.currentInventoryUnitBoundsList.size() - 1);
                        } else if (itemAction == World.ItemActions.EQUIP) {
                            worldRenderer.openGearStatsView(WorldRenderer.currentlySelectedItemIndex);
                        }
                    } else if (OverlapTester.pointInRectangle(WorldRenderer.currentDestroyItemBounds, clickPosition.x, clickPosition.y)) {
                        worldRenderer.closeGearStatsView();
                        inventory.remove(WorldRenderer.currentlySelectedItemIndex);
                        worldRenderer.currentInventoryUnitBoundsList.remove(worldRenderer.currentInventoryUnitBoundsList.size() - 1);
                    }
                    worldRenderer.toggleInventoryOptions(0);
                } else {
                    int counter = 1;
                    for (Rectangle inventoryUnitBounds : worldRenderer.currentInventoryUnitBoundsList) {
                        if (OverlapTester.pointInRectangle(inventoryUnitBounds, clickPosition.x, clickPosition.y)) {
                            worldRenderer.toggleInventoryOptions(counter);
                        }
                        counter++;
                    }
                }
            }

            if (worldRenderer.showGearStats) {
                Item item = inventory.get(WorldRenderer.currentlyViewingItemIndex);
                if (OverlapTester.pointInRectangle(WorldRenderer.equipGearBounds, clickPosition.x, clickPosition.y)) {
                    shouldRemoveBounds = World.hero.equipItem(item);
                    if (shouldRemoveBounds) {
                        worldRenderer.currentInventoryUnitBoundsList.remove(worldRenderer.currentInventoryUnitBoundsList.size() - 1);
                    }
                    worldRenderer.closeGearStatsView();
                    worldRenderer.closeGearCompareView();
                } else if (OverlapTester.pointInRectangle(WorldRenderer.showGearStatsCloseBounds, clickPosition.x, clickPosition.y)) {
                    worldRenderer.closeGearStatsView();
                    worldRenderer.closeGearCompareView();
                } else if (OverlapTester.pointInRectangle(WorldRenderer.showCurrentGearStatsBounds, clickPosition.x, clickPosition.y)) {
                    worldRenderer.openGearCompareView(item);
                } else if (OverlapTester.pointInRectangle(WorldRenderer.showCurrentGearStatsCloseBounds, clickPosition.x, clickPosition.y)) {
                    worldRenderer.closeGearCompareView();
                } else if (OverlapTester.pointInRectangle(WorldRenderer.showGearStatsDestroyBounds, clickPosition.x, clickPosition.y)) {
                    worldRenderer.closeGearStatsView();
                    worldRenderer.closeGearCompareView();
                    inventory.remove(item);
                    worldRenderer.currentInventoryUnitBoundsList.remove(worldRenderer.currentInventoryUnitBoundsList.size() - 1);
                }
            }

            if (worldRenderer.showLevelUpStats) {
                if (OverlapTester.pointInRectangle(WorldRenderer.showLevelStatsCloseBounds, clickPosition.x, clickPosition.y)) {
                    worldRenderer.closeLevelStatsView();
                }

                if (xpPoints > 0) {
                    if (OverlapTester.pointInRectangle(WorldRenderer.showLevelStats1Bounds, clickPosition.x, clickPosition.y)) {
                        world.hero.incrementHealth();
                    } else if (OverlapTester.pointInRectangle(WorldRenderer.showLevelStats2Bounds, clickPosition.x, clickPosition.y)) {
                        world.hero.incrementGoldBonus();
                    } else if (OverlapTester.pointInRectangle(WorldRenderer.showLevelStats3Bounds, clickPosition.x, clickPosition.y)) {
                        world.hero.incrementSpellDmg();
                    } else if (OverlapTester.pointInRectangle(WorldRenderer.showLevelStats4Bounds, clickPosition.x, clickPosition.y)) {
                        world.hero.incrementMeleeDmg();
                    }
                }
            }
        } else if (game_state == GAME_STATES.GAME_OVER) {
            if (OverlapTester.pointInRectangle(WorldRenderer.retryYesButtonBounds, clickPosition.x, clickPosition.y)) {
                worldRenderer = new WorldRenderer(mapNames.get(mapNameIndex), 100, 100);
                world = new World(null, worldRenderer, true);
                game_state = GAME_STATES.RUNNING;
            } else if (OverlapTester.pointInRectangle(WorldRenderer.retryNoButtonBounds, clickPosition.x, clickPosition.y)) {
                //Do something else
            }
        }
        return true;
    }

    public boolean continualTouchDown(float screenX, float screenY) {
        Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
        Vector3 clickPosition = WorldRenderer.camera.unproject(clickCoordinates);

        if (game_state == GAME_STATES.RUNNING) {
            float heroOriginalX = World.hero.position.x;
            float heroOriginalY = World.hero.position.y;

            if (OverlapTester.pointInRectangle(WorldRenderer.aButtonBounds, clickPosition.x, clickPosition.y)) {
                handleAButtonPress(heroOriginalX, heroOriginalY);
            } else if (clickPosition.x < (WorldRenderer.dpadSprite.getX() + WorldRenderer.DPAD_RENDER_SIZE_X + DPAD_EXTRA_X_SPACE) && clickPosition.y < (WorldRenderer.dpadSprite.getY() + WorldRenderer.DPAD_RENDER_SIZE_Y + DPAD_EXTRA_Y_SPACE)) {
                handleDpadMovement(clickPosition, heroOriginalX, heroOriginalY);
            }
        }
        return true;
    }

    private void handleAButtonPress(float heroOriginalX, float heroOriginalY) {
        if (lastActionTime == 0 || lastActionTime > (deltaTime * (BASE_ATTACK_SPEED - World.hero.getAttackSpeed()))) {
            worldRenderer.updatePandaShootingSpriteTexture(World.hero.getCurrentDirection());
            worldRenderer.addFireballSprite(heroOriginalX, heroOriginalY, World.hero.getCurrentDirection());

            lastActionTime = deltaTime;
        }
    }

    private void handleDpadMovement(Vector3 position, float heroOriginalX, float heroOriginalY) {
        if (position.y < (WorldRenderer.dpadSprite.getY() + DPAD_MOVE_SIDEWAYS_Y_UPPER_OFFSET) && position.y > (WorldRenderer.dpadSprite.getY() + DPAD_MOVE_SIDEWAYS_Y_LOWER_OFFSET)) {
            if (position.x < (WorldRenderer.dpadSprite.getX() + DPAD_X_MOVE_OFFSET) && heroOriginalX > LEFT_SIDE_OF_MAP) {
                //left
                World.hero.setCurrentDirection(World.HeroDirections.LEFT);
                World.hero.position.x = World.hero.position.x - (World.HERO_MOVE_SPEED * deltaTime);
                World.hero.update(deltaTime);

                world.checkStaticObjectCollisionsFor(World.hero, World.HeroDirections.LEFT, HERO_X_BOUNDS_OFFSET, true);
                World.hero.updateBounds();

                worldRenderer.updatePandaWalkingSpriteTexture(World.HeroDirections.LEFT);
                worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, World.HeroDirections.LEFT);
            } else if (position.x > (WorldRenderer.dpadSprite.getX() + DPAD_X_MOVE_OFFSET) && heroOriginalX < RIGHT_SIDE_OF_MAP) {
                //right
                World.hero.setCurrentDirection(World.HeroDirections.RIGHT);
                World.hero.position.x = World.hero.position.x + (World.HERO_MOVE_SPEED * deltaTime);
                World.hero.update(deltaTime);

                world.checkStaticObjectCollisionsFor(World.hero, World.HeroDirections.RIGHT, HERO_X_BOUNDS_OFFSET, true);
                World.hero.updateBounds();

                worldRenderer.updatePandaWalkingSpriteTexture(World.HeroDirections.RIGHT);
                worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, World.HeroDirections.RIGHT);
            }
        } else {
            if (position.y < (WorldRenderer.dpadSprite.getY() + DPAD_Y_MOVE_DOWN_OFFSET) && heroOriginalY > BOTTOM_OF_MAP) {
                //down
                World.hero.setCurrentDirection(World.HeroDirections.DOWN);
                World.hero.position.y = World.hero.position.y - (World.HERO_MOVE_SPEED * deltaTime);
                World.hero.update(deltaTime);

                world.checkStaticObjectCollisionsFor(World.hero, World.HeroDirections.DOWN, HERO_Y_BOUNDS_OFFSET, true);
                World.hero.updateBounds();

                worldRenderer.updatePandaWalkingSpriteTexture(World.HeroDirections.DOWN);
                worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, World.HeroDirections.DOWN);
            } else if (position.y > (WorldRenderer.dpadSprite.getY() + DPAD_Y_MOVE_UP_OFFSET) && heroOriginalY < TOP_OF_MAP) {
                //up
                World.hero.setCurrentDirection(World.HeroDirections.UP);
                World.hero.position.y = World.hero.position.y + (World.HERO_MOVE_SPEED * deltaTime);
                World.hero.update(deltaTime);

                world.checkStaticObjectCollisionsFor(World.hero, World.HeroDirections.UP, HERO_Y_BOUNDS_OFFSET, true);
                World.hero.updateBounds();

                worldRenderer.updatePandaWalkingSpriteTexture(World.HeroDirections.UP);
                worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, World.HeroDirections.UP);
            }
        }
    }
}