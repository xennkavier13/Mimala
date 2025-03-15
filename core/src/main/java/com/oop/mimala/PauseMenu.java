package com.oop.mimala;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class PauseMenu {
    private final Stage stage;
    private boolean isPaused;

    public PauseMenu(Skin skin, int width, int height) {
        stage = new Stage();
        this.isPaused = false;

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Window window = new Window("Paused", skin);
        window.setSize(400, 300);
        window.setPosition(width / 2f - 200, height / 2f - 150);

        // Resume Button
        TextButton resumeButton = new TextButton("Resume", skin);
        resumeButton.getLabel().setFontScale(2);
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                resume();
            }
        });

        // Exit Button
        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.getLabel().setFontScale(2);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        window.add(resumeButton).pad(10).row();
        window.add(exitButton).pad(10).row();

        table.add(window).center();
    }

    public void togglePause() {
        isPaused = !isPaused;
        Gdx.input.setInputProcessor(isPaused ? stage : null);
        Gdx.input.setCursorCatched(!isPaused);
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void resume() {
        isPaused = false;
        Gdx.input.setInputProcessor(null);
        Gdx.input.setCursorCatched(true);
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
