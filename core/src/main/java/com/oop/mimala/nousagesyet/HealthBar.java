package com.oop.mimala.nousagesyet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HealthBar {
    private Texture healthTexture;
    private float maxHealth;
    private float currentHealth;
    private float width;
    private float height;

    // Fixed screen position (top-left corner)
    private static final float FIXED_X = 20; // Left padding
    private static final float FIXED_Y = 570; // Near top (assuming screen height = 600)

    public HealthBar(float maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.width = 200; // Adjust health bar width
        this.height = 15; // Adjust height for better visibility

        try {
            healthTexture = new Texture("healthbar.png"); // Ensure the file exists
        } catch (Exception e) {
            System.err.println("Error loading healthbar.png. Make sure it's in the assets folder!");
            healthTexture = null;
        }
    }

    public void update(float newHealth) {
        this.currentHealth = Math.max(0, newHealth); // Prevent negative health
    }

    public void render(SpriteBatch batch) {
        if (healthTexture == null) return; // Prevent crash if texture is missing

        float healthPercentage = currentHealth / maxHealth;
        batch.draw(healthTexture, FIXED_X, FIXED_Y, width * healthPercentage, height);
    }

    public void dispose() {
        if (healthTexture != null) healthTexture.dispose();
    }
}
