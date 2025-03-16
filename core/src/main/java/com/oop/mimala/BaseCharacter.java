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

    public BaseCharacter(float startX, float startY) {
        this.x = startX;
        this.y = startY;
        stateTime = 0f;
        textures = new Array<>();

        loadAnimations();
    }

    // ✅ Each character must define its own animations
    protected abstract void loadAnimations();

    protected Array<TextureRegion> loadFrames(String[] framePaths) {
        Array<TextureRegion> frames = new Array<>();
        for (String path : framePaths) {
            try {
                Texture texture = new Texture(Gdx.files.internal(path));
                textures.add(texture);
                frames.add(new TextureRegion(texture));
            } catch (Exception e) {
                System.err.println("Error loading texture: " + path);
            }
        }
        return frames;
    }

    public void update(float delta, float velocityX, boolean attack) {
        // ✅ If attack just started, reset animation time
        if (attack && !isAttacking) {
            isAttacking = true;
            stateTime = 0;  // Reset time to start attack animation from frame 1
        }

        // ✅ If attacking, play attack animation until finished
        if (isAttacking && attackAnimation != null) {
            currentFrame = attackAnimation.getKeyFrame(stateTime);

            // ✅ Stop attacking after animation completes
            if (attackAnimation.isAnimationFinished(stateTime)) {
                isAttacking = false;
                stateTime = 0; // Reset for next action
            }
        }
        // ✅ If not attacking, play movement or idle animation
        else {
            isMoving = velocityX != 0;

            if (isMoving && walkAnimation != null) {
                currentFrame = walkAnimation.getKeyFrame(stateTime);
            } else if (!isMoving && idleAnimation != null) {
                currentFrame = idleAnimation.getKeyFrame(stateTime);
            }
        }

        // ✅ Flip character when direction changes
        if (velocityX > 0) {
            facingRight = true;
        } else if (velocityX < 0) {
            facingRight = false;
        }

        // ✅ Update animation time
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
}
