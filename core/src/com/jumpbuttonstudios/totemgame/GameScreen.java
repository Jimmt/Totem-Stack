package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen extends AbstractScreen {
	Background background;
	TotemSpawner spawner;
	PointChecker pointChecker;
	GameContactListener contactListener;
	ShapeRenderer sr;
	HudTable hudTable;
	Stage hudStage;
	InputMultiplexer multiplexer;
	int score = 0;
	boolean gameOver;

	public GameScreen(TotemGame game) {
		super(game);

		contactListener = new GameContactListener(this);
		world.setContactListener(contactListener);

		FitViewport viewport = new FitViewport(Constants.SCLWIDTH, Constants.SCLHEIGHT, camera);
		stage.setViewport(viewport);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

		hudStage = new Stage();
		hudTable = new HudTable(getSkin(), this);
		hudTable.setTransform(true);
		hudTable.setFillParent(true);
		hudStage.addActor(hudTable);

		multiplexer = new InputMultiplexer(stage, hudStage);
		Gdx.input.setInputProcessor(multiplexer);

		background = new Background(stage, world);

		spawner = new TotemSpawner(this);
		stage.addActor(spawner);

		sr = new ShapeRenderer();
		spawner.newTotem();
	}

	@Override
	public void show() {
		super.show();
	}

	public void gameOver() {
		gameOver = true;
		System.out.println("GAME OVER");

	}

	/**
	 * not sure if I wrote this in the gdd, but when the totem tower falls, the
	 * screen will pan down so that you see it hit the ground
	 */

	@Override
	public void render(float delta) {
		super.render(delta);

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

		if (spawner.totems.size > 1) {
			if (spawner.totems.get(spawner.totems.size - 1).getY() < spawner.totems.get(
					spawner.totems.size - 2).getY()
					+ 10f * Constants.SCALE) {
				if (spawner.totems.get(spawner.totems.size - 1).getRectangle()
						.overlaps(spawner.totems.get(spawner.totems.size - 2).getRectangle())) {
					gameOver();
				}

			}
		}

		if (contactListener.getGroundTotems().size > 1) {
			gameOver();
		}

		hudStage.act(delta);
		hudStage.draw();
		Table.drawDebug(hudStage);
		hudTable.debug();
	}

}
