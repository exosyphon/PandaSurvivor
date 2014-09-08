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
    public static final int SINGLE_TILE_WIDTH = 520;
    TiledMap tiledMap;
    OrthographicCamera camera;
    OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
    Texture texture;
    Sprite pandaSprite;
    Sprite dpadSprite;
    Sprite aButtonSprite;
    private static final int FRAME_COLS = 12;
    private static final int FRAME_ROWS = 8;
    Texture pandaSheet;
    TextureRegion[] firstPandaFrames;
    Animation pandaDownAnimation;
    Animation pandaLeftAnimation;
    Animation pandaRightAnimation;
    Animation pandaUpAnimation;
    float deltaTime;
    Hero hero;


    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);

        camera.update();

        pandaSheet = new Texture(Gdx.files.internal("panda2.png")); // #9
        TextureRegion[][] tmp = TextureRegion.split(pandaSheet, pandaSheet.getWidth() / FRAME_COLS, pandaSheet.getHeight() / FRAME_ROWS);              // #10
        firstPandaFrames = new TextureRegion[(FRAME_COLS * FRAME_ROWS) / 2];
        int index = 0;
        for (int i = 4; i < FRAME_ROWS; i++) {
            for (int j = 0; j < 3; j++) {
                firstPandaFrames[index++] = tmp[i][j];
            }
        }

        pandaDownAnimation = new Animation(.2f, firstPandaFrames[0], firstPandaFrames[1], firstPandaFrames[2]);
        pandaLeftAnimation = new Animation(.2f, firstPandaFrames[3], firstPandaFrames[4], firstPandaFrames[5]);
        pandaRightAnimation = new Animation(.2f, firstPandaFrames[6], firstPandaFrames[7], firstPandaFrames[8]);
        pandaUpAnimation = new Animation(.2f, firstPandaFrames[9], firstPandaFrames[10], firstPandaFrames[11]);

        pandaSprite = new Sprite(firstPandaFrames[0]);
        pandaSprite.setSize(96, 96);
        pandaSprite.setPosition(w / 2, h / 2);
        hero = new Hero(w / 2, h / 2);

        texture = new Texture(Gdx.files.internal("dpad.png"));
        dpadSprite = new Sprite(texture);
        dpadSprite.setPosition(75, 75);

        texture = new Texture(Gdx.files.internal("a-button.png"));
        aButtonSprite = new Sprite(texture);
        aButtonSprite.setPosition(w - texture.getWidth() - 75, 100);

        tiledMap = new TmxMapLoader().load("panda_snow.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);
        tiledMapRenderer.addSprite(pandaSprite);
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

        hero.update(deltaTime);

        if (Gdx.input.isTouched(0)) {
            touchDown(Gdx.input.getX(), Gdx.input.getY());
        }
    }

    public boolean touchDown(float screenX, float screenY) {
        Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
        Vector3 position = camera.unproject(clickCoordinates);

        float originalx = pandaSprite.getX();
        float originaly = pandaSprite.getY();

        if (position.x < (dpadSprite.getX() + dpadSprite.getWidth() + 80) && position.y < (dpadSprite.getY() + dpadSprite.getHeight() + 40)) {
            if (position.y < (dpadSprite.getY() + 180) && position.y > (dpadSprite.getY() + 68)) {
                if (position.x < (dpadSprite.getX() + 112) && originalx > 0) {
                    //left
                    pandaSprite.setRegion(pandaLeftAnimation.getKeyFrame(hero.stateTime, Animation.ANIMATION_LOOPING));

                    pandaSprite.setPosition(originalx - (SINGLE_TILE_WIDTH * deltaTime), originaly);
                    dpadSprite.setPosition(dpadSprite.getX() - (SINGLE_TILE_WIDTH * deltaTime), dpadSprite.getY());
                    aButtonSprite.setPosition(aButtonSprite.getX() - (SINGLE_TILE_WIDTH * deltaTime), aButtonSprite.getY());
                    camera.translate(-(SINGLE_TILE_WIDTH * deltaTime), 0);
                } else if (position.x > (dpadSprite.getX() + 148) && originalx < 7584) {
                    //right
                    pandaSprite.setRegion(pandaRightAnimation.getKeyFrame(hero.stateTime, Animation.ANIMATION_LOOPING));

                    pandaSprite.setPosition(originalx + (SINGLE_TILE_WIDTH * deltaTime), originaly);
                    dpadSprite.setPosition(dpadSprite.getX() + (SINGLE_TILE_WIDTH * deltaTime), dpadSprite.getY());
                    aButtonSprite.setPosition(aButtonSprite.getX() + (SINGLE_TILE_WIDTH * deltaTime), aButtonSprite.getY());
                    camera.translate((SINGLE_TILE_WIDTH * deltaTime), 0);
                }
            } else {
                if (position.y < (dpadSprite.getY() + 80) && originaly > 0) {
                    //down
                    pandaSprite.setRegion(pandaDownAnimation.getKeyFrame(hero.stateTime, Animation.ANIMATION_LOOPING));

                    pandaSprite.setPosition(originalx, originaly - (SINGLE_TILE_WIDTH * deltaTime));
                    dpadSprite.setPosition(dpadSprite.getX(), dpadSprite.getY() - (SINGLE_TILE_WIDTH * deltaTime));
                    aButtonSprite.setPosition(aButtonSprite.getX(), aButtonSprite.getY() - (SINGLE_TILE_WIDTH * deltaTime));
                    camera.translate(0, -(SINGLE_TILE_WIDTH * deltaTime));
                } else if (position.y > (dpadSprite.getY() + 148) && originaly < 7616) {
                    //up
                    pandaSprite.setRegion(pandaUpAnimation.getKeyFrame(hero.stateTime, Animation.ANIMATION_LOOPING));

                    pandaSprite.setPosition(originalx, originaly + (SINGLE_TILE_WIDTH * deltaTime));
                    dpadSprite.setPosition(dpadSprite.getX(), dpadSprite.getY() + (SINGLE_TILE_WIDTH * deltaTime));
                    aButtonSprite.setPosition(aButtonSprite.getX(), aButtonSprite.getY() + (SINGLE_TILE_WIDTH * deltaTime));
                    camera.translate(0, (SINGLE_TILE_WIDTH * deltaTime));
                }
            }
        }
        return true;
    }
}