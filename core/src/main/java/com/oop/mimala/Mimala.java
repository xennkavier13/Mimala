package com.oop.mimala;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.mimala.characters.SantoCharacter;

public class Mimala extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture bg;

    private Viewport viewport;
    private CameraController cameraController;
    private PlayerMovement input;
    private BaseCharacter playerCharacter;
    private PauseMenu pauseMenu;

    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    @Override
    public void create() {
        batch = new SpriteBatch();
        bg = new Texture(Gdx.files.internal("bg.jpg"));

        viewport = new FitViewport(WIDTH, HEIGHT);
        viewport.apply();

        pauseMenu = new PauseMenu();

        // ✅ Initialize the game with Santo as the first character
        playerCharacter = new SantoCharacter(100,50);
        input = new PlayerMovement(playerCharacter.getX(), playerCharacter.getY());
        cameraController = new CameraController(WIDTH, HEIGHT);

        Gdx.input.setCursorCatched(true); // ✅ Hide cursor during gameplay
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

        // ✅ Update movement & character animations
        input.move(delta);
        input.jump(delta);
        playerCharacter.update(delta, input.getVelocityX(), Gdx.input.isButtonJustPressed(Input.Buttons.LEFT));

        // ✅ Sync movement with PlayerMovement
        playerCharacter.move(input.getX(), input.getY());
        cameraController.follow(playerCharacter);

        batch.setProjectionMatrix(cameraController.getCamera().combined);

        batch.begin();
        batch.draw(bg, 0, 0, WIDTH, HEIGHT);
        playerCharacter.render(batch);
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
        if (playerCharacter != null) {
            playerCharacter.dispose();
        }
    }
}
