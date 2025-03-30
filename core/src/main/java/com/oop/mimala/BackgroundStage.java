package com.oop.mimala;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;

public class BackgroundStage implements Disposable {
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private final int viewportWidth;
    private final int viewportHeight;

    public BackgroundStage(int viewportWidth, int viewportHeight) {
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;

        tiledMap = new TmxMapLoader().load("stage_test.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    public void update(float playerVelocityX, float delta) {
        // No scrolling, so no update needed
    }

    public void render(SpriteBatch batch) {
        // Set the SpriteBatch projection matrix to match the viewport
        batch.getProjectionMatrix().setToOrtho2D(0, 0, viewportWidth, viewportHeight);

        // Render the map at the fixed position (0, 0)
        mapRenderer.setView(batch.getProjectionMatrix(), 0, 0, viewportWidth, viewportHeight);
        mapRenderer.render();
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        mapRenderer.dispose();
    }
}
