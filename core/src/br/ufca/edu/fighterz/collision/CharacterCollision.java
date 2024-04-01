package br.ufca.edu.fighterz.collision;

import br.ufca.edu.fighterz.state.PlayerState;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public final class CharacterCollision {
    private final PlayerState playerState;
    private final Rectangle bodyRectangle;
    private final Rectangle attackRectangle;
    private final float scale;
    private final Vector2 playerPosition;
    private int counter = 0;
    private int lastCurrentFrameIndex = 0;
    private Hitbox[] lightPunchHitboxes;
    private Hitbox[] lightKickHitboxes;
    private Hitbox[] strongPunchHitboxes;
    private Hitbox[] strongKickHitboxes;

    public enum HitboxType {
        LIGHT_PUNCH,
        LIGHT_KICK,
        STRONG_PUNCH,
        STRONG_KICK,
    }

    public CharacterCollision(final PlayerState playerState, final float scale, final Vector2 playerPosition) {
        bodyRectangle = new Rectangle();
        attackRectangle = new Rectangle();
        this.playerState = playerState;
        this.scale = scale;
        this.playerPosition = playerPosition;
    }

    public void setHitboxesByType(final HitboxType hitboxType, final Hitbox[] hitboxes) {
        switch (hitboxType) {
            case LIGHT_KICK:
                lightKickHitboxes = hitboxes;
                break;
            case STRONG_PUNCH:
                strongPunchHitboxes = hitboxes;
                break;
            case STRONG_KICK:
                strongKickHitboxes = hitboxes;
                break;
            case LIGHT_PUNCH:
            default:
                lightPunchHitboxes = hitboxes;
                break;
        }
    }

    private Hitbox[] getHitboxesByType(final HitboxType hitboxType) {
        switch (hitboxType) {
            case LIGHT_KICK:
                return lightKickHitboxes;
            case STRONG_PUNCH:
                return strongPunchHitboxes;
            case STRONG_KICK:
                return strongKickHitboxes;
            case LIGHT_PUNCH:
            default:
                return lightPunchHitboxes;
        }
    }

    public void setBodyRectangle(final float x, final float y, final float width, final float height) {
        bodyRectangle.set(playerPosition.x + x, playerPosition.y + y, width * scale, height * scale);
    }

    public void iterateHitboxes(final HitboxType hitboxType, final int currentFrameIndex,
                                final int startIndex, final int endIndex) {
        final Hitbox[] hitboxes = getHitboxesByType(hitboxType);
        if (currentFrameIndex >= startIndex && currentFrameIndex <= endIndex) {
            final float x = playerPosition.x + hitboxes[counter].x,
                    y = playerPosition.y + hitboxes[counter].y,
                    width = hitboxes[counter].width,
                    height = hitboxes[counter].height;
            attackRectangle.set(x, y, width * scale, height * scale);
            if (lastCurrentFrameIndex < currentFrameIndex - 1) {
                lastCurrentFrameIndex = currentFrameIndex;
                counter++;
            }
        }
        else {
            counter = 0;
            lastCurrentFrameIndex = 0;
            dismissAttackRectangle();
        }
    }

    public Rectangle getBodyRectangle() {
        return bodyRectangle;
    }

    public Rectangle getAttackRectangle() {
        return attackRectangle;
    }

    private void dismissAttackRectangle() {
        attackRectangle.set(playerPosition.x, playerPosition.y, 0,0);
    }

    public boolean getHit(Rectangle attackRectangle, float deltaTime, Vector2 anotherCharacterPosition) {
        final boolean gettingHit = attackRectangle.overlaps(bodyRectangle);
        if (gettingHit) {
            if (playerState.isFacingRight) anotherCharacterPosition.x += 60f * deltaTime;
            else anotherCharacterPosition.x -= 60f * deltaTime;
        }
        return gettingHit;
    }

    public boolean checkBodyCollision(Rectangle bodyRectangle) {
        return this.bodyRectangle.overlaps(bodyRectangle);
    }
}
