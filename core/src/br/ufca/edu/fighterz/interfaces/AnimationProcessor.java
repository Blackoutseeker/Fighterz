package br.ufca.edu.fighterz.interfaces;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface AnimationProcessor {
    void render(SpriteBatch batch, float x, float y);
    void dispose();

    Sprite getSprite();
    Animation<TextureRegion> getIdleAnimation();

    Animation<TextureRegion> getCrouchAnimation();

    Animation<TextureRegion> getMoveForwardAnimation();

    Animation<TextureRegion> getMoveBackAnimation();

    Animation<TextureRegion> getAutoTauntAnimation();

    Animation<TextureRegion> getLightPunchAnimation();

    Animation<TextureRegion> getStrongPunchAnimation();

    Animation<TextureRegion> getLightKickAnimation();

    Animation<TextureRegion> getStrongKickAnimation();

    Animation<TextureRegion> getLightHitAnimation();

    Animation<TextureRegion> getBlockAnimation();
}
