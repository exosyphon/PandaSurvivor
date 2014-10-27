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

// Tested using a screen size of 1794 by 1080 Nexus 5
public class WorldRenderer {
    public static float w = Gdx.graphics.getWidth();
    public static float h = Gdx.graphics.getHeight();

    public static final float FIREBALL_WIDTH = (h * .029f);
    public static final float FIREBALL_HEIGHT = (h * .029f);
    public static final float FIREBALL_Y_POSITION = (h * .022f);
    public static final float BOSS_KEY_WIDTH = (w * .017f);
    public static final float BOSS_KEY_HEIGHT = (h * .029f);
    public static final float COINS_WIDTH = (w * .017f);
    public static final float COINS_HEIGHT = (h * .029f);
    public static final float SWORD_WIDTH = (w * .035f);
    public static final float SWORD_HEIGHT = (h * .059f);
    public static final float HELMET_WIDTH = (w * .035f);
    public static final float HELMET_HEIGHT = (h * .059f);
    public static final float CHESTPIECE_WIDTH = (w * .035f);
    public static final float CHESTPIECE_HEIGHT = (h * .059f);
    public static final float BOOTS_WIDTH = (w * .035f);
    public static final float BOOTS_HEIGHT = (h * .059f);
    public static final float GLOVES_WIDTH = (w * .035f);
    public static final float GLOVES_HEIGHT = (h * .059f);
    public static final float BRACERS_WIDTH = (w * .035f);
    public static final float BRACERS_HEIGHT = (h * .059f);
    public static final float PANTS_WIDTH = (w * .035f);
    public static final float PANTS_HEIGHT = (h * .059f);
    public static final float STAFF_WIDTH = (w * .035f);
    public static final float STAFF_HEIGHT = (h * .059f);
    public static final float NINJA_WIDTH = (w * .053f);
    public static final float NINJA_HEIGHT = (h * .088f);
    public static final float DPAD_RENDER_SIZE_X = (w * .075f);
    public static final float DPAD_RENDER_SIZE_Y = (w * .075f);
    public static Rectangle aButtonBounds;
    public static Rectangle bagButtonBounds;
    public static Rectangle armorButtonBounds;
    public static Rectangle retryYesButtonBounds;
    public static Rectangle retryNoButtonBounds;
    public static Rectangle currentDestroyItemBounds;
    public static Rectangle currentUseItemBounds;
    public static Rectangle equipGearBounds;
    public static Rectangle showGearStatsCloseBounds;
    public static Rectangle showGearStatsDestroyBounds;
    public static Rectangle showCurrentGearStatsBounds;
    public static Rectangle showCurrentGearStatsCloseBounds;
    public static Rectangle showLevelStatsButtonBounds;
    public static Rectangle showLevelStatsCloseBounds;
    public static Rectangle showLevelStats1Bounds;
    public static Rectangle showLevelStats2Bounds;
    public static Rectangle showLevelStats3Bounds;
    public static Rectangle showLevelStats4Bounds;
    public static List<Rectangle> destroyItemBoundsList;
    public static List<Rectangle> useItemBoundsList;
    private static final int FRAME_ROWS = 8;
    private static final int FRAME_COLS = 12;
    public static Sprite aButtonSprite;
    public static Sprite bagButtonSprite;
    public static Sprite armorButtonSprite;
    public static Sprite dpadSprite;
    public static Sprite healthBarSprite;
    public static Sprite xpBarSprite;
    public static Sprite heroSprite;
    public static Sprite retrySprite;
    public static Sprite inventorySprite;
    public static Sprite armorViewSprite;
    public static Sprite useDestroyInventoryOptionsSprite;
    public static Sprite statsDestroyViewSprite;
    public static Sprite emptyBootsSprite;
    public static Sprite emptyBracersSprite;
    public static Sprite emptyPantsSprite;
    public static Sprite emptyGloveSprite;
    public static Sprite emptyChestpieceSprite;
    public static Sprite emptyStaffSprite;
    public static Sprite emptyHelmetSprite;
    public static Sprite gearStatsViewSprite;
    public static Sprite gearStatsWithoutButtonsViewSprite;
    public static Sprite grayAddStatButtonSprite;
    public static Sprite addStatButtonSprite;

    public static List<Rectangle> inventoryUnitBoundsList;
    public static List<Rectangle> currentInventoryUnitBoundsList;
    public static OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
    public static OrthographicCamera camera;
    public static ShapeRenderer shapeRenderer;
    public static BitmapFont levelFont;
    public static BitmapFont coinFont;
    public static BitmapFont coinFont2;
    public static BitmapFont equipmentStatsFont;
    public static BitmapFont portalFont;
    public static boolean showInventory;
    public static boolean showArmorView;
    public static boolean showInventoryOptions;
    public static boolean showGearStats;
    public static boolean showCurrentGearStats;
    public static boolean showLevelUpStats;
    public static boolean showLevelUpStatsButton;
    public static int showInventoryOptionsOffsetX;
    public static int showInventoryOptionsOffsetY;
    public static int currentlySelectedItemIndex;
    public static int currentlyViewingItemIndex;
    public static Item currentlyComparingItem;
    public static boolean showPortalMessage;

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
    TiledMap insideHouseMap;
    TiledMap grassMap;
    TiledMap snowMap;

    public WorldRenderer(String mapName, float startX, float startY) {
        setupConstants();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);

        camera.update();

        slurpFrames();

        addHero(w, h);

        setupControlSprites(w);

