package com.oop.mimala.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.oop.mimala.enemies.Humanoid;

public class EnemyHealthBar {
    private Humanoid enemy;
    private OrthographicCamera camera;
    private Texture healthBarTexture;
    private Texture healthBarBackgroundTexture;
    private float barWidth;
    private float barHeight = 40f; // Increased height for better visibility
    private float offsetY; // Dynamic positioning above enemy

    public EnemyHealthBar(Humanoid enemy, OrthographicCamera camera) {
        this.enemy = enemy;
        this.camera = camera;

        // Load health bar textures
        healthBarTexture = new Texture("greenbar.png"); // Foreground bar
        healthBarBackgroundTexture = new Texture("greenbar_bg.png"); // Background bar

        this.barWidth = enemy.getWidth() * 1.5f; // 1.5x enemy width for larger bar
        this.offsetY = enemy.getHeight() + 20f; // Higher position above enemy
    }

    public void render(SpriteBatch batch) {
        float x = enemy.getX() - (barWidth - enemy.getWidth()) / 2; // Center bar over enemy
        float y = enemy.getY() + offsetY; // Place it above the enemy

        // Draw the background bar
        batch.draw(healthBarBackgroundTexture, x, y, barWidth, barHeight);

        // Calculate width based on health percentage
        float healthPercentage = enemy.getHealth() / enemy.getMaxHealth();
        float healthBarWidth = barWidth * healthPercentage;

        // Draw the health bar (foreground)
        batch.draw(healthBarTexture, x, y, healthBarWidth, barHeight);
    }

    public void dispose() {
        healthBarTexture.dispose();
        healthBarBackgroundTexture.dispose();
    }
}
