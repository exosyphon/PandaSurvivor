package com.courter.pandasurvivor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector3;

public class PandaSurvivor extends ApplicationAdapter implements InputProcessor {
    TiledMap tiledMap;
    OrthographicCamera camera;
    OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
    Texture texture;
    Sprite pandaSprite;
    Sprite dpadSprite;
    Sprite aButtonSprite;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);

        camera.update();

        texture = new Texture(Gdx.files.internal("pik.png"));
        pandaSprite = new Sprite(texture);
        pandaSprite.setPosition(w / 2, h / 2);

        texture = new Texture(Gdx.files.internal("dpad.png"));
        dpadSprite = new Sprite(texture);
        dpadSprite.setPosition(0, 0);

        texture = new Texture(Gdx.files.internal("a-button.png"));
        aButtonSprite = new Sprite(texture);
        aButtonSprite.setPosition(w - texture.getWidth() - 24, 24);

        tiledMap = new TmxMapLoader().load("panda_snow.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);
        tiledMapRenderer.addSprite(pandaSprite);
        tiledMapRenderer.addSprite(dpadSprite);
        tiledMapRenderer.addSprite(aButtonSprite);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT)
            camera.translate(-32, 0);
        if (keycode == Input.Keys.RIGHT)
            camera.translate(32, 0);
        if (keycode == Input.Keys.UP)
            camera.translate(0, -32);
        if (keycode == Input.Keys.DOWN)
            camera.translate(0, 32);
        if (keycode == Input.Keys.NUM_1)
            tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
        if (keycode == Input.Keys.NUM_2)
            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
        System.out.println(screenX + "  Y:" + screenY);
        Vector3 position = camera.unproject(clickCoordinates);

        float originalx = pandaSprite.getX();
        float originaly = pandaSprite.getY();

        if (position.x < (dpadSprite.getX() + dpadSprite.getWidth()) && position.y < (dpadSprite.getY() + dpadSprite.getHeight())) {
            if (position.y <  (dpadSprite.getY() + 74) && position.y > (dpadSprite.getY() + 56)) {
                if (position.x < (dpadSprite.getX() + 56)) {
                    //left
                    pandaSprite.setPosition(originalx - 32, originaly);
                    dpadSprite.setPosition(dpadSprite.getX() - 32, dpadSprite.getY());
                    aButtonSprite.setPosition(aButtonSprite.getX() - 32, aButtonSprite.getY());
                    camera.translate(-32, 0);
                } else {
                    //right
                    pandaSprite.setPosition(originalx + 32, originaly);
                    dpadSprite.setPosition(dpadSprite.getX() + 32, dpadSprite.getY());
                    aButtonSprite.setPosition(aButtonSprite.getX() + 32, aButtonSprite.getY());
                    camera.translate(32, 0);
                }
            } else {
                if (position.y < (dpadSprite.getY() + 56)) {
                    //down
                    pandaSprite.setPosition(originalx, originaly - 32);
                    dpadSprite.setPosition(dpadSprite.getX(), dpadSprite.getY() - 32);
                    aButtonSprite.setPosition(aButtonSprite.getX(), aButtonSprite.getY() - 32);
                    camera.translate(0, -32);
                } else {
                    //up
                    pandaSprite.setPosition(originalx, originaly + 32);
                    dpadSprite.setPosition(dpadSprite.getX(), dpadSprite.getY() + 32);
                    aButtonSprite.setPosition(aButtonSprite.getX(), aButtonSprite.getY() + 32);
                    camera.translate(0, 32);
                }
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}