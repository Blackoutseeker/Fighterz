package br.ufca.edu.fighterz;

import br.ufca.edu.fighterz.ui.HUD;
import br.ufca.edu.fighterz.audio.AudioManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

import java.io.FileNotFoundException;

public final class FighterzGame extends Game {
	public final static String GAME_TITLE = "Fighterz";
	private final boolean DEBUG = true;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;
	private GameStage gameStage;
	private Character character1;
	private Character character2;
	private Rectangle leftEdge;
	private Rectangle rightEdge;
	private DebugFPSLogger debugFPSLogger;
	private ShapeRenderer shapeRenderer;
	private AudioManager audioManager;
	private HUD hud1;
	private HUD hud2;
	private float initialWorldCenterPositionX;
	private Vector2 initialCharacterPosition1;
	private Vector2 initialCharacterPosition2;

	@Override
	public void create() {
		batch = new SpriteBatch();
		gameStage = new GameStage("KEN");
		debugFPSLogger = new DebugFPSLogger(DebugFPSLogger.Type.WINDOW, GAME_TITLE);
		shapeRenderer = new ShapeRenderer();

		float aspectRatio = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		viewport = new FitViewport(480 * aspectRatio, 480, camera);
		viewport.apply();

		float worldCenter = camera.viewportWidth / 2f;
		camera.position.x = worldCenter;
		initialWorldCenterPositionX = worldCenter;

		audioManager = new AudioManager();
		audioManager.load();
		audioManager.playBackgroundMusic();

		final PlayableCharacter playableCharacter1 = PlayableCharacter.RYU;
		final PlayableCharacter playableCharacter2 = PlayableCharacter.RYU;

        try {
            character1 = new Character(playableCharacter1, .8f, worldCenter, 10, false, audioManager);
        	character2 = new Character(playableCharacter2, .8f, worldCenter + 80, 10, true, audioManager);
        } catch (FileNotFoundException exception) {
            throw new RuntimeException(exception);
        }

		hud1 = new HUD(batch, character1.getPlayerState(), playableCharacter1, false);
		hud2 = new HUD(batch, character2.getPlayerState(), playableCharacter2, true);

		float cameraHeight = camera.viewportHeight;
		float leftX = camera.position.x - worldCenter;
		float rightX = camera.position.x + (worldCenter) - 5;
		leftEdge = new Rectangle(leftX, 0, 5, cameraHeight);
		rightEdge = new Rectangle(rightX, 0, 5, cameraHeight);

		initialCharacterPosition1 = new Vector2(character1.getPosition().cpy());
		initialCharacterPosition2 = new Vector2(character2.getPosition().cpy());
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.setToOrtho(false, gameStage.getStageTextureHeight(), gameStage.getStageTextureHeight());

		float character1X = character1.getPosition().x;
		float character2X = character2.getPosition().x;
		float min = 104f;
		float max = viewport.getWorldWidth() + 22;

		camera.position.x = MathUtils.clamp((character1X + character2X + min + 24) / 2, min, max);
		camera.update();

		float leftX = camera.position.x - (camera.viewportWidth / 2f);
		float rightX = camera.position.x + (camera.viewportWidth / 2f) - 5;
		leftEdge.setPosition(leftX, 0);
		rightEdge.setPosition(rightX, 0);

		batch.begin();
		batch.setProjectionMatrix(camera.combined);

		gameStage.update(Gdx.graphics.getDeltaTime());
		gameStage.render(batch);

		hud1.draw(camera.position.x, camera.position.y);
		hud2.draw(camera.position.x, camera.position.y);

		character1.update(Gdx.graphics.getDeltaTime(), character2.getCollision(), character2.getPosition(),
				leftEdge, rightEdge);
		character1.render(batch, Gdx.graphics.getDeltaTime());

		character2.update(Gdx.graphics.getDeltaTime(), character1.getCollision(), character1.getPosition(),
				leftEdge, rightEdge);
		character2.render(batch, Gdx.graphics.getDeltaTime());

		if (character1.getPlayerState().life <= 0) {
			character1.getPlayerState().isDefeated = true;
			character2.getPlayerState().isWinning = true;
			resetGame();
		}
		if (character2.getPlayerState().life <= 0) {
			character1.getPlayerState().isWinning = true;
			character2.getPlayerState().isDefeated = true;
			resetGame();
		}

		batch.end();

		if (DEBUG) {
			debugFPSLogger.log();

			Rectangle character1BodyRectangle = character1.getCollision().getBodyRectangle();
			Rectangle character1AttackRectangle = character1.getCollision().getAttackRectangle();
			Rectangle chracter2BodyRectangle = character2.getCollision().getBodyRectangle();
			Rectangle character2AttackRectangle = character2.getCollision().getAttackRectangle();

			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			shapeRenderer.setColor(Color.BLACK);
			shapeRenderer.rect(leftEdge.x, leftEdge.y, leftEdge.width, leftEdge.height);
			shapeRenderer.rect(rightEdge.x, rightEdge.y, rightEdge.width, rightEdge.height);
			shapeRenderer.setColor(Color.GREEN);
			shapeRenderer.rect(character1BodyRectangle.x, character1BodyRectangle.y, character1BodyRectangle.width, character1BodyRectangle.height);
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.rect(character1AttackRectangle.x, character1AttackRectangle.y, character1AttackRectangle.width, character1AttackRectangle.height);
			shapeRenderer.setColor(Color.BLUE);
			shapeRenderer.rect(chracter2BodyRectangle.x, chracter2BodyRectangle.y, chracter2BodyRectangle.width, chracter2BodyRectangle.height);
			shapeRenderer.setColor(Color.ORANGE);
			shapeRenderer.rect(character2AttackRectangle.x, character2AttackRectangle.y, character2AttackRectangle.width, character2AttackRectangle.height);
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
		character1.dispose();
		character2.dispose();
		gameStage.dispose();
		shapeRenderer.dispose();
		audioManager.dispose();
		hud1.dispose();
		hud2.dispose();
		super.dispose();
	}

	private void resetGame() {
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				camera.position.x = initialWorldCenterPositionX;
				character1.getPlayerState().resetAllStates();
				character2.getPlayerState().resetAllStates();
				character1.getPosition().set(initialCharacterPosition1.cpy());
				character2.getPosition().set(initialCharacterPosition2.cpy());
			}
		}, 2);
	}
}
