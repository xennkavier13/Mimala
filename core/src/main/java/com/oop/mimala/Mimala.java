package com.oop.mimala;

import com.badlogic.gdx.*;
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
    private final int WIDTH = 1920;
    private final int HEIGHT = 1080;

    private Array<Humanoid> enemies; // List of enemies

    @Override
    public void create() {
        batch = new SpriteBatch();
        pauseMenu = new PauseMenu();


        playerCharacter = new MiloCharacter(100, 90); // Player's initial position
        healthBar = new HealthBar((MiloCharacter) playerCharacter);
        enemies = new Array<>(); // Enemy list

        input = new PlayerMovement(playerCharacter.getX(), playerCharacter.getY());
        cameraController = new CameraController(WIDTH, HEIGHT);

        viewport = new FitViewport(WIDTH, HEIGHT);
        viewport.apply();

        Gdx.input.setCursorCatched(true); // Hide cursor during gameplay

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

        ScreenUtils.clear(0, 0, 0, 1);

        input.move(delta);
        input.jump(delta);
        playerCharacter.update(delta, input.getVelocityX(), Gdx.input.isButtonJustPressed(Input.Buttons.LEFT));

        playerCharacter.move(input.getX(), input.getY());

        // Player Attack - Damage Enemies When Clicking Left Mouse Button
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            for (int i = enemies.size - 1; i >= 0; i--) {
                Humanoid enemy = enemies.get(i);
                float distanceToEnemy = Math.abs(enemy.getX() - playerCharacter.getX());

                if (distanceToEnemy <= 100) { // If close enough, deal damage
                    enemy.takeDamage(20);
                    System.out.println("Enemy took damage! Current HP: " + enemy.getHealth());

                    if (enemy.isDead()) {
                        System.out.println("Enemy defeated!");
                        enemies.removeIndex(i); // Remove enemy if dead
                    }
                }
            }
        }

        // Update all enemies before rendering
        for (Humanoid enemy : enemies) {
            boolean shouldAttack = Math.abs(enemy.getX() - playerCharacter.getX()) <= enemy.getAttackRange();
            enemy.update(delta, playerCharacter.getX(), shouldAttack, playerCharacter);
        }

        cameraController.follow(playerCharacter, input, delta);
        batch.setProjectionMatrix(cameraController.getCamera().combined);

        batch.begin();

        playerCharacter.render(batch);

        for (Humanoid enemy : enemies) {
            enemy.render(batch);
        }

        batch.end();

        // Render Health Bar (UI Layer)
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        healthBar.render(batch);
        batch.end();
    }




    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        healthBar.dispose();

        batch.dispose();
        pauseMenu.dispose();
        if (playerCharacter != null) {
            playerCharacter.dispose();
        }
    }

    private void spawnEnemiesOnStage() {
//        enemies.add(new Humanoid(500, 150));
//        enemies.add(new Humanoid(900, 150));
//        enemies.add(new Humanoid(1300, 150));
//        enemies.add(new Humanoid(1600, 150));
    }
}
