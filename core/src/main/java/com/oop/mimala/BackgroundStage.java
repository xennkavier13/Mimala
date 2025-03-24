package com.oop.mimala;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;

public class BackgroundStage implements Disposable {
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private float scrollX; // Tracks horizontal scrolling
    private final float viewportWidth;


    public BackgroundStage(float viewportWidth) {
        this.viewportWidth = viewportWidth;

        // Load the Tiled map
        tiledMap = new TmxMapLoader().load("stage_test.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap); // Adjust scale if needed
        // Adjust scrolling
        scrollX = 0;
    }

    public void update(float playerVelocityX, float delta) {
        // Move the background based on the player's movement speed
        scrollX -= playerVelocityX * delta * 0.5f;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        mapRenderer.dispose();
    }
}
