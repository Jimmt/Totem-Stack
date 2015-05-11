package com.jumpbuttonstudio.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HudTable extends Table {
	static boolean soundOn = true;
	AbstractScreen game;
	Label score;
	ImageButton pause, sound, options, shop, home, leftButton, rightButton;
	ImageButtonStyle soundOnStyle, soundOffStyle;
	Image header;

	String[] paths = { "pause", "setting", "shop", "home" };
	ImageButton[] buttons = { pause, options, shop, home };
	String[] powerups = { "retry", "freeze", "slow", "wind" };

	OptionsDialog optionsDialog;
	ShopDialog shopDialog;
	Drape[] drapes = new Drape[4];
	Table left, right;

	public HudTable(Skin skin, final AbstractScreen game) {
		super(skin);

		setTransform(true);
		setFillParent(true);

		this.game = game;

		LabelStyle style = new LabelStyle();
		style.font = new BitmapFont(Gdx.files.internal("ui/top/scoreFont.fnt"));

		header = new Image(Icons.getTex("ui/top/header.png"));
		header.setWidth(Constants.WIDTH);
		header.setY(Constants.HEIGHT - header.getHeight());

		game.hudStage.addActor(header);

		for (int i = 0; i < buttons.length; i++) {
			ImageButtonStyle ibstyle = new ImageButtonStyle();
			ibstyle.imageUp = new Image(Icons.getTex("ui/top/" + paths[i] + ".png")).getDrawable();
			ibstyle.imageDown = new Image(Icons.getTex("ui/top/" + paths[i] + "_pressed.png"))
					.getDrawable();
			buttons[i] = new ImageButton(ibstyle);

		}

		soundOnStyle = new ImageButtonStyle();
		soundOnStyle.up = new Image(Icons.getTex("ui/top/soundon.png")).getDrawable();
		soundOnStyle.down = new Image(Icons.getTex("ui/top/soundon_pressed.png")).getDrawable();

		sound = new ImageButton(soundOnStyle);

		soundOffStyle = new ImageButtonStyle();
		soundOffStyle.up = new Image(Icons.getTex("ui/top/soundoff.png")).getDrawable();
		soundOffStyle.down = new Image(Icons.getTex("ui/top/soundoff_pressed.png")).getDrawable();

		left = new Table();
		right = new Table();

		for (int i = 0; i < buttons.length; i++) {
			if (i == 1) {
				left.add(sound).padLeft(4f).padTop(4f).width(sound.getWidth())
						.height(sound.getHeight());
			}
			left.add(buttons[i]).padLeft(4f).padTop(4f).width(buttons[i].getWidth())
					.height(buttons[i].getHeight());
		}

		for (int i = 0; i < 4; i++) {
			Drape d = new Drape(powerups[i]);
			drapes[i] = d;
			right.add(d).padLeft(1f);
		}

		add(left).expand().left().top();

		add(right).expand().right().top();

// add(right);

		if (game instanceof GameScreen) {

			// Table bottom = new Table(); // not used?
			ImageButtonStyle ibs = new ImageButtonStyle();
			ibs.imageUp = new Image(Icons.getTex("ui/gameplay/left.png")).getDrawable();
			ibs.imageDown = new Image(Icons.getTex("ui/gameplay/leftclicked.png")).getDrawable();
			leftButton = new ImageButton(ibs);
			leftButton.addListener(new ClickListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					if (((GameScreen) game).spawner.currentTotem != null) {
						((GameScreen) game).spawner.moveLeft();

					}
					return true;

				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					super.touchUp(event, x, y, pointer, button);

					((GameScreen) game).spawner.stop();

				}

			});

			leftButton.setPosition(0, 0);

			ImageButtonStyle ibs2 = new ImageButtonStyle();
			ibs2.imageUp = new Image(Icons.getTex("ui/gameplay/right.png")).getDrawable();
			ibs2.imageDown = new Image(Icons.getTex("ui/gameplay/rightclicked.png")).getDrawable();
			rightButton = new ImageButton(ibs2);
			rightButton.addListener(new ClickListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

					if (((GameScreen) game).spawner.currentTotem != null) {
						((GameScreen) game).spawner.moveRight();
					}
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					super.touchUp(event, x, y, pointer, button);

					((GameScreen) game).spawner.stop();

				}

			});

			rightButton.setPosition(Constants.WIDTH - rightButton.getWidth(), 0);
			game.hudStage.addActor(leftButton);
			game.hudStage.addActor(rightButton);
			leftButton.setVisible(false);
			rightButton.setVisible(false);
		}

		score = new Label("", style);
		score.setPosition(Constants.WIDTH / 2 - score.getWidth() / 2,
				Constants.HEIGHT - score.getHeight() / 2);
		game.hudStage.addActor(score);

		optionsDialog = new OptionsDialog("", game.getSkin(), game);
		game.hudStage.addActor(optionsDialog);
		optionsDialog.setPosition(Constants.WIDTH / 2 - optionsDialog.getWidth() / 2,
				Constants.HEIGHT / 2 - optionsDialog.getHeight() / 2);
		optionsDialog.setVisible(false);

		if (game instanceof GameScreen) {
			shopDialog = new ShopDialog("", game.getSkin(), (GameScreen) game, game);
		} else {
			shopDialog = new ShopDialog("", game.getSkin(), null, game);
		}

		game.hudStage.addActor(shopDialog);
		shopDialog.setPosition(Constants.WIDTH / 2 - shopDialog.getWidth() / 2, Constants.HEIGHT
				/ 2 - shopDialog.getHeight() / 2);
		shopDialog.setVisible(false);

		setupListeners();
