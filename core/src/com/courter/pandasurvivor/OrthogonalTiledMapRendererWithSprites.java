package com.courter.pandasurvivor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.g2d.Batch.C1;
import static com.badlogic.gdx.graphics.g2d.Batch.C2;
import static com.badlogic.gdx.graphics.g2d.Batch.C3;
import static com.badlogic.gdx.graphics.g2d.Batch.C4;
import static com.badlogic.gdx.graphics.g2d.Batch.U1;
import static com.badlogic.gdx.graphics.g2d.Batch.U2;
import static com.badlogic.gdx.graphics.g2d.Batch.U3;
import static com.badlogic.gdx.graphics.g2d.Batch.U4;
import static com.badlogic.gdx.graphics.g2d.Batch.V1;
import static com.badlogic.gdx.graphics.g2d.Batch.V2;
import static com.badlogic.gdx.graphics.g2d.Batch.V3;
import static com.badlogic.gdx.graphics.g2d.Batch.V4;
import static com.badlogic.gdx.graphics.g2d.Batch.X1;
import static com.badlogic.gdx.graphics.g2d.Batch.X2;
import static com.badlogic.gdx.graphics.g2d.Batch.X3;
import static com.badlogic.gdx.graphics.g2d.Batch.X4;
import static com.badlogic.gdx.graphics.g2d.Batch.Y1;
import static com.badlogic.gdx.graphics.g2d.Batch.Y2;
import static com.badlogic.gdx.graphics.g2d.Batch.Y3;
import static com.badlogic.gdx.graphics.g2d.Batch.Y4;

public class OrthogonalTiledMapRendererWithSprites extends OrthogonalTiledMapRenderer {
    public static float INVENTORY_X_OFFSET;
    public static float INVENTORY_Y_OFFSET;
    public static float INVENTORY_X_OFFSET_2;
    public static float INVENTORY_COLUMN_OFFSET;
    public static float INVENTORY_Y_OFFSET_2;
    public static float INVENTORY_ROW_OFFSET;
    public static float INVENTORY_SPRITE_RENDER_X_SIZE;
    public static float INVENTORY_SPRITE_RENDER_Y_SIZE;
    public static float USE_DESTROY_X_OFFSET;
    public static float USE_DESTROY_Y_OFFSET;
    public static float USE_DESTROY_SPRITE_RENDER_X_SIZE;
    public static float USE_DESTROY_SPRITE_RENDER_Y_SIZE;
    public static float UNIT_SCALE;
    public static float XP_LVL_FONT_X;
    public static float XP_LVL_FONT_Y;
    public static float COIN_BACKGROUND_FONT_X;
    public static float COIN_BACKGROUND_FONT_Y;
    public static float MONEY_INCREMENT_OFFSET;
    public static float COIN_FONT_Y;
    public static float COIN_FONT_X;
    public static float DPAD_X;
    public static float DPAD_Y;
    public static float DPAD_RENDER_SIZE_X;
    public static float DPAD_RENDER_SIZE_Y;
    public static float A_BUTTON_X;
    public static float A_BUTTON_Y;
    public static float A_BUTTON_RENDER_X;
    public static float A_BUTTON_RENDER_Y;
    public static float BAG_BUTTON_X;
    public static float BAG_BUTTON_Y;
    public static float BAG_BUTTON_RENDER_X;
    public static float BAG_BUTTON_RENDER_Y;
    public static float HEALTH_BAR_Y;
    public static float HEALTH_BAR_X;
    public static float HEALTH_BAR_RENDER_X;
    public static float HEALTH_BAR_RENDER_Y;
    public static float ARMOR_BUTTON_X;
    public static float ARMOR_BUTTON_Y;
    public static float ARMOR_BUTTON_RENDER_X;
    public static float ARMOR_BUTTON_RENDER_Y;
    public static float XP_BAR_X;
    public static float XP_BAR_Y;
    public static float XP_BAR_RENDER_X;
    public static float XP_BAR_RENDER_Y;
    public static float HEALTH_FILL_X;
    public static float HEALTH_FILL_Y;
    public static float HEALTH_FILL_RENDER_Y;
    public static float HEALTH_FILL_RENDER_X;
    public static float XP_FILL_X;
    public static float XP_FILL_Y;
    public static float XP_FILL_RENDER_X;
    public static float XP_FILL_RENDER_Y;
    private List<Sprite> sprites;
    private List<Sprite> controlSprites;
    private int backgroundLayer = 0;
    private int drawSpritesAfterLayer = 1;
    private int treeBottomSpritesLayer = 2;
    private int treeMidSpritesLayer = 3;
    private boolean renderTreeBottomsFlag = true;
    private SpriteBatch batcher;

