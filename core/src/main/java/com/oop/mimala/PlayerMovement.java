package com.oop.mimala;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerMovement {
    private float x, y;
    private float speed = 200f;
    private boolean isJumping = false;
    private float jumpHeight = 50f;
    private float jumpSpeed = 300f;

    private float velocityX = 0; // Add velocity tracking

    public PlayerMovement() {
        this.x = 0;
        this.y = 0;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getVelocityX() { return velocityX; } // New method to get velocity

    public void move(float delta) {
        velocityX = 0; // Reset velocity each frame

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocityX = speed; // Move right
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocityX = -speed; // Move left
        }

        x += velocityX * delta; // Apply movement
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



