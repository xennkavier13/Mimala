package com.oop.mimala.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.oop.mimala.BaseCharacter;

public class MiloCharacter extends BaseCharacter {
    public MiloCharacter(float startX, float startY) {
        super(startX, startY, 100);
    }

    @Override
    protected void loadAnimations() {
        Array<TextureRegion> walkFrames = new Array<>();
        for (int i = 1; i <= 6; i++) {
            walkFrames.add(new TextureRegion(new Texture(Gdx.files.internal("Milo Reyes/milo_walking/milo_walking" + i + ".png"))));
        }

        Array<TextureRegion> idleFrames = new Array<>();
        for (int i = 1; i <= 6; i++) {
            idleFrames.add(new TextureRegion(new Texture(Gdx.files.internal("Milo Reyes/milo_standing/milo_standing" + i + ".png"))));
        }

        Array<TextureRegion> attackFrames = new Array<>();
        for (int i = 1; i <= 6; i++) {
            attackFrames.add(new TextureRegion(new Texture(Gdx.files.internal("Milo Reyes/milo_attack/milo_attack" + i + ".png"))));
        }


        if (walkFrames.size > 0) {
            walkAnimation = new Animation<>(0.1f, walkFrames, Animation.PlayMode.LOOP);
        }
        if (idleFrames.size > 0) {
            idleAnimation = new Animation<>(0.2f, idleFrames, Animation.PlayMode.LOOP);
        }
        if (attackFrames.size > 0) {
            attackAnimation = new Animation<>(0.05f, attackFrames, Animation.PlayMode.NORMAL);
        }
    }
}
