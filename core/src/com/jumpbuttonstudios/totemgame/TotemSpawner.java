package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
	float randomMagnitude = 0.3f, windSpeed;
	float lastIncreaseTime = 999f, magIncreaseCap = 3f;
	float windChangeTime = 999f, windChangeCap = 15f;
	long nextGoldSpawn;
	float cloudSpawnTime = 999f, cloudSpawnCap;
	Array<Cloud> clouds = new Array<Cloud>();
	Zone zone = Zone.LOWER;
	Array<Image> stars;
	int count;
	ParticleEffectActor rain;

	public TotemSpawner(GameScreen game) {
		this.game = game;
		totems = new Array<Totem>();
		flags = new Array<Flag>();

		pointChecker = new PointChecker(currentTotem, game.stage, game);
		game.stage.addActor(pointChecker);

		nextGoldSpawn = TimeUtils.millis() + 30000 + MathUtils.random(90000);
		cloudSpawnCap = MathUtils.random(zone.getCloudFrequency() * 1000);

		stars = new Array<Image>();
		Image star = Icons.getImage("bg/stars.png");
		star.setSize(Constants.WIDTH, Constants.HEIGHT);
		star.setPosition(0, Zone.STARS.getY());
		stars.add(star);
		game.stage.addActor(star);

		rain = new ParticleEffectActor("effects/rain.p", "effects");
		game.particleStage.addActor(rain);
		rain.effect.setPosition(0, Constants.HEIGHT);
		rain.effect.getEmitters().get(0).getXOffsetValue()
				.setLow(-Constants.WIDTH * 2, Constants.WIDTH * 2);
		rain.effect.start();
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public void newTotem() {
		newTotem = true;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

	}

	@Override
	public void act(float delta) {
		rain.effect.setPosition(0, game.camera.position.y / Constants.SCALE + Constants.HEIGHT / 2);
		if (stars.size > 0) {

			if ((stars.get(stars.size - 1).getY() + 8.00f) < game.camera.position.y
					+ Constants.SCLHEIGHT / 2) {

				Image star = new Image(new Texture(Gdx.files.internal("bg/stars.png")));
				star.setSize(Constants.SCLWIDTH, Constants.SCLHEIGHT);
				star.setPosition(0, (stars.get(stars.size - 1).getY()) + 8.00f);
				stars.add(star);
				game.stage.addActor(star);
			}
		}

		if (!game.gameOver) {
			for (int i = 0; i < Zone.getArray().length; i++) {
				if (game.camera.position.y + Constants.SCLHEIGHT / 2 > Zone.getArray()[i].getY()) {
					zone = Zone.getArray()[i];
					cloudSpawnCap = (long) zone.getCloudFrequency();

				}
			}

			if (windChangeTime > windChangeCap) {
				windChangeTime = 0;
				windSpeed = MathUtils.random(-0.01f, 0.01f);
			} else {
				windChangeTime += delta;
			}

			for (int i = 0; i < clouds.size; i++) {
				clouds.get(i).influence = windSpeed;
			}

			if (cloudSpawnTime > cloudSpawnCap && totems.size > 0) {
				if (cloudSpawnCap != -1) {
					Cloud cloud = new Cloud(game.camera.position.y + Constants.SCLHEIGHT * 2,
							windSpeed);
					clouds.add(cloud);
					game.stage.addActor(cloud);
					cloudSpawnCap = MathUtils.random(zone.getCloudFrequency());
					cloudSpawnTime = 0;

				}
			} else {
				cloudSpawnTime += delta;

			}

			if (totems.size > 0) {
				for (int i = 0; i < clouds.size; i++) {
					if (clouds.get(i).hitbox.overlaps(totems.get(totems.size - 1).getRectangle())) {

						totems.get(totems.size - 1).body.applyForceToCenter(
								clouds.get(i).influence * 1000f, 0, true);

					}
				}
			}

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

					t = new GoldTotem(0.5f * Constants.SCLWIDTH, spawnY, game.world,
							game.particleStage);
				} else {
// t = new GoldTotem(0.5f * Constants.SCLWIDTH, spawnY, game.world,
// game.particleStage);
// t = new IceTotem(0.5f * Constants.SCLWIDTH, spawnY, game.world,
// game.particleStage);

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
