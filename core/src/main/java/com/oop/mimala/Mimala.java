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
        private Viewport viewport;

        private CameraController cameraController;
        private PlayerMovement input;
        private BaseCharacter playerCharacter;
        private PauseMenu pauseMenu;
        private BackgroundStage backgroundStage;

        private final int WIDTH = 1920;
        private final int HEIGHT = 900;

        @Override
        public void create() {
            batch = new SpriteBatch();

            backgroundStage = new BackgroundStage();

            pauseMenu = new PauseMenu();

            playerCharacter = new SantoCharacter(100,90);

            input = new PlayerMovement(playerCharacter.getX(), playerCharacter.getY());

            cameraController = new CameraController(WIDTH, HEIGHT);


            viewport = new FitViewport(WIDTH, HEIGHT);
            viewport.apply();


            Gdx.input.setCursorCatched(true); // Hide cursor during gameplay
        }

        @Override
        public void render() {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { // Pauses the game when Esc is pressed
                pauseMenu.togglePause();
            }

            if (pauseMenu.isPaused()) { // Renders pause screen
                pauseMenu.render();
                return;
            }

            ScreenUtils.clear(0,0,0, 1);

            float delta = Gdx.graphics.getDeltaTime();
            // Update movement & character animations
            input.move(delta);
            input.jump(delta);
            playerCharacter.update(delta, input.getVelocityX(), Gdx.input.isButtonJustPressed(Input.Buttons.LEFT));

            // Sync movement with PlayerMovement
            playerCharacter.move(input.getX(), input.getY());


            cameraController.follow(playerCharacter); // Camera follows the character

            batch.setProjectionMatrix(cameraController.getCamera().combined);


            backgroundStage.render(batch);


            batch.begin();
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

            pauseMenu.dispose();
            if (playerCharacter != null) {
                playerCharacter.dispose();
            }

            backgroundStage.dispose();

        }
    }
