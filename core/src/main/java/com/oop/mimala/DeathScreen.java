package com.oop.mimala;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DeathScreen {
    private Texture deathOverlay;
    private BitmapFont deathFont;
    private GlyphLayout layout;
    private int width;
    private int height;
    private boolean exit = false;

    public DeathScreen(int width, int height) {
        this.width = width;
        this.height = height;
        deathOverlay = new Texture(Gdx.files.internal("ui/death_overlay.png"));
        deathFont = new BitmapFont(Gdx.files.internal("ui/TrojanPro.fnt"));
        layout = new GlyphLayout();
        deathFont.getData().setScale(4);
    }

    public void render(SpriteBatch batch) {
        batch.begin();

        // Black-and-white translucent overlay (still see-through)
        batch.setColor(1, 1, 1, 0.70f);
        batch.draw(deathOverlay, 0, 0, width, height);
        batch.setColor(1, 1, 1, 1);

        // "YOU DIED" message
        deathFont.setColor(1, 0, 0, 1);
        deathFont.getData().setScale(4);
        layout.setText(deathFont, "YOU DIED");
        deathFont.draw(batch, "YOU DIED", (width - layout.width) / 2f, height / 2f + 100);

        // Click anywhere to exit (for now)
        deathFont.getData().setScale(1f);
        deathFont.setColor(1, 1, 1, 1);
        layout.setText(deathFont, "Click anywhere to exit");
        deathFont.draw(batch, "Click anywhere to exit", (width - layout.width) / 2f, height / 2f - 50);

        batch.end();

        // Exit the game on touch
        if (Gdx.input.isTouched()) {
            exit = true;
        }
    }

    public void dispose() {
        deathOverlay.dispose();
        deathFont.dispose();
    }

    public boolean isExit() {
        return exit;
    }
}
