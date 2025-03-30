package com.oop.mimala;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.oop.mimala.characters.MiloCharacter;

public class PlayerMovement {
    private float x, y;
    private float speed = 200f;
    private boolean isJumping = false;
    private int jumpCount = 0;
    private final int maxJumps = 2;
    private float jumpSpeed = 350f;
    private float velocityX = 0;
    private float velocityY = 0;
    private float gravity = -900f;
    private float ground = 150f;

    // Dash variables
    private boolean isDashing = false;
    private float dashSpeed = 500f;   // Speed increase while dashing
    private float dashDuration = 0.2f; // Dash lasts for 0.2 seconds
    private float dashCooldown = 1.5f; // Cooldown before dashing again
    private float dashTimer = 0;
    private float cooldownTimer = 0;
    private int dashDirection = 0; // -1 for left, 1 for right

    public PlayerMovement(float startX, float startY) {
        this.x = startX;
        this.y = startY;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getVelocityX() { return velocityX; }
    public boolean isDashing() { return isDashing; }

    public void move(float delta) {
        velocityX = 0;

        // Handle dash cooldown
        if (cooldownTimer > 0) {
            cooldownTimer -= delta;
        }


        // Dash activation (only if moving left or right)
        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT) && cooldownTimer <= 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                isDashing = true;
                dashDirection = 1; // Dash to the right
            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                isDashing = true;
                dashDirection = -1; // Dash to the left
            }

            if (isDashing) {
                dashTimer = dashDuration;
                cooldownTimer = dashCooldown;
            }
        }

        // Dash logic
        if (isDashing) {
            if (dashTimer > 0) {
                dashTimer -= delta;
                velocityX = dashDirection * dashSpeed;
            } else {
                isDashing = false; // Stop dashing when timer ends
                dashDirection = 0;
            }
        } else {
            // Regular movement
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                velocityX = speed;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                velocityX = -speed;
            }
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
            jumpCount = 0;
        }
    }


    public boolean useUltimate() {
        return Gdx.input.isKeyJustPressed(Input.Keys.R);
    }

    public void update(float delta, MiloCharacter player, BaseCharacter enemy) {
        boolean isAttacking = Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
        boolean isDashing = this.isDashing; // Pass the dashing state to the player
        if (isDashing) {
            player.dash(); // Activate dash animation in MiloCharacter
        }
        player.update(delta, isAttacking, isDashing, enemy);
    }


}
