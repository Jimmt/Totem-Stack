package com.jumpbuttonstudios.totemgame;

import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class GameContactListener implements ContactListener {
	GameScreen game;
	Array<Totem> groundTotems;
	AnimatedImage perfectLandImage;
	ParticleEffectActor stars, goldStars, ice;
	Image perfect, good;
	Totem lastCheck;
	float[] values = { 0.0f, 0.0f, 0.0f };
	boolean green = true, yellow;
	Sprite iceSprite1, iceSprite2;
	float lastChangeTime = 999f, changeCap = .5f;

	public GameContactListener(GameScreen game) {
		this.game = game;
		groundTotems = new Array<Totem>();

		perfectLandImage = new AnimatedImage("effects/perfectland.png", 1 / 15f, 500, 475,
				Constants.SCLWIDTH / 2, Constants.SCLHEIGHT / 2);
		perfectLandImage.animatedSprite.setKeepSize(true);
		game.stage.addActor(perfectLandImage);
		perfectLandImage.animatedSprite.setAlpha(0.0f);
		perfectLandImage.animatedSprite.getAnimation().setPlayMode(PlayMode.LOOP);

		stars = new ParticleEffectActor("effects/stars.p", "effects");

		goldStars = new ParticleEffectActor("totem/special/stars.p", "totem/special");

		ice = new ParticleEffectActor("totem/ice/ice.p", "totem/ice");
		iceSprite1 = new Sprite(Icons.iceImages.get(MathUtils.random(3)));
		iceSprite2 = new Sprite(Icons.iceImages.get(MathUtils.random(3)));

		ice.effect.getEmitters().get(0).setSprite(iceSprite1);
		ice.effect.getEmitters().get(1).setSprite(iceSprite2);

		game.particleStage.addActor(ice);

		perfect = Icons.getImage("perfect.png");
		good = Icons.getImage("good.png");

	}

	public void update(float delta) {

		if (lastChangeTime > changeCap) {
			lastChangeTime = 0;

			iceSprite1.setRegion(Icons.iceImages.get(MathUtils.random(3)));
			iceSprite2.setRegion(Icons.iceImages.get(MathUtils.random(3)));

			ice.effect.getEmitters().get(0).setSprite(iceSprite1);
			ice.effect.getEmitters().get(1).setSprite(iceSprite2);

		} else {
			lastChangeTime += delta;
		}

		if (green) {
			values[0] = 0.07f;
			values[1] = MathUtils.random();
			values[2] = 0.07f;

			stars.effect.getEmitters().get(0).getTint().setColors(values);
		}
		if (yellow) {
			values[0] = .5f + MathUtils.random() / 2;
			values[1] = .5f + MathUtils.random() / 2;
			values[2] = 0;

			stars.effect.getEmitters().get(0).getTint().setColors(values);
		}

		if (game.spawner.totems.get(game.spawner.totems.size - 1) instanceof GoldTotem) {
			GoldTotem lastTotem = (GoldTotem) (game.spawner.totems
					.get(game.spawner.totems.size - 1));
			goldStars.effect.setPosition(lastTotem.getX() / Constants.SCALE,
					(lastTotem.getY() + lastTotem.getHeight() / 2) / Constants.SCALE);

			if (lastCheck != lastTotem) {
				if (!game.particleStage.getActors().contains(goldStars, false)) {
					game.particleStage.addActor(goldStars);
				}

				goldStars.effect.reset();
				goldStars.effect.start();
				lastCheck = lastTotem;
			}

		} else if (game.spawner.totems.get(game.spawner.totems.size - 1) instanceof IceTotem) {
			IceTotem lastTotem = (IceTotem) (game.spawner.totems.get(game.spawner.totems.size - 1));

			ice.effect.setPosition(lastTotem.getX() / Constants.SCALE,
					(lastTotem.getY() + lastTotem.getHeight() / 2) / Constants.SCALE);

			if (lastCheck != lastTotem) {
				if (!game.particleStage.getActors().contains(ice, false)) {
					game.particleStage.addActor(ice);
				}

				ice.effect.reset();
				ice.effect.start();
				lastCheck = lastTotem;
			}

		}

	}

	@Override
	public void beginContact(Contact contact) {
		Object a = contact.getFixtureA().getBody().getUserData();
		Object b = contact.getFixtureB().getBody().getUserData();

		if (a instanceof GoldTotem) {
			((GoldTotem) a).removeAura();
		}
		if (b instanceof GoldTotem) {
			((GoldTotem) b).removeAura();
		}
		if (a instanceof Totem || b instanceof Totem) {
			if (a instanceof Totem) {
				((Totem) a).removeParachute();
			} else {
				((Totem) b).removeParachute();
			}

		}

		if (a instanceof Ground && b instanceof Totem) {

			if (!game.gameOver) {
				TotemGame.soundManager.play("hitground");
			}

			if (((Totem) b).equals(game.spawner.currentTotem)) {
				createTotem();
				if (!groundTotems.contains((Totem) b, false)) {
					groundTotems.add((Totem) b);
				}
			} else {
				game.gameOver();
			}
		}
		if (b instanceof Ground && a instanceof Totem) {

			if (!game.gameOver) {
				TotemGame.soundManager.play("hitground");
			}
			
			if (((Totem) a).equals(game.spawner.currentTotem)) {
				createTotem();
				if (!groundTotems.contains((Totem) a, false)) {
					groundTotems.add((Totem) a);
				}
			} else {
				game.gameOver();
			}
		}
		if (a instanceof Totem && b instanceof Totem) {

			if (((Totem) a).equals(game.spawner.currentTotem)
					|| ((Totem) b).equals(game.spawner.currentTotem)) {

				if (((Totem) a).equals(game.spawner.currentTotem)
						&& ((Totem) a) instanceof GoldTotem) {

				} else if (((Totem) b).equals(game.spawner.currentTotem)
						&& ((Totem) b) instanceof GoldTotem) {

				}

				createTotem();

				resetParticles();

				Totem lastTotem = game.spawner.totems.get(game.spawner.totems.size - 1);

				checkPoints(a, b, lastTotem);
			}
		}
		if (b instanceof Totem && a instanceof Totem) {
			if (((Totem) a).equals(game.spawner.currentTotem)
					|| ((Totem) b).equals(game.spawner.currentTotem)) {
				createTotem();

				resetParticles();

				Totem lastTotem = game.spawner.totems.get(game.spawner.totems.size - 1);

				checkPoints(a, b, lastTotem);
			}

		}
	}

	public void resetParticles() {

		if (game.particleStage.getActors().contains(stars, false)) {
			game.particleStage.getActors().removeValue(stars, false);
		}
		game.particleStage.addActor(stars);

		stars.effect.allowCompletion();
		stars.effect.start();

	}

	public Array<Totem> getGroundTotems() {
		return groundTotems;
	}

	public void checkPoints(Object a, Object b, Totem lastTotem) {
		int points = getPlacePoints((Totem) a, (Totem) b);
		TotemGame.soundManager.play("normalland");
		if (lastTotem instanceof GoldTotem) {

			game.score += (points + 1) * 2;

			if (game.stage.getActors().contains(Icons.doublePoints[points], false)) {
				Icons.doublePoints[points].addAction(Actions.alpha(1.0f));
			} else {
				game.stage.addActor(Icons.doublePoints[points]);
			}
			Icons.doublePoints[points].addAction(Actions.sequence(Actions.alpha(0, 0.5f)));

			Icons.doublePoints[points].setPosition(
					lastTotem.body.getPosition().x + lastTotem.width / 2,
					lastTotem.getY() + lastTotem.getHeight() / 2
							- Icons.doublePoints[points].getHeight() * Constants.SCALE / 2);

			yellow = true;
			green = false;

			goldStars.effect.allowCompletion();

		} else {

			game.score += points + 1;

			if (game.stage.getActors().contains(Icons.normalPoints[points], false)) {
				Icons.normalPoints[points].addAction(Actions.alpha(1.0f));
			} else {
				game.stage.addActor(Icons.normalPoints[points]);
			}
			Icons.normalPoints[points].addAction(Actions.sequence(Actions.alpha(0, 0.5f)));

			Icons.normalPoints[points].setPosition(lastTotem.getX() + lastTotem.getWidth(),
					lastTotem.getY() + lastTotem.getHeight() / 4);

			green = true;
			yellow = false;

			if (lastTotem instanceof IceTotem) {
				ice.effect.allowCompletion();
				ice.effect.start();

				for (int i = 0; i < 3; i++) {
					if (game.spawner.totems.size > i + 1) {
						game.spawner.totems.get(game.spawner.totems.size - 2 - i).freeze();
					}

				}

			}
		}

		perfect.setPosition(
				lastTotem.getX() - Constants.SCALE * perfect.getWidth(),
				lastTotem.getY() + lastTotem.getHeight() / 2 - Constants.SCALE
						* perfect.getHeight() / 2);
		good.setPosition(lastTotem.getX() - Constants.SCALE * good.getWidth(), lastTotem.getY()
				+ lastTotem.getHeight() / 2 - Constants.SCALE * good.getHeight() / 2);

		perfectLandImage.animatedSprite.setPosition(
				lastTotem.getX() + lastTotem.getWidth() / 2
						- perfectLandImage.animatedSprite.getWidth() / 2,
				lastTotem.getY() + lastTotem.getHeight() / 2
						- perfectLandImage.animatedSprite.getHeight() / 2);

		stars.effect.setPosition((lastTotem.getX() + lastTotem.getWidth() / 2) / Constants.SCALE,
				(lastTotem.getY() + lastTotem.getHeight() / 2) / Constants.SCALE);

		ice.effect.setPosition((lastTotem.getX() + lastTotem.getWidth() / 2) / Constants.SCALE,
				(lastTotem.getY() + lastTotem.getHeight() / 2) / Constants.SCALE);

	}

	public int getPlacePoints(Totem current, Totem bottom) {
		float distance = Math.abs(current.body.getPosition().x - bottom.body.getPosition().x);

		if (distance <= 10f * Constants.SCALE) {

			if (distance < 5f * Constants.SCALE) {

				TotemGame.soundManager.play("perfectland");

				if (game.stage.getActors().contains(perfect, false)) {
					game.stage.getActors().removeValue(perfect, false);
					game.stage.addActor(perfect);
				} else {
					game.stage.addActor(perfect);

				}

				game.stage.getActors().removeValue(perfectLandImage, false);
				game.stage.addActor(perfectLandImage);
				perfectLandImage.play();

				perfect.addAction(Actions.sequence(Actions.alpha(1.0f), Actions.alpha(0, 0.5f)));

			}
			return 2;
		}
		if (distance <= 20f * Constants.SCALE) {
			if (distance <= 17f * Constants.SCALE) {
				green = true;

				if (game.stage.getActors().contains(good, false)) {
					game.stage.getActors().removeValue(good, false);
					game.stage.addActor(good);
				} else {
					game.stage.addActor(good);
				}
				good.addAction(Actions.sequence(Actions.alpha(1.0f), Actions.alpha(0, 0.5f)));
			} else {
				green = false;
			}
			return 1;
		}
		if (distance <= 50f * Constants.SCALE) {
			return 0;
		} else {
			return 0;
		}

	}

	public void createTotem() {
		game.spawner.currentTotem = null;
		game.spawner.newTotem();
	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
