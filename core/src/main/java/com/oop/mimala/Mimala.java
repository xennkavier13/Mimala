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
    private BaseCharacter playerCharacter;
    private PauseMenu pauseMenu;
    private CharacterSelectionMenu characterSelectionMenu;

    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private boolean gameStarted = false;

    @Override
    public void create() {
        batch = new SpriteBatch();
        bg = new Texture(Gdx.files.internal("bg.jpg"));

        viewport = new FitViewport(WIDTH, HEIGHT);
        viewport.apply();

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        pauseMenu = new PauseMenu(skin, WIDTH, HEIGHT);
        characterSelectionMenu = new CharacterSelectionMenu(skin, WIDTH, HEIGHT, this);

        Gdx.input.setCursorCatched(false); // ✅ Cursor visible in selection screen
    }

    public void startGame(BaseCharacter selectedCharacter) {
        this.playerCharacter = selectedCharacter;
        this.input = new PlayerMovement(playerCharacter.getX(), playerCharacter.getY());
        this.cameraController = new CameraController(WIDTH, HEIGHT);
        this.gameStarted = true;

        Gdx.input.setCursorCatched(true); // ✅ Hide cursor after selecting a character
    }

    @Override
    public void render() {
        if (!gameStarted) {
            characterSelectionMenu.render(); // ✅ Show selection menu before game starts
            return;
        }

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
        characterSelectionMenu = null; // ✅ Cleanup selection menu
        if (playerCharacter != null) {
            playerCharacter.dispose();
        }
    }
}
