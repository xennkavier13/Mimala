package com.oop.mimala;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    private PlayerMovement input;
    private BaseCharacter playerCharacter;
    private PauseMenu pauseMenu;
    private HealthBar healthBar;
    private BackgroundStage backgroundStage;
    private DeathScreen deathScreen;

    private Texture background;

    private int WIDTH;
    private int HEIGHT;

    private Array<Humanoid> enemies;

    private boolean isDead = false;

    @Override
    public void create() {
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        pauseMenu = new PauseMenu();
        deathScreen = new DeathScreen(WIDTH, HEIGHT);

        playerCharacter = new MiloCharacter(0, 100);
        healthBar = new HealthBar((MiloCharacter) playerCharacter);
        enemies = new Array<>();

        input = new PlayerMovement(playerCharacter.getX(), playerCharacter.getY());

        viewport = new FitViewport(WIDTH, HEIGHT);
        viewport.apply();
        backgroundStage = new BackgroundStage(WIDTH, HEIGHT);

        Gdx.input.setCursorCatched(true);

        background = new Texture("assets/background_test.png");

        spawnEnemiesOnStage();
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

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            for (int i = enemies.size - 1; i >= 0; i--) {
                Humanoid enemy = enemies.get(i);
                float distanceToEnemy = Math.abs(enemy.getX() - playerCharacter.getX());

                if (distanceToEnemy <= 100) {
                    enemy.takeDamage(7);
                    System.out.println("Enemy took damage! Current HP: " + enemy.getHealth());

                    if (enemy.isDead()) {
                        System.out.println("Enemy defeated!");
                        enemies.removeIndex(i);
                    }
                }
            }
        }

        ScreenUtils.clear(0, 0, 0, 1);

        input.move(delta);
        input.jump(delta);
        playerCharacter.update(delta, input.getVelocityX(), Gdx.input.isButtonJustPressed(Input.Buttons.LEFT));

        playerCharacter.move(input.getX(), input.getY());
        backgroundStage.update(delta);

        for (Humanoid enemy : enemies) {
            boolean shouldAttack = Math.abs(enemy.getX() - playerCharacter.getX()) <= enemy.getAttackRange();
            enemy.update(delta, playerCharacter.getX(), shouldAttack, playerCharacter);
        }

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        batch.draw(background, 0, 0, WIDTH, HEIGHT);
        batch.end();

        batch.begin();
        backgroundStage.render(batch);
        playerCharacter.render(batch);

        for (Humanoid enemy : enemies) {
            enemy.render(batch);
        }
        batch.end();

        batch.begin();
        healthBar.render(batch);
        batch.end();

        if (isDead) {
            deathScreen.render(batch);
            if (deathScreen.isExit()) {
                Gdx.app.exit();
            }
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
        deathScreen.dispose();
    }

    private void spawnEnemiesOnStage() {
    }
}
