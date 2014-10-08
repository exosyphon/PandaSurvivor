package com.courter.pandasurvivor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class PandaSurvivor extends ApplicationAdapter {
    public static final int RIGHT_SIDE_OF_MAP = 7584;
    public static final int LEFT_SIDE_OF_MAP = 0;
    public static final int BOTTOM_OF_MAP = 0;
    public static final int TOP_OF_MAP = 7616;
    public static final float BUTTON_ACTION_BUFFER = 15;
    float lastActionTime = 0;
    float testActionTime = 0;
    float deltaTime;
    World world;
    WorldRenderer worldRenderer;
    public static GAME_STATES game_state;
    boolean firstFingerAlreadyDown;
    boolean secondFingerAlreadyDown;

    enum GAME_STATES {
        RUNNING, GAME_OVER, PAUSED
    }


    @Override
    public void create() {
        Assets.load();
        worldRenderer = new WorldRenderer();
        world = new World(null, worldRenderer);
        game_state = GAME_STATES.RUNNING;
        firstFingerAlreadyDown = false;
        secondFingerAlreadyDown = false;
    }

    @Override
    public void render() {
        if (game_state == GAME_STATES.RUNNING) {
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

            if (testActionTime == 0 || testActionTime > (deltaTime * BUTTON_ACTION_BUFFER)) {
//        worldRenderer.updatePandaShootingSpriteTexture(World.hero.getCurrentDirection());
                for (GameObject gameObject : World.redNinjaList) {
                    Ninja ninja = (Ninja) gameObject;
                    if (ninja.getCurrentDirection() == World.HeroDirections.RIGHT || ninja.getCurrentDirection() == World.HeroDirections.LEFT)
                        worldRenderer.addEnemyFireballSprite(ninja.position.x, ninja.position.y, ninja.getCurrentDirection());
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
            if (OverlapTester.pointInRectangle(WorldRenderer.bagButtonBounds, clickPosition.x, clickPosition.y)) {
                worldRenderer.toggleInventory();
            }
            if (OverlapTester.pointInRectangle(WorldRenderer.armorButtonBounds, clickPosition.x, clickPosition.y)) {
                System.out.println("unicorn fun");
            }
            if (worldRenderer.showInventory) {
                if (WorldRenderer.showInventoryOptions) {
                    ArrayList<Item> inventory = World.hero.getInventory();
                    if (OverlapTester.pointInRectangle(WorldRenderer.currentUseItemBounds, clickPosition.x, clickPosition.y)) {
                        World.ItemActions itemAction = inventory.get(WorldRenderer.currentlySelectedItemIndex).getItemAction();
                        if(itemAction == World.ItemActions.SPAWN_BOSS) {
                            worldRenderer.addPumpkinBoss(World.hero.position.x, World.hero.position.y);
                        }
                        inventory.remove(WorldRenderer.currentlySelectedItemIndex);
                        worldRenderer.currentInventoryUnitBoundsList.remove(worldRenderer.currentInventoryUnitBoundsList.size() - 1);
                    } else if (OverlapTester.pointInRectangle(WorldRenderer.currentDestroyItemBounds, clickPosition.x, clickPosition.y)) {
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
        } else if (game_state == GAME_STATES.GAME_OVER) {
            if (OverlapTester.pointInRectangle(WorldRenderer.retryYesButtonBounds, clickPosition.x, clickPosition.y)) {
                worldRenderer = new WorldRenderer();
                world = new World(null, worldRenderer);
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
            } else if (clickPosition.x < (WorldRenderer.dpadSprite.getX() + WorldRenderer.dpadSprite.getWidth() + 220) && clickPosition.y < (WorldRenderer.dpadSprite.getY() + WorldRenderer.dpadSprite.getHeight() + 200)) {
                handleDpadMovement(clickPosition, heroOriginalX, heroOriginalY);
            }
        }
        return true;
    }

    private void handleAButtonPress(float heroOriginalX, float heroOriginalY) {
        if (lastActionTime == 0 || lastActionTime > (deltaTime * BUTTON_ACTION_BUFFER)) {
            worldRenderer.updatePandaShootingSpriteTexture(World.hero.getCurrentDirection());
            worldRenderer.addFireballSprite(heroOriginalX, heroOriginalY, World.hero.getCurrentDirection());

            lastActionTime = deltaTime;
        }
    }

    private void handleDpadMovement(Vector3 position, float heroOriginalX, float heroOriginalY) {
        if (position.y < (WorldRenderer.dpadSprite.getY() + 180) && position.y > (WorldRenderer.dpadSprite.getY() + 48)) {
            if (position.x < (WorldRenderer.dpadSprite.getX() + 166) && heroOriginalX > LEFT_SIDE_OF_MAP) {
                //left
                World.hero.setCurrentDirection(World.HeroDirections.LEFT);
                World.hero.position.x = World.hero.position.x - (World.HERO_MOVE_SPEED * deltaTime);
                World.hero.update(deltaTime);

                world.checkStaticObjectCollisionsFor(World.hero, World.HeroDirections.LEFT, 10, true);
                World.hero.updateBounds();

                worldRenderer.updatePandaWalkingSpriteTexture(World.HeroDirections.LEFT);
                worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, World.HeroDirections.LEFT);
            } else if (position.x > (WorldRenderer.dpadSprite.getX() + 168) && heroOriginalX < RIGHT_SIDE_OF_MAP) {
                //right
                World.hero.setCurrentDirection(World.HeroDirections.RIGHT);
                World.hero.position.x = World.hero.position.x + (World.HERO_MOVE_SPEED * deltaTime);
                World.hero.update(deltaTime);

                world.checkStaticObjectCollisionsFor(World.hero, World.HeroDirections.RIGHT, 10, true);
                World.hero.updateBounds();

                worldRenderer.updatePandaWalkingSpriteTexture(World.HeroDirections.RIGHT);
                worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, World.HeroDirections.RIGHT);
            }
        } else {
            if (position.y < (WorldRenderer.dpadSprite.getY() + 125) && heroOriginalY > BOTTOM_OF_MAP) {
                //down
                World.hero.setCurrentDirection(World.HeroDirections.DOWN);
                World.hero.position.y = World.hero.position.y - (World.HERO_MOVE_SPEED * deltaTime);
                World.hero.update(deltaTime);

                world.checkStaticObjectCollisionsFor(World.hero, World.HeroDirections.DOWN, 10, true);
                World.hero.updateBounds();

                worldRenderer.updatePandaWalkingSpriteTexture(World.HeroDirections.DOWN);
                worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, World.HeroDirections.DOWN);
            } else if (position.y > (WorldRenderer.dpadSprite.getY() + 188) && heroOriginalY < TOP_OF_MAP) {
                //up
                World.hero.setCurrentDirection(World.HeroDirections.UP);
                World.hero.position.y = World.hero.position.y + (World.HERO_MOVE_SPEED * deltaTime);
                World.hero.update(deltaTime);

                world.checkStaticObjectCollisionsFor(World.hero, World.HeroDirections.UP, 10, true);
                World.hero.updateBounds();

                worldRenderer.updatePandaWalkingSpriteTexture(World.HeroDirections.UP);
                worldRenderer.updateControlSprites(heroOriginalX, heroOriginalY, World.HeroDirections.UP);
            }
        }
    }
}