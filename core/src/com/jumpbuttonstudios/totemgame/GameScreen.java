package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GameScreen extends AbstractScreen {
	Background background;
	TotemSpawner spawner;
	PointChecker pointChecker;
	GameContactListener contactListener;
	ShapeRenderer sr;
	HudTable hudTable;
	int score = 0;
	boolean gameOver;
	Array<ParticleEffect> particles;
	Stage particleStage;
	Image black;
	FitViewport viewport;
	Array<Totem> removeTotems = new Array<Totem>();

	public GameScreen(TotemGame game) {
		super(game);

		particleStage = new Stage();

		particles = new Array<ParticleEffect>();

		viewport = new FitViewport(Constants.SCLWIDTH, Constants.SCLHEIGHT, camera);
		stage.setViewport(viewport);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

		hudStage = new Stage();
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

		black = new Image(new Texture(Gdx.files.internal("black.png")));
		black.setSize(Constants.SCLWIDTH, Constants.SCLHEIGHT);
		black.setColor(black.getColor().r, black.getColor().g, black.getColor().b, 0.75f);

		multiplexer = new InputMultiplexer(stage, hudStage);

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

			GameOverDialog god = new GameOverDialog(score, getSkin(), this, game);
			god.setPosition(Constants.WIDTH / 2 - god.getWidth() / 2,
					Constants.HEIGHT / 2 - god.getHeight() / 2);
			hudStage.addActor(god);

			gameOver = true;

			TotemGame.soundManager.play("lose");
		}
	}

	public void pause() {
		paused = !paused;
		black.setVisible(paused);
		black.setPosition(camera.position.x - Constants.SCLWIDTH / 2, camera.position.y
				- Constants.SCLHEIGHT / 2);
		if (!stage.getActors().contains(black, false)) {
			stage.getActors().removeValue(black, false);
		}
		stage.addActor(black);

	}

	/**
	 * not sure if I wrote this in the gdd, but when the totem tower falls, the
	 * screen will pan down so that you see it hit the ground
	 */

	@Override
	public void render(float delta) {
		super.render(delta);

		contactListener.update(delta);

// if (currentTotem != null) {
// sr.setProjectionMatrix(camera.combined);
// sr.begin(ShapeType.Line);
// sr.box(pointChecker.onePointLeft.x, pointChecker.onePointLeft.y, 0,
// pointChecker.onePointLeft.width, pointChecker.onePointLeft.height, 0);
// sr.box(pointChecker.onePointRight.x, pointChecker.onePointRight.y, 0,
// pointChecker.onePointRight.width, pointChecker.onePointRight.height, 0);
// sr.box(pointChecker.twoPointLeft.x, pointChecker.twoPointLeft.y, 0,
// pointChecker.twoPointLeft.width, pointChecker.twoPointLeft.height, 0);
// sr.box(pointChecker.twoPointRight.x, pointChecker.twoPointRight.y, 0,
// pointChecker.twoPointRight.width, pointChecker.twoPointRight.height, 0);
// sr.box(pointChecker.threePoint.x, pointChecker.threePoint.y, 0,
// pointChecker.threePoint.width, pointChecker.threePoint.height, 0);
// sr.box(pointChecker.totemRect.x, pointChecker.totemRect.y, 0,
// pointChecker.totemRect.width, pointChecker.totemRect.height, 0);
// sr.end();
// }

		for(int i = 0; i < removeTotems.size; i++){
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

		if (contactListener.getGroundTotems().size > 1 && !gameOver) {
			gameOver();
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
		hudTable.debug();
		hudTable.act(delta);

		hudStage.draw();

	}

}
