package com.oop.mimala;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Mimala extends ApplicationAdapter {
    private SpriteBatch batch;
    private Sprite testball;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Texture bg; //test

    //for movement
    private PlayerMovement input;

    //Game Resolution
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    //initialize all global variables here
    @Override
    public void create() {
        batch = new SpriteBatch();
        testball = new Sprite(new Texture("testball.png"));
        camera = new OrthographicCamera();
        viewport = new FitViewport(WIDTH, HEIGHT, camera);
        viewport.apply();

        camera.position.set(WIDTH / 2f, HEIGHT / 2f, 0);
        camera.update();
        input = new PlayerMovement();

        bg = new Texture("bg.png");
    }




    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        float delta = Gdx.graphics.getDeltaTime();

        input.input(delta);

        camera.position.set(input.getX() + testball.getWidth() / 2f, input.getY() + testball.getHeight() / 2f, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(bg, 0,0, WIDTH, HEIGHT);

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
