package com.oop.mimala;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.mimala.UI.HealthBar;
import com.oop.mimala.characters.MiloCharacter;
import com.oop.mimala.enemies.Humanoid;

public class Mimala extends ApplicationAdapter {
    private SpriteBatch batch;
    private Viewport viewport;

    private CameraController cameraController;
    private PlayerMovement input;
    private BaseCharacter playerCharacter;
    private PauseMenu pauseMenu;
    private HealthBar healthBar;
    private BackgroundStage backgroundStage;

    private final int WIDTH = 1920;
    private final int HEIGHT = 900;

    private Array<Humanoid> enemies; // List of enemies

    private Texture deathOverlay;
    private BitmapFont deathFont;
    private GlyphLayout layout;
    private boolean isDead = false;

    @Override
    public void create() {
        batch = new SpriteBatch();
        pauseMenu = new PauseMenu();

        playerCharacter = new MiloCharacter(0, 0); // Player's initial position
        healthBar = new HealthBar((MiloCharacter) playerCharacter);
        enemies = new Array<>(); // Enemy list

        input = new PlayerMovement(playerCharacter.getX(), playerCharacter.getY());
        cameraController = new CameraController(WIDTH, HEIGHT);

        viewport = new FitViewport(WIDTH, HEIGHT);
        viewport.apply();
        backgroundStage = new BackgroundStage(WIDTH);

        Gdx.input.setCursorCatched(true); // Hide cursor during gameplay

        // Load death screen assets
        deathOverlay = new Texture(Gdx.files.internal("ui/death_overlay.png"));
        deathFont = new BitmapFont(Gdx.files.internal("ui/TrojanPro.fnt"));
        layout = new GlyphLayout();
        deathFont.getData().setScale(4);

        spawnEnemiesOnStage(); // Spawn enemies once at the start
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pauseMenu.togglePause();
        }

        if (pauseMenu.isPaused()) {
            pauseMenu.render();
            return;
        }

        if (playerCharacter.getHealth() <= 0) {
            isDead = true;
        }

        ScreenUtils.clear(0, 0, 0, 1);

        // Continue updating the game world even if the player is dead
        input.move(delta);
        input.jump(delta);
        playerCharacter.update(delta, input.getVelocityX(), Gdx.input.isButtonJustPressed(Input.Buttons.LEFT));

        playerCharacter.move(input.getX(), input.getY());
        backgroundStage.update(input.getVelocityX(), delta);

        // Update enemies (mobs keep moving)
        for (Humanoid enemy : enemies) {
            boolean shouldAttack = Math.abs(enemy.getX() - playerCharacter.getX()) <= enemy.getAttackRange();
            enemy.update(delta, playerCharacter.getX(), shouldAttack, playerCharacter);
        }

        cameraController.follow(playerCharacter, input, delta);
        batch.setProjectionMatrix(cameraController.getCamera().combined);

        batch.begin();
        backgroundStage.render(batch); // Render the background
        playerCharacter.render(batch); // Render the player

        // Render all enemies (keep them moving)
        for (Humanoid enemy : enemies) {
            enemy.render(batch);
        }
        batch.end();

        // Render UI (health bar, etc.)
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        healthBar.render(batch);
        batch.end();

        // Show death screen without stopping the world
        if (isDead) {
            renderDeathScreen();
        }
    }

    private void renderDeathScreen() {
        batch.begin();

        // Black-and-white translucent overlay (still see-through)
        batch.setColor(1, 1, 1, 0.70f);
        batch.draw(deathOverlay, 0, 0, WIDTH, HEIGHT);
        batch.setColor(1, 1, 1, 1);

        // "YOU DIED" message
        deathFont.setColor(1, 0, 0, 1);
        deathFont.getData().setScale(4);
        layout.setText(deathFont, "YOU DIED");
        deathFont.draw(batch, "YOU DIED", (WIDTH - layout.width) / 2f, HEIGHT / 2f + 100);

        // Click anywhere to exit (for now)
        deathFont.getData().setScale(1f);
        deathFont.setColor(1, 1, 1, 1);
        layout.setText(deathFont, "Click anywhere to exit");
        deathFont.draw(batch, "Click anywhere to exit", (WIDTH - layout.width) / 2f, HEIGHT / 2f - 50);

        batch.end();

        // Exit the game on touch
        if (Gdx.input.isTouched()) {
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        healthBar.dispose();
        backgroundStage.dispose();
        batch.dispose();
        pauseMenu.dispose();
        if (playerCharacter != null) {
            playerCharacter.dispose();
        }
        deathOverlay.dispose();
        deathFont.dispose();
    }

    private void spawnEnemiesOnStage() {
        OrthographicCamera camera = cameraController.getCamera();
        enemies.add(new Humanoid(500, 150, camera));
        enemies.add(new Humanoid(900, 150, camera));
        enemies.add(new Humanoid(1300, 150, camera));
        enemies.add(new Humanoid(1600, 150, camera));
    }
}
