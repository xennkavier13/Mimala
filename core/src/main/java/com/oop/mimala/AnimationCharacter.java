package com.oop.mimala;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimationCharacter {
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> idleAnimation;
    private float stateTime;
    private float x, y;
    private TextureRegion currentFrame;
    private Array<Texture> textures;
    private boolean isMoving = false;
    private boolean facingRight = true; // ✅ Track direction

    public AnimationCharacter(float startX, float startY) {
        stateTime = 0f;
        this.x = startX;
        this.y = startY;
        textures = new Array<>();

        Array<TextureRegion> walkFrames = loadFrames(new String[]{
            "milo_walking/milo_walking1.png",
            "milo_walking/milo_walking2.png",
            "milo_walking/milo_walking3.png",
            "milo_walking/milo_walking4.png",
            "milo_walking/milo_walking5.png",
            "milo_walking/milo_walking6.png"
        });

        Array<TextureRegion> idleFrames = loadFrames(new String[]{
            "milo_standing/milo_standing1.png",
            "milo_standing/milo_standing2.png",
            "milo_standing/milo_standing3.png",
            "milo_standing/milo_standing4.png",
            "milo_standing/milo_standing5.png",
            "milo_standing/milo_standing6.png"
        });

        if (walkFrames.size > 0) {
            walkAnimation = new Animation<>(0.1f, walkFrames, Animation.PlayMode.LOOP);
        }
        if (idleFrames.size > 0) {
            idleAnimation = new Animation<>(0.2f, idleFrames, Animation.PlayMode.LOOP);
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

        if (isMoving && walkAnimation != null) {
            currentFrame = walkAnimation.getKeyFrame(stateTime);
        } else if (!isMoving && idleAnimation != null) {
            currentFrame = idleAnimation.getKeyFrame(stateTime);
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
                currentFrame.flip(true, false); // ✅ Flip left
            } else if (facingRight && currentFrame.isFlipX()) {
                currentFrame.flip(true, false); // ✅ Flip right
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
