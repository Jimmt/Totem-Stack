package com.jumpbuttonstudio.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class CoinLabel extends Actor {
	Label coins;
	Image coinImage;
	ImageButton coinButton;
	Skin skin;
	Stage hudStage;
	BuyCoinsDialog dialog;

	public CoinLabel(Skin skin, Stage hudStage) {
		BitmapFont numbers = new BitmapFont(Gdx.files.internal("ui/shop/numbers.fnt"));

		this.hudStage = hudStage;

		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = numbers;
		labelStyle.fontColor = Color.WHITE;
		Texture bgTex = Icons.getTex("shop/jbscoin.png");
		Image background = new Image(bgTex);
		labelStyle.background = background.getDrawable();

		coins = new Label(" 420", labelStyle);
		coins.setWidth(bgTex.getWidth());
		coins.setHeight(bgTex.getHeight());

		Texture coinTex = Icons.getTex("shop/coin.png");
		coinImage = new Image(coinTex);
		coinImage.setScale(1);

		ImageButtonStyle coinButtonStyle = new ImageButtonStyle();
		coinButtonStyle.up = new Image(Icons.getTex("shop/add.png")).getDrawable();
		coinButtonStyle.down = new Image(Icons.getTex("shop/add_pressed.png")).getDrawable();

		coinButton = new ImageButton(coinButtonStyle);
		coinButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				TotemGame.soundManager.play("button");
				dialog.setVisible(true);
			}
		});

		this.skin = skin;

		dialog = new BuyCoinsDialog(skin);
		dialog.setPosition(Constants.WIDTH / 2 - dialog.getWidth() / 2, Constants.HEIGHT / 2
				- dialog.getHeight() / 2);

		if (hudStage != null) {
			hudStage.addActor(dialog);
		}
		dialog.setVisible(false);
	}
	
	@Override
	public float getHeight(){
		return coinImage.getHeight();
	}

	@Override
	public void act(float delta) {
		super.act(delta);
//		coinButton.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		coins.setText(" " + String.valueOf(GamePrefs.prefs.getInteger("coins")));
		coins.setPosition(getX() + coinImage.getWidth() - 20, getY() + coinImage.getHeight() / 2
				- coins.getHeight() / 2 - 3);
		coinImage.setPosition(getX(), getY());
		coinButton.setPosition(getX() + coinImage.getWidth() - coinButton.getWidth(), getY()
				- coinButton.getHeight() / 5);

		coins.draw(batch, parentAlpha);
		coinImage.draw(batch, parentAlpha);
//		coinButton.draw(batch, parentAlpha);
	}

}
