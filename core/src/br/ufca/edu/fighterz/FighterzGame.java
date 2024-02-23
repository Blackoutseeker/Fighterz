package br.ufca.edu.fighterz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class FighterzGame extends Game {
	public final static String GAME_TITLE = "Fighterz";
	private final boolean DEBUG = true;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;
	private GameStage gameStage;
	private Character character;
	private DebugFPSLogger debugFPSLogger;
	private ShapeRenderer shapeRenderer;

	@Override
	public void create() {
		batch = new SpriteBatch();
		gameStage = new GameStage("KEN");
		character = new Character(PlayableCharacter.RYU, .8f, 0, 0);
		debugFPSLogger = new DebugFPSLogger(DebugFPSLogger.Type.WINDOW, GAME_TITLE);
		shapeRenderer = new ShapeRenderer();

		float aspectRatio = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		viewport = new FitViewport(480 * aspectRatio, 480, camera);
		viewport.apply();
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
		if (DEBUG) {
			debugFPSLogger.log();
		}

		camera.setToOrtho(false, gameStage.getStageTextureHeight(), gameStage.getStageTextureHeight());

		float characterX = character.getPosition().x;
		float characterY = character.getPosition().y;
		float min = 104f;
		float max = viewport.getWorldWidth() + 22;
		camera.position.x = MathUtils.clamp(characterX, min, max);

		camera.update();
		batch.begin();
		batch.setProjectionMatrix(camera.combined);

		gameStage.update(Gdx.graphics.getDeltaTime());
		gameStage.render(batch);

		character.update(Gdx.graphics.getDeltaTime(), (min + max));
		character.render(batch, Gdx.graphics.getDeltaTime());
		batch.end();

		if (DEBUG) {
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			Rectangle rectangle = character.getRectangle();
			Rectangle rectangleAttack = character.getAttackRectangle();
			shapeRenderer.setColor(Color.GREEN);
			shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
			shapeRenderer.rect(rectangleAttack.x, rectangleAttack.y, rectangleAttack.width, rectangleAttack.height);
			shapeRenderer.end();
		}
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		viewport.apply();
		super.resize(width, height);
	}

	@Override
	public void dispose() {
		batch.dispose();
		character.dispose();
		gameStage.dispose();
		shapeRenderer.dispose();
		super.dispose();
	}
}
