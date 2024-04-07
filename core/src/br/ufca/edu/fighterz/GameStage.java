package br.ufca.edu.fighterz;

import br.ufca.edu.fighterz.interfaces.GameStageBehavior;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class GameStage implements GameStageBehavior {
    private final String name;
    private final TextureRegion[] stageFrames;
    private final Animation<TextureRegion> stageAnimation;
    private float stateTime;

    public GameStage(String name) {
        this.name = name.toUpperCase();
        stateTime = 0f;
        final int stageSpritesLength = getFilesLength();
        stageFrames = new TextureRegion[stageSpritesLength];
        stageAnimation = initializeAnimation(stageFrames, stageSpritesLength);
    }

    private Animation<TextureRegion> initializeAnimation(TextureRegion[] textureRegions, int spritesLength) {
        for (int index = 0; index < spritesLength; index++) {
            textureRegions[index] = new TextureRegion(new Texture(getSpriteImage(index)));
        }
        return new Animation<>(1f/5f, textureRegions);
    }

    private int getFilesLength() {
        FileHandle dirHandle = Gdx.files.internal("images/sprites/stages/" + (name + "/"));
        FileHandle[] files = dirHandle.list();

        int pngCount = 0;
        for (FileHandle file : files) {
            if (file.extension().equalsIgnoreCase("png")) {
                pngCount++;
            }
        }

        return pngCount;
    }

    private String getSpriteImage(int index) {
        return "images/sprites/stages/" + (name + "/") + (index + ".png");
    }

    @Override
    public void update(float deltaTime) {
        stateTime += deltaTime;
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame;
        currentFrame = stageAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, 0, 0);
    }

    @Override
    public void dispose() {
        for (TextureRegion textureRegion : stageFrames) {
            textureRegion.getTexture().dispose();
        }
    }

    @Override
    public int getStageTextureHeight() {
        int height = 0;
        for (TextureRegion textureRegion : stageFrames) {
            height = textureRegion.getRegionHeight();
        }
        return height;
    }
}
