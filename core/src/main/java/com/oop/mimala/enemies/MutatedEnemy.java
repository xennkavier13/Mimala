package com.oop.mimala.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.oop.mimala.BaseCharacter;

public class MutatedEnemy extends BaseCharacter {
    private float attackRange = 50f; // Distance at which enemy attacks
    private boolean isAttacking = false;
    private float speed = 50f; // Enemy movement speed
    private float playerX; // Store player's X position
    private boolean facingRight = true;
    private float detectionRange = 300f; // Only start chasing when the player is within 300 pixels

    public MutatedEnemy(float startX, float startY) {
        super(startX, startY);
    }

    @Override
    protected void loadAnimations() {
        Array<TextureRegion> idleFrames = new Array<>();
        for (int i = 1; i <= 8; i++) {
            idleFrames.add(new TextureRegion(new Texture(Gdx.files.internal("MOBS&BOSS/Mutated/standing_mutant/standing_mutant" + i + ".png"))));
        }

        Array<TextureRegion> walkFrames = new Array<>();
        for (int i = 1; i <= 8; i++) {
            walkFrames.add(new TextureRegion(new Texture(Gdx.files.internal("MOBS&BOSS/Mutated/walking_mutant/walking_mutant" + i + ".png"))));
        }

        Array<TextureRegion> attackFrames = new Array<>();
        for (int i = 1; i <= 22; i++) {
            attackFrames.add(new TextureRegion(new Texture(Gdx.files.internal("MOBS&BOSS/Mutated/attack_mutant/attack_mutant" + i + ".png"))));
        }

        if (walkFrames.size > 0) {
            walkAnimation = new Animation<>(0.1f, walkFrames, Animation.PlayMode.LOOP);
        }
        if (idleFrames.size > 0) {
            idleAnimation = new Animation<>(0.2f, idleFrames, Animation.PlayMode.LOOP);
        }
        if (attackFrames.size > 0) {
            attackAnimation = new Animation<>(0.15f, attackFrames, Animation.PlayMode.NORMAL);
        }
    }

    public void update(float delta, float playerX, boolean attack) {
        this.playerX = playerX;
        float distance = Math.abs(playerX - this.x);

        if (distance > detectionRange) {
            // If player is too far, stay idle and don't move
            isAttacking = false;
            super.update(delta, 0, false); // No movement
            return;
        }

        // If player is within detection range, start chasing
        if (distance <= attackRange) {
            isAttacking = true;
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



    public float getAttackRange(){
        return attackRange;
    }
}
