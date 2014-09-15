package com.courter.pandasurvivor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

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

    enum GAME_STATES {
        RUNNING, GAME_OVER
    }


    @Override
    public void create() {
        Assets.load();
        worldRenderer = new WorldRenderer();
        world = new World(null, worldRenderer);
        worldRenderer.addWalls();
        worldRenderer.addEnemy();
        game_state = GAME_STATES.RUNNING;
    }

    @Override
    public void render() {
        if (game_state == GAME_STATES.RUNNING) {
            deltaTime = Gdx.graphics.getDeltaTime();

            world.update(deltaTime);
            worldRenderer.render(World.hero.getCurrentDirection());

            if (Gdx.input.isTouched(0)) {
                touchDown(Gdx.input.getX(0), Gdx.input.getY(0));
                if (Gdx.input.isTouched(1)) {
                    touchDown(Gdx.input.getX(1), Gdx.input.getY(1));
                }
            } else if (Gdx.input.isTouched(1)) {
                touchDown(Gdx.input.getX(1), Gdx.input.getY(1));
            }

            if (testActionTime == 0 || testActionTime > (deltaTime * BUTTON_ACTION_BUFFER)) {
//        worldRenderer.updatePandaShootingSpriteTexture(World.hero.getCurrentDirection());
                if (World.enemyList.size() > 0) {
                    worldRenderer.addEnemyFireballSprite(World.enemyList.get(0).position.x, World.enemyList.get(0).position.y, ((Enemy) World.enemyList.get(0)).getCurrentDirection());

                    testActionTime = deltaTime;
                }
            }
            testActionTime += deltaTime;

            lastActionTime += deltaTime;
        } else if (game_state == GAME_STATES.GAME_OVER) {
            worldRenderer.render(World.hero.getCurrentDirection());
        }
    }

    public boolean touchDown(float screenX, float screenY) {
        Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
        Vector3 clickPosition = WorldRenderer.camera.unproject(clickCoordinates);

        float heroOriginalX = World.hero.position.x;
        float heroOriginalY = World.hero.position.y;

        if (OverlapTester.pointInRectangle(WorldRenderer.aButtonBounds, clickPosition.x, clickPosition.y)) {
            handleAButtonPress(heroOriginalX, heroOriginalY);
        } else if (clickPosition.x < (WorldRenderer.dpadSprite.getX() + WorldRenderer.dpadSprite.getWidth() + 180) && clickPosition.y < (WorldRenderer.dpadSprite.getY() + WorldRenderer.dpadSprite.getHeight() + 140)) {
            handleDpadMovement(clickPosition, heroOriginalX, heroOriginalY);
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
        if (position.y < (WorldRenderer.dpadSprite.getY() + 180) && position.y > (WorldRenderer.dpadSprite.getY() + 68)) {
            if (position.x < (WorldRenderer.dpadSprite.getX() + 125) && heroOriginalX > LEFT_SIDE_OF_MAP) {
                //left
                World.hero.setCurrentDirection(World.HeroDirections.LEFT);
                World.hero.position.x = World.hero.position.x - (World.HERO_MOVE_SPEED * deltaTime);
                World.hero.update(deltaTime);

                world.checkStaticObjectCollisions(World.HeroDirections.LEFT);
                worldRenderer.updatePandaWalkingSpriteTexture(World.HeroDirections.LEFT);
                worldRenderer.updateCameraAndPandaSpritePositionsLeft(heroOriginalX);
            } else if (position.x > (WorldRenderer.dpadSprite.getX() + 148) && heroOriginalX < RIGHT_SIDE_OF_MAP) {
                //right
                World.hero.setCurrentDirection(World.HeroDirections.RIGHT);
                World.hero.position.x = World.hero.position.x + (World.HERO_MOVE_SPEED * deltaTime);
                World.hero.update(deltaTime);

                world.checkStaticObjectCollisions(World.HeroDirections.RIGHT);
                worldRenderer.updatePandaWalkingSpriteTexture(World.HeroDirections.RIGHT);
                worldRenderer.updateCameraAndPandaSpritePositionsRight(heroOriginalX);
            }
        } else {
            if (position.y < (WorldRenderer.dpadSprite.getY() + 80) && heroOriginalY > BOTTOM_OF_MAP) {
                //down
                World.hero.setCurrentDirection(World.HeroDirections.DOWN);
                World.hero.position.y = World.hero.position.y - (World.HERO_MOVE_SPEED * deltaTime);
                World.hero.update(deltaTime);

                world.checkStaticObjectCollisions(World.HeroDirections.DOWN);
                worldRenderer.updatePandaWalkingSpriteTexture(World.HeroDirections.DOWN);
                worldRenderer.updateCameraAndPandaSpritePositionsDown(heroOriginalY);
            } else if (position.y > (WorldRenderer.dpadSprite.getY() + 148) && heroOriginalY < TOP_OF_MAP) {
                //up
                World.hero.setCurrentDirection(World.HeroDirections.UP);
                World.hero.position.y = World.hero.position.y + (World.HERO_MOVE_SPEED * deltaTime);
                World.hero.update(deltaTime);

                world.checkStaticObjectCollisions(World.HeroDirections.UP);
                worldRenderer.updatePandaWalkingSpriteTexture(World.HeroDirections.UP);
                worldRenderer.updateCameraAndPandaSpritePositionsUp(heroOriginalY);
            }
        }
    }
}