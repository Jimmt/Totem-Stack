package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class PauseScreen extends AbstractScreen {
	GameScreen gs;

	public PauseScreen(TotemGame game, GameScreen gs) {
		super(game);

		this.gs = gs;
		
	}

	@Override
	public void show() {
		super.show();

		

	}

	@Override
	public void render(float delta) {
		if(Gdx.input.isKeyPressed(Keys.SPACE)){
			game.setScreen(gs);
		}
	}

}
