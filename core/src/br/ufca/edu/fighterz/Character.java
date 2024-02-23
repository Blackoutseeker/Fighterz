package br.ufca.edu.fighterz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

enum PlayerAction {
    IDLE,
    CROUCH,
    MOVE_FORWARD,
    MOVE_BACK,
    AUTO_TAUNT,
    ATTACK,
}

public class Character {
    private final PlayableCharacter playableCharacter;
    private final Sprite sprite;
    private final Animation<TextureRegion> idleAnimation;
    private final Animation<TextureRegion> crouchAnimation;
    private final Animation<TextureRegion> moveForwardAnimation;
    private final Animation<TextureRegion> moveBackAnimation;
    private final Animation<TextureRegion> autoTauntAnimation;
    private final Animation<TextureRegion> attackAnimation;
    private float stateTime;
    private final TextureRegion[] idleFrames;
    private final TextureRegion[] crouchFrames;
    private final TextureRegion[] moveForwardFrames;
    private final TextureRegion[] moveBackFrames;
    private final TextureRegion[] autoTauntFrames;
    private final TextureRegion[] attackFrames;
    private boolean isWalkingLeft;
    private boolean isWalkingRight;
    private boolean isCrouching;
    private final Vector2 position;
    private final float scale;
    private boolean isFacingRight;
    private float idleTimer = 0f;
    private boolean shouldPlayIdleAnimation = false;
    private float currentStateTime = 0f;
    private boolean isAttacking = false;
    private Rectangle rectangle;
    private Rectangle attackRectangle;

    public Character(final PlayableCharacter playableCharacter, final float scale, final float x, final float y) {
        this.playableCharacter = playableCharacter;
        sprite = new Sprite();
        rectangle = sprite.getBoundingRectangle();
        attackRectangle = new Sprite().getBoundingRectangle();
        stateTime = 0f;
        position = new Vector2(x, y);
        this.scale = scale;

        final int idleFramesLength = getFramesLength(PlayerAction.IDLE);
        final int crouchFramesLength = getFramesLength(PlayerAction.CROUCH);
        final int moveForwardFramesLength = getFramesLength(PlayerAction.MOVE_FORWARD);
        final int moveBackFramesLength = getFramesLength(PlayerAction.MOVE_BACK);
        final int autoTauntFramesLength = getFramesLength(PlayerAction.AUTO_TAUNT);
        final int attackFramesLength = getFramesLength(PlayerAction.ATTACK);

        idleFrames = new TextureRegion[idleFramesLength];
        crouchFrames = new TextureRegion[crouchFramesLength];
        moveForwardFrames = new TextureRegion[moveForwardFramesLength];
        moveBackFrames = new TextureRegion[moveBackFramesLength];
        autoTauntFrames = new TextureRegion[autoTauntFramesLength];
        attackFrames = new TextureRegion[attackFramesLength];

        idleAnimation = initializeAnimation(PlayerAction.IDLE, idleFrames, idleFramesLength);
        crouchAnimation = initializeAnimation(PlayerAction.CROUCH, crouchFrames, crouchFramesLength);
        moveForwardAnimation = initializeAnimation(PlayerAction.MOVE_FORWARD, moveForwardFrames, moveForwardFramesLength);
        moveBackAnimation = initializeAnimation(PlayerAction.MOVE_BACK, moveBackFrames, moveBackFramesLength);
        autoTauntAnimation = initializeAnimation(PlayerAction.AUTO_TAUNT, autoTauntFrames, autoTauntFramesLength);
        attackAnimation = initializeAnimation(PlayerAction.ATTACK, attackFrames, attackFramesLength);
    }

    private Animation<TextureRegion> initializeAnimation(PlayerAction playerAction, TextureRegion[] textureRegions, int spritesLength) {
        for (int index = 0; index < spritesLength; index++) {
            final Texture texture = new Texture(getFrameImage(playerAction, index));
            textureRegions[index] = new TextureRegion(texture);
        }
        return new Animation<>(1f / 15f, textureRegions);
    }

