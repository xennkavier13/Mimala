package com.oop.mimala;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

public class CameraController {
    private OrthographicCamera camera;
    private final float lerpFactor = 0.1f; // Smooth movement factor
    private final float zoomLerpFactor = 0.05f; // Smooth zooming factor
    private final float minZoom = 0.3f; // Closer view
    private final float maxZoom = 0.7f; // Further view

    public CameraController(float viewportWidth, float viewportHeight) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, viewportWidth, viewportHeight);
        camera.zoom = maxZoom; // Start zoomed out
        camera.position.set(viewportWidth / 2f, viewportHeight / 2f, 0);
        camera.update();
    }

    public void follow(BaseCharacter character, PlayerMovement movement, float delta) {
        if (character == null || movement == null) return;

        // Get player position
        float targetX = character.getX();
        float targetY = character.getY();

        // Get velocity from PlayerMovement
        float speed = Math.abs(movement.getVelocityX());

        // Adjust zoom based on speed (faster = zoom out, slower = zoom in)
        float targetZoom = MathUtils.clamp(maxZoom - (speed * 0.001f), minZoom, maxZoom);

        // Smoothly move camera using LERP
        camera.position.x = MathUtils.lerp(camera.position.x, targetX, lerpFactor);
        camera.position.y = MathUtils.lerp(camera.position.y, targetY, lerpFactor);
        camera.zoom = MathUtils.lerp(camera.zoom, targetZoom, zoomLerpFactor); // Smooth zoom transition

        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
