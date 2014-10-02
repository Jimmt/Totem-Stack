package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class TotemSpawner extends Actor {
	PointChecker pointChecker;
	Array<Totem> totems;
	Array<Flag> flags;
	Totem currentTotem;
	float spawnY = Constants.SCLHEIGHT;
	boolean newTotem;
	GameScreen game;
	float lerp = 0.1f;
	float randomMagnitude = 0.3f;
	float lastIncreaseTime = 999f, magIncreaseCap = 3f;

	public TotemSpawner(GameScreen game) {
		this.game = game;
		totems = new Array<Totem>();
		flags = new Array<Flag>();

		pointChecker = new PointChecker(currentTotem, game.stage, game);
		game.stage.addActor(pointChecker);
	}

	public void newTotem() {
		newTotem = true;
	}

	@Override
	public void act(float delta) {

		if (lastIncreaseTime > magIncreaseCap) {
			randomMagnitude += 0.1f;
			lastIncreaseTime = 0;
		} else {
			lastIncreaseTime += delta;
		}

		if (newTotem) {
			Totem t = new Totem(MathUtils.random(Constants.SCLWIDTH / 2 - randomMagnitude,
					Constants.SCLWIDTH / 2 + randomMagnitude), spawnY, game.world);
// Totem t = new Totem(0.5f * Constants.SCLWIDTH, spawnY, game.world);
			totems.add(t);
			game.stage.addActor(t);
			currentTotem = t;
			newTotem = false;

			if (totems.size >= 3) {
				spawnY += currentTotem.getHeight();
			}
		}

		for (int i = 9; i < totems.size; i += 10) {

			if (totems.get(i).getFlag() == null) {
				totems.get(i).createFlag();
			}
		}

		if (!game.gameOver) {
			if (totems.size > 3) {
				game.camera.position.y += (totems.get(totems.size - 1 - 3).getY() + 5f - game.camera.position.y)
						* lerp;
			}
		} else {
			game.camera.position.y -= (game.camera.position.y - 1)
					* lerp;
		}

		if (currentTotem != null) {
			if (Gdx.input.isKeyPressed(Keys.A)) {
				currentTotem.body.setLinearVelocity(-5, currentTotem.body.getLinearVelocity().y);
			}
			if (Gdx.input.isKeyPressed(Keys.D)) {
				currentTotem.body.setLinearVelocity(5, currentTotem.body.getLinearVelocity().y);
			}
			if (!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D)) {
				currentTotem.body.setLinearVelocity(0, currentTotem.body.getLinearVelocity().y);
			}
		}

		pointChecker.setTotem(currentTotem);
	}
}
