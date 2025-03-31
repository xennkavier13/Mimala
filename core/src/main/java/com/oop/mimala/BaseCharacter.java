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
    protected Animation<TextureRegion> attackAnimation2;
    protected Animation<TextureRegion> attackAnimation3;
    protected Animation<TextureRegion> deathAnimation;
    protected Animation<TextureRegion> getHitAnimation;

    protected float stateTime;
    protected float x, y;
    protected boolean isMoving = false;
    protected boolean facingRight = true;
    protected boolean isAttacking = false;
    protected int attackCombo = 0;

    protected TextureRegion currentFrame;
    protected Array<Texture> textures;

    protected float health; // Health system
    protected float maxHealth;

    protected boolean isDying = false;
    protected boolean deathAnimationPlayed = false;

    protected boolean isGettingHit = false;
    protected boolean isInvincible = false; // Add invincibility flag

    public BaseCharacter(float startX, float startY, float maxHealth) {
        this.x = startX;
        this.y = startY;
        this.maxHealth = maxHealth;
        this.health = maxHealth; // Start with full health
        stateTime = 0f;
        textures = new Array<>();

        loadAnimations();
    }

    protected abstract void loadAnimations();

    public void update(float delta, float velocityX, boolean attack) {
        stateTime += delta; // Always update state time

        if (isDead()) {
            if (!isDying) {
                isDying = true;
                stateTime = 0; // Reset animation timer for death animation
            }
            if (deathAnimation != null && !deathAnimationPlayed) {
                currentFrame = deathAnimation.getKeyFrame(stateTime);
                if (deathAnimation.isAnimationFinished(stateTime)) {
                    deathAnimationPlayed = true;
                }
            }
            return; // Don't process other animations if dead
        }
        // **Getting Hit Animation**
        if (isGettingHit && getHitAnimation != null) {
            currentFrame = getHitAnimation.getKeyFrame(stateTime, false);

            if (getHitAnimation.isAnimationFinished(stateTime)) {
                isGettingHit = false; // Stop getting hit after animation ends
            }
            return; // Stop further updates when hit animation is playing
        }

        // **Attack Animation**
        if (attack && !isAttacking && !isGettingHit) { // Ensure attack doesn't start if getting hit
            isAttacking = true;
            stateTime = 0;
            attackCombo = (attackCombo + 1) % 3;
        }

        if (isAttacking) {
            switch (attackCombo) {
                case 0:
                    if (attackAnimation != null) {
                        currentFrame = attackAnimation.getKeyFrame(stateTime);
                        if (attackAnimation.isAnimationFinished(stateTime)) {
                            isAttacking = false;  // Only allow new attacks once this one finishes
                        }
                    }
                    break;
                case 1:
                    if (attackAnimation2 != null) {
                        currentFrame = attackAnimation2.getKeyFrame(stateTime);
                        if (attackAnimation2.isAnimationFinished(stateTime)) {
                            isAttacking = false;
                        }
                    }
                    break;
                case 2:
                    if (attackAnimation3 != null) {
                        currentFrame = attackAnimation3.getKeyFrame(stateTime);
                        if (attackAnimation3.isAnimationFinished(stateTime)) {
                            isAttacking = false;
                        }
                    }
                    break;
            }
        } else {
            isMoving = velocityX != 0;

            if (isMoving) {
                if (walkAnimation != null) {
                    currentFrame = walkAnimation.getKeyFrame(stateTime, true);
                }
            } else {
                if (idleAnimation != null) {
                    currentFrame = idleAnimation.getKeyFrame(stateTime, true);
                }
            }
        }

        if (velocityX > 0) {
            facingRight = true;
        } else if (velocityX < 0) {
            facingRight = false;
        }
    }

    public boolean isDeathAnimationComplete() {
        return deathAnimationPlayed;
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
        if(isDead()) return;
        this.x = dx;
        this.y = dy;
    }

    public float getX() { return x; }
    public float getY() { return y; }

    // Health management
    public void takeDamage(int damage) {
        if (!isInvincible) { // Only take damage if not invincible
            health -= damage;
            if (health <= 0) {
                health = 0;
            } else {
                isGettingHit = true; // Play get hit animation
                stateTime = 0; // Reset animation timer
                isAttacking = false; // Stop attack if getting hit
            }
            System.out.println("Character took damage: " + damage + ", Health: " + health);
        } else {
            System.out.println("Character is invincible and took no damage!");
        }
    }

    public void setInvincible(boolean invincible) {
        this.isInvincible = invincible;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public float getHealth() {
        return health;
    }


}
