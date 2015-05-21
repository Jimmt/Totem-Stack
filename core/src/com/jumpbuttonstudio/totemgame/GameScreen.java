package com.jumpbuttonstudio.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation.PowIn;
import com.badlogic.gdx.math.Interpolation.PowOut;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen extends AbstractScreen {
	Background background;
	TotemSpawner spawner;
	GameContactListener contactListener;
	ShapeRenderer sr;
	HudTable hudTable;
	int score = 0;
	boolean gameOver;
	Array<ParticleEffect> particles;
	Stage particleStage;
	Array<Totem> removeTotems = new Array<Totem>();

	public GameScreen(TotemGame game) {
		super(game);

		if (GamePrefs.prefs.getBoolean("bgMusic")) {
			TotemGame.soundManager.stopMusic();
			TotemGame.soundManager.loopMusic("game", 1f);
		}

		particleStage = new Stage();
		FitViewport viewport1 = new FitViewport(Constants.SCLWIDTH, Constants.SCLHEIGHT, camera);
		stage.setViewport(viewport1);
		viewport1.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		particles = new Array<ParticleEffect>();
		hudTable = new HudTable(getSkin(), this);
		hudTable.setTransform(true);
		hudTable.setFillParent(true);
		hudStage.addActor(hudTable);

		background = new Background(stage, world);

		contactListener = new GameContactListener(this);
		world.setContactListener(contactListener);

		spawner = new TotemSpawner(this);
		stage.addActor(spawner);

		sr = new ShapeRenderer();
		spawner.newTotem();

// black.setSize(Constants.SCLWIDTH, Constants.SCLHEIGHT * 2);

		InputMultiplexer multiplexer = new InputMultiplexer(hudTable.optionsDialog.xStage,
				hudStage, stage);

		Gdx.input.setInputProcessor(multiplexer);

	}

	@Override
	public void show() {
		super.show();
	}

	public void gameOver() {
		if (!gameOver) {
			for (int i = 0; i < spawner.totems.size; i++) {
				if (spawner.totems.get(i) instanceof IceTotem) {
					((IceTotem) spawner.totems.get(i)).unfreeze();
				}
			}

			GameOverDialog god = new GameOverDialog(score, getSkin(), this, game, false);
			god.setKeepWithinStage(false);
			god.setPosition(Constants.WIDTH / 2 - god.getWidth() / 2, Constants.HEIGHT);
			hudStage.addActor(god);

			PowIn pi = new PowIn(2);
			PowOut po = new PowOut(2);
			god.addAction(Actions.sequence(Actions.moveBy(0, -Constants.HEIGHT, 0.75f, pi),
					Actions.moveBy(0, Constants.HEIGHT / 8, 0.3f, po),
					Actions.moveBy(0, -Constants.HEIGHT / 8, 0.3f, pi)));

			gameOver = true;

			TotemGame.soundManager.play("lose");
		}
	}

	/**
	 * not sure if I wrote this in the gdd, but when the totem tower falls, the
	 * screen will pan down so that you see it hit the ground
	 */

	@Override
	public void render(float delta) {
		super.render(delta);

		contactListener.update(delta);

// if (spawner.pointChecker != null) {
// sr.setProjectionMatrix(camera.combined);
// sr.begin(ShapeType.Line);
// sr.box(spawner.pointChecker.onePointLeft.x,
// spawner.pointChecker.onePointLeft.y, 0,
// spawner.pointChecker.onePointLeft.width,
// spawner.pointChecker.onePointLeft.height, 0);
// sr.box(spawner.pointChecker.onePointRight.x,
// spawner.pointChecker.onePointRight.y, 0,
// spawner.pointChecker.onePointRight.width,
// spawner.pointChecker.onePointRight.height, 0);
// sr.box(spawner.pointChecker.twoPointLeft.x,
// spawner.pointChecker.twoPointLeft.y, 0,
// spawner.pointChecker.twoPointLeft.width,
// spawner.pointChecker.twoPointLeft.height, 0);
// sr.box(spawner.pointChecker.twoPointRight.x,
// spawner.pointChecker.twoPointRight.y, 0,
// spawner.pointChecker.twoPointRight.width,
// spawner.pointChecker.twoPointRight.height, 0);
// sr.box(spawner.pointChecker.threePoint.x, spawner.pointChecker.threePoint.y,
// 0,
// spawner.pointChecker.threePoint.width,
// spawner.pointChecker.threePoint.height, 0);
// sr.box(spawner.pointChecker.totemRect.x, spawner.pointChecker.totemRect.y, 0,
// spawner.pointChecker.totemRect.width, spawner.pointChecker.totemRect.height,
// 0);
// sr.end();
// }

		for (int i = 0; i < removeTotems.size; i++) {
			world.destroyBody(removeTotems.get(i).body);
			removeTotems.removeIndex(i);
		}

		if (spawner.totems.size > 1 && !gameOver) {
			if (spawner.totems.get(spawner.totems.size - 1).getY() < spawner.totems.get(
					spawner.totems.size - 2).getY()
					+ 10f * Constants.SCALE) {
				if (spawner.totems.get(spawner.totems.size - 1).getRectangle()
						.overlaps(spawner.totems.get(spawner.totems.size - 2).getRectangle())) {
					gameOver();

				}

			}
		}

		if (score < 150) {
			spawner.totemScale = 1 - score / 300f;
		} else {
			spawner.totemScale = 0.5f;
		}

		if (contactListener.getGroundTotems().size > 1 && !gameOver && spawner.totems.size > 1) {
			gameOver();

		}

		if (Gdx.input.isKeyPressed(Keys.Q) && !gameOver) {
			gameOver();
		}

		if (score >= 20) {
			if (TotemGame.services.getSignedIn()) {
				TotemGame.services.unlockAchievement(Constants.ACHIEVEMENT_BEGINNER);

				if (score >= 100) {
					TotemGame.services.unlockAchievement(Constants.ACHIEVEMENT_ELITE);

				}
			}
		}

		if (gameOver) {
			if (!stage.getActors().contains(black, false)) {
				stage.addActor(black);
			}
			black.setVisible(true);
			black.setPosition(camera.position.x - Constants.SCLWIDTH / 2, camera.position.y
					- Constants.SCLHEIGHT / 2);
		}

		particleStage.act(delta);
		particleStage.draw();
		particleStage.getCamera().position.set(stage.getCamera().position.x / Constants.SCALE,
				stage.getCamera().position.y / Constants.SCALE, 0);

// Table.drawDebug(hudStage);
// hudTable.debug();
		hudTable.act(delta);

		hudStage.draw();

	}

}
