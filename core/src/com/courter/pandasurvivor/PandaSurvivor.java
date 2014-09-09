package com.courter.pandasurvivor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector3;

public class PandaSurvivor extends ApplicationAdapter {
    enum Directions {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public static final int HERO_MOVE_SPEED = 520;
    private static final int FRAME_COLS = 12;
    private static final int FRAME_ROWS = 8;
    public static final int RIGHT_SIDE_OF_MAP = 7584;
    public static final int LEFT_SIDE_OF_MAP = 0;
    public static final int BOTTOM_OF_MAP = 0;
    public static final int TOP_OF_MAP = 7616;
    public static final String PANDA_SNOW_MAP_NAME = "panda_snow.tmx";
    OrthographicCamera camera;
    OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
    Sprite heroSprite;
    Sprite dpadSprite;
    Sprite aButtonSprite;
    TextureRegion[] firstPandaFrames;
    Animation pandaDownAnimation;
    Animation pandaLeftAnimation;
    Animation pandaRightAnimation;
    Animation pandaUpAnimation;
    float deltaTime;
    Hero hero;
    TiledMap tiledMap;


    @Override
    public void create() {
        Assets.load();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);

        camera.update();

        addHero(w, h);

        setupControlSprites(w);

        tiledMap = new TmxMapLoader().load("panda_snow.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);
        tiledMapRenderer.addSprite(heroSprite);
        tiledMapRenderer.addControlSprite(dpadSprite);
        tiledMapRenderer.addControlSprite(aButtonSprite);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        deltaTime = Gdx.graphics.getDeltaTime();

        updateSprites();

        if (Gdx.input.isTouched(0)) {
            touchDown(Gdx.input.getX(), Gdx.input.getY());
        }
    }

    public boolean touchDown(float screenX, float screenY) {
        Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
        Vector3 position = camera.unproject(clickCoordinates);

        float originalx = heroSprite.getX();
        float originaly = heroSprite.getY();

        if (position.x < (dpadSprite.getX() + dpadSprite.getWidth() + 80) && position.y < (dpadSprite.getY() + dpadSprite.getHeight() + 40)) {
            if (position.y < (dpadSprite.getY() + 180) && position.y > (dpadSprite.getY() + 68)) {
                if (position.x < (dpadSprite.getX() + 112) && originalx > LEFT_SIDE_OF_MAP) {
                    //left
                    updatePandaSpriteTexture(Directions.LEFT);
                    updateCameraAndPandaSpritePositionsLeft(originalx, originaly);
                } else if (position.x > (dpadSprite.getX() + 148) && originalx < RIGHT_SIDE_OF_MAP) {
                    //right
                    updatePandaSpriteTexture(Directions.RIGHT);
                    updateCameraAndPandaSpritePositionsRight(originalx, originaly);
                }
            } else {
                if (position.y < (dpadSprite.getY() + 80) && originaly > BOTTOM_OF_MAP) {
                    //down
                    updatePandaSpriteTexture(Directions.DOWN);
                    updateCameraAndPandaSpritePositionsDown(originalx, originaly);
                } else if (position.y > (dpadSprite.getY() + 148) && originaly < TOP_OF_MAP) {
                    //up
                    updatePandaSpriteTexture(Directions.UP);
                    updateCameraAndPandaSpritePositionsUp(originalx, originaly);
                }
            }
        }
        return true;
    }

    private void slurpPandaFramesIntoAnimations() {
        Texture pandaSheet = Assets.pandaSpriteSheet;
        TextureRegion[][] tmp = TextureRegion.split(pandaSheet, pandaSheet.getWidth() / FRAME_COLS, pandaSheet.getHeight() / FRAME_ROWS);              // #10
        firstPandaFrames = new TextureRegion[(FRAME_COLS * FRAME_ROWS) / 2];
        int index = 0;
        //First 4 rows are blank in the sprite sheet
        //First 3 columns are for the first panda
        for (int i = 4; i < FRAME_ROWS; i++) {
            for (int j = 0; j < 3; j++) {
                firstPandaFrames[index++] = tmp[i][j];
            }
        }

        pandaDownAnimation = new Animation(.2f, firstPandaFrames[0], firstPandaFrames[1], firstPandaFrames[2]);
        pandaLeftAnimation = new Animation(.2f, firstPandaFrames[3], firstPandaFrames[4], firstPandaFrames[5]);
        pandaRightAnimation = new Animation(.2f, firstPandaFrames[6], firstPandaFrames[7], firstPandaFrames[8]);
        pandaUpAnimation = new Animation(.2f, firstPandaFrames[9], firstPandaFrames[10], firstPandaFrames[11]);
    }

