package com.oop.mimala;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PauseMenu {
    private final Stage stage;
    private boolean isPaused;

    public PauseMenu() {
        stage = new Stage();
        this.isPaused = false;


        // ✅ Full-screen semi-transparent overlay
        Image overlay = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/pause_overlay.png")))));
        overlay.setColor(1, 1, 1, 0.5f); // Set transparency (50%)
        overlay.setFillParent(true); // ✅ Ensure it covers the whole screen
        stage.addActor(overlay);

        // ✅ Table for buttons
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // ✅ Resume Button (Image)
        ImageButton resumeButton = createImageButton(
            "ui/Pause/Resume/resume_normal.png",
            "ui/Pause/Resume/resume_hover.png",
            "ui/Pause/Resume/resume_hover.png"
        );
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                resume();
            }
        });

        // ✅ Exit Button (Image)
        ImageButton exitButton = createImageButton(
            "ui/Pause/Exit/exit_normal.png",
            "ui/Pause/Exit/exit_hover.png",
            "ui/Pause/Exit/exit_hover.png"
        );
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        // ✅ Add buttons to table
        table.add(resumeButton).pad(10).row();
        table.add(exitButton).pad(10);
    }

    private ImageButton createImageButton(String normal, String hover, String pressed) {
        TextureRegionDrawable normalDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(normal))));
        TextureRegionDrawable hoverDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(hover))));
        TextureRegionDrawable pressedDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pressed))));

        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.imageUp = normalDrawable;
        buttonStyle.imageOver = hoverDrawable;
        buttonStyle.imageDown = pressedDrawable;

        return new ImageButton(buttonStyle);
    }

    public void togglePause() {
        isPaused = !isPaused;
        Gdx.input.setInputProcessor(isPaused ? stage : null);

        // ✅ Show cursor when paused, hide when unpaused
        Gdx.input.setCursorCatched(!isPaused);
        Gdx.input.setCursorPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void resume() {
        isPaused = false;
        Gdx.input.setInputProcessor(null);
    }

    public void render() {
        if (isPaused) {
            stage.act();
            stage.draw();
        }
    }

    public void dispose() {
        stage.dispose();
    }
}
