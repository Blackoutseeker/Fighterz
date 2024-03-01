package br.ufca.edu.fighterz;

import br.ufca.edu.fighterz.sprites.CharacterAnimation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Character {
    private final Sprite sprite;
    private final CharacterAnimation characterAnimation;
    private final Animation<TextureRegion> idleAnimation;
    private final Animation<TextureRegion> crouchAnimation;
    private final Animation<TextureRegion> moveForwardAnimation;
    private final Animation<TextureRegion> moveBackAnimation;
    private final Animation<TextureRegion> autoTauntAnimation;
    private final Animation<TextureRegion> attackAnimation;
    private float stateTime;
    private boolean isWalkingLeft;
    private boolean isWalkingRight;
    private boolean isCrouching;
    private final Vector2 position;
    private final float scale;
    private boolean isFacingRight;
    private float idleTimer = 0f;
    private boolean shouldPlayIdleAnimation = false;
    private float currentStateTime = 0f;
    private  boolean isStrongPunching = false;
    private boolean isLightPunching = false;
    private  boolean isLightKicking = false;
    private  boolean  isStrongKicking = false;
    private final Rectangle rectangle;
    private final Rectangle attackRectangle;
    private final InputHandler inputHandler;

    public Character(final PlayableCharacter playableCharacter, final float scale, final float x, final float y) {
        this.scale = scale;
        characterAnimation = new CharacterAnimation(playableCharacter, scale, 1f / 15f);
        sprite = characterAnimation.getSprite();
        rectangle = new Rectangle();
        attackRectangle = new Rectangle();
        stateTime = 0f;
        position = new Vector2(x, y);
        inputHandler = new InputHandler(isLightPunching, isStrongKicking, isLightKicking, idleTimer, isWalkingLeft, isWalkingRight, isCrouching, isStrongPunching);
        idleAnimation = characterAnimation.getIdleAnimation();
        crouchAnimation = characterAnimation.getCrouchAnimation();
        moveForwardAnimation = characterAnimation.getMoveForwardAnimation();
        moveBackAnimation = characterAnimation.getMoveBackAnimation();
        autoTauntAnimation = characterAnimation.getAutoTauntAnimation();
        attackAnimation = characterAnimation.getAttackAnimation();
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Rectangle getAttackRectangle() {
        return attackRectangle;
    }

    public void update(float deltaTime, float stageWidth) {
        stateTime += deltaTime;
        isWalkingLeft = inputHandler.getIsWalkingLeft();
        isWalkingRight = inputHandler.getIsWalkingRight();
        isCrouching = inputHandler.getIsCrouching();
        isStrongPunching = inputHandler.getIsStrongPunching();
        isLightPunching = inputHandler.getIsLightPunching();
        isStrongKicking = inputHandler.getIsStrongKicking();
        isLightKicking = inputHandler.getIsLightKicking();

        inputHandler.handleInput(deltaTime, shouldPlayIdleAnimation);

        if (idleTimer >= 7f) {
            shouldPlayIdleAnimation = true;
            idleTimer =  0f;
        }
        // AUTO_TAUNT
        float x = 100f * deltaTime;
        if (isWalkingLeft) {
            if (position.x > 0) {
                position.sub(x, 0);
            }
        }
        else if (isWalkingRight) {
            if (position.x < stageWidth - 60) {
                position.add(x, 0);
            }
        }

        isFacingRight = (position.x < stageWidth / 2f);
    }

    public void render(SpriteBatch batch, float deltaTime) {
        TextureRegion currentFrame;

        if (isWalkingLeft) {
            currentFrame = moveForwardAnimation.getKeyFrame(stateTime, true);
            if (isFacingRight) currentFrame = moveBackAnimation.getKeyFrame(stateTime, true);
        }
        else if (isWalkingRight) {
            currentFrame = moveBackAnimation.getKeyFrame(stateTime, true);
            if (isFacingRight) currentFrame = moveForwardAnimation.getKeyFrame(stateTime, true);
        }
        else if (isCrouching) {
            currentFrame = crouchAnimation.getKeyFrame(stateTime, true);
        }
        else if (isLightPunching) {
            if (!attackAnimation.isAnimationFinished(currentStateTime)) {
                currentFrame = attackAnimation.getKeyFrame(currentStateTime, false);
                currentStateTime += deltaTime;
            } else {
                inputHandler.setIsLightPunching(false);
                currentFrame = idleAnimation.getKeyFrame(stateTime, true);
                currentStateTime = 0f;
            }
        }
        else {
            currentFrame = idleAnimation.getKeyFrame(stateTime, true);
            if (shouldPlayIdleAnimation) {
                if (!autoTauntAnimation.isAnimationFinished(currentStateTime)) {
                    currentFrame = autoTauntAnimation.getKeyFrame(currentStateTime, false);
                    currentStateTime += deltaTime;
                } else {
                    shouldPlayIdleAnimation = false;
                    currentFrame = idleAnimation.getKeyFrame(stateTime, true);
                    currentStateTime = 0f;
                }
            }
        }

        sprite.setRegion(currentFrame);
        sprite.setFlip(!isFacingRight, false);
        if (isLightPunching) {
            if (isFacingRight)
                attackRectangle.set((position.x + 50 * scale), (sprite.getRegionHeight() - 35) * scale, 50 * scale, 20 * scale);
            else
                attackRectangle.set((position.x), (sprite.getRegionHeight() - 35) * scale, 50 * scale, 20 * scale);
        } else attackRectangle.set(position.x, position.y, 0, 0);
        rectangle.set((position.x + 14 * scale), position.y, 50 * scale, 106 * scale);
        characterAnimation.render(batch, position.x, position.y);
    }

    public void dispose() {
        characterAnimation.dispose();
        sprite.getTexture().dispose();
    }
}
