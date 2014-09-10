package com.courter.pandasurvivor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

public class PandaSurvivor extends ApplicationAdapter {
    public static final int RIGHT_SIDE_OF_MAP = 7584;
    public static final int LEFT_SIDE_OF_MAP = 0;
    public static final int BOTTOM_OF_MAP = 0;
    public static final int TOP_OF_MAP = 7616;
    public static final float BUTTON_ACTION_BUFFER = 20;
    float lastActionTime = 0;
    float deltaTime;
    World world;
    WorldRenderer worldRenderer;


    @Override
    public void create() {
        Assets.load();
        worldRenderer = new WorldRenderer();
        world = new World(null, WorldRenderer.tiledMapRenderer);
    }

    @Override
    public void render() {
        deltaTime = Gdx.graphics.getDeltaTime();

        world.update(deltaTime);
        worldRenderer.render();

        if (Gdx.input.isTouched(0)) {
            touchDown(Gdx.input.getX(0), Gdx.input.getY(0));
            if (Gdx.input.isTouched(1)) {
                touchDown(Gdx.input.getX(1), Gdx.input.getY(1));
            }
        }

        lastActionTime += deltaTime;
    }

    public boolean touchDown(float screenX, float screenY) {
        Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
        Vector3 clickPosition = WorldRenderer.camera.unproject(clickCoordinates);

        float heroOriginalX = WorldRenderer.heroSprite.getX();
        float heroOriginalY = WorldRenderer.heroSprite.getY();

        if (OverlapTester.pointInRectangle(WorldRenderer.aButtonBounds, clickPosition.x, clickPosition.y)) {
            handleAButtonPress(heroOriginalX, heroOriginalY);
        } else if (clickPosition.x < (WorldRenderer.dpadSprite.getX() + WorldRenderer.dpadSprite.getWidth() + 80) && clickPosition.y < (WorldRenderer.dpadSprite.getY() + WorldRenderer.dpadSprite.getHeight() + 40)) {
            handleDpadMovement(clickPosition, heroOriginalX, heroOriginalY);
        }
        return true;
    }

    private void handleAButtonPress(float heroOriginalX, float heroOriginalY) {
        if (lastActionTime == 0 || lastActionTime > (deltaTime * BUTTON_ACTION_BUFFER)) {
            worldRenderer.updatePandaShootingSpriteTexture(World.hero.getCurrentDirection());
            worldRenderer.addFireballSprite(heroOriginalX, heroOriginalY);

            lastActionTime = deltaTime;
        }
    }

    private void handleDpadMovement(Vector3 position, float heroOriginalX, float heroOriginalY) {
        if (position.y < (WorldRenderer.dpadSprite.getY() + 180) && position.y > (WorldRenderer.dpadSprite.getY() + 68)) {
            if (position.x < (WorldRenderer.dpadSprite.getX() + 125) && heroOriginalX > LEFT_SIDE_OF_MAP) {
                //left
                World.hero.setCurrentDirection(World.HeroDirections.LEFT);
                worldRenderer.updatePandaWalkingSpriteTexture(World.HeroDirections.LEFT);
                worldRenderer.updateCameraAndPandaSpritePositionsLeft(deltaTime, heroOriginalX, heroOriginalY);
            } else if (position.x > (WorldRenderer.dpadSprite.getX() + 148) && heroOriginalX < RIGHT_SIDE_OF_MAP) {
                //right
                World.hero.setCurrentDirection(World.HeroDirections.RIGHT);
                worldRenderer.updatePandaWalkingSpriteTexture(World.HeroDirections.RIGHT);
                worldRenderer.updateCameraAndPandaSpritePositionsRight(deltaTime, heroOriginalX, heroOriginalY);
            }
        } else {
            if (position.y < (WorldRenderer.dpadSprite.getY() + 80) && heroOriginalY > BOTTOM_OF_MAP) {
                //down
                World.hero.setCurrentDirection(World.HeroDirections.DOWN);
                worldRenderer.updatePandaWalkingSpriteTexture(World.HeroDirections.DOWN);
                worldRenderer.updateCameraAndPandaSpritePositionsDown(deltaTime, heroOriginalX, heroOriginalY);
            } else if (position.y > (WorldRenderer.dpadSprite.getY() + 148) && heroOriginalY < TOP_OF_MAP) {
                //up
                World.hero.setCurrentDirection(World.HeroDirections.UP);
                worldRenderer.updatePandaWalkingSpriteTexture(World.HeroDirections.UP);
                worldRenderer.updateCameraAndPandaSpritePositionsUp(deltaTime, heroOriginalX, heroOriginalY);
            }
        }
    }
}