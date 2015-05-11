package com.jumpbuttonstudio.totemgame;

import java.io.IOException;

import twitter4j.TwitterException;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
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
	Image yourScore, bestScore, gameOver;
	Skin skin;
	boolean moved, submittedScore;

	public GameOverDialog(int score, Skin skin, GameScreen gs, TotemGame game,
			boolean submittedScore) {
		super("", skin);

		this.submittedScore = submittedScore;

		if (TotemGame.services.getSignedIn()) {
			TotemGame.services.submitHighscore(score);
		}
		if (score >= GamePrefs.prefs.getInteger("bestScore")) {
			GamePrefs.putInteger("bestScore", score);
		}

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
			style.imageUp = new Image(Icons.getTex(paths[i])).getDrawable();
			String pressed = paths[i].substring(0, paths[i].length() - 4) + "_pressed.png";
			style.imageDown = new Image(Icons.getTex(pressed)).getDrawable();
			ImageButton imageButton = new ImageButton(style);
			imageButtons.add(imageButton);

		}

		setupListeners();

		Image panel = new Image(Icons.getTex("gameover/window.png"));
		setBackground(panel.getDrawable());

		panel.setSize(Constants.WIDTH, Constants.HEIGHT - 80);
		setSize(panel.getWidth(), panel.getHeight());

		gameOver = new Image(Icons.getTex("gameover/gameover.png"));
		gameOver.setPosition(Constants.WIDTH / 2 - gameOver.getWidth() / 2, Constants.HEIGHT);
		gameOver.setScaling(Scaling.fill);
		getContentTable().add(gameOver);
		getContentTable().row();

		ImageButtonStyle removeAdStyle = new ImageButtonStyle();
		removeAdStyle.up = new Image(Icons.getTex("ui/paused/removead.png")).getDrawable();
		removeAdStyle.down = new Image(Icons.getTex("ui/paused/removead.png")).getDrawable();
		ImageButton removeAds = new ImageButton(removeAdStyle);
		removeAds.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				TotemGame.soundManager.play("button");
			}
		});

		BitmapFont font = new BitmapFont(Gdx.files.internal("ui/top/scoreFont.fnt"));

		yourScore = new Image(Icons.getTex("gameover/yourScoreBox.png"));
		LabelStyle style = new LabelStyle();
		style.font = font;
		style.fontColor = Color.WHITE;
		style.background = yourScore.getDrawable();

		scoreLabel = new Label(String.valueOf(score), style);
		scoreLabel.setWidth(yourScore.getWidth());
		scoreLabel.setHeight(yourScore.getHeight());
		scoreLabel.setAlignment(Align.center);

		bestScore = new Image(new Texture(Gdx.files.internal("gameover/bestScoreBox.png")));
		LabelStyle style1 = new LabelStyle();
		style1.font = font;
		style1.fontColor = Color.WHITE;
		style1.background = bestScore.getDrawable();

		bestScoreLabel = new Label(String.valueOf(GamePrefs.prefs.getInteger("bestScore")), style1);
		bestScoreLabel.setWidth(bestScore.getWidth());
		bestScoreLabel.setHeight(bestScore.getHeight());
		bestScoreLabel.setAlignment(Align.center);

		getContentTable().add(scoreLabel).width(yourScore.getWidth()).height(yourScore.getHeight())
				.fill().center();
		getContentTable().row();
		getContentTable().add(bestScoreLabel).width(bestScore.getWidth())
				.height(bestScore.getHeight()).fill().center();

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
		final GameOverDialog dialog = this;
		imageButtons.get(1).addListener(new ClickListener() { // highscore
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						if(TotemGame.services.getSignedIn()){
							TotemGame.services.submitHighscore(score);
						}
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

	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		if (gs.camera.position.y > 0) {
			gs.camera.position.y -= (gs.camera.position.y - 1) * 0.1f;
		}

		scoreLabel.setWidth(yourScore.getWidth());
		scoreLabel.setHeight(yourScore.getHeight());
		bestScoreLabel.setWidth(bestScore.getWidth());
		bestScoreLabel.setHeight(bestScore.getHeight());

		if (!moved) {
// if (getActions().size == 0) {
			moved = true;
			gameOver.addAction(Actions.sequence(Actions.moveBy(0, gameOver.getHeight() * 3),
					Actions.delay(1.35f), Actions.moveBy(0, -gameOver.getHeight() * 3, 0.25f)));
// }
		}

	}

}