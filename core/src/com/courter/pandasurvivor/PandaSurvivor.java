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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class PandaSurvivor extends ApplicationAdapter {
    enum HeroDirections {
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
    public static final float BUTTON_ACTION_BUFFER = 20;
    float lastActionTime = 0;
    Rectangle aButtonBounds = new Rectangle(1590, 50, 196, 196);
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
    List<Fireball> fireballList;


    @Override
    public void create() {
        Assets.load();

        createObjects();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);

        camera.update();

        addHero(w, h);

        setupControlSprites(w);

        tiledMap = new TmxMapLoader().load(PANDA_SNOW_MAP_NAME);
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
        renderObjectSprites();

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
        Vector3 clickPosition = camera.unproject(clickCoordinates);

        float heroOriginalX = heroSprite.getX();
        float heroOriginalY = heroSprite.getY();

        if (OverlapTester.pointInRectangle(aButtonBounds, clickPosition.x, clickPosition.y)) {
            handleAButtonPress(heroOriginalX, heroOriginalY);
        } else if (clickPosition.x < (dpadSprite.getX() + dpadSprite.getWidth() + 80) && clickPosition.y < (dpadSprite.getY() + dpadSprite.getHeight() + 40)) {
            handleDpadMovement(clickPosition, heroOriginalX, heroOriginalY);
        }
        return true;
    }

    private void handleAButtonPress(float heroOriginalX, float heroOriginalY) {
        if (lastActionTime == 0 || lastActionTime > (deltaTime * BUTTON_ACTION_BUFFER)) {
            updatePandaShootingSpriteTexture(hero.getCurrentDirection());

            Sprite fireballSprite = new Sprite(Assets.fireball);
            fireballSprite.setSize(32, 32);
            fireballSprite.setPosition(heroOriginalX + (heroSprite.getWidth() / 2), heroOriginalY);
            tiledMapRenderer.addSprite(fireballSprite);

            fireballList.add(new Fireball(heroOriginalX + (heroSprite.getWidth() / 2), heroOriginalY, fireballSprite));

            lastActionTime = deltaTime;
        }
    }

    private void handleDpadMovement(Vector3 position, float heroOriginalX, float heroOriginalY) {
        if (position.y < (dpadSprite.getY() + 180) && position.y > (dpadSprite.getY() + 68)) {
            if (position.x < (dpadSprite.getX() + 125) && heroOriginalX > LEFT_SIDE_OF_MAP) {
                //left
                hero.setCurrentDirection(HeroDirections.LEFT);
                updatePandaWalkingSpriteTexture(HeroDirections.LEFT);
                updateCameraAndPandaSpritePositionsLeft(heroOriginalX, heroOriginalY);
            } else if (position.x > (dpadSprite.getX() + 148) && heroOriginalX < RIGHT_SIDE_OF_MAP) {
                //right
                hero.setCurrentDirection(HeroDirections.RIGHT);
                updatePandaWalkingSpriteTexture(HeroDirections.RIGHT);
                updateCameraAndPandaSpritePositionsRight(heroOriginalX, heroOriginalY);
            }
        } else {
            if (position.y < (dpadSprite.getY() + 80) && heroOriginalY > BOTTOM_OF_MAP) {
                //down
                hero.setCurrentDirection(HeroDirections.DOWN);
                updatePandaWalkingSpriteTexture(HeroDirections.DOWN);
                updateCameraAndPandaSpritePositionsDown(heroOriginalX, heroOriginalY);
            } else if (position.y > (dpadSprite.getY() + 148) && heroOriginalY < TOP_OF_MAP) {
                //up
                hero.setCurrentDirection(HeroDirections.UP);
                updatePandaWalkingSpriteTexture(HeroDirections.UP);
                updateCameraAndPandaSpritePositionsUp(heroOriginalX, heroOriginalY);
            }
        }
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
        aButtonBounds.setPosition(aButtonBounds.getX() - (HERO_MOVE_SPEED * deltaTime), aButtonBounds.getY());
        aButtonSprite.setPosition(aButtonSprite.getX() - (HERO_MOVE_SPEED * deltaTime), aButtonSprite.getY());
        camera.translate(-(HERO_MOVE_SPEED * deltaTime), 0);
    }

    private void updateCameraAndPandaSpritePositionsRight(float originalx, float originaly) {
        heroSprite.setPosition(originalx + (HERO_MOVE_SPEED * deltaTime), originaly);
        dpadSprite.setPosition(dpadSprite.getX() + (HERO_MOVE_SPEED * deltaTime), dpadSprite.getY());
        aButtonBounds.setPosition(aButtonBounds.getX() + (HERO_MOVE_SPEED * deltaTime), aButtonBounds.getY());
        aButtonSprite.setPosition(aButtonSprite.getX() + (HERO_MOVE_SPEED * deltaTime), aButtonSprite.getY());
        camera.translate((HERO_MOVE_SPEED * deltaTime), 0);
    }

    private void updateCameraAndPandaSpritePositionsDown(float originalx, float originaly) {
        heroSprite.setPosition(originalx, originaly - (HERO_MOVE_SPEED * deltaTime));
        dpadSprite.setPosition(dpadSprite.getX(), dpadSprite.getY() - (HERO_MOVE_SPEED * deltaTime));
        aButtonBounds.setPosition(aButtonBounds.getX(), aButtonBounds.getY() - (HERO_MOVE_SPEED * deltaTime));
        aButtonSprite.setPosition(aButtonSprite.getX(), aButtonSprite.getY() - (HERO_MOVE_SPEED * deltaTime));
        camera.translate(0, -(HERO_MOVE_SPEED * deltaTime));
    }

    private void updateCameraAndPandaSpritePositionsUp(float originalx, float originaly) {
        heroSprite.setPosition(originalx, originaly + (HERO_MOVE_SPEED * deltaTime));
        dpadSprite.setPosition(dpadSprite.getX(), dpadSprite.getY() + (HERO_MOVE_SPEED * deltaTime));
        aButtonBounds.setPosition(aButtonBounds.getX(), aButtonBounds.getY() + (HERO_MOVE_SPEED * deltaTime));
        aButtonSprite.setPosition(aButtonSprite.getX(), aButtonSprite.getY() + (HERO_MOVE_SPEED * deltaTime));
        camera.translate(0, (HERO_MOVE_SPEED * deltaTime));
    }

    private void updatePandaShootingSpriteTexture(HeroDirections direction) {
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

    private void updatePandaWalkingSpriteTexture(HeroDirections direction) {
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
        updateFireballs();
        checkCollisions();
    }

    private void checkCollisions() {
        checkFireballCollisions();
    }

    private void checkFireballCollisions() {
        for (int i = 0; i < fireballList.size(); i++) {
            Fireball fireball = fireballList.get(i);
            if (fireball.position.x < LEFT_SIDE_OF_MAP ||
                    fireball.position.x > (RIGHT_SIDE_OF_MAP + 64) ||
                    fireball.position.y < BOTTOM_OF_MAP ||
                    fireball.position.y > (TOP_OF_MAP + 64) ||
                    fireball.stateTime > Fireball.FIREBALL_DISTANCE) {
                tiledMapRenderer.removeSprite(fireball.getSprite());
                fireballList.remove(fireball);
            }
        }
    }

    private void updateFireballs() {
        HeroDirections heroDirection = hero.getCurrentDirection();
        for (int i = 0; i < fireballList.size(); i++) {
            Fireball fireball = fireballList.get(i);
            fireball.update(deltaTime, heroDirection);
        }
    }

    private void createObjects() {
        fireballList = new ArrayList<Fireball>();
    }

    private void renderObjectSprites() {
        renderFireballs();
    }

    private void renderFireballs() {
        int len = fireballList.size();
        for (int i = 0; i < len; i++) {
            Fireball fireball = fireballList.get(i);
            fireball.getSprite().setPosition(fireball.position.x, fireball.position.y);
        }
    }
}