package br.ufca.edu.fighterz.sprites;

import br.ufca.edu.fighterz.PlayableCharacter;

import br.ufca.edu.fighterz.interfaces.AnimationProcessor;
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
    LIGHT_PUNCH,
    STRONG_PUNCH,
    LIGHT_KICK,
    STRONG_KICK,
    LIGHT_HIT,
    BLOCK,
    DEFEAT,
    WIN,
}

final public class CharacterAnimation implements AnimationProcessor {
    private final PlayableCharacter playableCharacter;
    private final Sprite sprite;
    private final float scale;
    private final float frameDuration;
    private final TextureRegion[] idleFrames;
    private final TextureRegion[] crouchFrames;
    private final TextureRegion[] moveForwardFrames;
    private final TextureRegion[] moveBackFrames;
    private final TextureRegion[] autoTauntFrames;
    private final TextureRegion[] lightPunchFrames;
    private final TextureRegion[] strongPunchFrames;
    private final TextureRegion[] lightKickFrames;
    private final TextureRegion[] strongKickFrames;
    private final TextureRegion[] lightHitFrames;
    private final TextureRegion[] blockFrames;
    private final TextureRegion[] defeatFrames;
    private final TextureRegion[] winFrames;
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
    private final Animation<TextureRegion> blockAnimation;
    private final Animation<TextureRegion> defeatAnimation;
    private final Animation<TextureRegion> winAnimation;

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
        final int lightPunchFramesLength = getFramesLength(PlayerAction.LIGHT_PUNCH);
        final int strongPunchFramesLength = getFramesLength(PlayerAction.STRONG_PUNCH);
        final int lightKickFramesLength = getFramesLength(PlayerAction.LIGHT_KICK);
        final int strongKickFramesLength = getFramesLength(PlayerAction.STRONG_KICK);
        final int lightHitFramesLength = getFramesLength(PlayerAction.LIGHT_HIT);
        final int blockFramesLength = getFramesLength(PlayerAction.BLOCK);
        final int defeatFramesLength = getFramesLength(PlayerAction.DEFEAT);
        final int winFramesLength = getFramesLength(PlayerAction.WIN);

        idleFrames = new TextureRegion[idleFramesLength];
        crouchFrames = new TextureRegion[crouchFramesLength];
        moveForwardFrames = new TextureRegion[moveForwardFramesLength];
        moveBackFrames = new TextureRegion[moveBackFramesLength];
        autoTauntFrames = new TextureRegion[autoTauntFramesLength];
        lightPunchFrames = new TextureRegion[lightPunchFramesLength];
        strongPunchFrames = new TextureRegion[strongPunchFramesLength];
        lightKickFrames = new TextureRegion[lightKickFramesLength];
        strongKickFrames = new TextureRegion[strongKickFramesLength];
        lightHitFrames = new TextureRegion[lightHitFramesLength];
        blockFrames = new TextureRegion[blockFramesLength];
        defeatFrames = new TextureRegion[defeatFramesLength];
        winFrames = new TextureRegion[winFramesLength];

        idleAnimation = initializeAnimation(PlayerAction.IDLE, idleFrames, idleFramesLength);
        crouchAnimation = initializeAnimation(PlayerAction.CROUCH, crouchFrames, crouchFramesLength);
        moveForwardAnimation = initializeAnimation(PlayerAction.MOVE_FORWARD, moveForwardFrames, moveForwardFramesLength);
        moveBackAnimation = initializeAnimation(PlayerAction.MOVE_BACK, moveBackFrames, moveBackFramesLength);
        autoTauntAnimation = initializeAnimation(PlayerAction.AUTO_TAUNT, autoTauntFrames, autoTauntFramesLength);
        lightPunchAnimation = initializeAnimation(PlayerAction.LIGHT_PUNCH, lightPunchFrames, lightPunchFramesLength);
        strongPunchAnimation = initializeAnimation(PlayerAction.STRONG_PUNCH, strongPunchFrames, strongPunchFramesLength);
        lightKickAnimation = initializeAnimation(PlayerAction.LIGHT_KICK, lightKickFrames, lightKickFramesLength);
        strongKickAnimation = initializeAnimation(PlayerAction.STRONG_KICK, strongKickFrames, strongKickFramesLength);
        lightHitAnimation = initializeAnimation(PlayerAction.LIGHT_HIT, lightHitFrames, lightHitFramesLength);
        blockAnimation = initializeAnimation(PlayerAction.BLOCK, blockFrames, blockFramesLength);
        defeatAnimation = initializeAnimation(PlayerAction.DEFEAT, defeatFrames, defeatFramesLength);
        winAnimation = initializeAnimation(PlayerAction.WIN, winFrames, winFramesLength);
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

    @Override
    public void render(SpriteBatch batch, float x, float y) {
        batch.draw(sprite, x, y, sprite.getRegionWidth() * scale, sprite.getRegionHeight() * scale);
    }

    private void disposeTextureRegion(TextureRegion[] textureRegion) {
        for (TextureRegion region : textureRegion) {
            region.getTexture().dispose();
        }
    }

    @Override
    public void dispose() {
        disposeTextureRegion(idleFrames);
        disposeTextureRegion(crouchFrames);
        disposeTextureRegion(moveForwardFrames);
        disposeTextureRegion(moveBackFrames);
        disposeTextureRegion(autoTauntFrames);
        disposeTextureRegion(lightPunchFrames);
        disposeTextureRegion(strongPunchFrames);
        disposeTextureRegion(lightKickFrames);
        disposeTextureRegion(strongKickFrames);
        disposeTextureRegion(lightHitFrames);
        disposeTextureRegion(blockFrames);
        disposeTextureRegion(defeatFrames);
        disposeTextureRegion(winFrames);
        sprite.getTexture().dispose();
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public Animation<TextureRegion> getIdleAnimation() {
        return idleAnimation;
    }

    @Override
    public Animation<TextureRegion> getCrouchAnimation() {
        return crouchAnimation;
    }

    @Override
    public Animation<TextureRegion> getMoveForwardAnimation() {
        return moveForwardAnimation;
    }

    @Override
    public Animation<TextureRegion> getMoveBackAnimation() {
        return moveBackAnimation;
    }

    @Override
    public Animation<TextureRegion> getAutoTauntAnimation() {
        return autoTauntAnimation;
    }

    @Override
    public Animation<TextureRegion> getLightPunchAnimation() {
        return lightPunchAnimation;
    }

    @Override
    public Animation<TextureRegion> getStrongPunchAnimation() {
        return strongPunchAnimation;
    }

    @Override
    public Animation<TextureRegion> getLightKickAnimation() {
        return lightKickAnimation;
    }

    @Override
    public Animation<TextureRegion> getStrongKickAnimation() {
        return strongKickAnimation;
    }

    @Override
    public Animation<TextureRegion> getLightHitAnimation() {
        return lightHitAnimation;
    }

    @Override
    public Animation<TextureRegion> getBlockAnimation() {
        return blockAnimation;
    }

    @Override
    public Animation<TextureRegion> getDefeatAnimation() {
        return defeatAnimation;
    }

    @Override
    public Animation<TextureRegion> getWinAnimation() {
        return winAnimation;
    }
}
