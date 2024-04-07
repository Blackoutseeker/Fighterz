package br.ufca.edu.fighterz.ui;

import br.ufca.edu.fighterz.interfaces.HUDBehavior;
import br.ufca.edu.fighterz.state.PlayerState;
import br.ufca.edu.fighterz.PlayableCharacter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public final class HUD implements HUDBehavior {
    private final SpriteBatch batch;
    private final PlayerState playerState;
    private final boolean isSecondPlayer;
    private final float scale = 0.4f;
    private final Sprite rectangleSprite;
    private final Texture yellowBar = new Texture(Gdx.files.internal("images/sprites/hud/YELLOW.png"));
    private final Texture orangeBar = new Texture(Gdx.files.internal("images/sprites/hud/ORANGE.png"));
    private final Sprite portraitSprite;
    private final Sprite hudSprite;
    private final float maxLifeValue = 200;

    public HUD(final SpriteBatch batch, final PlayerState playerState,
               final PlayableCharacter playableCharacter, final boolean isSecondPlayer) {
        this.batch = batch;
        this.playerState = playerState;
        this.isSecondPlayer = isSecondPlayer;

        rectangleSprite = new Sprite(new Texture(Gdx.files.internal("images/sprites/hud/GREEN.png")));
        hudSprite = new Sprite(new Texture(Gdx.files.internal("images/sprites/hud/HUD.png")));
        hudSprite.flip(isSecondPlayer, false);
        portraitSprite = new Sprite(getPortraitTexture(playableCharacter));
        portraitSprite.flip(isSecondPlayer, false);
    }

    @Override
    public void draw(float x, float y) {
        final float lifeBarWidth = getLifeBarWidth();
        if (playerState.life < maxLifeValue && playerState.life > maxLifeValue / 2) {
            rectangleSprite.setRegion(yellowBar);
        }
        else if (playerState.life <= maxLifeValue / 2) {
            rectangleSprite.setRegion(orangeBar);
        }
        if (isSecondPlayer) {
            batch.draw(portraitSprite, x - (-84), y + 78, (portraitSprite.getRegionWidth() * scale) - 8, (portraitSprite.getRegionHeight() * scale) - 6);
            batch.draw(rectangleSprite, x - (-7), y + 82, lifeBarWidth, rectangleSprite.getRegionHeight() * scale + 4);
            batch.draw(hudSprite, x - 4, y + 74, hudSprite.getRegionWidth() * scale - 20, hudSprite.getRegionHeight() * scale);
        }
        else {
            batch.draw(portraitSprite, x - 100, y + 78, (portraitSprite.getRegionWidth() * scale) - 8, (portraitSprite.getRegionHeight() * scale) - 6);
            batch.draw(rectangleSprite, x - 77, y + 82, lifeBarWidth, rectangleSprite.getRegionHeight() * scale + 4);
            batch.draw(hudSprite, x - 104, y + 74, hudSprite.getRegionWidth() * scale - 20, hudSprite.getRegionHeight() * scale);
        }
    }

    private Texture getPortraitTexture(final PlayableCharacter playableCharacter) {
        return new Texture(Gdx.files.internal("images/sprites/portraits/" + playableCharacter + ".png"));
    }

    private float getLifeBarWidth() {
        final float maxLifeBarWidth = hudSprite.getRegionWidth() * scale - 58;
        final float width = (playerState.life / maxLifeValue) * maxLifeBarWidth;
        if (width <= 0) return 0;
        return width;
    }

    @Override
    public void dispose() {
        hudSprite.getTexture().dispose();
    }
}
