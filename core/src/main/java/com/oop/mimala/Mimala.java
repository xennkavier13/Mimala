package com.oop.mimala;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class Mimala extends ApplicationAdapter {
    private SpriteBatch batch;
    private Sprite testball;
    private Viewport viewport;
    private Texture bg;

    private boolean isPaused = false;
    private Stage stage;
    private Skin skin;


    private CameraController cameraController;
    private PlayerMovement input;

    // Game Resolution
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    // Background scrolling
    private float bgX = 0;
    private float scrollSpeed = 100; // Adjust speed

    @Override
    public void create() {
        batch = new SpriteBatch();
        testball = new Sprite(new Texture(Gdx.files.internal("testball.png")));
        bg = new Texture(Gdx.files.internal("bg.jpg"));

        viewport = new FitViewport(WIDTH, HEIGHT);
        viewport.apply();

        input = new PlayerMovement();
        cameraController = new CameraController(WIDTH, HEIGHT);

        Gdx.input.setCursorCatched(true);

        // Initialize UI stage
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json")); // Load a UI skin

// Create Pause Menu
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Window window = new Window("Paused", skin);
        window.setSize(400, 300); // Bigger window
        window.setPosition(WIDTH / 2f - 200, HEIGHT / 2f - 150); // Center window

// Resume Button
        TextButton resumeButton = new TextButton("Resume", skin);
        resumeButton.getLabel().setFontScale(2);  // Make text bigger
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isPaused = false;
                Gdx.input.setCursorCatched(true);
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

// Add buttons to window
        window.add(resumeButton).pad(10).row();
        window.add(exitButton).pad(10).row();

        table.add(window).center();



    }


    @Override
        public void render() {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                isPaused = !isPaused;
                Gdx.input.setCursorCatched(!isPaused);

                if (isPaused) {
                    Gdx.input.setInputProcessor(stage);  // Enable UI clicks
                } else {
                    Gdx.input.setInputProcessor(null);   // Reset input to game
                }
            }

            if (isPaused) {
                stage.act();
                stage.draw();
                return; // Don't update game logic when paused
            }

            ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

            float delta = Gdx.graphics.getDeltaTime();

            input.move(delta);
            input.jump(delta);

            cameraController.follow(testball);
            batch.setProjectionMatrix(cameraController.getCamera().combined);

            batch.begin();
            batch.draw(bg, 0, 0, WIDTH, HEIGHT);
            testball.setPosition(input.getX(), input.getY());
            testball.draw(batch);
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
        testball.getTexture().dispose();
    }
}