        tiledMap = new TmxMapLoader().load(mapName);
        tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);
        tiledMapRenderer.addSprite(heroSprite);
        tiledMapRenderer.addControlSprite(dpadSprite);

        moveStartingPosition(startX, startY);

        showInventory = false;
        showInventoryOptions = false;
        showArmorView = false;
        showGearStats = false;
        showCurrentGearStats = false;
        showLevelUpStats = false;
        showLevelUpStatsButton = false;

        insideHouseMap = new TmxMapLoader().load(PandaSurvivor.INSIDE_HOUSE_FILENAME);
        grassMap = new TmxMapLoader().load(PandaSurvivor.PANDA_GRASS_MAP_NAME);
        snowMap = new TmxMapLoader().load(PandaSurvivor.PANDA_SNOW_MAP_NAME);
    }

    private void setupConstants() {
        currentlySelectedItemIndex = 0;
        currentlyViewingItemIndex = 0;
        currentlyComparingItem = null;
        showPortalMessage = false;
        Rectangle destroyItemBounds1 = new Rectangle((w * .613f), (h * .76f), (w * .111f), (h * .055f));
        Rectangle destroyItemBounds2 = new Rectangle((w * .713f), (h * .76f), (w * .111f), (h * .055f));
        Rectangle destroyItemBounds3 = new Rectangle((w * .813f), (h * .76f), (w * .111f), (h * .055f));
        Rectangle destroyItemBounds4 = new Rectangle((w * .613f), (h * .59f), (w * .111f), (h * .055f));
        Rectangle destroyItemBounds5 = new Rectangle((w * .713f), (h * .59f), (w * .111f), (h * .055f));
        Rectangle destroyItemBounds6 = new Rectangle((w * .813f), (h * .59f), (w * .111f), (h * .055f));
        Rectangle destroyItemBounds7 = new Rectangle((w * .613f), (h * .425f), (w * .111f), (h * .055f));
        Rectangle destroyItemBounds8 = new Rectangle((w * .713f), (h * .425f), (w * .111f), (h * .055f));
        Rectangle destroyItemBounds9 = new Rectangle((w * .813f), (h * .425f), (w * .111f), (h * .055f));
        Rectangle destroyItemBounds10 = new Rectangle((w * .613f), (h * .287f), (w * .111f), (h * .055f));
        Rectangle destroyItemBounds11 = new Rectangle((w * .713f), (h * .287f), (w * .111f), (h * .055f));
        Rectangle destroyItemBounds12 = new Rectangle((w * .813f), (h * .287f), (w * .111f), (h * .055f));
        destroyItemBoundsList = new ArrayList<Rectangle>();
        destroyItemBoundsList.add(destroyItemBounds1);
        destroyItemBoundsList.add(destroyItemBounds2);
        destroyItemBoundsList.add(destroyItemBounds3);
        destroyItemBoundsList.add(destroyItemBounds4);
        destroyItemBoundsList.add(destroyItemBounds5);
        destroyItemBoundsList.add(destroyItemBounds6);
        destroyItemBoundsList.add(destroyItemBounds7);
        destroyItemBoundsList.add(destroyItemBounds8);
        destroyItemBoundsList.add(destroyItemBounds9);
        destroyItemBoundsList.add(destroyItemBounds10);
        destroyItemBoundsList.add(destroyItemBounds11);
        destroyItemBoundsList.add(destroyItemBounds12);

        Rectangle useItemBounds1 = new Rectangle((w * .613f), (h * .833f), (w * .122f), (h * .074f));
        Rectangle useItemBounds2 = new Rectangle((w * .713f), (h * .833f), (w * .122f), (h * .074f));
        Rectangle useItemBounds3 = new Rectangle((w * .813f), (h * .833f), (w * .122f), (h * .074f));
        Rectangle useItemBounds4 = new Rectangle((w * .613f), (h * .666f), (w * .122f), (h * .074f));
        Rectangle useItemBounds5 = new Rectangle((w * .713f), (h * .666f), (w * .122f), (h * .074f));
        Rectangle useItemBounds6 = new Rectangle((w * .813f), (h * .666f), (w * .122f), (h * .074f));
        Rectangle useItemBounds7 = new Rectangle((w * .613f), (h * .5f), (w * .122f), (h * .074f));
        Rectangle useItemBounds8 = new Rectangle((w * .713f), (h * .5f), (w * .122f), (h * .074f));
        Rectangle useItemBounds9 = new Rectangle((w * .813f), (h * .5f), (w * .122f), (h * .074f));
        Rectangle useItemBounds10 = new Rectangle((w * .613f), (h * .333f), (w * .122f), (h * .074f));
        Rectangle useItemBounds11 = new Rectangle((w * .713f), (h * .333f), (w * .122f), (h * .074f));
        Rectangle useItemBounds12 = new Rectangle((w * .813f), (h * .333f), (w * .122f), (h * .074f));
        useItemBoundsList = new ArrayList<Rectangle>();
        useItemBoundsList.add(useItemBounds1);
        useItemBoundsList.add(useItemBounds2);
        useItemBoundsList.add(useItemBounds3);
        useItemBoundsList.add(useItemBounds4);
        useItemBoundsList.add(useItemBounds5);
        useItemBoundsList.add(useItemBounds6);
        useItemBoundsList.add(useItemBounds7);
        useItemBoundsList.add(useItemBounds8);
        useItemBoundsList.add(useItemBounds9);
        useItemBoundsList.add(useItemBounds10);
        useItemBoundsList.add(useItemBounds11);
        useItemBoundsList.add(useItemBounds12);

        inventoryUnitBoundsList = new ArrayList<Rectangle>();
        currentInventoryUnitBoundsList = new ArrayList<Rectangle>();
        Rectangle inventoryUnitBounds1 = new Rectangle((w * .724f), (h * .777f), (w * .094f), (h * .129f));
        Rectangle inventoryUnitBounds2 = new Rectangle((w * .824f), (h * .777f), (w * .094f), (h * .129f));
        Rectangle inventoryUnitBounds3 = new Rectangle((w * .925f), (h * .777f), (w * .094f), (h * .129f));
        Rectangle inventoryUnitBounds4 = new Rectangle((w * .724f), (h * .601f), (w * .094f), (h * .129f));
        Rectangle inventoryUnitBounds5 = new Rectangle((w * .824f), (h * .601f), (w * .094f), (h * .129f));
        Rectangle inventoryUnitBounds6 = new Rectangle((w * .925f), (h * .601f), (w * .094f), (h * .129f));
        Rectangle inventoryUnitBounds7 = new Rectangle((w * .724f), (h * .425f), (w * .094f), (h * .129f));
        Rectangle inventoryUnitBounds8 = new Rectangle((w * .824f), (h * .425f), (w * .094f), (h * .129f));
        Rectangle inventoryUnitBounds9 = new Rectangle((w * .925f), (h * .425f), (w * .094f), (h * .129f));
        Rectangle inventoryUnitBounds10 = new Rectangle((w * .724f), (h * .25f), (w * .094f), (h * .129f));
        Rectangle inventoryUnitBounds11 = new Rectangle((w * .824f), (h * .25f), (w * .094f), (h * .129f));
        Rectangle inventoryUnitBounds12 = new Rectangle((w * .925f), (h * .25f), (w * .094f), (h * .129f));
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
        aButtonBounds = new Rectangle((w * .858f), (h * .064f), (w * .109f), (h * .181f));
        bagButtonBounds = new Rectangle((w * .685f), (h * .027f), (w * .094f), (h * .138f));
        armorButtonBounds = new Rectangle((w * .261f), h - (h * .157f), (w * .094f), (h * .157f));
        showGearStatsCloseBounds = new Rectangle((w * .39f), h - (h * .259f), (w * .053f), (h * .088f));
        equipGearBounds = new Rectangle((w * .351f), h - (h * .555f), (w * .083f), (h * .088f));
        showGearStatsDestroyBounds = new Rectangle((w * .217f), h - (h * .555f), (w * .094f), (h * .088f));
        showCurrentGearStatsBounds = new Rectangle((w * .083f), h - (h * .555f), (w * .117f), (h * .088f));
        showCurrentGearStatsCloseBounds = new Rectangle((w * .947f), h - (h * .259f), (w * .053f), (h * .088f));
        showLevelStatsButtonBounds = new Rectangle((w * .055f), h - (h * .175f), (w * .066f), (h * .111f));
        showLevelStatsCloseBounds = new Rectangle((w * .39f), h - (h * .259f), (w * .053f), (h * .088f));
        showLevelStats1Bounds = new Rectangle((w * .301f), h - (h * .240f), (w * .035f), (h * .059f));
        showLevelStats2Bounds = new Rectangle((w * .301f), h - (h * .310f), (w * .035f), (h * .059f));
        showLevelStats3Bounds = new Rectangle((w * .301f), h - (h * .379f), (w * .035f), (h * .059f));
        showLevelStats4Bounds = new Rectangle((w * .301f), h - (h * .449f), (w * .035f), (h * .059f));
        levelFont = new BitmapFont(Gdx.files.internal("font.fnt"), false);
        levelFont.setColor(0, 0, 1, 1);
        levelFont.setScale((w * .0016f), (h * .0027f));
        coinFont = new BitmapFont(Gdx.files.internal("font.fnt"), false);
        coinFont.setColor(1, 1, 0, 1);
        coinFont.setScale((w * .0011f), (h * .0018f));
        coinFont2 = new BitmapFont(Gdx.files.internal("font.fnt"), false);
        coinFont2.setColor(0, 0, 0, 1);
        coinFont2.setScale((w * .0011f), (h * .0018f));
        equipmentStatsFont = new BitmapFont(Gdx.files.internal("font.fnt"), false);
        equipmentStatsFont.setColor(1, 1, 1, 1);
        equipmentStatsFont.setScale((w * .0011f), (h * .0018f));
        portalFont = new BitmapFont(Gdx.files.internal("font.fnt"), false);
        portalFont.setColor(1, 0, 1, 1);
        portalFont.setScale((w * .0011f), (h * .0018f));
        shapeRenderer = new ShapeRenderer();
    }

    public WorldRenderer createNewRenderer(String mapName, float startX, float startY) {
        setupConstants();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);

        camera.update();

        addHero(w, h);

        setupControlSprites(w);

        moveStartingPosition(startX, startY);

        if (mapName.equals(PandaSurvivor.INSIDE_HOUSE_FILENAME)) {
            tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(insideHouseMap);
        } else if(mapName.equals(PandaSurvivor.PANDA_GRASS_MAP_NAME)) {
            tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(grassMap);
        } else if (mapName.equals(PandaSurvivor.PANDA_SNOW_MAP_NAME)) {
            tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(snowMap);
        }

        tiledMapRenderer.addSprite(heroSprite);
        tiledMapRenderer.addControlSprite(dpadSprite);

        showInventory = false;
        showInventoryOptions = false;
        showArmorView = false;
        showGearStats = false;
        showCurrentGearStats = false;
        showLevelUpStats = false;
        showLevelUpStatsButton = false;

        return this;
    }

    public void addInventoryUnitBounds(int inventorySize) {
        currentInventoryUnitBoundsList.add(inventoryUnitBoundsList.get(inventorySize - 1));
    }

    public void repopulateInventoryUnitBounds(int inventorySize) {
        for (int x = 0; x < inventorySize; x++) {
            currentInventoryUnitBoundsList.add(inventoryUnitBoundsList.get(x));
        }
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
        closeGearCompareView();
        this.showArmorView = false;
        this.showInventory = !showInventory;
        if (!this.showInventory) {
            this.showInventoryOptions = false;
        }
    }

    public void toggleArmorView() {
        closeGearCompareView();
        this.showArmorView = !showArmorView;
        this.showInventory = false;
        this.showInventoryOptions = false;
    }

    public void toggleInventoryOptions(int input) {
        this.showInventoryOptions = !showInventoryOptions;

        if (input == 0) {
            currentlySelectedItemIndex = 0;
            return;
        } else if (input == 1) {
            showInventoryOptionsOffsetX = 0;
            showInventoryOptionsOffsetY = 0;
        } else if (input == 2) {
            showInventoryOptionsOffsetX = (int) (w * .094f);
            showInventoryOptionsOffsetY = 0;
        } else if (input == 3) {
            showInventoryOptionsOffsetX = (int) (w * .189f);
            showInventoryOptionsOffsetY = 0;
        } else if (input == 4) {
            showInventoryOptionsOffsetX = 0;
            showInventoryOptionsOffsetY = (int) (h * .166f);
        } else if (input == 5) {
            showInventoryOptionsOffsetX = (int) (w * .094f);
            showInventoryOptionsOffsetY = (int) (h * .166f);
        } else if (input == 6) {
            showInventoryOptionsOffsetX = (int) (w * .189f);
            showInventoryOptionsOffsetY = (int) (h * .166f);
        } else if (input == 7) {
            showInventoryOptionsOffsetX = 0;
            showInventoryOptionsOffsetY = (int) (h * .333f);
        } else if (input == 8) {
            showInventoryOptionsOffsetX = (int) (w * .094f);
            showInventoryOptionsOffsetY = (int) (h * .333f);
        } else if (input == 9) {
            showInventoryOptionsOffsetX = (int) (w * .189f);
            showInventoryOptionsOffsetY = (int) (h * .333f);
        } else if (input == 10) {
            showInventoryOptionsOffsetX = 0;
            showInventoryOptionsOffsetY = (int) (h * .5f);
        } else if (input == 11) {
            showInventoryOptionsOffsetX = (int) (w * .094f);
            showInventoryOptionsOffsetY = (int) (h * .5f);
        } else if (input == 12) {
            showInventoryOptionsOffsetX = (int) (w * .189f);
            showInventoryOptionsOffsetY = (int) (h * .5f);
        }
        currentlySelectedItemIndex = input - 1;

        currentDestroyItemBounds = destroyItemBoundsList.get(input - 1);
        currentUseItemBounds = useItemBoundsList.get(input - 1);
    }

    public void addRetryBounds() {
        retryYesButtonBounds = new Rectangle(aButtonBounds.getX() - (w * .535f), aButtonBounds.getY() + (h * .25f), (w * .139f), (h * .185f));
        retryNoButtonBounds = new Rectangle(aButtonBounds.getX() - (w * .312f), aButtonBounds.getY() + (h * .25f), (w * .139f), (h * .185f));
    }

    private void slurpFrames() {
        slurpPandaFramesIntoAnimations();
        slurpNinjaFramesIntoAnimations();
        slurpPumpkinBossFramesIntoAnimations();
    }

    public void addHero(float w, float h) {
        heroSprite = new Sprite(firstPandaFrames[0]);
        heroSprite.setSize((w * .053f), (h * .088f));
        heroSprite.setPosition(w / 2, h / 2);
        World.hero = new Hero(w / 2, h / 2);
    }

    public void addFireballSprite(float x, float y, World.HeroDirections direction) {
        Sprite fireballSprite = new Sprite(Assets.fireball);
        fireballSprite.setSize(FIREBALL_WIDTH, FIREBALL_HEIGHT);
        float fireballSpriteXOffset;
        if (direction == World.HeroDirections.LEFT)
            fireballSpriteXOffset = heroSprite.getWidth() / 3;
        else
            fireballSpriteXOffset = heroSprite.getWidth() / 2.5f;
        fireballSprite.setPosition(x + (fireballSpriteXOffset), y + FIREBALL_Y_POSITION);
        tiledMapRenderer.addSprite(fireballSprite);
        World.fireballList.add(new Fireball(x + (fireballSpriteXOffset), y + FIREBALL_Y_POSITION, fireballSprite, direction, PandaSurvivor.currentLevel));
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
        fireballSprite.setSize(FIREBALL_WIDTH, FIREBALL_HEIGHT);
        float fireballSpriteXOffset;
        if (direction == World.HeroDirections.LEFT)
            fireballSpriteXOffset = heroSprite.getWidth() / 3;
        else
            fireballSpriteXOffset = heroSprite.getWidth() / 2.5f;
        fireballSprite.setPosition(x + (fireballSpriteXOffset), y + FIREBALL_Y_POSITION);
        tiledMapRenderer.addSprite(fireballSprite);
        World.enemyFireballList.add(new Fireball(x + (fireballSpriteXOffset), y + FIREBALL_Y_POSITION, fireballSprite, direction, PandaSurvivor.currentLevel));
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
        armorButtonBounds.setPosition(armorButtonBounds.getX() - (originalx - World.hero.position.x), armorButtonBounds.getY());
        equipGearBounds.setPosition(equipGearBounds.getX() - (originalx - World.hero.position.x), equipGearBounds.getY());
        showGearStatsCloseBounds.setPosition(showGearStatsCloseBounds.getX() - (originalx - World.hero.position.x), showGearStatsCloseBounds.getY());
        showCurrentGearStatsBounds.setPosition(showCurrentGearStatsBounds.getX() - (originalx - World.hero.position.x), showCurrentGearStatsBounds.getY());
        showCurrentGearStatsCloseBounds.setPosition(showCurrentGearStatsCloseBounds.getX() - (originalx - World.hero.position.x), showCurrentGearStatsCloseBounds.getY());
        showGearStatsDestroyBounds.setPosition(showGearStatsDestroyBounds.getX() - (originalx - World.hero.position.x), showGearStatsDestroyBounds.getY());
        showLevelStatsButtonBounds.setPosition(showLevelStatsButtonBounds.getX() - (originalx - World.hero.position.x), showLevelStatsButtonBounds.getY());
        showLevelStatsCloseBounds.setPosition(showLevelStatsCloseBounds.getX() - (originalx - World.hero.position.x), showLevelStatsCloseBounds.getY());
        showLevelStats1Bounds.setPosition(showLevelStats1Bounds.getX() - (originalx - World.hero.position.x), showLevelStats1Bounds.getY());
        showLevelStats2Bounds.setPosition(showLevelStats2Bounds.getX() - (originalx - World.hero.position.x), showLevelStats2Bounds.getY());
        showLevelStats3Bounds.setPosition(showLevelStats3Bounds.getX() - (originalx - World.hero.position.x), showLevelStats3Bounds.getY());
        showLevelStats4Bounds.setPosition(showLevelStats4Bounds.getX() - (originalx - World.hero.position.x), showLevelStats4Bounds.getY());
        for (Rectangle destroyItemBounds : destroyItemBoundsList) {
            destroyItemBounds.setPosition(destroyItemBounds.getX() - (originalx - World.hero.position.x), destroyItemBounds.getY());
        }
        for (Rectangle useItemBounds : useItemBoundsList) {
            useItemBounds.setPosition(useItemBounds.getX() - (originalx - World.hero.position.x), useItemBounds.getY());
        }
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
        armorButtonBounds.setPosition(armorButtonBounds.getX() + (World.hero.position.x - originalx), armorButtonBounds.getY());
        equipGearBounds.setPosition(equipGearBounds.getX() + (World.hero.position.x - originalx), equipGearBounds.getY());
        showGearStatsCloseBounds.setPosition(showGearStatsCloseBounds.getX() + (World.hero.position.x - originalx), showGearStatsCloseBounds.getY());
        showCurrentGearStatsBounds.setPosition(showCurrentGearStatsBounds.getX() + (World.hero.position.x - originalx), showCurrentGearStatsBounds.getY());
        showCurrentGearStatsCloseBounds.setPosition(showCurrentGearStatsCloseBounds.getX() + (World.hero.position.x - originalx), showCurrentGearStatsCloseBounds.getY());
        showGearStatsDestroyBounds.setPosition(showGearStatsDestroyBounds.getX() + (World.hero.position.x - originalx), showGearStatsDestroyBounds.getY());
        showLevelStatsButtonBounds.setPosition(showLevelStatsButtonBounds.getX() + (World.hero.position.x - originalx), showLevelStatsButtonBounds.getY());
        showLevelStatsCloseBounds.setPosition(showLevelStatsCloseBounds.getX() + (World.hero.position.x - originalx), showLevelStatsCloseBounds.getY());
        showLevelStats1Bounds.setPosition(showLevelStats1Bounds.getX() + (World.hero.position.x - originalx), showLevelStats1Bounds.getY());
        showLevelStats2Bounds.setPosition(showLevelStats2Bounds.getX() + (World.hero.position.x - originalx), showLevelStats2Bounds.getY());
        showLevelStats3Bounds.setPosition(showLevelStats3Bounds.getX() + (World.hero.position.x - originalx), showLevelStats3Bounds.getY());
        showLevelStats4Bounds.setPosition(showLevelStats4Bounds.getX() + (World.hero.position.x - originalx), showLevelStats4Bounds.getY());
        for (Rectangle destroyItemBounds : destroyItemBoundsList) {
            destroyItemBounds.setPosition(destroyItemBounds.getX() + (World.hero.position.x - originalx), destroyItemBounds.getY());
        }
        for (Rectangle useItemBounds : useItemBoundsList) {
            useItemBounds.setPosition(useItemBounds.getX() + (World.hero.position.x - originalx), useItemBounds.getY());
        }
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
        armorButtonBounds.setPosition(armorButtonBounds.getX(), armorButtonBounds.getY() - (originaly - World.hero.position.y));
        equipGearBounds.setPosition(equipGearBounds.getX(), equipGearBounds.getY() - (originaly - World.hero.position.y));
        showGearStatsCloseBounds.setPosition(showGearStatsCloseBounds.getX(), showGearStatsCloseBounds.getY() - (originaly - World.hero.position.y));
        showCurrentGearStatsBounds.setPosition(showCurrentGearStatsBounds.getX(), showCurrentGearStatsBounds.getY() - (originaly - World.hero.position.y));
        showCurrentGearStatsCloseBounds.setPosition(showCurrentGearStatsCloseBounds.getX(), showCurrentGearStatsCloseBounds.getY() - (originaly - World.hero.position.y));
        showGearStatsDestroyBounds.setPosition(showGearStatsDestroyBounds.getX(), showGearStatsDestroyBounds.getY() - (originaly - World.hero.position.y));
        showLevelStatsButtonBounds.setPosition(showLevelStatsButtonBounds.getX(), showLevelStatsButtonBounds.getY() - (originaly - World.hero.position.y));
        showLevelStatsCloseBounds.setPosition(showLevelStatsCloseBounds.getX(), showLevelStatsCloseBounds.getY() - (originaly - World.hero.position.y));
        showLevelStats1Bounds.setPosition(showLevelStats1Bounds.getX(), showLevelStats1Bounds.getY() - (originaly - World.hero.position.y));
        showLevelStats2Bounds.setPosition(showLevelStats2Bounds.getX(), showLevelStats2Bounds.getY() - (originaly - World.hero.position.y));
        showLevelStats3Bounds.setPosition(showLevelStats3Bounds.getX(), showLevelStats3Bounds.getY() - (originaly - World.hero.position.y));
        showLevelStats4Bounds.setPosition(showLevelStats4Bounds.getX(), showLevelStats4Bounds.getY() - (originaly - World.hero.position.y));
        for (Rectangle destroyItemBounds : destroyItemBoundsList) {
            destroyItemBounds.setPosition(destroyItemBounds.getX(), destroyItemBounds.getY() - (originaly - World.hero.position.y));
        }
        for (Rectangle useItemBounds : useItemBoundsList) {
            useItemBounds.setPosition(useItemBounds.getX(), useItemBounds.getY() - (originaly - World.hero.position.y));
        }
        for (Rectangle inventoryUnitBounds : inventoryUnitBoundsList) {
            inventoryUnitBounds.setPosition(inventoryUnitBounds.getX(), inventoryUnitBounds.getY() - (originaly - World.hero.position.y));
        }
        camera.translate(0, -(originaly - World.hero.position.y));
    }

    public void moveStartingPosition(float x, float y) {
        heroSprite.setPosition(x, y);
        World.hero.position.x = x;
        World.hero.position.y = y;
        World.hero.updateBounds();

        dpadSprite.setPosition(dpadSprite.getX() - (w / 2) + x, dpadSprite.getY() - (h / 2) + y);
        aButtonBounds.setPosition(aButtonBounds.getX() - (w / 2) + x, aButtonBounds.getY() - (h / 2) + y);
        bagButtonBounds.setPosition(bagButtonBounds.getX() - (w / 2) + x, bagButtonBounds.getY() - (h / 2) + y);
        armorButtonBounds.setPosition(armorButtonBounds.getX() - (w / 2) + x, armorButtonBounds.getY() - (h / 2) + y);
        equipGearBounds.setPosition(equipGearBounds.getX() - (w / 2) + x, equipGearBounds.getY() - (h / 2) + y);
        showGearStatsCloseBounds.setPosition(showGearStatsCloseBounds.getX() - (w / 2) + x, showGearStatsCloseBounds.getY() - (h / 2) + y);
        showCurrentGearStatsBounds.setPosition(showCurrentGearStatsBounds.getX() - (w / 2) + x, showCurrentGearStatsBounds.getY() - (h / 2) + y);
        showCurrentGearStatsCloseBounds.setPosition(showCurrentGearStatsCloseBounds.getX() - (w / 2) + x, showCurrentGearStatsCloseBounds.getY() - (h / 2) + y);
        showGearStatsDestroyBounds.setPosition(showGearStatsDestroyBounds.getX() - (w / 2) + x, showGearStatsDestroyBounds.getY() - (h / 2) + y);
        showLevelStatsButtonBounds.setPosition(showLevelStatsButtonBounds.getX() - (w / 2) + x, showLevelStatsButtonBounds.getY() - (h / 2) + y);
        showLevelStatsCloseBounds.setPosition(showLevelStatsCloseBounds.getX() - (w / 2) + x, showLevelStatsCloseBounds.getY() - (h / 2) + y);
        showLevelStats1Bounds.setPosition(showLevelStats1Bounds.getX() - (w / 2) + x, showLevelStats1Bounds.getY() - (h / 2) + y);
        showLevelStats2Bounds.setPosition(showLevelStats2Bounds.getX() - (w / 2) + x, showLevelStats2Bounds.getY() - (h / 2) + y);
        showLevelStats3Bounds.setPosition(showLevelStats3Bounds.getX() - (w / 2) + x, showLevelStats3Bounds.getY() - (h / 2) + y);
        showLevelStats4Bounds.setPosition(showLevelStats4Bounds.getX() - (w / 2) + x, showLevelStats4Bounds.getY() - (h / 2) + y);
        for (Rectangle destroyItemBounds : destroyItemBoundsList) {
            destroyItemBounds.setPosition(destroyItemBounds.getX() - (w / 2) + x, destroyItemBounds.getY() - (h / 2) + y);
        }
        for (Rectangle useItemBounds : useItemBoundsList) {
            useItemBounds.setPosition(useItemBounds.getX() - (w / 2) + x, useItemBounds.getY() - (h / 2) + y);
        }
        for (Rectangle inventoryUnitBounds : inventoryUnitBoundsList) {
            inventoryUnitBounds.setPosition(inventoryUnitBounds.getX() - (w / 2) + x, inventoryUnitBounds.getY() - (h / 2) + y);
        }
        camera.translate(-(w / 2) + x, -(h / 2) + y);
    }

    public void updateCameraAndPandaSpritePositionsUp(float originaly) {
        heroSprite.setPosition(World.hero.position.x, World.hero.position.y);
        dpadSprite.setPosition(dpadSprite.getX(), dpadSprite.getY() + (World.hero.position.y - originaly));
        aButtonBounds.setPosition(aButtonBounds.getX(), aButtonBounds.getY() + (World.hero.position.y - originaly));
        bagButtonBounds.setPosition(bagButtonBounds.getX(), bagButtonBounds.getY() + (World.hero.position.y - originaly));
        armorButtonBounds.setPosition(armorButtonBounds.getX(), armorButtonBounds.getY() + (World.hero.position.y - originaly));
        equipGearBounds.setPosition(equipGearBounds.getX(), equipGearBounds.getY() + (World.hero.position.y - originaly));
        showGearStatsCloseBounds.setPosition(showGearStatsCloseBounds.getX(), showGearStatsCloseBounds.getY() + (World.hero.position.y - originaly));
        showCurrentGearStatsBounds.setPosition(showCurrentGearStatsBounds.getX(), showCurrentGearStatsBounds.getY() + (World.hero.position.y - originaly));
        showCurrentGearStatsCloseBounds.setPosition(showCurrentGearStatsCloseBounds.getX(), showCurrentGearStatsCloseBounds.getY() + (World.hero.position.y - originaly));
        showGearStatsDestroyBounds.setPosition(showGearStatsDestroyBounds.getX(), showGearStatsDestroyBounds.getY() + (World.hero.position.y - originaly));
        showLevelStatsButtonBounds.setPosition(showLevelStatsButtonBounds.getX(), showLevelStatsButtonBounds.getY() + (World.hero.position.y - originaly));
        showLevelStatsCloseBounds.setPosition(showLevelStatsCloseBounds.getX(), showLevelStatsCloseBounds.getY() + (World.hero.position.y - originaly));
        showLevelStats1Bounds.setPosition(showLevelStats1Bounds.getX(), showLevelStats1Bounds.getY() + (World.hero.position.y - originaly));
        showLevelStats2Bounds.setPosition(showLevelStats2Bounds.getX(), showLevelStats2Bounds.getY() + (World.hero.position.y - originaly));
        showLevelStats3Bounds.setPosition(showLevelStats3Bounds.getX(), showLevelStats3Bounds.getY() + (World.hero.position.y - originaly));
        showLevelStats4Bounds.setPosition(showLevelStats4Bounds.getX(), showLevelStats4Bounds.getY() + (World.hero.position.y - originaly));
        for (Rectangle destroyItemBounds : destroyItemBoundsList) {
            destroyItemBounds.setPosition(destroyItemBounds.getX(), destroyItemBounds.getY() + (World.hero.position.y - originaly));
        }
        for (Rectangle useItemBounds : useItemBoundsList) {
            useItemBounds.setPosition(useItemBounds.getX(), useItemBounds.getY() + (World.hero.position.y - originaly));
        }
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
        dpadSprite.setPosition(DPAD_RENDER_SIZE_X, DPAD_RENDER_SIZE_Y);

        aButtonSprite = new Sprite(Assets.aButton);
        bagButtonSprite = new Sprite(Assets.bagButton);
        armorButtonSprite = new Sprite(Assets.armorButton);
        healthBarSprite = new Sprite(Assets.healthBar);
        xpBarSprite = new Sprite(Assets.healthBar);

        retrySprite = new Sprite(Assets.retryPrompt);
        inventorySprite = new Sprite(Assets.inventorySprite);
        useDestroyInventoryOptionsSprite = new Sprite(Assets.useDestroyInventoryOptionsSprite);
        statsDestroyViewSprite = new Sprite(Assets.statsDestroyViewSprite);
        gearStatsViewSprite = new Sprite(Assets.gearStatsViewSprite);
        gearStatsWithoutButtonsViewSprite = new Sprite(Assets.gearStatsWithoutButtonsViewSprite);
        grayAddStatButtonSprite = new Sprite(Assets.grayAddStatButtonSprite);
        addStatButtonSprite = new Sprite(Assets.addStatButtonSprite);

        armorViewSprite = new Sprite(Assets.armorViewSprite);
        emptyBootsSprite = new Sprite(Assets.emptyBootsSprite);
        emptyBracersSprite = new Sprite(Assets.emptyBracersSprite);
        emptyPantsSprite = new Sprite(Assets.emptyPantsSprite);
        emptyGloveSprite = new Sprite(Assets.emptyGloveSprite);
        emptyChestpieceSprite = new Sprite(Assets.emptyChestpieceSprite);
        emptyStaffSprite = new Sprite(Assets.emptyStaffSprite);
        emptyHelmetSprite = new Sprite(Assets.emptyHelmetSprite);
    }

    public void openGearStatsView(int itemToBeViewedIndex) {
        showGearStats = true;
        showLevelUpStats = false;
        currentlyViewingItemIndex = itemToBeViewedIndex;
    }

    public void closeGearStatsView() {
        showGearStats = false;
        currentlyViewingItemIndex = 0;
    }

    public void openLevelStatsView() {
        this.showInventoryOptions = false;
        closeGearStatsView();
        this.showLevelUpStats = true;
    }

    public void closeLevelStatsView() {
        showLevelUpStats = false;
    }

    public void openGearCompareView(Item item) {
        this.showArmorView = false;
        this.showInventory = false;
        this.showInventoryOptions = false;
        showCurrentGearStats = true;
        Item equippedItem = World.hero.getCurrentlyEquippedItemFromItemClass(item);
        currentlyComparingItem = equippedItem;
    }

    public void closeGearCompareView() {
        showCurrentGearStats = false;
        currentlyComparingItem = null;
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

    public void addLevelPortal() {
        this.showPortalMessage = true;
        addLevelPortalSprite(100, 100);
    }

    public void addCoins(float x, float y, float goldBonus) {
        addCoinsSprite(x, y, goldBonus);
    }

    public void addGear(float x, float y) {
        float percentageChance = (float) Math.random() * 100;

        if (percentageChance < 12.5) {
            addSwordSprite(x, y);
        } else if (percentageChance >= 12.5 && percentageChance <= 25) {
            addStaffSprite(x, y);
        } else if (percentageChance >= 25 && percentageChance <= 37.5) {
            addClothHelmetSprite(x, y);
        } else if (percentageChance >= 37.5 && percentageChance <= 50) {
            addClothChestpieceSprite(x, y);
        } else if (percentageChance >= 50 && percentageChance <= 62.5) {
            addClothGlovesSprite(x, y);
        } else if (percentageChance >= 62.5 && percentageChance <= 75) {
            addClothBootsSprite(x, y);
        } else if (percentageChance >= 75 && percentageChance <= 87.5) {
            addClothPantsSprite(x, y);
        } else {
            addClothBracersSprite(x, y);
        }
    }

    public void addBossKey(float x, float y) {
        addBossKeySprite(x, y);
    }

    public void addNinja(float x, float y, World.NinjaTypes ninjaType) {
        addNinjaSprite(x, y, ninjaType);
    }

    public void addHouse(float x, float y) {
        addHouseSprite(x, y);
    }

    public void addPumpkinBoss(float x, float y) {
        addBossSprite(x, y);
    }

    private void addBossSprite(float x, float y) {
        Sprite bossSprite = new Sprite(pumpkinBossFrames[0]);
        bossSprite.setSize((w * .071f), (h * .118f));
        bossSprite.setPosition(x - (w * .111f), y - (h * .185f));
        tiledMapRenderer.addSprite(bossSprite);
        World.bossList.add(new Boss(x, y, bossSprite, World.BossTypes.PUMPKIN_BOSS, PandaSurvivor.currentLevel));
    }

    private void addLevelPortalSprite(float x, float y) {
        Sprite levelPortalSprite = new Sprite(Assets.levelPortalSprite);
        levelPortalSprite.setSize((w * .035f), (h * .118f));
        levelPortalSprite.setPosition(100, 100);
        tiledMapRenderer.addSprite(levelPortalSprite);
        World.levelPortal = new LevelPortal(x, y);
    }

    private void addWallSprite(float x, float y) {
        Sprite wallSprite = new Sprite(Assets.wall);
        wallSprite.setSize((w * .035f), (h * .059f));
        wallSprite.setPosition(x, y);
        tiledMapRenderer.addSprite(wallSprite);
        World.wallList.add(new Wall(x, y, wallSprite));
    }

    private void addCoinsSprite(float x, float y, float goldBonus) {
        Sprite coinsSprite = new Sprite(Assets.coins);
        coinsSprite.setSize(COINS_WIDTH, COINS_HEIGHT);
        coinsSprite.setPosition(x, y);
        tiledMapRenderer.addSprite(coinsSprite);
        World.coinsList.add(new Coins(x, y, coinsSprite, goldBonus));
    }

    private void addBossKeySprite(float x, float y) {
        Sprite bossKeySprite = new Sprite(Assets.bossKey);
        bossKeySprite.setSize(BOSS_KEY_WIDTH, BOSS_KEY_HEIGHT);
        bossKeySprite.setPosition(x, y);
        tiledMapRenderer.addSprite(bossKeySprite);
        BossKey bossKey = new BossKey(x, y, bossKeySprite);
        bossKey.setItemAction(World.ItemActions.SPAWN_BOSS);
        World.itemsList.add(bossKey);
    }

    private void addSwordSprite(float x, float y) {
        Sprite swordSprite = new Sprite(Assets.swordSprite);
        swordSprite.setSize(SWORD_WIDTH, SWORD_HEIGHT);
        swordSprite.setPosition(x, y);
        tiledMapRenderer.addSprite(swordSprite);
        Sword sword = new Sword(x, y, swordSprite, PandaSurvivor.currentLevel);
        sword.setItemAction(World.ItemActions.EQUIP);
        World.itemsList.add(sword);
    }

    private void addClothHelmetSprite(float x, float y) {
        Sprite clothHelmetSprite = new Sprite(Assets.clothHelmetSprite);
        clothHelmetSprite.setSize(HELMET_WIDTH, HELMET_HEIGHT);
        clothHelmetSprite.setPosition(x, y);
        tiledMapRenderer.addSprite(clothHelmetSprite);
        Helmet helmet = new Helmet(x, y, clothHelmetSprite, PandaSurvivor.currentLevel);
        helmet.setItemAction(World.ItemActions.EQUIP);
        World.itemsList.add(helmet);
    }

    private void addClothChestpieceSprite(float x, float y) {
        Sprite clothChestpieceSprite = new Sprite(Assets.clothChestpieceSprite);
        clothChestpieceSprite.setSize(CHESTPIECE_WIDTH, CHESTPIECE_HEIGHT);
        clothChestpieceSprite.setPosition(x, y);
        tiledMapRenderer.addSprite(clothChestpieceSprite);
        Chestpiece chestpiece = new Chestpiece(x, y, clothChestpieceSprite, PandaSurvivor.currentLevel);
        chestpiece.setItemAction(World.ItemActions.EQUIP);
        World.itemsList.add(chestpiece);
    }

    private void addClothBootsSprite(float x, float y) {
        Sprite clothBootsSprite = new Sprite(Assets.clothBootsSprite);
        clothBootsSprite.setSize(BOOTS_WIDTH, BOOTS_HEIGHT);
        clothBootsSprite.setPosition(x, y);
        tiledMapRenderer.addSprite(clothBootsSprite);
        Boots boots = new Boots(x, y, clothBootsSprite, PandaSurvivor.currentLevel);
        boots.setItemAction(World.ItemActions.EQUIP);
        World.itemsList.add(boots);
    }

    private void addClothGlovesSprite(float x, float y) {
        Sprite clothGlovesSprite = new Sprite(Assets.clothGlovesSprite);
        clothGlovesSprite.setSize(GLOVES_WIDTH, GLOVES_HEIGHT);
        clothGlovesSprite.setPosition(x, y);
        tiledMapRenderer.addSprite(clothGlovesSprite);
        Gloves gloves = new Gloves(x, y, clothGlovesSprite, PandaSurvivor.currentLevel);
        gloves.setItemAction(World.ItemActions.EQUIP);
        World.itemsList.add(gloves);
    }

    private void addClothBracersSprite(float x, float y) {
        Sprite clothBracersSprite = new Sprite(Assets.clothBracersSprite);
        clothBracersSprite.setSize(BRACERS_WIDTH, BRACERS_HEIGHT);
        clothBracersSprite.setPosition(x, y);
        tiledMapRenderer.addSprite(clothBracersSprite);
        Bracers bracers = new Bracers(x, y, clothBracersSprite, PandaSurvivor.currentLevel);
        bracers.setItemAction(World.ItemActions.EQUIP);
        World.itemsList.add(bracers);
    }

    private void addClothPantsSprite(float x, float y) {
        Sprite clothPantsSprite = new Sprite(Assets.clothPantsSprite);
        clothPantsSprite.setSize(PANTS_WIDTH, PANTS_HEIGHT);
        clothPantsSprite.setPosition(x, y);
        tiledMapRenderer.addSprite(clothPantsSprite);
        Pants pants = new Pants(x, y, clothPantsSprite, PandaSurvivor.currentLevel);
        pants.setItemAction(World.ItemActions.EQUIP);
        World.itemsList.add(pants);
    }

    private void addStaffSprite(float x, float y) {
        Sprite staffSprite = new Sprite(Assets.staffSprite);
        staffSprite.setSize(STAFF_WIDTH, STAFF_HEIGHT);
        staffSprite.setPosition(x, y);
        tiledMapRenderer.addSprite(staffSprite);
        Staff staff = new Staff(x, y, staffSprite, PandaSurvivor.currentLevel);
        staff.setItemAction(World.ItemActions.EQUIP);
        World.itemsList.add(staff);
    }

    private void addHouseSprite(float x, float y) {
        Sprite houseSprite = new Sprite(Assets.houseSprite);
        houseSprite.setSize(300, 300);
        houseSprite.setPosition(x, y);
        tiledMapRenderer.addSprite(houseSprite);
        House house = new House(x, y);
        World.houseList.add(house);
    }

    private void addNinjaSprite(float x, float y, World.NinjaTypes ninjaType) {
        if (ninjaType == World.NinjaTypes.RED) {
            Sprite enemySprite = new Sprite(redNinjaFrames[0]);
            enemySprite.setSize(NINJA_WIDTH, NINJA_HEIGHT);
            enemySprite.setPosition(x, y);
            tiledMapRenderer.addSprite(enemySprite);
            World.redNinjaList.add(new Ninja(x, y, enemySprite, World.NinjaTypes.RED, PandaSurvivor.currentLevel));
        } else if (ninjaType == World.NinjaTypes.BLACK) {
            Sprite enemySprite = new Sprite(blackNinjaFrames[0]);
            enemySprite.setSize(NINJA_WIDTH, NINJA_HEIGHT);
            enemySprite.setPosition(x, y);
            tiledMapRenderer.addSprite(enemySprite);
            World.blackNinjaList.add(new Ninja(x, y, enemySprite, World.NinjaTypes.BLACK, PandaSurvivor.currentLevel));
        } else if (ninjaType == World.NinjaTypes.PURPLE) {
            Sprite enemySprite = new Sprite(purpleNinjaFrames[0]);
            enemySprite.setSize(NINJA_WIDTH, NINJA_HEIGHT);
            enemySprite.setPosition(x, y);
            tiledMapRenderer.addSprite(enemySprite);
            World.purpleNinjaList.add(new Ninja(x, y, enemySprite, World.NinjaTypes.PURPLE, PandaSurvivor.currentLevel));
        }
    }
}