// optionsDialog.debug();
	}

	@Override
	public void setVisible(boolean vis) {
		super.setVisible(vis);

	}

	public void setupListeners() {
		drapes[0].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) { // retry
				TotemGame.soundManager.play("button");

				if (game instanceof GameScreen) {
					if (GamePrefs.prefs.getInteger("retryUses") > 0
							&& drapes[0].spawner.totems.size > 1
							&& !drapes[0].spawner.totems.get(drapes[0].spawner.totems.size - 1).enableParachute) {
						GamePrefs.putInteger("retryUses",
								GamePrefs.prefs.getInteger("retryUses") - 1);

						((GameScreen) game).removeTotems.add(drapes[0].spawner.totems
								.get(drapes[0].spawner.totems.size - 1));
						((GameScreen) game).stage.getActors().removeValue(
								drapes[0].spawner.totems.get(drapes[0].spawner.totems.size - 1),
								false);
						drapes[0].spawner.totems.removeIndex(drapes[0].spawner.totems.size - 1);

					}
				}
			}

		});
		drapes[1].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) { // freeze
				TotemGame.soundManager.play("button");
				if (game instanceof GameScreen) {
					if (!drapes[0].spawner.frozeOnce) {
						if (GamePrefs.prefs.getInteger("freezeUses") > 0) {
							GamePrefs.putInteger("freezeUses",
									GamePrefs.prefs.getInteger("freezeUses") - 1);
							drapes[0].spawner.addFreezeTotem();
						}
					}
				}
			}

		});
		drapes[2].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) { // slow
				TotemGame.soundManager.play("button");
				if (game instanceof GameScreen) {
					if (GamePrefs.prefs.getInteger("slowUses") > 0) {
						GamePrefs.putInteger("slowUses", GamePrefs.prefs.getInteger("slowUses") - 1);
						game.setStep(1 / 120f);
					}
				}
			}

		});
		drapes[3].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) { // wind
				TotemGame.soundManager.play("button");
				if (game instanceof GameScreen) {
					if (GamePrefs.prefs.getInteger("windUses") > 0) {
						GamePrefs.putInteger("windUses", GamePrefs.prefs.getInteger("windUses") - 1);
						drapes[0].spawner.enableWindDebuff();
					}
				}
			}

		});
		buttons[0].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) { // pause
				TotemGame.soundManager.play("button");
				game.pause(true);

			}

		});
		if (TotemGame.soundManager.getMasterPlay() && TotemGame.soundManager.getPlay()) {
			sound.setStyle(soundOnStyle);
		} else {
			sound.setStyle(soundOffStyle);
		}
		sound.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) { // sound
				TotemGame.soundManager.play("button");

				if (soundOn) {
					sound.setStyle(soundOffStyle);
					if (GamePrefs.prefs.getBoolean("bgMusic")) {
						TotemGame.soundManager.stopMusic();
						TotemGame.soundManager.setPlayMusic(false);
					}
				} else {
					sound.setStyle(soundOnStyle);
					if (GamePrefs.prefs.getBoolean("bgMusic")) {
						TotemGame.soundManager.setPlayMusic(true);
					}
				}
				soundOn = !soundOn;
			}

		});
		buttons[1].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) { // options
				TotemGame.soundManager.play("button");
				game.pause(false);
				optionsDialog.setVisible(game.paused);

			}

		});
		buttons[2].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) { // shop
				TotemGame.soundManager.play("button");
				game.pause(false);
				shopDialog.setWidth(Constants.WIDTH);
				shopDialog.setVisible(true);
			}

		});
		buttons[3].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) { // home
				TotemGame.soundManager.play("button");
				game.game.setScreen(new MenuScreen(game.game));
			}

		});
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		score.setPosition(Constants.WIDTH / 2 - score.getPrefWidth() / 2,
				header.getY() + header.getHeight() * 26f/90f + score.getPrefHeight() / 2 + 1);

		for (int i = 0; i < drapes.length; i++) {
			drapes[i].setCount(GamePrefs.prefs.getInteger(powerups[i] + "Uses"));
		}

		if (game instanceof GameScreen) {
			score.setText(String.valueOf(((GameScreen) game).score));
			drapes[0].setTotemSpawner(((GameScreen) game).spawner);

			if (GamePrefs.prefs.getBoolean("tap")) {
				leftButton.setVisible(true);
				rightButton.setVisible(true);
			} else {
				leftButton.setVisible(false);
				rightButton.setVisible(false);
			}
		}

// if (score.getText().length() > 1) {
// score.setX(Constants.WIDTH / 2 - score.getPrefWidth() / 2);
// }

	}
}