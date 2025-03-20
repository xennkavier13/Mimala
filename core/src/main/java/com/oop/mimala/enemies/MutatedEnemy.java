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
        this.playerX = playerX; // ✅ Store player's X position
        float speed = 50f;
        float distance = Math.abs(playerX - this.x);

        if (distance <= attackRange) {
            isAttacking = true;
        } else {
            isAttacking = false;

            if (playerX > this.x) {
                this.x += speed * delta;
                facingRight = true;  // ✅ Face right
            } else {
                this.x -= speed * delta;
                facingRight = false; // ✅ Face left
            }
        }

        super.update(delta, isAttacking ? 0 : speed, isAttacking);
    }



    @Override
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame;

        if (isAttacking) {
            currentFrame = attackAnimation.getKeyFrame(stateTime, false); // Attack once
            if (attackAnimation.isAnimationFinished(stateTime)) {
                isAttacking = false; // Stop attacking after animation
                stateTime = 0; // Reset animation time
            }
        }  else if (Math.abs(this.playerX - this.x) > attackRange) {
            currentFrame = walkAnimation.getKeyFrame(stateTime, true); // Walking loop
        } else {
            currentFrame = idleAnimation.getKeyFrame(stateTime, true); // Idle loop
        }

        if ((!facingRight && currentFrame.isFlipX()) || (facingRight && !currentFrame.isFlipX())) {
            currentFrame.flip(true, false);
        }

        batch.draw(currentFrame, facingRight ? x : x + currentFrame.getRegionWidth(), y,
            facingRight ? currentFrame.getRegionWidth() : -currentFrame.getRegionWidth(),
            currentFrame.getRegionHeight());
    }


    public float getAttackRange(){
        return attackRange;
    }
}
