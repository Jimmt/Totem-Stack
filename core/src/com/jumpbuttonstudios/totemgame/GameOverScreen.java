package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class GameOverScreen extends AbstractScreen {
	int score;

	public GameOverScreen(TotemGame game, int score) {
		super(game);
		
		this.score = score;
	}

	@Override
	public void show() {
		super.show();

		Table table = super.getTable();
		table.setFillParent(true);

		Image panel = new Image(new Texture(Gdx.files.internal("ui/gameover/gowindow.png")));
		panel.setPosition(Constants.WIDTH / 2 - panel.getWidth() / 2, Constants.HEIGHT / 2 - panel.getHeight() / 2);
		hudStage.addActor(panel);

		LabelStyle style = new LabelStyle();
		style.font = new BitmapFont(Gdx.files.internal("ui/top/scoreFont.fnt"));

		Label scoreLabel = new Label(String.valueOf(score), style); 
		scoreLabel.setY(panel.getY() + panel.getHeight() * 0.456f + scoreLabel.getHeight() / 4);
		scoreLabel.setX(Constants.WIDTH / 2 - scoreLabel.getWidth() / 2);
		hudStage.addActor(scoreLabel);

		Label bestScoreLabel = new Label("0123456789", style);
		bestScoreLabel.setY(panel.getY() + panel.getHeight() * 0.125f + scoreLabel.getHeight() / 4);
		bestScoreLabel.setX(Constants.WIDTH / 2 - scoreLabel.getWidth() / 2);
		System.out.println(scoreLabel.getWidth() / Constants.SCALE);
		hudStage.addActor(bestScoreLabel);
		
		
	}

}
