package com.oop.mimala.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.oop.mimala.BaseCharacter;

public class Humanoid extends BaseCharacter {
    private float attackRange = 50f; // Distance at which enemy attacks
    private boolean isAttacking = false;
    private float speed = 50f; // Enemy movement speed
    private float playerX; // Store player's X position
    private boolean facingRight = true;
    private float detectionRange = 300f; // Only start chasing when the player is within 300 pixels

    public Humanoid(float startX, float startY) {
        super(startX, startY, 100);
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

    public void update(float delta, float playerX, boolean attack, BaseCharacter player) {
        this.playerX = playerX;
        float distance = Math.abs(playerX - this.x);

        if (distance > detectionRange) {
            isAttacking = false;
            super.update(delta, 0, false);
            return;
        }

        // Attack if close enough
        if (distance <= attackRange) {
            isAttacking = true;
            if (attackAnimation.isAnimationFinished(stateTime)) { // Damage at the end of animation
                player.takeDamage(5); // Enemy deals 10 damage
                System.out.println("Player took damage! Current HP: " + player.getHealth());
            }
        } else {
            isAttacking = false;
            if (playerX > this.x) {
                this.x += speed * delta;
                facingRight = true;
            } else {
                this.x -= speed * delta;
                facingRight = false;
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
    }

    public void takeDamage(float amount) {
        health -= amount;
        System.out.println("Humanoid took " + amount + " damage! Current HP: " + health);
    }

    public boolean isDead() {
        return health <= 0;
    }



    public float getAttackRange(){
        return attackRange;
    }
}
