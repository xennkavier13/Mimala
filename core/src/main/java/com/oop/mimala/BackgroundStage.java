package com.oop.mimala;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Disposable;

public class BackgroundStage implements Disposable {
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;

    private final int viewportWidth;
    private final int viewportHeight;

    private float mapWidth;
    private float mapHeight;

    public BackgroundStage(int viewportWidth, int viewportHeight) {
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;

        tiledMap = new TmxMapLoader().load("stage_test.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        shapeRenderer = new ShapeRenderer();

        // Get map dimensions
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        mapWidth = layer.getWidth() * layer.getTileWidth();
        mapHeight = layer.getHeight() * layer.getTileHeight();

        // Setup camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, viewportWidth, viewportHeight);

        // Center the map
        centerMap();
    }

    private void centerMap() {
        float centerX = mapWidth / 2f;
        float centerY = mapHeight / 2.25f; // Centering based on actual map height

        camera.position.set(centerX, centerY, 0);
        camera.update();
    }

    public void update(float delta) {
        // No movement, camera is fixed
    }

    public void render(SpriteBatch batch) {
        // Step 1: Render black background for black bars
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 1); // Black color

        // If the map is smaller than the viewport, draw black bars
        if (mapHeight < viewportHeight) {
            float barSize = (viewportHeight - mapHeight) / 1.3f;
            shapeRenderer.rect(0, 0, viewportWidth, barSize); // Bottom black bar
            shapeRenderer.rect(0, viewportHeight - barSize, viewportWidth, barSize); // Top black bar
        }

        shapeRenderer.end();

        // Step 2: Render the centered map
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        mapRenderer.dispose();
        shapeRenderer.dispose();
    }
}
