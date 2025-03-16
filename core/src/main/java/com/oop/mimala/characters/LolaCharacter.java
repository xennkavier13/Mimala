package com.oop.mimala.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.oop.mimala.BaseCharacter;

public class LolaCharacter extends BaseCharacter {
    public LolaCharacter(float startX, float startY) {
        super(startX, startY);
    }

    @Override
    protected void loadAnimations() {
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
            attackAnimation = new Animation<>(0.05f, attackFrames, Animation.PlayMode.NORMAL);
        }
    }
}
