package com.oop.mimala;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerMovement {
    private float x, y;
    private float speed = 200f;
    private boolean isJumping = false;
    private int jumpCount = 0; // Track jump count for double jump
    private final int maxJumps = 2; // Allow up to two jumps
    private float jumpSpeed = 350f;
    private float velocityX = 0;
    private float velocityY = 0;
    private float gravity = -900f;
    private float ground = 150f;

    public PlayerMovement(float startX, float startY) {
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

        x += velocityX * delta;
    }

    public void jump(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && jumpCount < maxJumps) {
            velocityY = jumpSpeed;
            isJumping = true;
            jumpCount++;
        }

        if (isJumping) {
            velocityY += gravity * delta;
        }

        y += velocityY * delta;

        if (y <= ground) {
            y = ground;
            velocityY = 0;
            isJumping = false;
            jumpCount = 0; // Reset jump count when landing
        }
    }
}
