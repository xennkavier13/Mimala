package com.oop.mimala;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Mimala extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private Texture testball;


    private float x, y; //tracks the position of the testball
    private float speed = 200f; //speed in pixels per second


    private OrthographicCamera camera;
    private Viewport viewport;
    private final int VIRTUAL_WIDTH = 800;
    private final int VIRTUAL_HEIGHT = 600;

    @Override
    public void create() {
        batch = new SpriteBatch();
        testball = new Texture("testball.png");
        x = 0;
        y = 0;

        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();

        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();
    }


    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);


        //delta makes it less laggy when moving
        float delta = Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += speed * delta;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            y += -(speed) * delta;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            x += -(speed) * delta;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += speed * delta;
        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(testball, x, y);
        batch.end();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

}
