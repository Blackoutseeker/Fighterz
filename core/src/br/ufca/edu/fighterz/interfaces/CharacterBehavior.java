package br.ufca.edu.fighterz.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import br.ufca.edu.fighterz.collision.CharacterCollision;
import br.ufca.edu.fighterz.state.PlayerState;
import com.badlogic.gdx.math.Rectangle;

public interface CharacterBehavior {
    Vector2 getPosition();
    CharacterCollision getCollision();
    PlayerState getPlayerState();
    void update(float deltaTime, CharacterCollision anotherCharacterCollision,
                Vector2 anotherCharacterPosition,
                Rectangle leftWall, Rectangle rightWall);
    void render(SpriteBatch batch, float deltaTime);
    void dispose();
}
