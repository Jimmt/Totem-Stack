package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	String[] paths = { "pause", "soundon", "setting", "shop", "home" };
	ImageButton[] buttons = { pause, sound, options, shop, home };

	public HudTable(Skin skin, GameScreen game) {
		super(skin);

		setFillParent(true);

		this.game = game;

		LabelStyle style = new LabelStyle();
		style.font = new BitmapFont(Gdx.files.internal("ui/top/scoreFont.fnt"));

		Image header = new Image(new Texture(Gdx.files.internal("ui/top/header.png")));
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
			left.add(buttons[i]).padLeft(4f).padTop(4f);
		}

		for (int i = 0; i < 4; i++) {
			right.add(new Image(new Texture(Gdx.files.internal("drape.png"))));
		}

		add(left).expand().left().top();
		add(right).expand().right().top();

		score = new Label("", style);

		score.setPosition(Constants.WIDTH / 2 - 18, Constants.HEIGHT - 44);
		game.hudStage.addActor(score);

	}

	public void setupListeners() {
		buttons[0].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}

		});
		buttons[1].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}

		});
		buttons[2].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}

		});
		buttons[3].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}

		});
		buttons[4].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

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
