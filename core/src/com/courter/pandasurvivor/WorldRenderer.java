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

import java.util.ArrayList;
import java.util.List;

public class WorldRenderer {
    public static final String PANDA_SNOW_MAP_NAME = "panda_snow.tmx";
    public static Rectangle aButtonBounds;
    public static Rectangle bagButtonBounds;
    public static Rectangle retryYesButtonBounds;
    public static Rectangle retryNoButtonBounds;
    private static final int FRAME_ROWS = 8;
    private static final int FRAME_COLS = 12;
    public static Sprite aButtonSprite;
    public static Sprite bagButtonSprite;
    public static Sprite dpadSprite;
    public static Sprite healthBarSprite;
    public static Sprite xpBarSprite;
    public static Sprite heroSprite;
    public static Sprite retrySprite;
    public static Sprite inventorySprite;
    public static Sprite useDestroyInventoryOptionsSprite;
    public static List<Rectangle> inventoryUnitBoundsList;
    public static List<Rectangle> currentInventoryUnitBoundsList;
    public static OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
    public static OrthographicCamera camera;
    public static ShapeRenderer shapeRenderer;
    public static BitmapFont levelFont;
    public static BitmapFont coinFont;
    public static BitmapFont coinFont2;
    public static boolean showInventory;
    public static boolean showInventoryOptions;
    public static int showInventoryOptionsOffsetX;
    public static int showInventoryOptionsOffsetY;

    TextureRegion[] pumpkinBossFrames;
    Animation pumpkinBossDownAnimation;
    Animation pumpkinBossLeftAnimation;
    Animation pumpkinBossRightAnimation;
    Animation pumpkinBossUpAnimation;

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

    TextureRegion[] redNinjaFrames;
    Animation redNinjaDownAnimation;
    Animation redNinjaLeftAnimation;
    Animation redNinjaRightAnimation;
    Animation redNinjaUpAnimation;

    TextureRegion[] purpleNinjaFrames;
    Animation purpleNinjaDownAnimation;
    Animation purpleNinjaLeftAnimation;
    Animation purpleNinjaRightAnimation;
    Animation purpleNinjaUpAnimation;

    TextureRegion[] blackNinjaFrames;
    Animation blackNinjaDownAnimation;
    Animation blackNinjaLeftAnimation;
    Animation blackNinjaRightAnimation;
    Animation blackNinjaUpAnimation;

    TiledMap tiledMap;

    public WorldRenderer() {
        inventoryUnitBoundsList = new ArrayList<Rectangle>();
        currentInventoryUnitBoundsList = new ArrayList<Rectangle>();
        Rectangle inventoryUnitBounds1 = new Rectangle(1300, 870, 170, 120);
        Rectangle inventoryUnitBounds2 = new Rectangle(1480, 870, 170, 120);
        Rectangle inventoryUnitBounds3 = new Rectangle(1660, 870, 170, 120);
        Rectangle inventoryUnitBounds4 = new Rectangle(1300, 680, 170, 120);
        Rectangle inventoryUnitBounds5 = new Rectangle(1480, 680, 170, 120);
        Rectangle inventoryUnitBounds6 = new Rectangle(1660, 680, 170, 120);
        Rectangle inventoryUnitBounds7 = new Rectangle(1300, 490, 170, 120);
        Rectangle inventoryUnitBounds8 = new Rectangle(1480, 490, 170, 120);
        Rectangle inventoryUnitBounds9 = new Rectangle(1660, 490, 170, 120);
        Rectangle inventoryUnitBounds10 = new Rectangle(1300, 300, 170, 120);
        Rectangle inventoryUnitBounds11 = new Rectangle(1480, 300, 170, 120);
        Rectangle inventoryUnitBounds12 = new Rectangle(1660, 300, 170, 120);
        inventoryUnitBoundsList.add(inventoryUnitBounds1);
        inventoryUnitBoundsList.add(inventoryUnitBounds2);
        inventoryUnitBoundsList.add(inventoryUnitBounds3);
        inventoryUnitBoundsList.add(inventoryUnitBounds4);
        inventoryUnitBoundsList.add(inventoryUnitBounds5);
        inventoryUnitBoundsList.add(inventoryUnitBounds6);
        inventoryUnitBoundsList.add(inventoryUnitBounds7);
        inventoryUnitBoundsList.add(inventoryUnitBounds8);
        inventoryUnitBoundsList.add(inventoryUnitBounds9);
        inventoryUnitBoundsList.add(inventoryUnitBounds10);
        inventoryUnitBoundsList.add(inventoryUnitBounds11);
        inventoryUnitBoundsList.add(inventoryUnitBounds12);
        aButtonBounds = new Rectangle(1540, 70, 196, 196);
        bagButtonBounds = new Rectangle(1300, 30, 120, 120);
        levelFont = new BitmapFont(Gdx.files.internal("font.fnt"), false);
        levelFont.setColor(0, 0, 1, 1);
        levelFont.setScale(3, 3);
        coinFont = new BitmapFont(Gdx.files.internal("font.fnt"), false);
        coinFont.setColor(1, 1, 0, 1);
        coinFont.setScale(2, 2);
        coinFont2 = new BitmapFont(Gdx.files.internal("font.fnt"), false);
        coinFont2.setColor(0, 0, 0, 1);
        coinFont2.setScale(2, 2);
        shapeRenderer = new ShapeRenderer();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);

