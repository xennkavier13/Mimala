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

    public void follow(Object target) {
        if (target instanceof Sprite) {
            Sprite sprite = (Sprite) target;
            camera.position.set(sprite.getX() + sprite.getWidth() / 2f, sprite.getY() + sprite.getHeight() / 2f, 0);
        } else if (target instanceof AnimationCharacter) {
            AnimationCharacter character = (AnimationCharacter) target;
            camera.position.set(character.getX(), character.getY(), 0);
        }
        camera.update();
    }


    public OrthographicCamera getCamera() {
        return camera;
    }
}