    private int getFramesLength(PlayerAction playerAction) {
        FileHandle dirHandle = Gdx.files.internal("images/sprites/characters/" + (playableCharacter + "/") + (playerAction + "/"));
        FileHandle[] files = dirHandle.list();

        int pngCount = 0;
        for (FileHandle file : files) {
            if (file.extension().equalsIgnoreCase("png")) {
                pngCount++;
            }
        }

        return pngCount;
    }

    private String getFrameImage(PlayerAction playerAction, int index) {
        return "images/sprites/characters/" + (playableCharacter + "/") + (playerAction + "/") + (index + ".png");
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

        if (Gdx.input.isKeyPressed(Input.Keys.A) && !isAttacking) {
            isWalkingLeft = true;
            isWalkingRight = false;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D) && !isAttacking) {
            isWalkingLeft = false;
            isWalkingRight = true;
        }
        else {
            isWalkingLeft = false;
            isWalkingRight = false;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.K) && !isAttacking) {
            isAttacking = true;
        }

        isCrouching = Gdx.input.isKeyPressed(Input.Keys.S) && !isAttacking;
        // AUTO_TAUNT
        if (!isWalkingLeft && !isWalkingRight && !isCrouching && !isAttacking) {
            idleTimer += deltaTime;
        } else {
            idleTimer =  0f;
            shouldPlayIdleAnimation = false;
        }

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

    public void render(SpriteBatch batch, float currentStateTime) {
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
        else if (isAttacking) {
            if (!attackAnimation.isAnimationFinished(this.currentStateTime)) {
                currentFrame = attackAnimation.getKeyFrame(this.currentStateTime, false);
                this.currentStateTime += currentStateTime;
            } else {
                isAttacking = false;
                currentFrame = idleAnimation.getKeyFrame(stateTime, true);
                this.currentStateTime = 0f;
            }
        }
        else {
            currentFrame = idleAnimation.getKeyFrame(stateTime, true);
            if (shouldPlayIdleAnimation) {
                if (!autoTauntAnimation.isAnimationFinished(this.currentStateTime)) {
                    currentFrame = autoTauntAnimation.getKeyFrame(this.currentStateTime, false);
                    this.currentStateTime += currentStateTime;
                } else {
                    shouldPlayIdleAnimation = false;
                    currentFrame = idleAnimation.getKeyFrame(stateTime, true);
                    this.currentStateTime = 0f;
                }
            }
        }

        sprite.setRegion(currentFrame);
        sprite.setFlip(!isFacingRight, false);
        rectangle = sprite.getBoundingRectangle();
        attackRectangle = sprite.getBoundingRectangle();
        if (isAttacking) {
            if (isFacingRight)
                attackRectangle.set((position.x + 50 * scale), (sprite.getRegionHeight() - 35) * scale, 50 * scale, 20 * scale);
            else
                attackRectangle.set((position.x), (sprite.getRegionHeight() - 35) * scale, 50 * scale, 20 * scale);
        }
        else rectangle.set((position.x + 14 * scale), position.y, 50 * scale, 106 * scale);
        batch.draw(sprite, position.x, position.y,
                sprite.getRegionWidth() * scale, sprite.getRegionHeight() * scale);
    }

    private void disposeTextureRegion(TextureRegion[] textureRegion) {
        for (TextureRegion region : textureRegion) {
            region.getTexture().dispose();
        }
    }

    public void dispose() {
        disposeTextureRegion(idleFrames);
        disposeTextureRegion(crouchFrames);
        disposeTextureRegion(moveForwardFrames);
        disposeTextureRegion(moveBackFrames);
        disposeTextureRegion(autoTauntFrames);
        disposeTextureRegion(attackFrames);
        sprite.getTexture().dispose();
    }
}
