package com.oop.mimala;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public abstract class BaseCharacter {
    protected Animation<TextureRegion> walkAnimation;
    protected Animation<TextureRegion> idleAnimation;
    protected Animation<TextureRegion> attackAnimation;

    protected float stateTime;
    protected float x, y;
    protected boolean isMoving = false;
    protected boolean facingRight = true;
    protected boolean isAttacking = false;

    protected TextureRegion currentFrame;
    protected Array<Texture> textures;

    protected float health; // ✅ Health system
    protected float maxHealth;

    public BaseCharacter(float startX, float startY, float maxHealth) {
        this.x = startX;
        this.y = startY;
        this.maxHealth = maxHealth;
        this.health = maxHealth; // ✅ Start with full health
        stateTime = 0f;
        textures = new Array<>();

        loadAnimations();
    }

    protected abstract void loadAnimations();

    public void update(float delta, float velocityX, boolean attack) {
        if (attack && !isAttacking) {
            isAttacking = true;
            stateTime = 0;
        }

        if (isAttacking && attackAnimation != null) {
            currentFrame = attackAnimation.getKeyFrame(stateTime);
            if (attackAnimation.isAnimationFinished(stateTime)) {
                isAttacking = false;
                stateTime = 0;
            }
        } else {
            isMoving = velocityX != 0;
            if (isMoving && walkAnimation != null) {
                currentFrame = walkAnimation.getKeyFrame(stateTime);
            } else if (!isMoving && idleAnimation != null) {
                currentFrame = idleAnimation.getKeyFrame(stateTime);
            }
        }

        if (velocityX > 0) {
            facingRight = true;
        } else if (velocityX < 0) {
            facingRight = false;
        }

        stateTime += delta;
    }

    public void render(SpriteBatch batch) {
        if (currentFrame != null) {
            if (!facingRight && !currentFrame.isFlipX()) {
                currentFrame.flip(true, false);
            } else if (facingRight && currentFrame.isFlipX()) {
                currentFrame.flip(true, false);
            }
            batch.draw(currentFrame, x, y);
        }
    }

    public void dispose() {
        for (Texture texture : textures) {
            texture.dispose();
        }
    }

    public void move(float dx, float dy) {
        this.x = dx;
        this.y = dy;
    }

    public float getX() { return x; }
    public float getY() { return y; }

    // ✅ Health management
    public void takeDamage(float amount) {
        health -= amount;
        if (health <= 0) {
            health = 0;
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    public float getHealth() {
        return health;
    }
}
