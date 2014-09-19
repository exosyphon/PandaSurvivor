package com.courter.pandasurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;

public class WorldRenderer {
    public static final Rectangle aButtonBounds = new Rectangle(1590, 50, 196, 196);
    private static final int FRAME_ROWS = 8;
    private static final int FRAME_COLS = 12;
    public static Sprite aButtonSprite;
    public static Sprite dpadSprite;
    public static Sprite healthBarSprite;
    public static Sprite xpBarSprite;
    public static Sprite heroSprite;
    public static OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
    public static OrthographicCamera camera;
    public static ShapeRenderer shapeRenderer;
    public static BitmapFont font;

    TextureRegion[] firstPandaFrames;
    TextureRegion[] firstPandaHitFrames;
    Animation pandaDownAnimation;
    Animation pandaLeftAnimation;
    Animation pandaRightAnimation;
    Animation pandaUpAnimation;
    Animation pandaHitDownAnimation;
    Animation pandaHitLeftAnimation;
    Animation pandaHitRightAnimation;
    Animation pandaHitUpAnimation;
    TiledMap tiledMap;


    public WorldRenderer() {
        font = new BitmapFont(Gdx.files.internal("font.fnt"), false);
        font.setColor(0, 0, 1, 1);
        font.setScale(3, 3);
        shapeRenderer = new ShapeRenderer();
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
        tiledMapRenderer.addControlSprite(healthBarSprite);
        tiledMapRenderer.addControlSprite(xpBarSprite);
    }

    public void render(World.HeroDirections direction) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        updatePandaWalkingSpriteTexture(direction);

