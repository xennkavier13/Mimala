package com.oop.mimala.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.oop.mimala.BaseCharacter;

public class MiloCharacter extends BaseCharacter {
    private float scale = 2.0f;
    private float targetX;
    private float targetY;

    public MiloCharacter(float startX, float startY) {
        super(startX, startY, 100);
        this.targetX = startX;
        this.targetY = startY;
        loadAnimations();
    }

    @Override
    protected void loadAnimations() {
        idleAnimation = loadAnimation("assets/Milo/Move/Idle.png", 6, 0.15f, Animation.PlayMode.LOOP);
        walkAnimation = loadAnimation("assets/Milo/Move/Run.png", 6, 0.13f, Animation.PlayMode.LOOP);
        attackAnimation = loadAnimation("assets/Milo/Basic Attack/Basic_Attack1.png", 4, 0.13f, Animation.PlayMode.NORMAL);

        attackAnimation2 = loadAnimation("assets/Milo/Basic Attack/Basic_Attack2.png", 4, 0.13f, Animation.PlayMode.NORMAL);
        attackAnimation3 = loadAnimation("assets/Milo/Basic Attack/Basic_Attack3.png", 4, 0.13f, Animation.PlayMode.NORMAL);
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

    public void move(float delta, float targetX, float targetY) {
        this.targetX = targetX;
        this.targetY = targetY;

        float lerpFactor = 0.1f;
        x = MathUtils.lerp(x, this.targetX, lerpFactor);
        y = MathUtils.lerp(y, this.targetY, lerpFactor);
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Animation<TextureRegion> getAttackAnimation2() {
        return attackAnimation2;
    }

    public Animation<TextureRegion> getAttackAnimation3() {
        return attackAnimation3;
    }
}
