package com.jbs.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class HudTable extends Table {
	static boolean soundOn = true;
	AbstractScreen game;
	Label score;
	ImageButton jbs, pause, sound, options, shop, home, leftButton, rightButton;
	ImageButtonStyle soundOnStyle, soundOffStyle;
	Image header;

	boolean pauseDisplayed = true, moved;
	String[] paths = { "jbsbutton", "settings", "shop", "home", "pause" };
	ImageButton[] buttons = { jbs, options, shop, home, pause };
	String[] powerups = { "retry", "freeze", "slow", "wind" };

	OptionsDialog optionsDialog;
	ShopDialog shopDialog;
	Drape[] drapes = new Drape[4];
	Table drapesLeft, drapesRight;

	public HudTable(Skin skin, final AbstractScreen game) {
		super(skin);

		setTransform(true);
		setFillParent(true);

		this.game = game;

		header = new Image(Icons.getTex("ui/top/header.png"));
		header.setWidth(Constants.HUD_WIDTH);
		header.setY(Constants.HUD_HEIGHT - header.getHeight());

		game.hudStage.addActor(header);

		for (int i = 0; i < buttons.length; i++) {
			ImageButtonStyle ibstyle = new ImageButtonStyle();
			ibstyle.imageUp = new Image(Icons.getTex("ui/top/" + paths[i] + ".png")).getDrawable();

			if (paths[i].equals("pause")) {
// ibstyle.imageChecked = new
// Image(Icons.getTex("ui/top/play.png")).getDrawable();
			}

// ibstyle.imageDown = new Image(Icons.getTex("ui/top/" + paths[i] +
// "_pressed.png"))
// .getDrawable();
			buttons[i] = new ImageButton(ibstyle);

		}

		soundOnStyle = new ImageButtonStyle();
		soundOnStyle.imageUp = new Image(Icons.getTex("ui/top/soundon.png")).getDrawable();

		sound = new ImageButton(soundOnStyle);

		soundOffStyle = new ImageButtonStyle();
		soundOffStyle.imageUp = new Image(Icons.getTex("ui/top/soundoff.png")).getDrawable();

		drapesLeft = new Table();
		drapesRight = new Table();

		Table innerLeft = new Table();
		Table innerRight = new Table();

		for (int i = 0; i < buttons.length; i++) {

			if (i < 3) {
				innerLeft.add(buttons[i]).padLeft(4f).padTop(4f).width(buttons[i].getWidth())
						.height(header.getHeight() - 13);
			} else {
				innerRight.add(buttons[i]).padLeft(4f).padTop(4f).width(buttons[i].getWidth())
						.height(header.getHeight() - 13);
			}

		}
		innerRight.add(sound).padLeft(4f).padTop(4f).width(sound.getWidth())
				.height(header.getHeight() - 13).right();

		LabelStyle style = new LabelStyle();
		style.font = new BitmapFont(Gdx.files.internal("ui/top/scoreFont.fnt"));
		score = new Label("", style);
		Image box = new Image(Icons.getTex("ui/top/box.png"));
		Group group = new Group();
		group.addActor(box);
		box.setPosition(-box.getWidth() / 2, header.getHeight() / 2 - box.getHeight() / 2);

		Table buttons = new Table();
		Table top = new Table();
		buttons.add(innerLeft).expand().left().top();
		buttons.add(box);
		buttons.add(innerRight).expand().right().top();
		top.add(buttons).expandX().top().colspan(2);

		for (int i = 0; i < 4; i++) {
			Drape d = new Drape(powerups[i]);
			drapes[i] = d;
			if (i < 2) {
				drapesLeft.add(d);
			} else {
				drapesRight.add(d);
			}
		}

		top.row();
		top.add(drapesLeft).height(drapes[0].getHeight() - 10).left();
		top.add(drapesRight).height(drapes[0].getHeight() - 10).right();
		add(top).expand().top();

		if (game instanceof GameScreen) {

			// Table bottom = new Table(); // not used?
			ImageButtonStyle ibs = new ImageButtonStyle();
			Image leftUp = new Image(Icons.getTex("ui/gameplay/left.png"));
			ibs.imageUp = leftUp.getDrawable();
			Image leftDown =  new Image(Icons.getTex("ui/gameplay/leftclicked.png"));
			ibs.imageDown = leftDown.getDrawable();
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
			
			rightButton.setPosition(Constants.HUD_WIDTH - rightButton.getWidth(), 0);
			game.hudStage.addActor(leftButton);
			game.hudStage.addActor(rightButton);
			leftButton.setVisible(false);
			rightButton.setVisible(false);
		}

		optionsDialog = new OptionsDialog("", game.getSkin(), game);
		game.hudStage.addActor(optionsDialog);
		optionsDialog.setPosition(Constants.HUD_WIDTH / 2 - optionsDialog.getWidth() / 2,
				Constants.HUD_HEIGHT / 2 - optionsDialog.getHeight() / 2);
		optionsDialog.setVisible(false);

		if (game instanceof GameScreen) {
			shopDialog = new ShopDialog("", game.getSkin(), (GameScreen) game, game);
		} else {
			shopDialog = new ShopDialog("", game.getSkin(), null, game);
		}
		if (game instanceof MenuScreen) {
			this.buttons[3].setTouchable(Touchable.disabled);
			this.buttons[4].setTouchable(Touchable.disabled);
		}

		game.hudStage.addActor(shopDialog);
		shopDialog.show(game.hudStage);
		shopDialog.setPosition(Constants.HUD_WIDTH / 2 - shopDialog.getWidth() / 2,
				Constants.HUD_HEIGHT / 2 - shopDialog.getHeight() / 2);
		shopDialog.setVisible(false);

		setupListeners();
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

		buttons[4].addListener(new ClickListener() {
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
						TotemGame.soundManager.setPlayEffects(false);
						TotemGame.soundManager.stopMusic();

					}
					TotemGame.soundManager.setMasterPlay(false);
				} else {

					sound.setStyle(soundOnStyle);
					if (GamePrefs.prefs.getBoolean("bgMusic")) {
						TotemGame.soundManager.setPlayEffects(true);
						TotemGame.soundManager.setPlayMusic(true);
					}
					TotemGame.soundManager.setMasterPlay(true);
				}
				soundOn = !soundOn;
			}

		});
		buttons[0].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) { // jbs
				TotemGame.soundManager.play("button");
				Gdx.net.openURI("https://www.jumpbuttonstudio.com/");

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
				shopDialog.setWidth(Constants.HUD_WIDTH);
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
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		score.draw(batch, parentAlpha);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		score.setPosition(Constants.HUD_WIDTH / 2 - score.getPrefWidth() / 2, header.getY()
				+ header.getHeight() / 2);
		score.act(delta);

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
// score.setX(Constants.HUD_WIDTH / 2 - score.getPrefWidth() / 2);
// }
		


	}
}
