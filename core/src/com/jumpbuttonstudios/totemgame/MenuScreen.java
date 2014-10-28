package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class MenuScreen extends AbstractScreen {
	ImageButton startButton, highscoresButton, loginButton;
	Texture tex;
	Image tmp;
	LoginDialog loginDialog;
	LogoutDialog logoutDialog;
	ImageButtonStyle logoutStyle, loginStyle;

	public MenuScreen(TotemGame game) {
		super(game);

	}

	@Override
	public void show() {
		super.show();

		Icons.loadIcons();
		GamePrefs.initialize();

		loginDialog = new LoginDialog("", getSkin());
		logoutDialog = new LogoutDialog("", getSkin());

		Table table = super.getTable();
		table.setFillParent(true);

		ImageButtonStyle startStyle = new ImageButtonStyle();
		tmp = new Image(tex = new Texture(Gdx.files.internal("login/start.png")));
		startStyle.imageUp = tmp.getDrawable();
		tmp = new Image(tex = new Texture(Gdx.files.internal("login/start_clicked.png")));
		tmp.setSize(tmp.getWidth() * 2, tmp.getHeight() * 2);
		startStyle.imageDown = tmp.getDrawable();

		ImageButtonStyle highscoresStyle = new ImageButtonStyle();
		tmp = new Image(tex = new Texture(Gdx.files.internal("login/highscore.png")));
		highscoresStyle.imageUp = tmp.getDrawable();
		tmp = new Image(tex = new Texture(Gdx.files.internal("login/highscore_clicked.png")));
		highscoresStyle.imageDown = tmp.getDrawable();

		loginStyle = new ImageButtonStyle();
		tmp = new Image(tex = new Texture(Gdx.files.internal("login/login.png")));
		loginStyle.imageUp = tmp.getDrawable();
		tmp = new Image(tex = new Texture(Gdx.files.internal("login/login_clicked.png")));
		loginStyle.imageDown = tmp.getDrawable();
		
		
		logoutStyle = new ImageButtonStyle();
		logoutStyle.up = Icons.getImage("login/logout.png").getDrawable();
		logoutStyle.down = Icons.getImage("login/logout_clicked.png").getDrawable();

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
				game.setScreen(new GameScreen(game));
				TotemGame.soundManager.play("button");
			}

		});
		loginButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (JBSApi.loggedIn) {
					logoutDialog.setVisible(true);
				} else {
					loginDialog.setVisible(true);
				}
				TotemGame.soundManager.play("button");
			}

		});

		Image background = new Image(new Texture(Gdx.files.internal("bg/net.png")));
		stage.addActor(background);

		stage.getActors().removeValue(table, false);
		stage.addActor(table);

		HudTable hudTable = new HudTable(getSkin(), this);
		hudStage.addActor(hudTable);

		table.add(highscoresButton).width(highscoresButton.getWidth())
				.height(highscoresButton.getHeight()).padTop(startButton.getHeight() / 2);
		table.add(startButton).width(startButton.getWidth()).height(startButton.getHeight());
		table.add(loginButton).width(loginButton.getWidth()).height(loginButton.getHeight())
				.padTop(startButton.getHeight() / 2);

		hudStage.addActor(loginDialog);
		loginDialog.setPosition(Constants.WIDTH / 2 - loginDialog.getWidth() / 2, Constants.HEIGHT
				/ 2 - loginDialog.getHeight() / 2);
		loginDialog.setVisible(false);

		hudStage.addActor(logoutDialog);
		logoutDialog.setPosition(Constants.WIDTH / 2 - loginDialog.getWidth() / 2, Constants.HEIGHT
				/ 2 - loginDialog.getHeight() / 2);
		logoutDialog.setVisible(false);

		InputMultiplexer multiplexer = new InputMultiplexer(hudStage, stage);

		Gdx.input.setInputProcessor(multiplexer);
		
		

	}

	@Override
	public void render(float delta) {
		super.render(delta);

		hudStage.draw();
		hudStage.act(delta);
		Table.drawDebug(stage);

		if (JBSApi.loggedIn) {
			loginButton.setStyle(logoutStyle);	
			
		}
		 else {
//			loginButton.setStyle(loginStyle);
		}
	}

}
