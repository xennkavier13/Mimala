package com.oop.mimala;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;

public class PlayerMovement {
    private float x, y;
    private float speed = 200f;
    private boolean isJumping = false;
    private float jumpSpeed = 350f;
    private float velocityX = 0;
    private float velocityY = 0;
    private float gravity = -900f; // Stronger gravity for realistic movement
    private float ground; // Ground level (can be set dynamically)
    private boolean isOnGround = false; // Flag to check if the player is on a platform

    public PlayerMovement(float startX, float startY) {
        this.x = startX;
        this.y = startY;
        this.ground = startY; // Set ground level to the initial Y position
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getVelocityX() { return velocityX; }

    public void move(float delta) {
        velocityX = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocityX = speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocityX = -speed;
        }

        x += velocityX * delta; // Apply movement
    }

    public void jump(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && isOnGround) {
            isJumping = true;
            velocityY = jumpSpeed; // Apply upward force
        }

        if (isJumping) {
            velocityY += gravity * delta; // Apply gravity
        }

        y += velocityY * delta; // Apply vertical movement

        // Check if player lands on the ground
        if (y <= ground && isOnGround) {
            y = ground;
            velocityY = 0;
            isJumping = false;
        }
    }

    /**
     * Updates the player's ground state based on collision detection.
     *
     * @param isOnGround True if the player is standing on a platform, false otherwise.
     */
    public void setOnGround(boolean isOnGround) {
        this.isOnGround = isOnGround;
        if (!isOnGround) {
            isJumping = true; // Player is falling
        }
    }
}
