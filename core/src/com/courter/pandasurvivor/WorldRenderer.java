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
        Gdx.gl.glClearColor(1, 0, 0, 1);
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
        fireballSprite.setPosition(heroOriginalX + (heroSprite.getWidth() / 3), heroOriginalY + (heroSprite.getHeight() / 4));
        tiledMapRenderer.addSprite(fireballSprite);
        World.fireballList.add(new Fireball(heroOriginalX + (heroSprite.getWidth() / 3), heroOriginalY + (heroSprite.getHeight() / 4), fireballSprite));
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
        WorldRenderer.heroSprite.setPosition(originalx - (World.HERO_MOVE_SPEED * deltaTime), originaly);
        WorldRenderer.dpadSprite.setPosition(WorldRenderer.dpadSprite.getX() - (World.HERO_MOVE_SPEED * deltaTime), WorldRenderer.dpadSprite.getY());
        aButtonBounds.setPosition(aButtonBounds.getX() - (World.HERO_MOVE_SPEED * deltaTime), aButtonBounds.getY());
        WorldRenderer.aButtonSprite.setPosition(WorldRenderer.aButtonSprite.getX() - (World.HERO_MOVE_SPEED * deltaTime), WorldRenderer.aButtonSprite.getY());
        WorldRenderer.camera.translate(-(World.HERO_MOVE_SPEED * deltaTime), 0);
    }

    public void updateCameraAndPandaSpritePositionsRight(float deltaTime, float originalx, float originaly) {
        WorldRenderer.heroSprite.setPosition(originalx + (World.HERO_MOVE_SPEED * deltaTime), originaly);
        WorldRenderer.dpadSprite.setPosition(WorldRenderer.dpadSprite.getX() + (World.HERO_MOVE_SPEED * deltaTime), WorldRenderer.dpadSprite.getY());
        aButtonBounds.setPosition(aButtonBounds.getX() + (World.HERO_MOVE_SPEED * deltaTime), aButtonBounds.getY());
        WorldRenderer.aButtonSprite.setPosition(WorldRenderer.aButtonSprite.getX() + (World.HERO_MOVE_SPEED * deltaTime), WorldRenderer.aButtonSprite.getY());
        WorldRenderer.camera.translate((World.HERO_MOVE_SPEED * deltaTime), 0);
    }

    public void updateCameraAndPandaSpritePositionsDown(float deltaTime, float originalx, float originaly) {
        WorldRenderer.heroSprite.setPosition(originalx, originaly - (World.HERO_MOVE_SPEED * deltaTime));
        WorldRenderer.dpadSprite.setPosition(WorldRenderer.dpadSprite.getX(), WorldRenderer.dpadSprite.getY() - (World.HERO_MOVE_SPEED * deltaTime));
        aButtonBounds.setPosition(aButtonBounds.getX(), aButtonBounds.getY() - (World.HERO_MOVE_SPEED * deltaTime));
        WorldRenderer.aButtonSprite.setPosition(WorldRenderer.aButtonSprite.getX(), WorldRenderer.aButtonSprite.getY() - (World.HERO_MOVE_SPEED * deltaTime));
        WorldRenderer.camera.translate(0, -(World.HERO_MOVE_SPEED * deltaTime));
    }

    public void updateCameraAndPandaSpritePositionsUp(float deltaTime, float originalx, float originaly) {
        WorldRenderer.heroSprite.setPosition(originalx, originaly + (World.HERO_MOVE_SPEED * deltaTime));
        WorldRenderer.dpadSprite.setPosition(WorldRenderer.dpadSprite.getX(), WorldRenderer.dpadSprite.getY() + (World.HERO_MOVE_SPEED * deltaTime));
        aButtonBounds.setPosition(aButtonBounds.getX(), aButtonBounds.getY() + (World.HERO_MOVE_SPEED * deltaTime));
        aButtonSprite.setPosition(aButtonSprite.getX(), aButtonSprite.getY() + (World.HERO_MOVE_SPEED * deltaTime));
        camera.translate(0, (World.HERO_MOVE_SPEED * deltaTime));
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
}