        camera.update();

        slurpFrames();

        addHero(w, h);

        setupControlSprites(w);

        tiledMap = new TmxMapLoader().load(PANDA_SNOW_MAP_NAME);
        tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);
        tiledMapRenderer.addSprite(heroSprite);
        tiledMapRenderer.addControlSprite(dpadSprite);

        showInventory = false;
        showInventoryOptions = false;
    }

    public void addInventoryUnitBounds(int inventorySize) {
        currentInventoryUnitBoundsList.add(inventoryUnitBoundsList.get(inventorySize - 1));
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

    public void toggleInventory() {
        this.showInventory = !showInventory;
        if (!showInventory) {
            showInventoryOptions = false;
        }
    }

    public void toggleInventoryOptions(int input) {
        this.showInventoryOptions = !showInventoryOptions;

        if (input == 1) {
            showInventoryOptionsOffsetX = 0;
            showInventoryOptionsOffsetY = 0;
        } else if (input == 2) {
            showInventoryOptionsOffsetX = 170;
            showInventoryOptionsOffsetY = 0;
        } else if (input == 3) {
            showInventoryOptionsOffsetX = 340;
            showInventoryOptionsOffsetY = 0;
        } else if (input == 4) {
            showInventoryOptionsOffsetX = 0;
            showInventoryOptionsOffsetY = 180;
        } else if (input == 5) {
            showInventoryOptionsOffsetX = 170;
            showInventoryOptionsOffsetY = 180;
        } else if (input == 6) {
            showInventoryOptionsOffsetX = 340;
            showInventoryOptionsOffsetY = 180;
        } else if (input == 7) {
            showInventoryOptionsOffsetX = 0;
            showInventoryOptionsOffsetY = 360;
        } else if (input == 8) {
            showInventoryOptionsOffsetX = 170;
            showInventoryOptionsOffsetY = 360;
        } else if (input == 9) {
            showInventoryOptionsOffsetX = 340;
            showInventoryOptionsOffsetY = 360;
        } else if (input == 10) {
            showInventoryOptionsOffsetX = 0;
            showInventoryOptionsOffsetY = 540;
        } else if (input == 11) {
            showInventoryOptionsOffsetX = 170;
            showInventoryOptionsOffsetY = 540;
        } else if (input == 12) {
            showInventoryOptionsOffsetX = 340;
            showInventoryOptionsOffsetY = 540;
        }
    }

    public void addRetryBounds() {
        retryYesButtonBounds = new Rectangle(aButtonBounds.getX() - 960, aButtonBounds.getY() + 270, 250, 200);
        retryNoButtonBounds = new Rectangle(aButtonBounds.getX() - 560, aButtonBounds.getY() + 270, 250, 200);
    }

    private void slurpFrames() {
        slurpPandaFramesIntoAnimations();
        slurpNinjaFramesIntoAnimations();
        slurpPumpkinBossFramesIntoAnimations();
    }

    public void addHero(float w, float h) {
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

    public void updateNinjaWalkingSpriteTexture(Enemy enemy, World.HeroDirections direction, World.NinjaTypes ninjaType) {
        switch (ninjaType) {
            case RED:
                updateRedNinjaWalkingSpriteTexture(enemy, direction);
                break;
            case BLACK:
                updateBlackNinjaWalkingSpriteTexture(enemy, direction);
                break;
            case PURPLE:
                updatePurpleNinjaWalkingSpriteTexture(enemy, direction);
                break;
        }
    }

    public void updateRedNinjaWalkingSpriteTexture(Enemy enemy, World.HeroDirections direction) {
        switch (direction) {
            case UP:
                enemy.getSprite().setRegion(redNinjaUpAnimation.getKeyFrame(enemy.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case DOWN:
                enemy.getSprite().setRegion(redNinjaDownAnimation.getKeyFrame(enemy.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case LEFT:
                enemy.getSprite().setRegion(redNinjaLeftAnimation.getKeyFrame(enemy.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case RIGHT:
                enemy.getSprite().setRegion(redNinjaRightAnimation.getKeyFrame(enemy.stateTime, Animation.ANIMATION_LOOPING));
                break;
        }
    }

    public void updateBlackNinjaWalkingSpriteTexture(Enemy enemy, World.HeroDirections direction) {
        switch (direction) {
            case UP:
                enemy.getSprite().setRegion(blackNinjaUpAnimation.getKeyFrame(enemy.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case DOWN:
                enemy.getSprite().setRegion(blackNinjaDownAnimation.getKeyFrame(enemy.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case LEFT:
                enemy.getSprite().setRegion(blackNinjaLeftAnimation.getKeyFrame(enemy.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case RIGHT:
                enemy.getSprite().setRegion(blackNinjaRightAnimation.getKeyFrame(enemy.stateTime, Animation.ANIMATION_LOOPING));
                break;
        }
    }

    public void updatePurpleNinjaWalkingSpriteTexture(Enemy enemy, World.HeroDirections direction) {
        switch (direction) {
            case UP:
                enemy.getSprite().setRegion(purpleNinjaUpAnimation.getKeyFrame(enemy.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case DOWN:
                enemy.getSprite().setRegion(purpleNinjaDownAnimation.getKeyFrame(enemy.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case LEFT:
                enemy.getSprite().setRegion(purpleNinjaLeftAnimation.getKeyFrame(enemy.stateTime, Animation.ANIMATION_LOOPING));
                break;
            case RIGHT:
                enemy.getSprite().setRegion(purpleNinjaRightAnimation.getKeyFrame(enemy.stateTime, Animation.ANIMATION_LOOPING));
                break;
        }
    }

    public void updateBossWalkingSpriteTexture(Boss boss, World.HeroDirections direction, World.BossTypes bossType) {
        if (bossType == World.BossTypes.PUMPKIN_BOSS) {
            switch (direction) {
                case UP:
                    boss.getSprite().setRegion(pumpkinBossUpAnimation.getKeyFrame(boss.stateTime, Animation.ANIMATION_LOOPING));
                    break;
                case DOWN:
                    boss.getSprite().setRegion(pumpkinBossDownAnimation.getKeyFrame(boss.stateTime, Animation.ANIMATION_LOOPING));
                    break;
                case LEFT:
                    boss.getSprite().setRegion(pumpkinBossLeftAnimation.getKeyFrame(boss.stateTime, Animation.ANIMATION_LOOPING));
                    break;
                case RIGHT:
                    boss.getSprite().setRegion(pumpkinBossRightAnimation.getKeyFrame(boss.stateTime, Animation.ANIMATION_LOOPING));
                    break;
            }
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
        aButtonBounds.setPosition(aButtonBounds.getX() - (originalx - World.hero.position.x), aButtonBounds.getY());
        bagButtonBounds.setPosition(bagButtonBounds.getX() - (originalx - World.hero.position.x), bagButtonBounds.getY());
        for (Rectangle inventoryUnitBounds : inventoryUnitBoundsList) {
            inventoryUnitBounds.setPosition(inventoryUnitBounds.getX() - (originalx - World.hero.position.x), inventoryUnitBounds.getY());
        }
        camera.translate(-(originalx - World.hero.position.x), 0);
    }

    public void updateCameraAndPandaSpritePositionsRight(float originalx) {
        heroSprite.setPosition(World.hero.position.x, World.hero.position.y);
        dpadSprite.setPosition(dpadSprite.getX() + (World.hero.position.x - originalx), dpadSprite.getY());
        aButtonBounds.setPosition(aButtonBounds.getX() + (World.hero.position.x - originalx), aButtonBounds.getY());
        bagButtonBounds.setPosition(bagButtonBounds.getX() + (World.hero.position.x - originalx), bagButtonBounds.getY());
        for (Rectangle inventoryUnitBounds : inventoryUnitBoundsList) {
            inventoryUnitBounds.setPosition(inventoryUnitBounds.getX() + (World.hero.position.x - originalx), inventoryUnitBounds.getY());
        }
        camera.translate((World.hero.position.x - originalx), 0);
    }

    public void updateCameraAndPandaSpritePositionsDown(float originaly) {
        heroSprite.setPosition(World.hero.position.x, World.hero.position.y);
        dpadSprite.setPosition(dpadSprite.getX(), dpadSprite.getY() - (originaly - World.hero.position.y));
        aButtonBounds.setPosition(aButtonBounds.getX(), aButtonBounds.getY() - (originaly - World.hero.position.y));
        bagButtonBounds.setPosition(bagButtonBounds.getX(), bagButtonBounds.getY() - (originaly - World.hero.position.y));
        for (Rectangle inventoryUnitBounds : inventoryUnitBoundsList) {
            inventoryUnitBounds.setPosition(inventoryUnitBounds.getX(), inventoryUnitBounds.getY() - (originaly - World.hero.position.y));
        }
        camera.translate(0, -(originaly - World.hero.position.y));
    }

    public void updateCameraAndPandaSpritePositionsUp(float originaly) {
        heroSprite.setPosition(World.hero.position.x, World.hero.position.y);
        dpadSprite.setPosition(dpadSprite.getX(), dpadSprite.getY() + (World.hero.position.y - originaly));
        aButtonBounds.setPosition(aButtonBounds.getX(), aButtonBounds.getY() + (World.hero.position.y - originaly));
        bagButtonBounds.setPosition(bagButtonBounds.getX(), bagButtonBounds.getY() + (World.hero.position.y - originaly));
        for (Rectangle inventoryUnitBounds : inventoryUnitBoundsList) {
            inventoryUnitBounds.setPosition(inventoryUnitBounds.getX(), inventoryUnitBounds.getY() + (World.hero.position.y - originaly));
        }
        camera.translate(0, (World.hero.position.y - originaly));
    }

    private void slurpPumpkinBossFramesIntoAnimations() {
        Texture pumpkinBossSpriteSheet = Assets.pumpkinBossSpriteSheet;
        TextureRegion[][] tmp = TextureRegion.split(pumpkinBossSpriteSheet, pumpkinBossSpriteSheet.getWidth() / 3, pumpkinBossSpriteSheet.getHeight() / 4);
        pumpkinBossFrames = new TextureRegion[3 * 4];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                pumpkinBossFrames[index++] = tmp[i][j];
            }
        }

        pumpkinBossDownAnimation = new Animation(.2f, pumpkinBossFrames[0], pumpkinBossFrames[1], pumpkinBossFrames[2]);
        pumpkinBossRightAnimation = new Animation(.2f, pumpkinBossFrames[3], pumpkinBossFrames[4], pumpkinBossFrames[5]);
        pumpkinBossLeftAnimation = new Animation(.2f, pumpkinBossFrames[6], pumpkinBossFrames[7], pumpkinBossFrames[8]);
        pumpkinBossUpAnimation = new Animation(.2f, pumpkinBossFrames[9], pumpkinBossFrames[10], pumpkinBossFrames[11]);
    }

    private void slurpNinjaFramesIntoAnimations() {
        Texture ninjaSheet = Assets.ninjaSpriteSheet;
        TextureRegion[][] tmp = TextureRegion.split(ninjaSheet, ninjaSheet.getWidth() / FRAME_COLS, ninjaSheet.getHeight() / FRAME_ROWS);
        redNinjaFrames = new TextureRegion[(FRAME_COLS * FRAME_ROWS) / 2];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 3; j < 6; j++) {
                redNinjaFrames[index++] = tmp[i][j];
            }
        }

        redNinjaDownAnimation = new Animation(.2f, redNinjaFrames[0], redNinjaFrames[1], redNinjaFrames[2]);
        redNinjaLeftAnimation = new Animation(.2f, redNinjaFrames[3], redNinjaFrames[4], redNinjaFrames[5]);
        redNinjaRightAnimation = new Animation(.2f, redNinjaFrames[6], redNinjaFrames[7], redNinjaFrames[8]);
        redNinjaUpAnimation = new Animation(.2f, redNinjaFrames[9], redNinjaFrames[10], redNinjaFrames[11]);

        blackNinjaFrames = new TextureRegion[(FRAME_COLS * FRAME_ROWS) / 2];
        index = 0;
        for (int xx = 0; xx < 4; xx++) {
            for (int xy = 0; xy < 3; xy++) {
                blackNinjaFrames[index++] = tmp[xx][xy];
            }
        }

        blackNinjaDownAnimation = new Animation(.2f, blackNinjaFrames[0], blackNinjaFrames[1], blackNinjaFrames[2]);
        blackNinjaLeftAnimation = new Animation(.2f, blackNinjaFrames[3], blackNinjaFrames[4], blackNinjaFrames[5]);
        blackNinjaRightAnimation = new Animation(.2f, blackNinjaFrames[6], blackNinjaFrames[7], blackNinjaFrames[8]);
        blackNinjaUpAnimation = new Animation(.2f, blackNinjaFrames[9], blackNinjaFrames[10], blackNinjaFrames[11]);

        purpleNinjaFrames = new TextureRegion[(FRAME_COLS * FRAME_ROWS) / 2];
        index = 0;
        for (int y = 0; y < 4; y++) {
            for (int yy = 6; yy < 9; yy++) {
                purpleNinjaFrames[index++] = tmp[y][yy];
            }
        }

        purpleNinjaDownAnimation = new Animation(.2f, purpleNinjaFrames[0], purpleNinjaFrames[1], purpleNinjaFrames[2]);
        purpleNinjaLeftAnimation = new Animation(.2f, purpleNinjaFrames[3], purpleNinjaFrames[4], purpleNinjaFrames[5]);
        purpleNinjaRightAnimation = new Animation(.2f, purpleNinjaFrames[6], purpleNinjaFrames[7], purpleNinjaFrames[8]);
        purpleNinjaUpAnimation = new Animation(.2f, purpleNinjaFrames[9], purpleNinjaFrames[10], purpleNinjaFrames[11]);

//        pandaHitDownAnimation = new Animation(.2f, firstPandaHitFrames[0], firstPandaHitFrames[1], firstPandaHitFrames[2]);
//        pandaHitLeftAnimation = new Animation(.2f, firstPandaHitFrames[3], firstPandaHitFrames[4], firstPandaHitFrames[5]);
//        pandaHitRightAnimation = new Animation(.2f, firstPandaHitFrames[6], firstPandaHitFrames[7], firstPandaHitFrames[8]);
//        pandaHitUpAnimation = new Animation(.2f, firstPandaHitFrames[9], firstPandaHitFrames[10], firstPandaHitFrames[11]);
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
        dpadSprite.setPosition(135, 135);

        aButtonSprite = new Sprite(Assets.aButton);
        bagButtonSprite = new Sprite(Assets.bagButton);
        healthBarSprite = new Sprite(Assets.healthBar);
        xpBarSprite = new Sprite(Assets.healthBar);

        retrySprite = new Sprite(Assets.retryPrompt);
        inventorySprite = new Sprite(Assets.inventorySprite);
        useDestroyInventoryOptionsSprite = new Sprite(Assets.useDestroyInventoryOptionsSprite);
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

    public void addCoins(float x, float y) {
        addCoinsSprite(x, y);
    }

    public void addBossKey(float x, float y) {
        addBossKeySprite(x, y);
    }

    public void addNinja(float x, float y, World.NinjaTypes ninjaType) {
        addNinjaSprite(x, y, ninjaType);
    }

    public void addPumpkinBoss(float x, float y) {
        addBossSprite(x, y);
    }

    private void addBossSprite(float x, float y) {
        Sprite bossSprite = new Sprite(pumpkinBossFrames[0]);
        bossSprite.setSize(128, 128);
        bossSprite.setPosition(x - 200, y - 200);
        tiledMapRenderer.addSprite(bossSprite);
        World.bossList.add(new Boss(x, y, bossSprite, World.BossTypes.PUMPKIN_BOSS));
    }

    private void addWallSprite(float x, float y) {
        Sprite wallSprite = new Sprite(Assets.wall);
        wallSprite.setSize(64, 64);
        wallSprite.setPosition(x, y);
        tiledMapRenderer.addSprite(wallSprite);
        World.wallList.add(new Wall(x, y, wallSprite));
    }

    private void addCoinsSprite(float x, float y) {
        Sprite coinsSprite = new Sprite(Assets.coins);
        coinsSprite.setSize(32, 32);
        coinsSprite.setPosition(x, y);
        tiledMapRenderer.addSprite(coinsSprite);
        World.coinsList.add(new Coins(x, y, coinsSprite));
    }

    private void addBossKeySprite(float x, float y) {
        Sprite bossKeySprite = new Sprite(Assets.bossKey);
        bossKeySprite.setSize(32, 32);
        bossKeySprite.setPosition(x, y);
        tiledMapRenderer.addSprite(bossKeySprite);
        World.itemsList.add(new Item(x, y, bossKeySprite));
    }

    private void addNinjaSprite(float x, float y, World.NinjaTypes ninjaType) {
        if (ninjaType == World.NinjaTypes.RED) {
            Sprite enemySprite = new Sprite(redNinjaFrames[0]);
            enemySprite.setSize(96, 96);
            enemySprite.setPosition(x, y);
            tiledMapRenderer.addSprite(enemySprite);
            World.redNinjaList.add(new Ninja(x, y, enemySprite, World.NinjaTypes.RED));
        } else if (ninjaType == World.NinjaTypes.BLACK) {
            Sprite enemySprite = new Sprite(blackNinjaFrames[0]);
            enemySprite.setSize(96, 96);
            enemySprite.setPosition(x, y);
            tiledMapRenderer.addSprite(enemySprite);
            World.blackNinjaList.add(new Ninja(x, y, enemySprite, World.NinjaTypes.BLACK));
        } else if (ninjaType == World.NinjaTypes.PURPLE) {
            Sprite enemySprite = new Sprite(purpleNinjaFrames[0]);
            enemySprite.setSize(96, 96);
            enemySprite.setPosition(x, y);
            tiledMapRenderer.addSprite(enemySprite);
            World.purpleNinjaList.add(new Ninja(x, y, enemySprite, World.NinjaTypes.PURPLE));
        }
    }
}