    public OrthogonalTiledMapRendererWithSprites(TiledMap map) {
        super(map);
        sprites = new ArrayList<Sprite>();
        controlSprites = new ArrayList<Sprite>();
        batcher = new SpriteBatch();
        setConstants();
    }
    
    private void setConstants() {
        INVENTORY_X_OFFSET = WorldRenderer.camera.viewportWidth - (WorldRenderer.w * .289f);
        INVENTORY_Y_OFFSET = (WorldRenderer.h * .231f);
        INVENTORY_X_OFFSET_2 = (WorldRenderer.w * .017f);
        INVENTORY_COLUMN_OFFSET = (WorldRenderer.w * .094f);
        INVENTORY_Y_OFFSET_2 = (WorldRenderer.h * .022f);
        INVENTORY_ROW_OFFSET = (WorldRenderer.h * .166f);
        INVENTORY_SPRITE_RENDER_X_SIZE = (WorldRenderer.w * .278f);
        INVENTORY_SPRITE_RENDER_Y_SIZE = (WorldRenderer.h * .666f);
        USE_DESTROY_X_OFFSET = (WorldRenderer.w * .418f);
        USE_DESTROY_Y_OFFSET = (WorldRenderer.h * .254f);
        USE_DESTROY_SPRITE_RENDER_X_SIZE = (WorldRenderer.w * .156f);
        USE_DESTROY_SPRITE_RENDER_Y_SIZE = (WorldRenderer.h * .185f);
        UNIT_SCALE = (WorldRenderer.h * .000925f);
        XP_LVL_FONT_X = (WorldRenderer.w * .015f);
        XP_LVL_FONT_Y = WorldRenderer.camera.viewportHeight - (WorldRenderer.h * .075f);
        COIN_BACKGROUND_FONT_X = (WorldRenderer.camera.viewportWidth - (WorldRenderer.w * .032f));
        COIN_BACKGROUND_FONT_Y = (WorldRenderer.camera.viewportHeight - (WorldRenderer.h * .022f));
        MONEY_INCREMENT_OFFSET = (WorldRenderer.w * .022f);
        COIN_FONT_Y = (WorldRenderer.camera.viewportHeight - (WorldRenderer.h * .019f));
        COIN_FONT_X = (WorldRenderer.camera.viewportWidth - (WorldRenderer.w * .034f));
        DPAD_X = (WorldRenderer.w * .075f);
        DPAD_Y = (WorldRenderer.h * .125f);
        DPAD_RENDER_SIZE_X = (WorldRenderer.w * .142f);
        DPAD_RENDER_SIZE_Y = (WorldRenderer.h * .237f);
        A_BUTTON_X = ((WorldRenderer.camera.viewportWidth - (WorldRenderer.w * .071f)) - (WorldRenderer.w * .075f));
        A_BUTTON_Y = (WorldRenderer.h * .111f);
        A_BUTTON_RENDER_X = (WorldRenderer.w * .071f);
        A_BUTTON_RENDER_Y = (WorldRenderer.h * .118f);
        BAG_BUTTON_X = (WorldRenderer.camera.viewportWidth - (WorldRenderer.w * .071f) - (WorldRenderer.w * .242f));
        BAG_BUTTON_Y = (WorldRenderer.h * .037f);
        BAG_BUTTON_RENDER_X = (WorldRenderer.w * .071f);
        BAG_BUTTON_RENDER_Y = (WorldRenderer.h * .118f);
        HEALTH_BAR_Y = (WorldRenderer.camera.viewportHeight - (WorldRenderer.h * .029f) - (WorldRenderer.h * .009f));
        HEALTH_BAR_X = (WorldRenderer.w * .013f);
        HEALTH_BAR_RENDER_X = (WorldRenderer.w * .214f);
        HEALTH_BAR_RENDER_Y = (WorldRenderer.h * .029f);
        ARMOR_BUTTON_X = ((WorldRenderer.w * .111f) + (WorldRenderer.w * .142f));
        ARMOR_BUTTON_Y = (WorldRenderer.camera.viewportHeight - (WorldRenderer.h * .118f) - (WorldRenderer.h * .009f));
        ARMOR_BUTTON_RENDER_X = (WorldRenderer.w * .071f);
        ARMOR_BUTTON_RENDER_Y = (WorldRenderer.h * .118f);
        XP_BAR_X = (WorldRenderer.w * .013f);
        XP_BAR_Y = (WorldRenderer.camera.viewportHeight - (WorldRenderer.h * .029f) * 2 - (WorldRenderer.h * .018f));
        XP_BAR_RENDER_X = (WorldRenderer.w * .214f);
        XP_BAR_RENDER_Y = (WorldRenderer.h * .029f);
        HEALTH_FILL_X = (WorldRenderer.w * .015f);
        HEALTH_FILL_Y = (WorldRenderer.camera.viewportHeight - (WorldRenderer.h * .035f));
        HEALTH_FILL_RENDER_Y = (WorldRenderer.h * .024f);
        HEALTH_FILL_RENDER_X = (WorldRenderer.w * .208f);
        XP_FILL_X = (WorldRenderer.w * .015f);
        XP_FILL_Y = (WorldRenderer.camera.viewportHeight - (WorldRenderer.h * .075f));
        XP_FILL_RENDER_X = (WorldRenderer.w * .208f);
        XP_FILL_RENDER_Y = (WorldRenderer.h * .024f);
    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
    }

