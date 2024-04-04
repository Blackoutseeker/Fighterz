package br.ufca.edu.fighterz;

import br.ufca.edu.fighterz.state.PlayerState;
import br.ufca.edu.fighterz.sprites.CharacterAnimation;
import br.ufca.edu.fighterz.collision.CharacterCollision;
import br.ufca.edu.fighterz.collision.Hitbox;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public final class Character {
    private final Sprite sprite;
    private final CharacterAnimation characterAnimation;
    private final PlayerState playerState;
    private final Animation<TextureRegion> idleAnimation;
    private final Animation<TextureRegion> crouchAnimation;
    private final Animation<TextureRegion> moveForwardAnimation;
    private final Animation<TextureRegion> moveBackAnimation;
    private final Animation<TextureRegion> autoTauntAnimation;
    private final Animation<TextureRegion> lightPunchAnimation;
    private final Animation<TextureRegion> strongPunchAnimation;
    private final Animation<TextureRegion> lightKickAnimation;
    private final Animation<TextureRegion> strongKickAnimation;
    private final Animation<TextureRegion> lightHitAnimation;
    private final Vector2 position;
    private final CharacterCollision collision;
    private final InputHandler inputHandler;


    public Character(final PlayableCharacter playableCharacter, final float scale,
                     final float x, final float y,
                     final boolean isSecondPlayer) {
        playerState = new PlayerState();
        characterAnimation = new CharacterAnimation(playableCharacter, scale, 1f / 12f);
        sprite = characterAnimation.getSprite();
        position = new Vector2(x, y);
        inputHandler = new InputHandler(playerState, isSecondPlayer);
        collision = new CharacterCollision(playerState, scale, position);

        idleAnimation = characterAnimation.getIdleAnimation();
        crouchAnimation = characterAnimation.getCrouchAnimation();
        moveForwardAnimation = characterAnimation.getMoveForwardAnimation();
        moveBackAnimation = characterAnimation.getMoveBackAnimation();
        autoTauntAnimation = characterAnimation.getAutoTauntAnimation();
        lightPunchAnimation = characterAnimation.getLightPunchAnimation();
        strongPunchAnimation = characterAnimation.getStrongPunchAnimation();
        lightKickAnimation = characterAnimation.getLightKickAnimation();
        strongKickAnimation = characterAnimation.getStrongKickAnimation();
        lightHitAnimation = characterAnimation.getLightHitAnimation();
    }

    public Vector2 getPosition() {
        return position;
    }

    public CharacterCollision getCollision() {
        return collision;
    }

    public void update(float deltaTime,
                       CharacterCollision anotherCharacterCollision, Vector2 anotherCharacterPosition,
                       Rectangle leftWall, Rectangle rightWall) {
        playerState.stateTime += deltaTime;
        inputHandler.handleInput(deltaTime);

        if (collision.getHit(anotherCharacterCollision.getAttackRectangle(), deltaTime, anotherCharacterPosition))
            playerState.isGettingHit = true;

        if (playerState.idleTimer >= 7f) {
            playerState.shouldPlayIdleAnimation = true;
            playerState.idleTimer = 0f;
        }

        float x = 100f * deltaTime;
        if (playerState.isWalkingLeft) {
            if (collision.checkBodyCollision(anotherCharacterCollision.getBodyRectangle())) {
                if (!playerState.isFacingRight && !anotherCharacterCollision.checkBodyCollision(leftWall))
                    anotherCharacterPosition.sub(x, 0);
            }
            if (!collision.checkBodyCollision(leftWall))
                position.sub(x, 0);
        }
        else if (playerState.isWalkingRight) {
            if (collision.checkBodyCollision(anotherCharacterCollision.getBodyRectangle())) {
                if (playerState.isFacingRight && !anotherCharacterCollision.checkBodyCollision(rightWall))
                    anotherCharacterPosition.add(x, 0);
            }
            if (!collision.checkBodyCollision(rightWall))
                position.add(x, 0);
        }


        if (collision.checkBodyCollision(anotherCharacterCollision.getBodyRectangle())
                && !anotherCharacterCollision.checkBodyCollision(leftWall)
                && !anotherCharacterCollision.checkBodyCollision(rightWall)) {
            if (playerState.isFacingRight)
                anotherCharacterPosition.add(2f * deltaTime, 0);
            else
                anotherCharacterPosition.sub(2f * deltaTime, 0);
        }

        playerState.isFacingRight = (position.x < anotherCharacterPosition.x);
    }

    public void render(SpriteBatch batch, float deltaTime) {
        TextureRegion currentFrame;

        if (playerState.isGettingHit) {
            if (!lightHitAnimation.isAnimationFinished(playerState.currentStateTime)) {
                currentFrame = lightHitAnimation.getKeyFrame(playerState.currentStateTime, false);
                playerState.currentStateTime += deltaTime;
            }
            else {
                playerState.isGettingHit = false;
                currentFrame = idleAnimation.getKeyFrame(playerState.stateTime, true);
                playerState.currentStateTime = 0f;
            }
        }

        else if (playerState.isWalkingLeft) {
            currentFrame = moveForwardAnimation.getKeyFrame(playerState.stateTime, true);
            if (playerState.isFacingRight)
                currentFrame = moveBackAnimation.getKeyFrame(playerState.stateTime, true);
        }
        else if (playerState.isWalkingRight) {
            currentFrame = moveBackAnimation.getKeyFrame(playerState.stateTime, true);
            if (playerState.isFacingRight)
                currentFrame = moveForwardAnimation.getKeyFrame(playerState.stateTime, true);

        }
        else if (playerState.isCrouching) {
            currentFrame = crouchAnimation.getKeyFrame(playerState.stateTime, true);
        }
        else if (playerState.isLightPunching) {
            if (!lightPunchAnimation.isAnimationFinished(playerState.currentStateTime)) {
                currentFrame = lightPunchAnimation.getKeyFrame(playerState.currentStateTime, false);
                playerState.currentStateTime += deltaTime;
            }
            else {
                playerState.isLightPunching = false;
                playerState.isAttacking = false;
                currentFrame = idleAnimation.getKeyFrame(playerState.stateTime, true);
                playerState.currentStateTime = 0f;
            }
        }
        else if (playerState.isStrongPunching) {
            if (!strongPunchAnimation.isAnimationFinished(playerState.currentStateTime)) {
                currentFrame = strongPunchAnimation.getKeyFrame(playerState.currentStateTime, false);
                playerState.currentStateTime += deltaTime;
            }
            else {
                playerState.isStrongPunching = false;
                playerState.isAttacking = false;
                currentFrame = idleAnimation.getKeyFrame(playerState.stateTime, true);
                playerState.currentStateTime = 0f;
            }
        }
        else if (playerState.isLightKicking) {
            if (!lightKickAnimation.isAnimationFinished(playerState.currentStateTime)) {
                currentFrame = lightKickAnimation.getKeyFrame(playerState.currentStateTime, false);
                playerState.currentStateTime += deltaTime;
            }
            else {
                playerState.isLightKicking = false;
                playerState.isAttacking = false;
                currentFrame = idleAnimation.getKeyFrame(playerState.stateTime, true);
                playerState.currentStateTime = 0f;
            }
        }
        else if (playerState.isStrongKicking) {
            if (!strongKickAnimation.isAnimationFinished(playerState.currentStateTime)) {
                currentFrame = strongKickAnimation.getKeyFrame(playerState.currentStateTime, false);
                playerState.currentStateTime += deltaTime;
            }
            else {
                playerState.isStrongKicking = false;
                playerState.isAttacking = false;
                currentFrame = idleAnimation.getKeyFrame(playerState.stateTime, true);
                playerState.currentStateTime = 0f;
            }
        }
        else {
            currentFrame = idleAnimation.getKeyFrame(playerState.stateTime, true);
            if (playerState.shouldPlayIdleAnimation) {
                if (!autoTauntAnimation.isAnimationFinished(playerState.currentStateTime)) {
                    currentFrame = autoTauntAnimation.getKeyFrame(playerState.currentStateTime, false);
                    playerState.currentStateTime += deltaTime;
                }
                else {
                    playerState.shouldPlayIdleAnimation = false;
                    currentFrame = idleAnimation.getKeyFrame(playerState.stateTime, true);
                    playerState.currentStateTime = 0f;
                }
            }
        }

        sprite.setRegion(currentFrame);
        sprite.setFlip(!playerState.isFacingRight, false);

        if (playerState.isAttacking) {
            CharacterCollision.HitboxType hitboxType = CharacterCollision.HitboxType.LIGHT_PUNCH;
            int currentFrameIndex = 0, startIndex = 0, endIndex = 0;
            float x;
            Hitbox[] hitboxes = new Hitbox[]{new Hitbox(0, 0, 0, 0)};

            if (playerState.isLightPunching) {
                currentFrameIndex = lightPunchAnimation.getKeyFrameIndex(playerState.currentStateTime);
                startIndex = 1; endIndex = 2;
                x = (playerState.isFacingRight) ? 80 : 16;
                hitboxes = new Hitbox[]{
                        new Hitbox(x, sprite.getRegionHeight() - 64, 45, 20),
                        new Hitbox(x, sprite.getRegionHeight() - 64, 45, 20),
                };
            }
            else if (playerState.isLightKicking) {
                hitboxType = CharacterCollision.HitboxType.LIGHT_KICK;
                currentFrameIndex = lightKickAnimation.getKeyFrameIndex(playerState.currentStateTime);
                startIndex = 2; endIndex = 3;
                x = (playerState.isFacingRight) ? 80 : 8;
                hitboxes = new Hitbox[]{
                        new Hitbox(x, sprite.getRegionHeight() - 105, 55, 40),
                        new Hitbox(x, sprite.getRegionHeight() - 105, 55, 40),
                };
            }
            else if (playerState.isStrongPunching) {
                hitboxType = CharacterCollision.HitboxType.STRONG_PUNCH;
                currentFrameIndex = strongPunchAnimation.getKeyFrameIndex(playerState.currentStateTime);
                startIndex = 3; endIndex = 5;
                x = (playerState.isFacingRight) ? 82 : 10;
                hitboxes = new Hitbox[]{
                        new Hitbox(x, sprite.getRegionHeight() - 74, 50, 20),
                        new Hitbox(x, sprite.getRegionHeight() - 74, 50, 20),
                        new Hitbox(x, sprite.getRegionHeight() - 74, 50, 20),
                };
            }
            else if (playerState.isStrongKicking) {
                hitboxType = CharacterCollision.HitboxType.STRONG_KICK;
                currentFrameIndex = strongKickAnimation.getKeyFrameIndex(playerState.currentStateTime);
                startIndex = 3; endIndex = 4;
                x = (playerState.isFacingRight) ? 80 : 10;
                hitboxes = new Hitbox[]{
                        new Hitbox(x, sprite.getRegionHeight() - 66, 58, 36),
                        new Hitbox(x, sprite.getRegionHeight() - 66, 58, 36),
                };
            }

            collision.setHitboxesByType(hitboxType, hitboxes);
            collision.iterateHitboxes(hitboxType, currentFrameIndex, startIndex, endIndex);
        }

        collision.setBodyRectangle(44, 0, 50, 106);
        characterAnimation.render(batch, position.x, position.y);
    }

    public void dispose() {
        characterAnimation.dispose();
        sprite.getTexture().dispose();
    }
}
