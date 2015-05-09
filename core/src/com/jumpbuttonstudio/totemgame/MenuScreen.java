package com.jumpbuttonstudio.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen extends AbstractScreen {
	ImageButton startButton, highscoresButton, loginButton;
	Texture tex;
	Image tmp;
	LogoutDialog logoutDialog;
	ImageButtonStyle logoutStyle, loginStyle;
	Table table;
	CoinsDialog coinsDialog;

	public MenuScreen(TotemGame game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();

// try {
// TwitterUtil.post("test");
// } catch (TwitterException e) {
// e.printStackTrace();
// } catch (IOException e) {
// e.printStackTrace();
// }
		if (GamePrefs.prefs.getBoolean("bgMusic")) {
			TotemGame.soundManager.stopMusic();
			TotemGame.soundManager.setPlayEffects(GamePrefs.prefs.getBoolean("soundEffects"));
			TotemGame.soundManager.loopMusic("menu", 1f);
		}

		logoutDialog = new LogoutDialog("", getSkin());

		table = super.getTable();
		table.setFillParent(true);

		Image background = new Image(Icons.getTex("bg/net.png"));
		background.setSize(Constants.WIDTH, Constants.HEIGHT);
		table.setBackground(background.getDrawable());

		ImageButtonStyle startStyle = new ImageButtonStyle();
		tmp = new Image(tex = Icons.getTex("login/start.png"));
		startStyle.imageUp = tmp.getDrawable();
		tmp = new Image(tex = Icons.getTex("login/start_clicked.png"));
		tmp.setSize(tmp.getWidth() * 2, tmp.getHeight() * 2);
		startStyle.imageDown = tmp.getDrawable();

		ImageButtonStyle highscoresStyle = new ImageButtonStyle();
		tmp = new Image(tex = Icons.getTex("login/highscore.png"));
		highscoresStyle.imageUp = tmp.getDrawable();
		tmp = new Image(tex = Icons.getTex("login/highscore_clicked.png"));
		highscoresStyle.imageDown = tmp.getDrawable();

		loginStyle = new ImageButtonStyle();
		tmp = new Image(tex = Icons.getTex("login/login.png"));
		loginStyle.imageUp = tmp.getDrawable();
		tmp = new Image(tex = Icons.getTex("login/login_clicked.png"));
		loginStyle.imageDown = tmp.getDrawable();

		logoutStyle = new ImageButtonStyle();
		tmp = new Image(tex = Icons.getTex("login/logout.png"));
		logoutStyle.imageUp = tmp.getDrawable();
		tmp = new Image(tex = Icons.getTex("login/logout_clicked.png"));
		logoutStyle.imageDown = tmp.getDrawable();

		startButton = new ImageButton(startStyle);
		highscoresButton = new ImageButton(highscoresStyle);
		loginButton = new ImageButton(loginStyle);

		startButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
				game.setScreen(new GameScreen(game));

			}

		});
		highscoresButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
// game.setScreen(new HighScoresScreen(game, null, null));
				TotemGame.soundManager.play("button");
			}

		});

		stage.getActors().removeValue(table, false);
		stage.addActor(table);

		HudTable hudTable = new HudTable(getSkin(), this);
		hudStage.addActor(hudTable);

		table.add(highscoresButton).width(highscoresButton.getWidth())
				.height(highscoresButton.getHeight()).padTop(startButton.getHeight() / 2);
		table.add(startButton).width(startButton.getWidth()).height(startButton.getHeight());
		table.add(loginButton).width(loginButton.getWidth()).height(loginButton.getHeight())
				.padTop(startButton.getHeight() / 2);

		InputMultiplexer multiplexer = new InputMultiplexer(hudTable.optionsDialog.xStage,
				hudStage, stage);

		Gdx.input.setInputProcessor(multiplexer);

		coinsDialog = new CoinsDialog("", getSkin(), hudStage);
		hudStage.addActor(coinsDialog);

		coinsDialog.setY(hudTable.header.getY() - coinsDialog.getHeight());
		coinsDialog.setModal(false);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		hudStage.draw();
		hudStage.act(delta);

	}

}