        renderObjectSprites();
    }

    public void addHero(float w, float h) {
        slurpPandaFramesIntoAnimations();

        heroSprite = new Sprite(firstPandaFrames[0]);
        heroSprite.setSize(96, 96);
        heroSprite.setPosition(w / 2, h / 2);
        World.hero = new Hero(w / 2, h / 2);
    }

    public void addFireballSprite(float x, float y, World.HeroDirections direction) {
        Sprite fireballSprite = new Sprite(Assets.fireball);
        fireballSprite.setSize(32, 32);
        float fireballSpriteXOffset = heroSprite.getWidth() / 2.5f;
        if (direction == World.HeroDirections.LEFT)
            fireballSpriteXOffset = heroSprite.getWidth() / 3;
        fireballSprite.setPosition(x + (fireballSpriteXOffset), y + (heroSprite.getHeight() / 4));
        tiledMapRenderer.addSprite(fireballSprite);
        World.fireballList.add(new Fireball(x + (fireballSpriteXOffset), y + (heroSprite.getHeight() / 4), fireballSprite, direction));
    }

    public void updateControlSprites(float heroOriginalX, float heroOriginalY, World.HeroDirections direction) {
        switch (direction) {
            case UP:
                updateCameraAndPandaSpritePositionsUp(heroOriginalY);
                break;
            case DOWN:
                updateCameraAndPandaSpritePositionsDown(heroOriginalY);
                break;
            case LEFT:
                updateCameraAndPandaSpritePositionsLeft(heroOriginalX);
                break;
            case RIGHT:
                updateCameraAndPandaSpritePositionsRight(heroOriginalX);
                break;
        }
    }

    public void addEnemyFireballSprite(float x, float y, World.HeroDirections direction) {
        Sprite fireballSprite = new Sprite(Assets.fireball);
        fireballSprite.setSize(32, 32);
        float fireballSpriteXOffset = heroSprite.getWidth() / 2.5f;
        if (direction == World.HeroDirections.LEFT)
            fireballSpriteXOffset = heroSprite.getWidth() / 3;
        fireballSprite.setPosition(x + (fireballSpriteXOffset), y + (heroSprite.getHeight() / 4));
        tiledMapRenderer.addSprite(fireballSprite);
        World.enemyFireballList.add(new Fireball(x + (fireballSpriteXOffset), y + (heroSprite.getHeight() / 4), fireballSprite, direction));
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

    public void updatePandaHitSpriteTexture(World.HeroDirections direction) {
        switch (direction) {
            case UP:
                heroSprite.setRegion(pandaHitUpAnimation.getKeyFrame(World.hero.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case DOWN:
                heroSprite.setRegion(pandaHitDownAnimation.getKeyFrame(World.hero.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case LEFT:
                heroSprite.setRegion(pandaHitLeftAnimation.getKeyFrame(World.hero.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case RIGHT:
                heroSprite.setRegion(pandaHitRightAnimation.getKeyFrame(World.hero.stateTime, Animation.ANIMATION_LOOPING));
                break;
        }
    }

    public void updateCameraAndPandaSpritePositionsLeft(float originalx) {
        heroSprite.setPosition(World.hero.position.x, World.hero.position.y);
        dpadSprite.setPosition(dpadSprite.getX() - (originalx - World.hero.position.x), dpadSprite.getY());
        healthBarSprite.setPosition(healthBarSprite.getX() - (originalx - World.hero.position.x), healthBarSprite.getY());
        xpBarSprite.setPosition(xpBarSprite.getX() - (originalx - World.hero.position.x), xpBarSprite.getY());
        aButtonBounds.setPosition(aButtonBounds.getX() - (originalx - World.hero.position.x), aButtonBounds.getY());
        aButtonSprite.setPosition(aButtonSprite.getX() - (originalx - World.hero.position.x), aButtonSprite.getY());
        camera.translate(-(originalx - World.hero.position.x), 0);
    }

    public void updateCameraAndPandaSpritePositionsRight(float originalx) {
        heroSprite.setPosition(World.hero.position.x, World.hero.position.y);
        dpadSprite.setPosition(dpadSprite.getX() + (World.hero.position.x - originalx), dpadSprite.getY());
        healthBarSprite.setPosition(healthBarSprite.getX() + (World.hero.position.x - originalx), healthBarSprite.getY());
        xpBarSprite.setPosition(xpBarSprite.getX() + (World.hero.position.x - originalx), xpBarSprite.getY());
        aButtonBounds.setPosition(aButtonBounds.getX() + (World.hero.position.x - originalx), aButtonBounds.getY());
        aButtonSprite.setPosition(aButtonSprite.getX() + (World.hero.position.x - originalx), aButtonSprite.getY());
        camera.translate((World.hero.position.x - originalx), 0);
    }

    public void updateCameraAndPandaSpritePositionsDown(float originaly) {
        heroSprite.setPosition(World.hero.position.x, World.hero.position.y);
        dpadSprite.setPosition(dpadSprite.getX(), dpadSprite.getY() - (originaly - World.hero.position.y));
        healthBarSprite.setPosition(healthBarSprite.getX(), healthBarSprite.getY() - (originaly - World.hero.position.y));
        xpBarSprite.setPosition(healthBarSprite.getX(), xpBarSprite.getY() - (originaly - World.hero.position.y));
        aButtonBounds.setPosition(aButtonBounds.getX(), aButtonBounds.getY() - (originaly - World.hero.position.y));
        aButtonSprite.setPosition(aButtonSprite.getX(), aButtonSprite.getY() - (originaly - World.hero.position.y));
        camera.translate(0, -(originaly - World.hero.position.y));
    }

    public void updateCameraAndPandaSpritePositionsUp(float originaly) {
        heroSprite.setPosition(World.hero.position.x, World.hero.position.y);
        dpadSprite.setPosition(dpadSprite.getX(), dpadSprite.getY() + (World.hero.position.y - originaly));
        healthBarSprite.setPosition(healthBarSprite.getX(), healthBarSprite.getY() + (World.hero.position.y - originaly));
        xpBarSprite.setPosition(xpBarSprite.getX(), xpBarSprite.getY() + (World.hero.position.y - originaly));
        aButtonBounds.setPosition(aButtonBounds.getX(), aButtonBounds.getY() + (World.hero.position.y - originaly));
        aButtonSprite.setPosition(aButtonSprite.getX(), aButtonSprite.getY() + (World.hero.position.y - originaly));
        camera.translate(0, (World.hero.position.y - originaly));
    }

    private void slurpPandaFramesIntoAnimations() {
        Texture pandaSheet = Assets.pandaSpriteSheet;
        TextureRegion[][] tmp = TextureRegion.split(pandaSheet, pandaSheet.getWidth() / FRAME_COLS, pandaSheet.getHeight() / FRAME_ROWS);
        firstPandaFrames = new TextureRegion[(FRAME_COLS * FRAME_ROWS) / 2];
        firstPandaHitFrames = new TextureRegion[(FRAME_COLS * FRAME_ROWS) / 2];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                firstPandaHitFrames[index++] = tmp[i][j];
            }
        }
        index = 0;
        for (int i = 4; i < FRAME_ROWS; i++) {
            for (int j = 0; j < 3; j++) {
                firstPandaFrames[index++] = tmp[i][j];
            }
        }

        pandaDownAnimation = new Animation(.2f, firstPandaFrames[0], firstPandaFrames[1], firstPandaFrames[2]);
        pandaLeftAnimation = new Animation(.2f, firstPandaFrames[3], firstPandaFrames[4], firstPandaFrames[5]);
        pandaRightAnimation = new Animation(.2f, firstPandaFrames[6], firstPandaFrames[7], firstPandaFrames[8]);
        pandaUpAnimation = new Animation(.2f, firstPandaFrames[9], firstPandaFrames[10], firstPandaFrames[11]);

        pandaHitDownAnimation = new Animation(.2f, firstPandaHitFrames[0], firstPandaHitFrames[1], firstPandaHitFrames[2]);
        pandaHitLeftAnimation = new Animation(.2f, firstPandaHitFrames[3], firstPandaHitFrames[4], firstPandaHitFrames[5]);
        pandaHitRightAnimation = new Animation(.2f, firstPandaHitFrames[6], firstPandaHitFrames[7], firstPandaHitFrames[8]);
        pandaHitUpAnimation = new Animation(.2f, firstPandaHitFrames[9], firstPandaHitFrames[10], firstPandaHitFrames[11]);
    }

    private void renderObjectSprites() {
        renderFireballs();
        renderEnemyFireballs();
    }

    private void setupControlSprites(float w) {
        dpadSprite = new Sprite(Assets.dpad);
        dpadSprite.setPosition(75, 75);

        aButtonSprite = new Sprite(Assets.aButton);
        aButtonSprite.setPosition(w - Assets.aButton.getWidth() - 75, 100);

        healthBarSprite = new Sprite(Assets.healthBar);
        healthBarSprite.setSize(384, 32);
        healthBarSprite.setPosition(25, camera.viewportHeight - Assets.healthBar.getHeight() - 10);

        xpBarSprite = new Sprite(Assets.healthBar);
        xpBarSprite.setSize(384, 32);
        xpBarSprite.setPosition(25, camera.viewportHeight - Assets.healthBar.getHeight() * 2 - 20);
    }

    private void renderFireballs() {
        int len = World.fireballList.size();
        for (int i = 0; i < len; i++) {
            Fireball fireball = World.fireballList.get(i);
            fireball.getSprite().setPosition(fireball.position.x, fireball.position.y);
        }
    }

    private void renderEnemyFireballs() {
        int len = World.enemyFireballList.size();
        for (int i = 0; i < len; i++) {
            Fireball fireball = World.enemyFireballList.get(i);
            fireball.getSprite().setPosition(fireball.position.x, fireball.position.y);
        }
    }

    public void addWalls() {
        addWallSprite(600, 600);
    }

    public void addEnemy(float x, float y) {
        addEnemySprite(x, y);
    }

    private void addWallSprite(float x, float y) {
        Sprite wallSprite = new Sprite(Assets.wall);
        wallSprite.setSize(64, 64);
        wallSprite.setPosition(x, y);
        tiledMapRenderer.addSprite(wallSprite);
        World.wallList.add(new Wall(x, y, wallSprite));
    }

    private void addEnemySprite(float x, float y) {
        Sprite enemySprite = new Sprite(Assets.enemy);
        enemySprite.setSize(64, 64);
        enemySprite.setPosition(x, y);
        tiledMapRenderer.addSprite(enemySprite);
        World.enemyList.add(new Enemy(x, y, enemySprite));
    }
}
