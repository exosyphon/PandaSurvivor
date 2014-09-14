package com.courter.pandasurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;

public class WorldRenderer {
    public static final Rectangle aButtonBounds = new Rectangle(1590, 50, 196, 196);
    private static final int FRAME_ROWS = 8;
    private static final int FRAME_COLS = 12;
    public static Sprite aButtonSprite;
    public static Sprite dpadSprite;
    public static Sprite heroSprite;
    public static OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
    public static OrthographicCamera camera;
    TextureRegion[] firstPandaFrames;
    Animation pandaDownAnimation;
    Animation pandaLeftAnimation;
    Animation pandaRightAnimation;
    Animation pandaUpAnimation;
    TiledMap tiledMap;


    public WorldRenderer() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);

        camera.update();

        addHero(w, h);

        setupControlSprites(w);

        tiledMap = new TmxMapLoader().load(World.PANDA_SNOW_MAP_NAME);
        tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);
        tiledMapRenderer.addSprite(heroSprite);
        tiledMapRenderer.addControlSprite(dpadSprite);
        tiledMapRenderer.addControlSprite(aButtonSprite);
    }
    
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        renderObjectSprites();
    }

    public void addHero(float w, float h) {
        slurpPandaFramesIntoAnimations();

        heroSprite = new Sprite(firstPandaFrames[0]);
        heroSprite.setSize(96, 96);
        heroSprite.setPosition(w / 2, h / 2);
        World.hero = new Hero(w / 2, h / 2);
    }
    
    public void addFireballSprite(float heroOriginalX, float heroOriginalY) {
        Sprite fireballSprite = new Sprite(Assets.fireball);
        fireballSprite.setSize(32, 32);
        fireballSprite.setPosition(heroOriginalX + (heroSprite.getWidth() / 2.5f), heroOriginalY + (heroSprite.getHeight() / 4));
        tiledMapRenderer.addSprite(fireballSprite);
        World.fireballList.add(new Fireball(heroOriginalX + (heroSprite.getWidth() / 2.5f), heroOriginalY + (heroSprite.getHeight() / 4), fireballSprite));
    }

    public void updatePandaShootingSpriteTexture(World.HeroDirections direction) {
        switch (direction) {
            case UP:
                heroSprite.setRegion(pandaUpAnimation.getKeyFrame(World.hero.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case DOWN:
                heroSprite.setRegion(pandaDownAnimation.getKeyFrame(World.hero.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case LEFT:
                heroSprite.setRegion(pandaLeftAnimation.getKeyFrame(World.hero.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case RIGHT:
                heroSprite.setRegion(pandaRightAnimation.getKeyFrame(World.hero.stateTime, Animation.ANIMATION_LOOPING));
                break;
        }

    }

    public void updatePandaWalkingSpriteTexture(World.HeroDirections direction) {
        switch (direction) {
            case UP:
                heroSprite.setRegion(pandaUpAnimation.getKeyFrame(World.hero.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case DOWN:
                heroSprite.setRegion(pandaDownAnimation.getKeyFrame(World.hero.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case LEFT:
                heroSprite.setRegion(pandaLeftAnimation.getKeyFrame(World.hero.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case RIGHT:
                heroSprite.setRegion(pandaRightAnimation.getKeyFrame(World.hero.stateTime, Animation.ANIMATION_LOOPING));
                break;
        }
    }

    public void updateCameraAndPandaSpritePositionsLeft(float deltaTime, float originalx, float originaly) {
        heroSprite.setPosition(World.hero.position.x, World.hero.position.y);
        dpadSprite.setPosition(dpadSprite.getX() - (originalx - World.hero.position.x), dpadSprite.getY());
        aButtonBounds.setPosition(aButtonBounds.getX() - (originalx - World.hero.position.x), aButtonBounds.getY());
        aButtonSprite.setPosition(aButtonSprite.getX() - (originalx - World.hero.position.x), aButtonSprite.getY());
        camera.translate(-(originalx - World.hero.position.x), 0);
    }

    public void updateCameraAndPandaSpritePositionsRight(float deltaTime, float originalx, float originaly) {
        heroSprite.setPosition(World.hero.position.x, World.hero.position.y);
        dpadSprite.setPosition(dpadSprite.getX() + (World.hero.position.x - originalx), dpadSprite.getY());
        aButtonBounds.setPosition(aButtonBounds.getX() + (World.hero.position.x - originalx), aButtonBounds.getY());
        aButtonSprite.setPosition(aButtonSprite.getX() + (World.hero.position.x - originalx), aButtonSprite.getY());
        camera.translate((World.hero.position.x - originalx), 0);
    }

    public void updateCameraAndPandaSpritePositionsDown(float deltaTime, float originalx, float originaly) {
        heroSprite.setPosition(World.hero.position.x, World.hero.position.y);
        dpadSprite.setPosition(dpadSprite.getX(), dpadSprite.getY() - (originaly - World.hero.position.y));
        aButtonBounds.setPosition(aButtonBounds.getX(), aButtonBounds.getY() - (originaly - World.hero.position.y));
        aButtonSprite.setPosition(aButtonSprite.getX(), aButtonSprite.getY() - (originaly - World.hero.position.y));
        camera.translate(0, -(originaly - World.hero.position.y));
    }

    public void updateCameraAndPandaSpritePositionsUp(float deltaTime, float originalx, float originaly) {
        heroSprite.setPosition(World.hero.position.x, World.hero.position.y);
        dpadSprite.setPosition(dpadSprite.getX(), dpadSprite.getY() + (World.hero.position.y - originaly));
        aButtonBounds.setPosition(aButtonBounds.getX(), aButtonBounds.getY() + (World.hero.position.y - originaly));
        aButtonSprite.setPosition(aButtonSprite.getX(), aButtonSprite.getY() + (World.hero.position.y - originaly));
        camera.translate(0, (World.hero.position.y - originaly));
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

    private void renderObjectSprites() {
        renderFireballs();
    }

    private void setupControlSprites(float w) {
        dpadSprite = new Sprite(Assets.dpad);
        dpadSprite.setPosition(75, 75);

        aButtonSprite = new Sprite(Assets.aButton);
        aButtonSprite.setPosition(w - Assets.aButton.getWidth() - 75, 100);
    }

    private void renderFireballs() {
        int len = World.fireballList.size();
        for (int i = 0; i < len; i++) {
            Fireball fireball = World.fireballList.get(i);
            fireball.getSprite().setPosition(fireball.position.x, fireball.position.y);
        }
    }

    public void addWalls() {
        addWallSprite(600, 600);
    }

    private void addWallSprite(float x, float y) {
        Sprite wallSprite = new Sprite(Assets.impassablePickachu);
        wallSprite.setSize(64, 64);
        wallSprite.setPosition(x, y);
        tiledMapRenderer.addSprite(wallSprite);
        World.wallList.add(new Wall(x, y, wallSprite));
    }
}
