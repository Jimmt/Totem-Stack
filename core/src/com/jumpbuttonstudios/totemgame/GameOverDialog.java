package com.jumpbuttonstudios.totemgame;

import java.io.IOException;

import twitter4j.TwitterException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;

public class GameOverDialog extends Dialog {
	int score;
	Array<ImageButton> imageButtons;
	GameScreen gs;
	TotemGame game;
	ShapeRenderer sr;
	Label bestScoreLabel, scoreLabel;
	Image label;
	float x, y, ratio;
	Skin skin;

	public GameOverDialog(int score, Skin skin, GameScreen gs, TotemGame game) {
		super("", skin);

		this.skin = skin;

		sr = new ShapeRenderer();

		setTransform(true);

		gs.spawner.rain.effect.allowCompletion();
		gs.spawner.lightning.effect.allowCompletion();
		TotemGame.soundManager.sounds.get("rain").stop();

		imageButtons = new Array<ImageButton>();

		setModal(false);

		this.gs = gs;
		this.game = game;
		this.score = score;

		String[] paths = { "ui/gameover/replay.png", "ui/gameover/highscores.png",
				"ui/gameover/achievements.png", "ui/gameover/twitter.png",
				"ui/gameover/facebook.png" };

		for (int i = 0; i < paths.length; i++) {
			ImageButtonStyle style = new ImageButtonStyle();
			style.imageUp = new Image(new Texture(Gdx.files.internal(paths[i]))).getDrawable();
			String pressed = paths[i].substring(0, paths[i].length() - 4) + "_pressed.png";
			style.imageDown = new Image(new Texture(Gdx.files.internal(pressed))).getDrawable();
			ImageButton imageButton = new ImageButton(style);
			imageButtons.add(imageButton);

		}

		setupListeners();

		Image panel = new Image(new Texture(Gdx.files.internal("gameover/window.png")));
		setBackground(panel.getDrawable());

		panel.setSize(Constants.WIDTH, Constants.HEIGHT - 80);
		setSize(panel.getWidth(), panel.getHeight());

		label = new Image(new Texture(Gdx.files.internal("gameover/gameover.png")));
		ratio = label.getWidth() / label.getHeight();
		label.setScale(1);
		label.setScaling(Scaling.none);
		getContentTable().add(label);
		getContentTable().row();

		ImageButtonStyle removeAdStyle = new ImageButtonStyle();
		removeAdStyle.up = new Image(new Texture(Gdx.files.internal("ui/paused/removead.png"))).getDrawable();
		removeAdStyle.down = new Image(new Texture(Gdx.files.internal("ui/paused/removead.png"))).getDrawable();
		ImageButton removeAds = new ImageButton(removeAdStyle);
		removeAds.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				TotemGame.soundManager.play("button");
			}
		});

		BitmapFont font = new BitmapFont(Gdx.files.internal("ui/top/scoreFont.fnt"));

		LabelStyle style = new LabelStyle();
		style.font = font;
		style.fontColor = Color.WHITE;
		style.background = new Image(new Texture(Gdx.files.internal("gameover/yourScoreBox.png"))).getDrawable();

		scoreLabel = new Label(String.valueOf(score), style);
		scoreLabel.setWidth(new Image(new Texture(Gdx.files.internal("gameover/yourScoreBox.png"))).getWidth());
		scoreLabel.setHeight(new Image(new Texture(Gdx.files.internal("gameover/yourScoreBox.png"))).getHeight());
		scoreLabel.setAlignment(Align.center);

		LabelStyle style1 = new LabelStyle();
		style1.font = font;
		style1.fontColor = Color.WHITE;
		style1.background = new Image(new Texture(Gdx.files.internal("gameover/bestScoreBox.png"))).getDrawable();

		bestScoreLabel = new Label(String.valueOf(GamePrefs.prefs.getInteger("bestScore")), style1);
		bestScoreLabel.setWidth(new Image(new Texture(Gdx.files.internal("gameover/bestScoreBox.png"))).getWidth());
		bestScoreLabel.setHeight(new Image(new Texture(Gdx.files.internal("gameover/bestScoreBox.png"))).getHeight());
		bestScoreLabel.setAlignment(Align.center);

		getContentTable().add(scoreLabel)
				.width(new Image(new Texture(Gdx.files.internal("gameover/yourScoreBox.png"))).getWidth())
				.height(new Image(new Texture(Gdx.files.internal("gameover/yourScoreBox.png"))).getHeight()).expandX().center();
		getContentTable().row();
		getContentTable().add(bestScoreLabel)
				.width(new Image(new Texture(Gdx.files.internal("gameover/bestScoreBox.png"))).getWidth())
				.height(new Image(new Texture(Gdx.files.internal("gameover/bestScoreBox.png"))).getHeight()).expandX().center();

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

						if (!TwitterUtil.loggedIn) {
							try {
								TwitterUtil.init();
								TwitterUtil.openURL();
							} catch (TwitterException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}

							PinDialog pinDialog = new PinDialog(String.valueOf(score), skin, gs);
							gs.hudStage.addActor(pinDialog);
							pinDialog.setPosition(Constants.WIDTH / 2 - pinDialog.getWidth() / 2,
									Constants.HEIGHT / 2 - pinDialog.getHeight() / 2);

						} else {
							TwitterUtil.postFinal("I just scored " + score
									+ " points in Totem Stack!");
							TweetedDialog tweeted = new TweetedDialog(
									"Your tweet has been posted!", skin);
							gs.hudStage.addActor(tweeted);
							tweeted.setPosition(Constants.WIDTH / 2 - tweeted.getWidth() / 2,
									Constants.HEIGHT / 2 - tweeted.getHeight() / 2);
						}

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
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		if (gs.camera.position.y > 0) {
			gs.camera.position.y -= (gs.camera.position.y - 1) * 0.1f;
		}

		scoreLabel.setWidth(new Image(new Texture(Gdx.files.internal("gameover/yourScoreBox.png"))).getWidth());
		scoreLabel.setHeight(new Image(new Texture(Gdx.files.internal("gameover/yourScoreBox.png"))).getHeight());
		bestScoreLabel.setWidth(new Image(new Texture(Gdx.files.internal("gameover/bestScoreBox.png"))).getWidth());
		bestScoreLabel.setHeight(new Image(new Texture(Gdx.files.internal("gameover/bestScoreBox.png"))).getHeight());

	}

}
