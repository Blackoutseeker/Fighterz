package br.ufca.edu.fighterz.sprites;

import br.ufca.edu.fighterz.PlayableCharacter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

enum PlayerAction {
    IDLE,
    CROUCH,
    MOVE_FORWARD,
    MOVE_BACK,
    AUTO_TAUNT,
    ATTACK,
}

final public class CharacterAnimation {
    private final PlayableCharacter playableCharacter;
    private final Sprite sprite;
    private final float scale;
    private final float frameDuration;
    private final TextureRegion[] idleFrames;
    private final TextureRegion[] crouchFrames;
    private final TextureRegion[] moveForwardFrames;
    private final TextureRegion[] moveBackFrames;
    private final TextureRegion[] autoTauntFrames;
    private final TextureRegion[] attackFrames;
    private final Animation<TextureRegion> idleAnimation;
    private final Animation<TextureRegion> crouchAnimation;
    private final Animation<TextureRegion> moveForwardAnimation;
    private final Animation<TextureRegion> moveBackAnimation;
    private final Animation<TextureRegion> autoTauntAnimation;
    private final Animation<TextureRegion> attackAnimation;

    public CharacterAnimation(final PlayableCharacter playableCharacter, final float scale, final float frameDuration) {
        this.playableCharacter = playableCharacter;
        this.scale = scale;
        this.frameDuration = frameDuration;
        sprite = new Sprite();

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
        return new Animation<>(frameDuration, textureRegions);
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

    public void render(SpriteBatch batch, float x, float y) {
        batch.draw(sprite, x, y, sprite.getRegionWidth() * scale, sprite.getRegionHeight() * scale);
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

    public Sprite getSprite() {
        return sprite;
    }

    public Animation<TextureRegion> getIdleAnimation() {
        return idleAnimation;
    }

    public Animation<TextureRegion> getCrouchAnimation() {
        return crouchAnimation;
    }

    public Animation<TextureRegion> getMoveForwardAnimation() {
        return moveForwardAnimation;
    }

    public Animation<TextureRegion> getMoveBackAnimation() {
        return moveBackAnimation;
    }

    public Animation<TextureRegion> getAutoTauntAnimation() {
        return autoTauntAnimation;
    }

    public Animation<TextureRegion> getAttackAnimation() {
        return attackAnimation;
    }
}
