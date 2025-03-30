package com.oop.mimala;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import java.util.*;

public class PlayerMovement {
    private float x, y;
    private float speed = 200f;
    private boolean isJumping = false;
    private float jumpSpeed = 400f;
    private float velocityX = 0;
    private float velocityY = 0;
    private float gravity = -900f; // Stronger gravity for realistic movement
    private float ground = 150f; // Default ground level



    public PlayerMovement(float startX, float startY) { // Initialize at character position
        this.x = startX;
        this.y = startY;
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isJumping) {
            isJumping = true;
            velocityY = jumpSpeed; // Apply upward force
        }

        if (isJumping) {
            velocityY += gravity * delta; // Apply gravity
        }

        y += velocityY * delta; // Apply vertical movement

        // Check if player lands on the ground
        if (y <= ground) {
            y = ground;
            velocityY = 0;
            isJumping = false;
        }
    }
}
