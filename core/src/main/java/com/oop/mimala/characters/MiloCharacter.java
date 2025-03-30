package com.oop.mimala.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.oop.mimala.BaseCharacter;

public class MiloCharacter extends BaseCharacter {
    private float attackCooldown = 0.5f; // Cooldown duration in seconds
    private float cooldownTimer = 0; // Timer to track attack cooldown
    private boolean damageDealt = false; // Ensures damage is applied only once per attack
    private Animation<TextureRegion> dashAnimation;
    private boolean isDashing = false;
    private float dashTimer = 0;
    private float dashDuration = 0.2f; // Duration of the dash

    public MiloCharacter(float startX, float startY) {
        super(startX, startY, 100);
        loadAnimations();
    }

    @Override
    protected void loadAnimations() {
        idleAnimation = loadAnimation("assets/Milo/Move/Idle.png", 6, 0.15f, Animation.PlayMode.LOOP);
        walkAnimation = loadAnimation("assets/Milo/Move/Run.png", 6, 0.13f, Animation.PlayMode.LOOP);
        deathAnimation = loadAnimation("assets/Milo/Move/Death.png", 10,0.1f, Animation.PlayMode.NORMAL);
        attackAnimation = loadAnimation("assets/Milo/Basic Attack/Basic_Attack1.png", 4, 0.13f, Animation.PlayMode.NORMAL);
        attackAnimation2 = loadAnimation("assets/Milo/Basic Attack/Basic_Attack2.png", 4, 0.13f, Animation.PlayMode.NORMAL);
        attackAnimation3 = loadAnimation("assets/Milo/Basic Attack/Basic_Attack3.png", 4, 0.13f, Animation.PlayMode.NORMAL);
        getHitAnimation = loadAnimation("assets/Milo/Move/Get Hit.png", 4, 0.13f, Animation.PlayMode.NORMAL);
        dashAnimation = loadAnimation("assets/Milo/Skills/1stSkill_Dash.png", 4, 0.13f, Animation.PlayMode.NORMAL);

//

    }

    private Animation<TextureRegion> loadAnimation(String filePath, int frameCount, float frameDuration, Animation.PlayMode playMode) {
        Texture spriteSheet = new Texture(Gdx.files.internal(filePath));
        int frameWidth = spriteSheet.getWidth() / frameCount;
        int frameHeight = spriteSheet.getHeight();

        TextureRegion[][] tempFrames = TextureRegion.split(spriteSheet, frameWidth, frameHeight);
        Array<TextureRegion> animationFrames = new Array<>();

        for (int i = 0; i < frameCount; i++) {
            animationFrames.add(tempFrames[0][i]);
        }

        Animation<TextureRegion> animation = new Animation<>(frameDuration, animationFrames);
        animation.setPlayMode(playMode);
        return animation;
    }

    public void update(float delta, boolean attack, boolean isDashing, BaseCharacter enemy) {
        stateTime += delta;

        // **Force Death Animation if Dead**
        if (getHealth() <= 0) {
            if (deathAnimation != null) {
                currentFrame = deathAnimation.getKeyFrame(stateTime, false);
                isAttacking = false;
                isMoving = false;

                if (deathAnimation.isAnimationFinished(stateTime)) {
                    System.out.println("Milo is dead.");
                }
            }
            return; // Stop updating anything else
        }

        // **Dashing Logic - Process First**
        if (isDashing) {
            this.isDashing = true;  // Ensure dashing state is set
            dashTimer = dashDuration;
            stateTime = 0; // Reset animation time
        }

        if (this.isDashing) {
            currentFrame = dashAnimation.getKeyFrame(stateTime, false);

            if (dashTimer > 0) {
                dashTimer -= delta;
            } else {
                this.isDashing = false; // Stop dashing after duration
            }
            return; // Skip attack and movement updates during dash
        }

        // If not dead or dashing, continue normal updates
        if (cooldownTimer > 0) {
            cooldownTimer -= delta;
        }

        // **Attack Logic**
        if (attack && canAttack()) {
            isAttacking = true;
            stateTime = 0;
            attackCombo = (attackCombo + 1) % 3; // Cycle through attack animations
            damageDealt = false; // Reset damage flag
        }

        if (isAttacking) {
            Animation<TextureRegion> currentAttackAnim = getCurrentAttackAnimation();
            if (currentAttackAnim != null) {
                currentFrame = currentAttackAnim.getKeyFrame(stateTime);

                // Apply damage once
                if (!damageDealt && currentAttackAnim.getKeyFrameIndex(stateTime) == 3) {
                    enemy.takeDamage(10);
                    damageDealt = true; // Ensure damage is applied only once per attack
                    System.out.println("Enemy took damage! Current HP: " + enemy.getHealth());
                }

                // Reset attack state when animation ends
                if (currentAttackAnim.isAnimationFinished(stateTime)) {
                    isAttacking = false;
                    cooldownTimer = attackCooldown;
                }
            }
        } else {
            if (walkAnimation != null) {
                currentFrame = walkAnimation.getKeyFrame(stateTime, true);
            }
        }
    }


    public void dash() {
        if (!isDashing) {
            isDashing = true;
            dashTimer = dashDuration;
            stateTime = 0; // Reset animation time
        }
    }

    public boolean isDashing() {
        return isDashing;
    }


    private Animation<TextureRegion> getCurrentAttackAnimation() {
        switch (attackCombo) {
            case 0: return attackAnimation;
            case 1: return attackAnimation2;
            case 2: return attackAnimation3;
            default: return attackAnimation;
        }
    }

    public boolean canAttack() {
        return !isAttacking && cooldownTimer <= 0;
    }
}
