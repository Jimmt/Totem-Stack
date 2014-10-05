package com.jumpbuttonstudios.totemgame;

import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
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
	ParticleEffectActor stars;
	Image perfect, good;
	float[] values = { 0.0f, 0.0f, 0.0f };
	boolean green = true, yellow;

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

		stars.effect.setPosition(Constants.SCLWIDTH / 2, Constants.SCLHEIGHT / 2);
		stars.effect.getEmitters().get(0).getScale().setHigh(1.28f, 1.28f);
		stars.effect.getEmitters().get(0).getVelocity().setHigh(0.0f, .7f);
		stars.effect.start();

		perfect = Icons.getImage("perfect.png");
		good = Icons.getImage("good.png");

	}

	public void update() {
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

	}

	@Override
	public void beginContact(Contact contact) {
		Object a = contact.getFixtureA().getBody().getUserData();
		Object b = contact.getFixtureB().getBody().getUserData();

		if (a instanceof Totem || b instanceof Totem) {
			if (a instanceof Totem) {
				((Totem) a).removeParachute();
			} else {
				((Totem) b).removeParachute();
			}

		}

		if (a instanceof Ground && b instanceof Totem) {

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

		if (game.stage.getActors().contains(stars, false)) {
			game.stage.getActors().removeValue(stars, false);
		}
		game.stage.addActor(stars);

		stars.effect.allowCompletion();
		stars.effect.start();
	}

	public Array<Totem> getGroundTotems() {
		return groundTotems;
	}

	public void checkPoints(Object a, Object b, Totem lastTotem) {
		int i = getPlacePoints((Totem) a, (Totem) b);
		game.score += i + 1;

		if (lastTotem instanceof GoldTotem) {
			if (game.stage.getActors().contains(Icons.doublePoints[i], false)) {
				Icons.doublePoints[i].addAction(Actions.alpha(1.0f));
			} else {
				game.stage.addActor(Icons.doublePoints[i]);
			}
			Icons.doublePoints[i].addAction(Actions.sequence(Actions.alpha(0, 0.5f)));

			Icons.doublePoints[i].setPosition(
					lastTotem.getX() + lastTotem.getWidth(),
					lastTotem.getY() + lastTotem.getHeight() / 2
							- Icons.doublePoints[i].getHeight() * Constants.SCALE / 2);

			yellow = true;
			green = false;

		} else {
			if (game.stage.getActors().contains(Icons.normalPoints[i], false)) {
				Icons.normalPoints[i].addAction(Actions.alpha(1.0f));
			} else {
				game.stage.addActor(Icons.normalPoints[i]);
			}
			Icons.normalPoints[i].addAction(Actions.sequence(Actions.alpha(0, 0.5f)));

			Icons.normalPoints[i].setPosition(lastTotem.getX() + lastTotem.getWidth(),
					lastTotem.getY());

			green = true;
			yellow = false;
		}

		perfect.setPosition(
				lastTotem.getX() - Constants.SCALE * perfect.getWidth(),
				lastTotem.getY() + lastTotem.getHeight() / 2 - Constants.SCALE
						* perfect.getHeight() / 2);
		good.setPosition(lastTotem.getX() - Constants.SCALE * good.getWidth(), lastTotem.getY()
				+ lastTotem.getHeight() / 2 - Constants.SCALE * good.getHeight() / 2);

		stars.setPosition(lastTotem.getX(), lastTotem.getY());

		perfectLandImage.animatedSprite.setPosition(
				lastTotem.getX() + lastTotem.getWidth() / 2
						- perfectLandImage.animatedSprite.getWidth() / 2,
				lastTotem.getY() + lastTotem.getHeight() / 2
						- perfectLandImage.animatedSprite.getHeight() / 2);

		stars.effect.setPosition(lastTotem.getX() + lastTotem.getWidth() / 2, lastTotem.getY()
				+ lastTotem.getHeight() / 2);
	}

	public int getPlacePoints(Totem current, Totem bottom) {
		float distance = Math.abs(current.getX() - bottom.getX());

		if (distance <= 10f * Constants.SCALE) {
			if (distance < 3f * Constants.SCALE) {

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