    private void addHero(float w, float h) {
        slurpPandaFramesIntoAnimations();

        heroSprite = new Sprite(firstPandaFrames[0]);
        heroSprite.setSize(96, 96);
        heroSprite.setPosition(w / 2, h / 2);
        hero = new Hero(w / 2, h / 2);
    }

    private void setupControlSprites(float w) {
        dpadSprite = new Sprite(Assets.dpad);
        dpadSprite.setPosition(75, 75);

        aButtonSprite = new Sprite(Assets.aButton);
        aButtonSprite.setPosition(w - Assets.aButton.getWidth() - 75, 100);
    }

    private void updateCameraAndPandaSpritePositionsLeft(float originalx, float originaly) {
        heroSprite.setPosition(originalx - (HERO_MOVE_SPEED * deltaTime), originaly);
        dpadSprite.setPosition(dpadSprite.getX() - (HERO_MOVE_SPEED * deltaTime), dpadSprite.getY());
        aButtonSprite.setPosition(aButtonSprite.getX() - (HERO_MOVE_SPEED * deltaTime), aButtonSprite.getY());
        camera.translate(-(HERO_MOVE_SPEED * deltaTime), 0);
    }

    private void updateCameraAndPandaSpritePositionsRight(float originalx, float originaly) {
        heroSprite.setPosition(originalx + (HERO_MOVE_SPEED * deltaTime), originaly);
        dpadSprite.setPosition(dpadSprite.getX() + (HERO_MOVE_SPEED * deltaTime), dpadSprite.getY());
        aButtonSprite.setPosition(aButtonSprite.getX() + (HERO_MOVE_SPEED * deltaTime), aButtonSprite.getY());
        camera.translate((HERO_MOVE_SPEED * deltaTime), 0);
    }

    private void updateCameraAndPandaSpritePositionsDown(float originalx, float originaly) {
        heroSprite.setPosition(originalx, originaly - (HERO_MOVE_SPEED * deltaTime));
        dpadSprite.setPosition(dpadSprite.getX(), dpadSprite.getY() - (HERO_MOVE_SPEED * deltaTime));
        aButtonSprite.setPosition(aButtonSprite.getX(), aButtonSprite.getY() - (HERO_MOVE_SPEED * deltaTime));
        camera.translate(0, -(HERO_MOVE_SPEED * deltaTime));
    }

    private void updateCameraAndPandaSpritePositionsUp(float originalx, float originaly) {
        heroSprite.setPosition(originalx, originaly + (HERO_MOVE_SPEED * deltaTime));
        dpadSprite.setPosition(dpadSprite.getX(), dpadSprite.getY() + (HERO_MOVE_SPEED * deltaTime));
        aButtonSprite.setPosition(aButtonSprite.getX(), aButtonSprite.getY() + (HERO_MOVE_SPEED * deltaTime));
        camera.translate(0, (HERO_MOVE_SPEED * deltaTime));
    }

    private void updatePandaSpriteTexture(Directions direction) {
        switch (direction) {
            case UP:
                heroSprite.setRegion(pandaUpAnimation.getKeyFrame(hero.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case DOWN:
                heroSprite.setRegion(pandaDownAnimation.getKeyFrame(hero.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case LEFT:
                heroSprite.setRegion(pandaLeftAnimation.getKeyFrame(hero.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case RIGHT:
                heroSprite.setRegion(pandaRightAnimation.getKeyFrame(hero.stateTime, Animation.ANIMATION_LOOPING));
                break;
        }
    }

    private void updateSprites() {
        hero.update(deltaTime);
    }
}