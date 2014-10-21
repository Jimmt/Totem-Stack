package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HudTable extends Table {
	GameScreen game;
	Label score;
	ImageButton pause, sound, options, shop, home;
	Image header;
	String[] paths = { "pause", "soundon", "setting", "shop", "home" };
	ImageButton[] buttons = { pause, sound, options, shop, home };
	OptionsDialog optionsDialog;

	public HudTable(Skin skin, final GameScreen game) {
		super(skin);

		setFillParent(true);

		this.game = game;

		LabelStyle style = new LabelStyle();
		style.font = new BitmapFont(Gdx.files.internal("ui/top/scoreFont.fnt"));

		header = new Image(new Texture(Gdx.files.internal("ui/top/header.png")));
		header.setY(Constants.HEIGHT - header.getHeight());

		game.hudStage.addActor(header);

		for (int i = 0; i < buttons.length; i++) {
			ImageButtonStyle ibstyle = new ImageButtonStyle();
			ibstyle.imageUp = new Image(new Texture(Gdx.files.internal("ui/top/" + paths[i]
					+ ".png"))).getDrawable();
			ibstyle.imageDown = new Image(new Texture(Gdx.files.internal("ui/top/" + paths[i]
					+ "_pressed.png"))).getDrawable();
			buttons[i] = new ImageButton(ibstyle);
			
		}

		setupListeners();

		Table left = new Table();
		Table right = new Table();

		for (int i = 0; i < buttons.length; i++) {
			left.add(buttons[i]).padLeft(4f).padTop(4f).width(buttons[i].getWidth())
					.height(buttons[i].getHeight());
		}

		for (int i = 0; i < 4; i++) {
			right.add(new Image(new Texture(Gdx.files.internal("drape.png"))));
		}

		add(left).expand().left().top();
		add(right).expand().right().top();

		Table bottom = new Table();
		ImageButtonStyle ibs = new ImageButtonStyle();
		ibs.imageUp = Icons.getImage("ui/gameplay/left.png").getDrawable();
		ibs.imageDown = Icons.getImage("ui/gameplay/leftclicked.png").getDrawable();
		ImageButton leftButton = new ImageButton(ibs);
		if (Gdx.app.getType() == ApplicationType.Android) {
			leftButton.addListener(new ClickListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					if (game.spawner.currentTotem != null) {
						game.spawner.moveLeft();

					}
					return true;

				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					super.touchUp(event, x, y, pointer, button);

					game.spawner.stop();

				}

			});
		}

		leftButton.setPosition(0, 0);

		ImageButtonStyle ibs2 = new ImageButtonStyle();
		ibs2.imageUp = Icons.getImage("ui/gameplay/right.png").getDrawable();
		ibs2.imageDown = Icons.getImage("ui/gameplay/rightclicked.png").getDrawable();
		ImageButton rightButton = new ImageButton(ibs2);
		if (Gdx.app.getType() == ApplicationType.Android) {
			rightButton.addListener(new ClickListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

					if (game.spawner.currentTotem != null) {
						game.spawner.moveRight();
					}
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					super.touchUp(event, x, y, pointer, button);

					game.spawner.stop();

				}

			});
		}
		rightButton.setPosition(Constants.WIDTH - rightButton.getWidth(), 0);

		game.hudStage.addActor(leftButton);
		game.hudStage.addActor(rightButton);

		score = new Label("", style);

		score.setPosition(Constants.WIDTH / 2 - 18, Constants.HEIGHT - 44);

		game.hudStage.addActor(score);
		
		optionsDialog = new OptionsDialog("", game.getSkin());

		game.hudStage.addActor(optionsDialog);
		optionsDialog.setPosition(Constants.WIDTH / 2 - optionsDialog.getWidth() / 2, Constants.HEIGHT / 2 - optionsDialog.getHeight() / 2);
		optionsDialog.setVisible(false);
	}

	
	
	
	
	
	public void setupListeners() {
		buttons[0].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
			}

		});
		buttons[1].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
			}

		});
		buttons[2].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
				
				optionsDialog.setVisible(true);

			}

		});
		buttons[3].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
			}

		});
		buttons[4].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
			}

		});
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		score.setText(String.valueOf(game.score));

		if (score.getText().length() > 1) {
			score.setX(Constants.WIDTH / 2 - score.getPrefWidth() / 2);
		}
		
		

	}
}
