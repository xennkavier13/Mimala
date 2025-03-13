package com.oop.mimala;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private float x, y; //tracks the position of the testball
    private float speed = 200f; //speed in pixels per second

    private OrthographicCamera camera;
    private Viewport viewport;
    private final int VIRTUAL_WIDTH = 1920;
    private final int VIRTUAL_HEIGHT = 1080;


    //initialize all global variables here
    @Override
    public void create() {
        batch = new SpriteBatch();
        testball = new Sprite(new Texture("testball.png"));
        x = 0;
        y = 0;
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();

        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();
    }

    public void movementInput(float delta) {
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

    }

    @Override
    public void render() {
        //clears the screen everytime render is called
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        //delta makes it less laggy when moving
        float delta = Gdx.graphics.getDeltaTime();
        movementInput(delta);

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
