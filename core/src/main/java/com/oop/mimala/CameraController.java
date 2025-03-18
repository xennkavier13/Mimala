package com.oop.mimala;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

public class CameraController {
    private OrthographicCamera camera;
    private final float lerpFactor = 0.1f; // Smooth movement factor

    public CameraController(float viewportWidth, float viewportHeight) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, viewportWidth, viewportHeight);
        camera.position.set(viewportWidth / 2f, viewportHeight / 2f, 0);
        camera.update();
    }

    public void follow(BaseCharacter character) {
        if (character == null) return;

        // Smoothly move camera to character's position (lerping)
        camera.position.x = MathUtils.lerp(camera.position.x, character.getX(), lerpFactor);
        camera.position.y = MathUtils.lerp(camera.position.y, character.getY(), lerpFactor);

        // Ensure camera stays within bounds (adjust limits if needed)
        float minX = camera.viewportWidth / 2f;
        float minY = camera.viewportHeight / 2f;
        float maxX = 1920 - minX; // Adjust according to world size
        float maxY = 1080 - minY;

        camera.position.x = MathUtils.clamp(camera.position.x, minX, maxX);
        camera.position.y = MathUtils.clamp(camera.position.y, minY, maxY);

        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
