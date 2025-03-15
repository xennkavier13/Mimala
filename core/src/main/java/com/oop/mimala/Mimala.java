package com.oop.mimala;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Mimala extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture bg;

    private Viewport viewport;

    private CameraController cameraController;
    private PlayerMovement input;
    private AnimationCharacter animationCharacter;

    private PauseMenu pauseMenu;

    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    @Override
    public void create() {
        batch = new SpriteBatch();
        bg = new Texture(Gdx.files.internal("bg.jpg"));

        viewport = new FitViewport(WIDTH, HEIGHT);
        viewport.apply();

        input = new PlayerMovement(100, 50); // ✅ Pass starting position
        cameraController = new CameraController(WIDTH, HEIGHT);
        animationCharacter = new AnimationCharacter(100, 50); // ✅ Match PlayerMovement

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        pauseMenu = new PauseMenu(skin, WIDTH, HEIGHT);

        Gdx.input.setCursorCatched(true);
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pauseMenu.togglePause();
        }

        if (pauseMenu.isPaused()) {
            pauseMenu.render();
            return;
        }

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        float delta = Gdx.graphics.getDeltaTime();

        input.move(delta);
        input.jump(delta);

        float velocityX = input.getVelocityX();
        animationCharacter.update(delta, velocityX);

        // ✅ Move character based on input
        animationCharacter.move(input.getX(), input.getY());

        cameraController.follow(animationCharacter);

        batch.setProjectionMatrix(cameraController.getCamera().combined);

        batch.begin();
        batch.draw(bg, 0, 0, WIDTH, HEIGHT);
        animationCharacter.render(batch);
        batch.end();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        bg.dispose();
        pauseMenu.dispose();
        animationCharacter.dispose();
    }
}
