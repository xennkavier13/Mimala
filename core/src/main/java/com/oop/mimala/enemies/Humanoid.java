package com.oop.mimala.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.oop.mimala.BaseCharacter;
import com.oop.mimala.UI.EnemyHealthBar;

public class Humanoid extends BaseCharacter {
    private float attackRange = 50f; // Distance at which enemy attacks
    private boolean isAttacking = false;
    private float speed = 50f; // Enemy movement speed
    private float playerX; // Store player's X position
    private boolean facingRight = true;
    private float detectionRange = 300f; // Only start chasing when the player is within 300 pixels
    private float attackCooldown = 1.0f; // 1 second cooldown
    private float attackTimer = 0f;
    private EnemyHealthBar healthBar; // Enemy health bar instance

    public Humanoid(float startX, float startY, OrthographicCamera camera) {
        super(startX, startY, 100);
        healthBar = new EnemyHealthBar(this, camera); // Attach health bar
    }

    @Override
    protected void loadAnimations() {
        Array<TextureRegion> idleFrames = new Array<>();
        for (int i = 1; i <= 8; i++) {
            idleFrames.add(new TextureRegion(new Texture(Gdx.files.internal("assets/MOBS&BOSS/Humanoids/standing_humanoid/standing_humanoid" + i + ".png"))));
        }

        Array<TextureRegion> walkFrames = new Array<>();
        for (int i = 1; i <= 8; i++) {
            walkFrames.add(new TextureRegion(new Texture(Gdx.files.internal("assets/MOBS&BOSS/Humanoids/walking_humanoid/walking_humanoid" + i + ".png"))));
        }

        Array<TextureRegion> attackFrames = new Array<>();
        for (int i = 1; i <= 9; i++) {
            attackFrames.add(new TextureRegion(new Texture(Gdx.files.internal("assets/MOBS&BOSS/Humanoids/attack_humanoid/standing_humanoid" + i + ".png"))));
        }

        if (walkFrames.size > 0) {
            walkAnimation = new Animation<>(0.1f, walkFrames, Animation.PlayMode.LOOP);
        }
        if (idleFrames.size > 0) {
            idleAnimation = new Animation<>(0.2f, idleFrames, Animation.PlayMode.LOOP);
        }
        if (attackFrames.size > 0) {
            attackAnimation = new Animation<>(0.1f, attackFrames, Animation.PlayMode.NORMAL);
        }
    }

    private boolean damageApplied = false; // Prevent multiple hits per animation

    public void update(float delta, float playerX, boolean attack, BaseCharacter player) {
        this.playerX = playerX;
        float distance = Math.abs(playerX - this.x);

        // Update attack cooldown timer
        if (attackTimer > 0) {
            attackTimer -= delta;
        }

        // If player is outside detection range, idle
        if (distance > detectionRange) {
            isAttacking = false;
            attackTimer = 0; // Reset cooldown if too far
            super.update(delta, 0, false);
            return;
        }

        // **ATTACK LOGIC**
        if (distance <= attackRange) {
            if (!isAttacking && attackTimer <= 0) { // Start attack only when cooldown is over
                isAttacking = true;
                attackTimer = attackCooldown; // Start cooldown
                stateTime = 0; // Reset animation timer
                damageApplied = false; // Reset damage flag for this attack
            }

            // **Set Attack Animation**
            if (isAttacking && attackAnimation != null) {
                currentFrame = attackAnimation.getKeyFrame(stateTime, false);

                // **Apply damage at a specific frame**
                float animationProgress = stateTime / attackAnimation.getAnimationDuration();
                if (!damageApplied && animationProgress >= 0.5f) { // Example: Apply damage at 50% of animation
                    player.takeDamage(5);
                    damageApplied = true; // Prevent multiple hits per attack
                    System.out.println("Player took damage! Current HP: " + player.getHealth());
                }

                if (attackAnimation.isAnimationFinished(stateTime)) {
                    isAttacking = false; // Stop attack after animation completes
                    stateTime = 0; // Reset timer for next attack
                }
            }

        } else {
            // **MOVEMENT LOGIC** (Only if NOT Attacking)
            isAttacking = false;
            if (playerX > this.x) {
                this.x += speed * delta;
                facingRight = true;
            } else {
                this.x -= speed * delta;
                facingRight = false;
            }

            // **Set Walking Animation**
            if (walkAnimation != null) {
                currentFrame = walkAnimation.getKeyFrame(stateTime, true);
            }
        }

        super.update(delta, isAttacking ? 0 : speed, isAttacking);
    }




    @Override
    public void render(SpriteBatch batch) {
        if (currentFrame != null) {
            float drawX = facingRight ? x : x + currentFrame.getRegionWidth();
            float drawWidth = facingRight ? currentFrame.getRegionWidth() : -currentFrame.getRegionWidth();

            batch.draw(currentFrame, drawX, y, drawWidth, currentFrame.getRegionHeight());
        }
        healthBar.render(batch);
    }

    public void takeDamage(float amount) {
        health -= amount;
        System.out.println("Humanoid took " + amount + " damage! Current HP: " + health);
    }

    public boolean isDead() {
        return health <= 0;
    }

    public float getMaxHealth(){
        return maxHealth;
    }

    public float getAttackRange(){
        return attackRange;
    }

    public float getWidth(){
        return 128;
    }

    public float getHeight(){
        return 128;
    }
}