    public void addControlSprite(Sprite sprite) {
        controlSprites.add(sprite);
    }

    @Override
    public void render() {
        beginRender();
        int currentLayer = 0;
        for (MapLayer layer : map.getLayers()) {
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    if (currentLayer == backgroundLayer)
                        super.renderTileLayer((TiledMapTileLayer) layer);
                    if (currentLayer == drawSpritesAfterLayer) {
                        for (int i = sprites.size() - 1; i > 0; i--)
                            sprites.get(i).draw(this.getSpriteBatch());
                    }
                    if (currentLayer == treeBottomSpritesLayer) {
                        renderTreeBottomsFlag = true;

                        this.renderTileLayer((TiledMapTileLayer) layer);

                        renderTreeBottomsFlag = false;
                    }
                    if (currentLayer == treeMidSpritesLayer) {
                        renderTileLayerLeftovers((TiledMapTileLayer) layer, false);

                        sprites.get(0).draw(this.getSpriteBatch());

                        renderTileLayerLeftovers((TiledMapTileLayer) layer, true);

                        for (Sprite sprite : controlSprites)
                            sprite.draw(this.getSpriteBatch());
                    }

                    currentLayer++;
                } else {
                    for (MapObject object : layer.getObjects()) {
                        renderObject(object);
                    }
                }
            }
        }

        batcher.begin();
        if (WorldRenderer.showInventory) {
            batcher.draw(WorldRenderer.inventorySprite, INVENTORY_X_OFFSET, WorldRenderer.camera.viewportHeight / 2 - INVENTORY_Y_OFFSET, INVENTORY_SPRITE_RENDER_X_SIZE, INVENTORY_SPRITE_RENDER_Y_SIZE);
            float baseX = INVENTORY_X_OFFSET;
            float baseY = WorldRenderer.camera.viewportHeight - INVENTORY_Y_OFFSET;

            int row = 0;
            int col = 0;
            ArrayList<Item> list = World.hero.getInventory();
            for (int x = 0; x < list.size(); x++) {
                Item item = list.get(x);
                batcher.draw(item.getSprite(), baseX + INVENTORY_X_OFFSET_2 + (col * INVENTORY_COLUMN_OFFSET), baseY + INVENTORY_Y_OFFSET_2 - (row * INVENTORY_ROW_OFFSET), item.inventoryRenderX(), item.inventoryRenderY());
                if ((x+1) % 3 == 0)
                    row++;
                col++;
                if(col > 2)
                    col = 0;
            }
        }

        if(WorldRenderer.showInventoryOptions) {
            batcher.draw(WorldRenderer.useDestroyInventoryOptionsSprite, WorldRenderer.camera.viewportWidth - USE_DESTROY_X_OFFSET + WorldRenderer.showInventoryOptionsOffsetX, WorldRenderer.camera.viewportHeight - USE_DESTROY_Y_OFFSET - WorldRenderer.showInventoryOptionsOffsetY, USE_DESTROY_SPRITE_RENDER_X_SIZE, USE_DESTROY_SPRITE_RENDER_Y_SIZE);
        }

        if (PandaSurvivor.game_state == PandaSurvivor.GAME_STATES.GAME_OVER) {
            batcher.draw(WorldRenderer.retrySprite, WorldRenderer.camera.viewportWidth / 2 - (WorldRenderer.w * .200f), WorldRenderer.camera.viewportHeight / 2 - (WorldRenderer.h * .333f), (WorldRenderer.w * .401f), (WorldRenderer.h * .666f));
        }

        batcher.draw(WorldRenderer.dpadSprite, DPAD_X, DPAD_Y, DPAD_RENDER_SIZE_X, DPAD_RENDER_SIZE_Y);
        batcher.draw(WorldRenderer.aButtonSprite, A_BUTTON_X, A_BUTTON_Y, A_BUTTON_RENDER_X, A_BUTTON_RENDER_Y);
        batcher.draw(WorldRenderer.bagButtonSprite, BAG_BUTTON_X, BAG_BUTTON_Y, BAG_BUTTON_RENDER_X, BAG_BUTTON_RENDER_Y);
        batcher.draw(WorldRenderer.healthBarSprite, HEALTH_BAR_X, HEALTH_BAR_Y, HEALTH_BAR_RENDER_X, HEALTH_BAR_RENDER_Y);
        batcher.draw(WorldRenderer.armorButtonSprite, ARMOR_BUTTON_X, ARMOR_BUTTON_Y, ARMOR_BUTTON_RENDER_X, ARMOR_BUTTON_RENDER_Y);
        batcher.draw(WorldRenderer.xpBarSprite, XP_BAR_X, XP_BAR_Y, XP_BAR_RENDER_X, XP_BAR_RENDER_Y);
        batcher.end();

        WorldRenderer.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        WorldRenderer.shapeRenderer.setColor(0, 1, 0, 1);
        WorldRenderer.shapeRenderer.rect(HEALTH_FILL_X, HEALTH_FILL_Y, HEALTH_FILL_RENDER_X * (World.hero.getHealth() * .01f), HEALTH_FILL_RENDER_Y);
        WorldRenderer.shapeRenderer.end();

        float xpPercentage = (float) World.hero.getCurrentXp() / World.hero.getCurrentLevelXpRequired();
        WorldRenderer.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        WorldRenderer.shapeRenderer.setColor(0, 0, 1, 1);
        WorldRenderer.shapeRenderer.rect(XP_FILL_X, XP_FILL_Y, XP_FILL_RENDER_X * (xpPercentage), XP_FILL_RENDER_Y);
        WorldRenderer.shapeRenderer.end();
        endRender();

        batcher.begin();
        WorldRenderer.levelFont.draw(batcher, String.valueOf(World.hero.getCurrentLevel()), XP_LVL_FONT_X, XP_LVL_FONT_Y);
        WorldRenderer.coinFont2.draw(batcher, "$ " + String.valueOf(World.hero.getMoneyTotal()), COIN_BACKGROUND_FONT_X - (String.valueOf(World.hero.getMoneyTotal()).length() * MONEY_INCREMENT_OFFSET), COIN_BACKGROUND_FONT_Y);
        WorldRenderer.coinFont.draw(batcher, "$ " + String.valueOf(World.hero.getMoneyTotal()), COIN_FONT_X - (String.valueOf(World.hero.getMoneyTotal()).length() * MONEY_INCREMENT_OFFSET), COIN_FONT_Y);
        batcher.end();
    }

    public void renderTileLayerLeftovers(TiledMapTileLayer layer, boolean checkLessThan) {
        Sprite heroSprite = sprites.get(0);

        final Color batchColor = spriteBatch.getColor();
        final float color = Color.toFloatBits(batchColor.r, batchColor.g, batchColor.b, batchColor.a * layer.getOpacity());

        final int layerWidth = layer.getWidth();
        final int layerHeight = layer.getHeight();

        final float layerTileWidth = layer.getTileWidth() * UNIT_SCALE;
        final float layerTileHeight = layer.getTileHeight() * UNIT_SCALE;

        final int col1 = Math.max(0, (int) (viewBounds.x / layerTileWidth));
        final int col2 = Math.min(layerWidth, (int) ((viewBounds.x + viewBounds.width + layerTileWidth) / layerTileWidth));

        final int row1 = Math.max(0, (int) (viewBounds.y / layerTileHeight));
        final int row2 = Math.min(layerHeight, (int) ((viewBounds.y + viewBounds.height + layerTileHeight) / layerTileHeight));

        float y = row2 * layerTileHeight;
        float xStart = col1 * layerTileWidth;
        final float[] vertices = this.vertices;

        for (int row = row2; row >= row1; row--) {
            float x = xStart;
            for (int col = col1; col < col2; col++) {
                final TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                if (cell == null) {
                    x += layerTileWidth;
                    continue;
                }
                final TiledMapTile tile = cell.getTile();

                if (tile != null) {
                    final boolean flipX = cell.getFlipHorizontally();
                    final boolean flipY = cell.getFlipVertically();
                    final int rotations = cell.getRotation();

                    TextureRegion region = tile.getTextureRegion();

                    float x1 = x + tile.getOffsetX() * UNIT_SCALE;
                    float y1 = y + tile.getOffsetY() * UNIT_SCALE;
                    float x2 = x1 + region.getRegionWidth() * UNIT_SCALE;
                    float y2 = y1 + region.getRegionHeight() * UNIT_SCALE;

                    float u1 = region.getU();
                    float v1 = region.getV2();
                    float u2 = region.getU2();
                    float v2 = region.getV();

                    if (checkLessThan == true) {
                        if (y2 > heroSprite.getVertices()[1] + heroSprite.getHeight()) {
                            continue;
                        }
                    }

                    vertices[X1] = x1;
                    vertices[Y1] = y1;
                    vertices[C1] = color;
                    vertices[U1] = u1;
                    vertices[V1] = v1;

                    vertices[X2] = x1;
                    vertices[Y2] = y2;
                    vertices[C2] = color;
                    vertices[U2] = u1;
                    vertices[V2] = v2;

                    vertices[X3] = x2;
                    vertices[Y3] = y2;
                    vertices[C3] = color;
                    vertices[U3] = u2;
                    vertices[V3] = v2;

                    vertices[X4] = x2;
                    vertices[Y4] = y1;
                    vertices[C4] = color;
                    vertices[U4] = u2;
                    vertices[V4] = v1;

                    if (flipX) {
                        float temp = vertices[U1];
                        vertices[U1] = vertices[U3];
                        vertices[U3] = temp;
                        temp = vertices[U2];
                        vertices[U2] = vertices[U4];
                        vertices[U4] = temp;
                    }
                    if (flipY) {
                        float temp = vertices[V1];
                        vertices[V1] = vertices[V3];
                        vertices[V3] = temp;
                        temp = vertices[V2];
                        vertices[V2] = vertices[V4];
                        vertices[V4] = temp;
                    }
                    if (rotations != 0) {
                        switch (rotations) {
                            case TiledMapTileLayer.Cell.ROTATE_90: {
                                float tempV = vertices[V1];
                                vertices[V1] = vertices[V2];
                                vertices[V2] = vertices[V3];
                                vertices[V3] = vertices[V4];
                                vertices[V4] = tempV;

                                float tempU = vertices[U1];
                                vertices[U1] = vertices[U2];
                                vertices[U2] = vertices[U3];
                                vertices[U3] = vertices[U4];
                                vertices[U4] = tempU;
                                break;
                            }
                            case TiledMapTileLayer.Cell.ROTATE_180: {
                                float tempU = vertices[U1];
                                vertices[U1] = vertices[U3];
                                vertices[U3] = tempU;
                                tempU = vertices[U2];
                                vertices[U2] = vertices[U4];
                                vertices[U4] = tempU;
                                float tempV = vertices[V1];
                                vertices[V1] = vertices[V3];
                                vertices[V3] = tempV;
                                tempV = vertices[V2];
                                vertices[V2] = vertices[V4];
                                vertices[V4] = tempV;
                                break;
                            }
                            case TiledMapTileLayer.Cell.ROTATE_270: {
                                float tempV = vertices[V1];
                                vertices[V1] = vertices[V4];
                                vertices[V4] = vertices[V3];
                                vertices[V3] = vertices[V2];
                                vertices[V2] = tempV;

                                float tempU = vertices[U1];
                                vertices[U1] = vertices[U4];
                                vertices[U4] = vertices[U3];
                                vertices[U3] = vertices[U2];
                                vertices[U2] = tempU;
                                break;
                            }
                        }
                    }
                    spriteBatch.draw(region.getTexture(), vertices, 0, 20);
                }
                x += layerTileWidth;
            }
            y -= layerTileHeight;
        }
    }

    @Override
    public void renderTileLayer(TiledMapTileLayer layer) {
        final Color batchColor = spriteBatch.getColor();
        final float color = Color.toFloatBits(batchColor.r, batchColor.g, batchColor.b, batchColor.a * layer.getOpacity());

        final int layerWidth = layer.getWidth();
        final int layerHeight = layer.getHeight();



        final float layerTileWidth = layer.getTileWidth() * UNIT_SCALE;
        final float layerTileHeight = layer.getTileHeight() * UNIT_SCALE;

        final int col1 = Math.max(0, (int) (viewBounds.x / layerTileWidth));
        final int col2 = Math.min(layerWidth, (int) ((viewBounds.x + viewBounds.width + layerTileWidth) / layerTileWidth));

        final int row1 = Math.max(0, (int) (viewBounds.y / layerTileHeight));
        final int row2 = Math.min(layerHeight, (int) ((viewBounds.y + viewBounds.height + layerTileHeight) / layerTileHeight));

        float y = row2 * layerTileHeight;
        float xStart = col1 * layerTileWidth;
        final float[] vertices = this.vertices;

        for (int row = row2; row >= row1; row--) {
            float x = xStart;
            for (int col = col1; col < col2; col++) {
                final TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                if (cell == null) {
                    x += layerTileWidth;
                    continue;
                }
                final TiledMapTile tile = cell.getTile();

                if (tile != null) {
                    final boolean flipX = cell.getFlipHorizontally();
                    final boolean flipY = cell.getFlipVertically();
                    final int rotations = cell.getRotation();

                    TextureRegion region = tile.getTextureRegion();

                    float x1 = x + tile.getOffsetX() * UNIT_SCALE;
                    float y1 = y + tile.getOffsetY() * UNIT_SCALE;
                    float x2 = x1 + region.getRegionWidth() * UNIT_SCALE;
                    float y2 = y1 + region.getRegionHeight() * UNIT_SCALE;

                    float u1 = region.getU();
                    float v1 = region.getV2();
                    float u2 = region.getU2();
                    float v2 = region.getV();

                    vertices[X1] = x1;
                    vertices[Y1] = y1;
                    vertices[C1] = color;
                    vertices[U1] = u1;
                    vertices[V1] = v1;

                    vertices[X2] = x1;
                    vertices[Y2] = y2;
                    vertices[C2] = color;
                    vertices[U2] = u1;
                    vertices[V2] = v2;

                    vertices[X3] = x2;
                    vertices[Y3] = y2;
                    vertices[C3] = color;
                    vertices[U3] = u2;
                    vertices[V3] = v2;

                    vertices[X4] = x2;
                    vertices[Y4] = y1;
                    vertices[C4] = color;
                    vertices[U4] = u2;
                    vertices[V4] = v1;

                    if (flipX) {
                        float temp = vertices[U1];
                        vertices[U1] = vertices[U3];
                        vertices[U3] = temp;
                        temp = vertices[U2];
                        vertices[U2] = vertices[U4];
                        vertices[U4] = temp;
                    }
                    if (flipY) {
                        float temp = vertices[V1];
                        vertices[V1] = vertices[V3];
                        vertices[V3] = temp;
                        temp = vertices[V2];
                        vertices[V2] = vertices[V4];
                        vertices[V4] = temp;
                    }
                    if (rotations != 0) {
                        switch (rotations) {
                            case TiledMapTileLayer.Cell.ROTATE_90: {
                                float tempV = vertices[V1];
                                vertices[V1] = vertices[V2];
                                vertices[V2] = vertices[V3];
                                vertices[V3] = vertices[V4];
                                vertices[V4] = tempV;

                                float tempU = vertices[U1];
                                vertices[U1] = vertices[U2];
                                vertices[U2] = vertices[U3];
                                vertices[U3] = vertices[U4];
                                vertices[U4] = tempU;
                                break;
                            }
                            case TiledMapTileLayer.Cell.ROTATE_180: {
                                float tempU = vertices[U1];
                                vertices[U1] = vertices[U3];
                                vertices[U3] = tempU;
                                tempU = vertices[U2];
                                vertices[U2] = vertices[U4];
                                vertices[U4] = tempU;
                                float tempV = vertices[V1];
                                vertices[V1] = vertices[V3];
                                vertices[V3] = tempV;
                                tempV = vertices[V2];
                                vertices[V2] = vertices[V4];
                                vertices[V4] = tempV;
                                break;
                            }
                            case TiledMapTileLayer.Cell.ROTATE_270: {
                                float tempV = vertices[V1];
                                vertices[V1] = vertices[V4];
                                vertices[V4] = vertices[V3];
                                vertices[V3] = vertices[V2];
                                vertices[V2] = tempV;

                                float tempU = vertices[U1];
                                vertices[U1] = vertices[U4];
                                vertices[U4] = vertices[U3];
                                vertices[U3] = vertices[U2];
                                vertices[U2] = tempU;
                                break;
                            }
                        }
                    }
                    spriteBatch.draw(region.getTexture(), vertices, 0, 20);
                    if (renderTreeBottomsFlag)
                        addTree(x2, y2, region.getTexture());
                }
                x += layerTileWidth;
            }
            y -= layerTileHeight;
        }
    }

    private void addTree(float positionX, float positionY, Texture texture) {
        Tree tree = new Tree(positionX, positionY, new Sprite(texture));
        World.treeList.add(tree);
    }
}