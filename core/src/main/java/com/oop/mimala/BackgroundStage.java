package com.oop.mimala;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class BackgroundStage implements Disposable {
    private Texture backgroundTexture;
    private TextureRegion backgroundRegion;

    private float scrollX; // Tracks horizontal scrolling
    private final float viewportWidth; // Game screen width

    public BackgroundStage(float viewportWidth) {
        this.viewportWidth = viewportWidth;

        backgroundTexture = new Texture("assets/background_test.png");
        backgroundRegion = new TextureRegion(backgroundTexture);

        // Initialize scrollX to align with the viewport width
        scrollX = 0;
    }

    public void update(float playerVelocityX, float delta) {
        // Move the background based on the player's movement speed
        scrollX -= playerVelocityX * delta * 0.5f;
    }

    public void render(SpriteBatch batch) {
        float textureWidth = backgroundTexture.getWidth();

        //  Ensure a seamless loop by drawing the background multiple times
        batch.draw(backgroundRegion, scrollX, 0);
        batch.draw(backgroundRegion, scrollX + textureWidth, 0);
        batch.draw(backgroundRegion, scrollX - textureWidth, 0); //  Draw on the left side too

        // Reset scroll position when fully out of frame
        if (scrollX <= -textureWidth) {
            scrollX += textureWidth;
        } else if (scrollX >= textureWidth) {
            scrollX -= textureWidth;
        }
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
    }
}
