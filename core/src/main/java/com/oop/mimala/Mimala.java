        package com.oop.mimala;

        import com.badlogic.gdx.*;
        import com.badlogic.gdx.graphics.g2d.SpriteBatch;
        import com.badlogic.gdx.math.MathUtils;
        import com.badlogic.gdx.utils.Array;
        import com.badlogic.gdx.utils.ScreenUtils;
        import com.badlogic.gdx.utils.viewport.FitViewport;
        import com.badlogic.gdx.utils.viewport.Viewport;
        import com.oop.mimala.characters.MiloCharacter;
        import com.oop.mimala.enemies.MutatedEnemy;

        public class Mimala extends ApplicationAdapter {
            private SpriteBatch batch;
            private Viewport viewport;

            private CameraController cameraController;
            private PlayerMovement input;
            private BaseCharacter playerCharacter;
            private PauseMenu pauseMenu;
            private BackgroundStage backgroundStage;

            private final int WIDTH = 1920;
            private final int HEIGHT = 900;

            private Array<MutatedEnemy> enemies; // ✅ List of enemies


            @Override
            public void create() {
                batch = new SpriteBatch();
                backgroundStage = new BackgroundStage();
                pauseMenu = new PauseMenu();

                playerCharacter = new MiloCharacter(100, 90); // Player's initial position
                enemies = new Array<>(); // Enemy list

                input = new PlayerMovement(playerCharacter.getX(), playerCharacter.getY());
                cameraController = new CameraController(WIDTH, HEIGHT);

                viewport = new FitViewport(WIDTH, HEIGHT);
                viewport.apply();

                Gdx.input.setCursorCatched(true); // Hide cursor during gameplay

                spawnEnemiesOnStage(); // ✅ Now enemies are only spawned ONCE
            }



            @Override
            public void render() {
                float delta = Gdx.graphics.getDeltaTime();
                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { // Pauses the game when Esc is pressed
                    pauseMenu.togglePause();
                }

                if (pauseMenu.isPaused()) { // Renders pause screen
                    pauseMenu.render();
                    return;
                }

                ScreenUtils.clear(0,0,0, 1);

                // Update movement & character animations
                input.move(delta);
                input.jump(delta);
                playerCharacter.update(delta, input.getVelocityX(), Gdx.input.isButtonJustPressed(Input.Buttons.LEFT));

                // Sync movement with PlayerMovement
                playerCharacter.move(input.getX(), input.getY());



                // ✅ Update enemies
                for (MutatedEnemy enemy : enemies) {
                    boolean shouldAttack = Math.abs(enemy.getX() - playerCharacter.getX()) <= enemy.getAttackRange();
                    enemy.update(delta, playerCharacter.getX(), shouldAttack);
                }

                cameraController.follow(playerCharacter, input, delta); // Camera follows the character

                batch.setProjectionMatrix(cameraController.getCamera().combined);


                backgroundStage.render(batch);


                batch.begin();
                playerCharacter.render(batch);

                for (MutatedEnemy enemy : enemies) {
                    enemy.render(batch);
                }
                batch.end();
            }

            @Override
            public void resize(int width, int height) {
                viewport.update(width, height, true);
            }

            @Override
            public void dispose() {
                batch.dispose();

                pauseMenu.dispose();
                if (playerCharacter != null) {
                    playerCharacter.dispose();
                }

                backgroundStage.dispose();

            }

            private void spawnEnemiesOnStage() {
                enemies.add(new MutatedEnemy(500, 150)); // First enemy position
                enemies.add(new MutatedEnemy(900, 150)); // Second enemy position
                enemies.add(new MutatedEnemy(1300, 150)); // Third enemy position
                enemies.add(new MutatedEnemy(1600, 150)); // Fourth enemy position
            }




        }
