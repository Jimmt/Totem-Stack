package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class GameOverDialog extends Dialog {
	int score;
	Array<ImageButton> imageButtons;
	GameScreen gs;
	TotemGame game;
	ShapeRenderer sr;
	Label bestScoreLabel, scoreLabel;
	Image idkwhythisworks;
	float x, y;

	public GameOverDialog(int score, Skin skin, GameScreen gs, TotemGame game) {
		super("", skin);

		sr = new ShapeRenderer();

		setTransform(true);

		imageButtons = new Array<ImageButton>();

		this.gs = gs;
		this.game = game;
		this.score = score;

		String[] paths = { "ui/gameover/replay.png", "ui/gameover/highscores.png",
				"ui/gameover/achievements.png", "ui/gameover/twitter.png",
				"ui/gameover/facebook.png" };

		for (int i = 0; i < paths.length; i++) {
			ImageButtonStyle style = new ImageButtonStyle();
			style.imageUp = Icons.getImage(paths[i]).getDrawable();
			String pressed = paths[i].substring(0, paths[i].length() - 4) + "_pressed.png";
			style.imageDown = Icons.getImage(pressed).getDrawable();
			ImageButton imageButton = new ImageButton(style);
			imageButtons.add(imageButton);

		}

		setupListeners();

		Image panel = new Image(new Texture(Gdx.files.internal("ui/gameover/gowindow.png")));
		setBackground(panel.getDrawable());
		setSize(panel.getWidth(), panel.getHeight());

		ImageButtonStyle removeAdStyle = new ImageButtonStyle();
		removeAdStyle.up = Icons.getImage("ui/paused/removead.png").getDrawable();
		removeAdStyle.down = Icons.getImage("ui/paused/removead.png").getDrawable();
		ImageButton removeAds = new ImageButton(removeAdStyle);
		removeAds.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				TotemGame.soundManager.play("button");
			}
		});

		LabelStyle style = new LabelStyle();
		style.font = new BitmapFont(Gdx.files.internal("ui/top/scoreFont.fnt"));

		scoreLabel = new Label(String.valueOf(score), style);
		

		bestScoreLabel = new Label(String.valueOf(GamePrefs.prefs.getInteger("bestScore")),
				style);

//		getContentTable().add(scoreLabel).colspan(5);
//		getContentTable().row();
//		getContentTable().add(bestScoreLabel).colspan(5);
//		gs.hudStage.addActor(scoreLabel);
//		gs.hudStage.addActor(bestScoreLabel);

		for (int i = 0; i < imageButtons.size; i++) {
			getButtonTable().add(imageButtons.get(i)).width(imageButtons.get(i).getWidth())
					.height(imageButtons.get(i).getHeight());
		}
		getButtonTable().row();
		getButtonTable().add(removeAds).colspan(5);
		
		scoreLabel.setPosition(100, getY());
		

	}

	public void setupListeners() {
		imageButtons.get(0).addListener(new ClickListener() { // replay
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						TotemGame.soundManager.play("button");
						game.setScreen(new GameScreen(game));
					}
				});
		imageButtons.get(1).addListener(new ClickListener() { // highscore
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						game.setScreen(new HighScoresScreen(game));
						TotemGame.soundManager.play("button");
					}
				});
		imageButtons.get(2).addListener(new ClickListener() { // achievement
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);

						TotemGame.soundManager.play("button");
					}
				});
		imageButtons.get(3).addListener(new ClickListener() { // twitter
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);

						TotemGame.soundManager.play("button");
					}
				});
		imageButtons.get(4).addListener(new ClickListener() { // facebook
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);

						TotemGame.soundManager.play("button");
					}
				});

		if (JBSApi.api != null && JBSApi.loggedIn) {
			JBSApi.api.submitHighscore(score);
		}
		HighScores.addScore(score);

		
		if (score >= GamePrefs.prefs.getInteger("bestScore")) {
			
			GamePrefs.putInteger("bestScore", score);
			System.out.println(score + " " + GamePrefs.prefs.getInteger("bestScore"));
		}
		idkwhythisworks = new Image(new Texture(Gdx.files.internal("black.png")));
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		scoreLabel.draw(batch, parentAlpha);
		bestScoreLabel.draw(batch, parentAlpha);
		
		
		
		if (gs.camera.position.y > 0) {
			gs.camera.position.y -= (gs.camera.position.y - 1) * 0.1f;
		}
		
		x = getX() + getWidth() / 2 - scoreLabel.getWidth() / 2;
		y =  getY() + getHeight() - scoreLabel.getHeight() - 132 - (81 - scoreLabel.getHeight()) / 2;
		
		scoreLabel.setPosition(x, y);
		bestScoreLabel.setPosition(x, y - 130f);

//		sr.setProjectionMatrix(getStage().getCamera().combined);
//		sr.begin(ShapeType.Line);
//		for (int i = 0; i < this.getContentTable().getCells().size; i++) {
//
//			sr.box(getX() + getContentTable().getCells().get(i).getActorX(), getY()
//					+ getContentTable().getY() + getContentTable().getCells().get(i).getActorY(),
//					0, getContentTable().getCells().get(i).getActorWidth(), getContentTable()
//							.getCells().get(i).getActorHeight(), 0);
//		}
//		sr.end();
//		sr.begin(ShapeType.Line);
//		for (int i = 0; i < this.getButtonTable().getCells().size; i++) {
//			sr.box(getX() + getButtonTable().getCells().get(i).getActorX(), getY()
//					+ getButtonTable().getY() + getButtonTable().getCells().get(i).getActorY(), 0,
//					getButtonTable().getCells().get(i).getActorWidth(), getButtonTable().getCells()
//							.get(i).getActorHeight(), 0);
//		}
//		sr.end();

	}

}
