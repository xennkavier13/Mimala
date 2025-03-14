package com.oop.mimala;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class CameraController {
    private OrthographicCamera camera;

    public CameraController(float viewportWidth, float viewportHeight) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, viewportWidth, viewportHeight);
        camera.position.set(viewportWidth / 2f, viewportHeight / 2f, 0);
        camera.update();
    }

    public void follow(Sprite target) {
        camera.position.set(target.getX() + target.getWidth() / 2f, target.getY() + target.getHeight() / 2f, 0);
        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
