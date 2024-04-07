package br.ufca.edu.fighterz.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameStageBehavior {
    void update(float deltaTime);
    void render(SpriteBatch batch);
    void dispose();
    int getStageTextureHeight();
}
