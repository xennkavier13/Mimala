package com.oop.mimala.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oop.mimala.characters.MiloCharacter;

public class HealthBar {
    private Texture[] healthFrames;
    private MiloCharacter player;
    private int screenWidth;
    private int screenHeight;

    private final float BAR_X = 0;
    private final float BAR_Y = 900; // Changed to top-left corner

    public HealthBar(MiloCharacter player) {
        this.player = player;
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
        healthFrames = new Texture[19];

        for (int i = 0; i < 19; i++) {
            int hpValue = i * 5;
            String path = "ui/healthBar/health_" + hpValue + ".png";

            try {
                healthFrames[i] = new Texture(Gdx.files.internal(path));
                System.out.println("Loaded: " + path);
            } catch (Exception e) {
                System.err.println(" ERROR: Failed to load " + path);
                healthFrames[i] = null;
            }
        }
    }

    public void render(SpriteBatch batch) {
        if (player == null) {
            System.err.println("ERROR: Player is null in HealthBar");
            return;
        }

        int hpIndex = Math.max(0, Math.min(18, (int) (player.getHealth() / 5)));

        if (healthFrames[hpIndex] == null) {
            System.err.println("ERROR: Missing texture for health index " + hpIndex);
            return;
        }

        float scale = 3.0f;
        float width = healthFrames[hpIndex].getWidth() * scale;
        float height = healthFrames[hpIndex].getHeight() * scale;

        batch.draw(healthFrames[hpIndex], BAR_X, BAR_Y, width, height);
    }

    public void dispose() {
        for (Texture texture : healthFrames) {
            if (texture != null) texture.dispose();
        }
    }
}
