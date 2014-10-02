package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class GameContactListener implements ContactListener {
	GameScreen game;

	public GameContactListener(GameScreen game) {
		this.game = game;
	}

	@Override
	public void beginContact(Contact contact) {
		Object a = contact.getFixtureA().getBody().getUserData();
		Object b = contact.getFixtureB().getBody().getUserData();

		if (a instanceof Ground && b instanceof Totem) {
			if (((Totem) b).equals(game.spawner.currentTotem)) {
				createTotem();
			}
		}
		if (b instanceof Ground && a instanceof Totem) {
			if (((Totem) a).equals(game.spawner.currentTotem)) {
				createTotem();
			}
		}
		if (a instanceof Totem && b instanceof Totem) {
			if (((Totem) a).equals(game.spawner.currentTotem) || ((Totem) b).equals(game.spawner.currentTotem)) {
				createTotem();

				Totem lastTotem = game.spawner.totems.get(game.spawner.totems.size - 1);

				checkPoints(a, b, lastTotem);
			}
		}
		if (b instanceof Totem && a instanceof Totem) {
			if (((Totem) a).equals(game.spawner.currentTotem) || ((Totem) b).equals(game.spawner.currentTotem)) {
				createTotem();

				Totem lastTotem = game.spawner.totems.get(game.spawner.totems.size - 1);

				checkPoints(a, b, lastTotem);
			}

		}
	}

	public void checkPoints(Object a, Object b, Totem lastTotem) {
		int i = getPlacePoints((Totem) a, (Totem) b);
		game.score += i + 1;
		if (game.stage.getActors().contains(Icons.normalPoints[i], false)) {
			Icons.normalPoints[i].addAction(Actions.alpha(1.0f));
		} else {
			game.stage.addActor(Icons.normalPoints[i]);
		}
		
		Icons.normalPoints[i].setPosition(lastTotem.getX() + lastTotem.getWidth(), lastTotem.getY());
		
		Icons.perfect.setPosition(lastTotem.getX() - Constants.SCALE * Icons.perfect.getWidth(), lastTotem.getY() + lastTotem.getHeight() / 2 - Constants.SCALE * Icons.perfect.getHeight() / 2);
		Icons.good.setPosition(lastTotem.getX() - Constants.SCALE * Icons.good.getWidth(), lastTotem.getY() + lastTotem.getHeight() / 2 - Constants.SCALE * Icons.good.getHeight() / 2);
	}

	public int getPlacePoints(Totem current, Totem bottom) {
		float distance = Math.abs(current.getX() - bottom.getX());

		if (distance <= 10f * Constants.SCALE) {
			if(distance < 3f * Constants.SCALE){
				if (game.stage.getActors().contains(Icons.perfect, false)) {
					game.stage.getActors().removeValue(Icons.perfect, false);
					game.stage.addActor(Icons.perfect);
				} else {
					game.stage.addActor(Icons.perfect);
					
					
				}
				Icons.perfect
				.addAction(Actions.sequence(Actions.alpha(1.0f), Actions.alpha(0, 0.5f)));
				
			}
			return 2;
		}
		if (distance <= 20f * Constants.SCALE) {
			if(distance <= 17f * Constants.SCALE){
				if (game.stage.getActors().contains(Icons.good, false)) {
					game.stage.getActors().removeValue(Icons.good, false);
					game.stage.addActor(Icons.good);
				} else {
					game.stage.addActor(Icons.good);
				}
				Icons.good
				.addAction(Actions.sequence(Actions.alpha(1.0f), Actions.alpha(0, 0.5f)));
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
		// TODO Auto-generated method stub

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
