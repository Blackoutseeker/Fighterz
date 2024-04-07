package br.ufca.edu.fighterz.interfaces;

import br.ufca.edu.fighterz.collision.CharacterCollision;
import br.ufca.edu.fighterz.collision.Hitbox;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface CollisionProcessor {
    void setHitboxesByType(final CharacterCollision.HitboxType hitboxType, final Hitbox[] hitboxes);
    void setBodyRectangle(final float x, final float y, final float width, final float height);
    void iterateHitboxes(final CharacterCollision.HitboxType hitboxType, final int currentFrameIndex,
                         final int startIndex, final int endIndex);
    Rectangle getBodyRectangle();
    Rectangle getAttackRectangle();
    boolean getHit(Rectangle attackRectangle, float deltaTime, Vector2 anotherCharacterPosition);
    boolean isBlocking();
    boolean checkBodyCollision(Rectangle bodyRectangle);
}
