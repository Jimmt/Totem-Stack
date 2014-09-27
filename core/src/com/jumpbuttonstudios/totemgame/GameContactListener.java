package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

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
			if (((Totem) b).equals(game.currentTotem)) {
				game.newTotem();
			}
		}
		if (b instanceof Ground && a instanceof Totem) {
			if (((Totem) a).equals(game.currentTotem)) {
				game.newTotem();
			}
		}
		if (a instanceof Totem && b instanceof Totem) {
			if (((Totem) a).equals(game.currentTotem) || ((Totem) b).equals(game.currentTotem)) {
				game.newTotem();
			}
		}
		if (b instanceof Totem && a instanceof Totem) {
			if (((Totem) a).equals(game.currentTotem) || ((Totem) b).equals(game.currentTotem)) {
				game.newTotem();
			}

		}
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
