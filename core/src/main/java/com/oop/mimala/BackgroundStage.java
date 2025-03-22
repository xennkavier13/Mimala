package com.oop.mimala;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class BackgroundStage implements Disposable {
    private Array<Texture> backgroundLayers; // Stores all 12 background layers
    private Array<Float> layerSpeeds; // Stores the scroll speed for each layer
    private Array<Rectangle> platforms; // List of platform rectangles
    private Texture platformTexture; // Texture for platforms

    public BackgroundStage() {
        // Load background layers
        backgroundLayers = new Array<>();
        layerSpeeds = new Array<>();

        for (int i = 1; i <= 11; i++) {
            backgroundLayers.add(new Texture("Background layers/Layer " + i + ".png"));
            layerSpeeds.add((float) i / 12); // Assign slower speeds to layers further back
        }

        // Load platform texture
        platformTexture = new Texture("stage.png");

        // Initialize platforms
        platforms = new Array<>();
        platforms.add(new Rectangle(0, 150, 1920, 50)); // Ground platform
        platforms.add(new Rectangle(500, 300, 200, 20)); // Raised platform
        platforms.add(new Rectangle(1000, 450, 200, 20)); // Another raised platform
    }

    /**
     * Renders the background layers with parallax scrolling.
     *
     * @param batch The SpriteBatch used for rendering.
     * @param cameraX The x-coordinate of the camera's position.
     */
    public void render(SpriteBatch batch, float cameraX) {
        // Draw each background layer with parallax effect
        for (int i = 0; i < backgroundLayers.size; i++) {
            Texture layer = backgroundLayers.get(i);
            float speed = layerSpeeds.get(i);

            // Calculate the offset for parallax scrolling
            float offsetX = cameraX * speed;

            // Draw the layer twice for seamless scrolling
            batch.draw(layer, -offsetX, 0, 1920, 900);
            batch.draw(layer, -offsetX + 1920, 0, 1920, 900);
        }

        // Draw the platforms
        for (Rectangle platform : platforms) {
            batch.draw(platformTexture, platform.x, platform.y, platform.width, platform.height);
        }
    }

    /**
     * Checks for collisions between the player and platforms.
     *
     * @param playerRect The rectangle representing the player's bounds.
     * @return True if the player is standing on a platform, false otherwise.
     */
    public boolean checkCollision(Rectangle playerRect) {
        for (Rectangle platform : platforms) {
            if (playerRect.overlaps(platform)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void dispose() {
        // Dispose of all textures
        for (Texture texture : backgroundLayers) {
            texture.dispose();
        }
        platformTexture.dispose(); // Dispose of the platform texture
    }
}
