package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen extends AbstractScreen {
	Background background;
	Array<Totem> totems;
	Totem currentTotem;
	GameContactListener contactListener;
	float spawnY = Constants.SCLHEIGHT;
	boolean newTotem;

	public GameScreen(TotemGame game) {
		super(game);

		contactListener = new GameContactListener(this);
		world.setContactListener(contactListener);

		FitViewport viewport = new FitViewport(Constants.SCLWIDTH, Constants.SCLWIDTH, camera);
		stage.setViewport(viewport);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

		background = new Background(stage, world);

		totems = new Array<Totem>();

		newTotem();
	}

	@Override
	public void show() {
		super.show();
	}

	public void newTotem() {
		newTotem = true;
	}

	//kevintheman: I didn't include this in the GDD but every 3 stacks, the screen moves up one totem so that you only see 2 totems
	
	@Override
	public void render(float delta) {
		super.render(delta);

		if (newTotem) {
			Totem t = new Totem("totem/00.png", spawnY, world);
			totems.add(t);
			stage.addActor(t);
			currentTotem = t;
			newTotem = false;
		}

		if (Gdx.input.isKeyPressed(Keys.A)) {
			currentTotem.body.setLinearVelocity(-10, currentTotem.body.getLinearVelocity().y);
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			currentTotem.body.setLinearVelocity(10, currentTotem.body.getLinearVelocity().y);
		}
		if (!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D)) {
			currentTotem.body.setLinearVelocity(0, currentTotem.body.getLinearVelocity().y);
		}

	}

}
