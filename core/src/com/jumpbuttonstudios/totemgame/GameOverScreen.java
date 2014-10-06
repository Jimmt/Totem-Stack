package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class GameOverScreen extends AbstractScreen {

	public GameOverScreen(TotemGame game) {
		super(game);
		
	}
	
	@Override
	public void show(){
		super.show();
		
		Table table = super.getTable();
		
		Image panel = Icons.getImage("ui/gameover/gowindow.png");
		panel.setScale(1f);
		panel.setPosition(Constants.WIDTH / 2 - panel.getWidth() / 2, Constants.HEIGHT / 2 - panel.getHeight() / 2);
		
		stage.addActor(panel);
		
	}

}
