package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class GameOverScreen extends AbstractScreen {
	int score;
	Array<ImageButton> imageButtons;
	GameScreen gameScreen;
	Table table;

	public GameOverScreen(TotemGame game, GameScreen gameScreen, int score) {
		super(game);

		this.gameScreen = gameScreen;

		this.score = score;

		imageButtons = new Array<ImageButton>();

	}

	@Override
	public void show() {
		super.show();

		table = super.getTable();
		table.setFillParent(true);

		String[] paths = { "ui/gameover/replay.png", "ui/gameover/highscores.png",
				"ui/gameover/achievements.png", "ui/gameover/twitter.png",
				"ui/gameover/facebook.png" };

		for (int i = 0; i < paths.length; i++) {
			ImageButtonStyle style = new ImageButtonStyle();
			style.imageUp = Icons.returnImage(paths[i]).getDrawable();
			String pressed = paths[i].substring(0, paths[i].length() - 4) + "_pressed.png";
			style.imageDown = Icons.returnImage(pressed).getDrawable();
			ImageButton imageButton = new ImageButton(style);
			imageButtons.add(imageButton);

			imageButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					super.clicked(event, x, y);

					TotemGame.soundManager.play("button");
				}
			});
		}

		Image panel = new Image(new Texture(Gdx.files.internal("ui/gameover/gowindow.png")));
		panel.setPosition(Constants.WIDTH / 2 - panel.getWidth() / 2, Constants.HEIGHT / 3 * 2
				- panel.getHeight() / 2);
		hudStage.addActor(panel);

		for (int i = 0; i < imageButtons.size; i++) {
			table.add(imageButtons.get(i)).padLeft(15f).padTop(panel.getHeight() / 3 * 2 + 15f)
					.width(imageButtons.get(i).getWidth()).height(imageButtons.get(i).getHeight());
		}

		float widgetY = panel.getY();

		LabelStyle style = new LabelStyle();
		style.font = new BitmapFont(Gdx.files.internal("ui/top/scoreFont.fnt"));

		Label scoreLabel = new Label(String.valueOf(score), style);
		scoreLabel.setY(panel.getY() + panel.getHeight() * 0.465f + scoreLabel.getHeight() / 4);
		scoreLabel.setX(Constants.WIDTH / 2 - scoreLabel.getWidth() / 2);
		hudStage.addActor(scoreLabel);

		Label bestScoreLabel = new Label("0123456789", style);
		bestScoreLabel.setY(panel.getY() + panel.getHeight() * 0.125f + bestScoreLabel.getHeight()
				/ 4);
		bestScoreLabel.setX(Constants.WIDTH / 2 - bestScoreLabel.getWidth() / 2);

		hudStage.addActor(bestScoreLabel);
		hudStage.addActor(table);
		gameScreen.hudTable.setVisible(false);
		gameScreen.hudTable.score.setVisible(false);
		gameScreen.hudTable.header.setVisible(false);
		Gdx.input.setInputProcessor(hudStage);
		// DIFFERENT HUD STAGES
		
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		gameScreen.render(1 / 60f);

		if (gameScreen.camera.position.y > 0) {
			gameScreen.camera.position.y -= (gameScreen.camera.position.y - 1) * 0.1f;
		}

		hudStage.draw();
		hudStage.act(delta);

	}

}
