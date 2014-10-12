package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

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
	long nextGoldSpawn;
	Array<Cloud> clouds = new Array<Cloud>();

	int count;

	public TotemSpawner(GameScreen game) {
		this.game = game;
		totems = new Array<Totem>();
		flags = new Array<Flag>();

		pointChecker = new PointChecker(currentTotem, game.stage, game);
		game.stage.addActor(pointChecker);

		nextGoldSpawn = TimeUtils.millis() + 30000 + MathUtils.random(90000);
		
		clouds.add(new Cloud("bg/fog.png"));
		
		game.stage.addActor(clouds.get(0));
	}

	public void newTotem() {
		newTotem = true;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		super.draw(batch, parentAlpha);
		
		
	}

	@Override
	public void act(float delta) {
		game.stage.addActor(clouds.get(0));
		if (lastIncreaseTime > magIncreaseCap) {
			randomMagnitude += 0.1f;
			lastIncreaseTime = 0;
		} else {
			lastIncreaseTime += delta;
		}

		if (newTotem) {

// Totem t = new Totem(MathUtils.random(Constants.SCLWIDTH / 2 -
// randomMagnitude,
// Constants.SCLWIDTH / 2 + randomMagnitude), spawnY, game.world);

			Totem t;
			if (TimeUtils.millis() > nextGoldSpawn && totems.size > 3) {
				nextGoldSpawn += 30000 + MathUtils.random(90000);
				System.out.println(TimeUtils.millis() + " " + nextGoldSpawn);
				t = new GoldTotem(0.5f * Constants.SCLWIDTH, spawnY, game.world, game.particleStage);
			} else {
// t = new GoldTotem(0.5f * Constants.SCLWIDTH, spawnY, game.world,
// game.particleStage);
//					t = new IceTotem(0.5f * Constants.SCLWIDTH, spawnY, game.world,
//							game.particleStage);
				
					t = new Totem(0.5f * Constants.SCLWIDTH, spawnY, game.world);

			}
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
			game.camera.position.y -= (game.camera.position.y - 1) * lerp;
		}

		if (Gdx.app.getType() == ApplicationType.Desktop) {
			if (currentTotem != null) {
				if (Gdx.input.isKeyPressed(Keys.A)) {
					moveLeft();
				}
				if (Gdx.input.isKeyPressed(Keys.D)) {
					moveRight();
				}
				if (!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D)) {
					stop();
				}
			}
		}

		pointChecker.setTotem(currentTotem);
	}

	public void moveLeft() {
		currentTotem.body.setLinearVelocity(-5, currentTotem.body.getLinearVelocity().y);
	}

	public void moveRight() {
		currentTotem.body.setLinearVelocity(5, currentTotem.body.getLinearVelocity().y);
	}

	public void stop() {
		currentTotem.body.setLinearVelocity(0, currentTotem.body.getLinearVelocity().y);
	}
}
