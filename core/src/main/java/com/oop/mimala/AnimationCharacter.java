package com.oop.mimala;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimationCharacter {
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> attackAnimation; // ✅ Attack animation

    private float stateTime;
    private float x, y;
    private TextureRegion currentFrame;
    private Array<Texture> textures;
    private boolean isMoving = false;
    private boolean facingRight = true;
    private boolean isAttacking = false; // ✅ Track attack state

    public AnimationCharacter(float startX, float startY) {
        stateTime = 0f;
        this.x = startX;
        this.y = startY;
        textures = new Array<>();

        Array<TextureRegion> walkFrames = loadFrames(new String[]{
            "Milo Reyes/milo_walking/milo_walking1.png",
            "Milo Reyes/milo_walking/milo_walking2.png",
            "Milo Reyes/milo_walking/milo_walking3.png",
            "Milo Reyes/milo_walking/milo_walking4.png",
            "Milo Reyes/milo_walking/milo_walking5.png",
            "Milo Reyes/milo_walking/milo_walking6.png"
        });

        Array<TextureRegion> idleFrames = loadFrames(new String[]{
            "Milo Reyes/milo_standing/milo_standing1.png",
            "Milo Reyes/milo_standing/milo_standing2.png",
            "Milo Reyes/milo_standing/milo_standing3.png",
            "Milo Reyes/milo_standing/milo_standing4.png",
            "Milo Reyes/milo_standing/milo_standing5.png",
            "Milo Reyes/milo_standing/milo_standing6.png"
        });

        Array<TextureRegion> attackFrames = loadFrames(new String[]{
            "Milo Reyes/milo_attack/milo_attack1.png",
            "Milo Reyes/milo_attack/milo_attack2.png",
            "Milo Reyes/milo_attack/milo_attack3.png",
            "Milo Reyes/milo_attack/milo_attack4.png",
            "Milo Reyes/milo_attack/milo_attack5.png",
            "Milo Reyes/milo_attack/milo_attack6.png"
        });

        if (walkFrames.size > 0) {
            walkAnimation = new Animation<>(0.1f, walkFrames, Animation.PlayMode.LOOP);
        }
        if (idleFrames.size > 0) {
            idleAnimation = new Animation<>(0.2f, idleFrames, Animation.PlayMode.LOOP);
        }
        if (attackFrames.size > 0) {
            attackAnimation = new Animation<>(0.05f, attackFrames, Animation.PlayMode.NORMAL); // ✅ Play once
        }
    }

    private Array<TextureRegion> loadFrames(String[] framePaths) {
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

    public void update(float delta, float velocityX) {
        stateTime += delta;
        isMoving = velocityX != 0;

        // ✅ Handle attack when mouse is clicked
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            isAttacking = true;
            stateTime = 0; // ✅ Reset animation timer
            System.out.println("Attack triggered!"); // ✅ Debugging
        }

        if (isAttacking && attackAnimation != null) {
            currentFrame = attackAnimation.getKeyFrame(stateTime);
            System.out.println("Playing attack frame: " + attackAnimation.getKeyFrameIndex(stateTime));

            // ✅ Stop attacking when animation finishes
            if (attackAnimation.isAnimationFinished(stateTime)) {
                isAttacking = false;
                System.out.println("Attack animation finished.");
            }
        } else {
            // ✅ Only play walk/idle if not attacking
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
