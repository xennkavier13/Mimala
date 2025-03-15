package com.oop.mimala;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerMovement {
    private float x, y;
    private float speed = 200f;
    private boolean isJumping = false;
    private float jumpHeight = 50f;
    private float jumpSpeed = 300f;
    private float velocityX = 0;

    public PlayerMovement(float startX, float startY) { // ✅ Initialize at character position
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

        x += velocityX * delta; // ✅ Apply movement
    }

    public void jump(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isJumping) {
            isJumping = true;
        }

        if (isJumping) {
            y += jumpSpeed * delta;
            if (y >= jumpHeight) {
                isJumping = false;
            }
        } else {
            if (y > 0) {
                y -= jumpSpeed * delta;
            } else {
                y = 0;
            }
        }
    }
}
