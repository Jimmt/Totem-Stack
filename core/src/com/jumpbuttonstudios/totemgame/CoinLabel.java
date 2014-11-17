package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class CoinLabel extends Actor {
	Label coins;
	Image coinImage;
	ImageButton coinButton;

	public CoinLabel() {
		BitmapFont numbers = new BitmapFont(Gdx.files.internal("ui/shop/numbers.fnt"));
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = numbers;
		labelStyle.fontColor = Color.WHITE;
		Texture bgTex = new Texture(Gdx.files.internal("shop/jbscoin.png"));
		bgTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		Image background = new Image(bgTex);
		labelStyle.background = background.getDrawable();

		coins = new Label(" 420", labelStyle);
		coins.setWidth(Icons.getImage("shop/jbscoin.png").getWidth());
		coins.setHeight(Icons.getImage("shop/jbscoin.png").getHeight());
		
		System.out.println(coins.getWidth());

		Texture coinTex = new Texture(Gdx.files.internal("shop/coin.png"));
		coinTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		coinImage = new Image(coinTex);
		coinImage.setScale(1);

		ImageButtonStyle coinButtonStyle = new ImageButtonStyle();
		coinButtonStyle.up = Icons.getImage("shop/add.png").getDrawable();
		coinButtonStyle.down = Icons.getImage("shop/add_pressed.png").getDrawable();

		coinButton = new ImageButton(coinButtonStyle);
		coinButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				TotemGame.soundManager.play("button");

			}
		});
	
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		if (Gdx.input.getX() > getX() + coinButton.getX()
				&& Gdx.input.getX() < getX() + coinButton.getX() + coinButton.getWidth()) {
			if (Constants.HEIGHT - Gdx.input.getY() > getY() / Constants.SCALE
					&& Constants.HEIGHT - Gdx.input.getY() < getY() / Constants.SCALE + coinButton.getHeight()) {
				if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
					System.out.println("hi");
				}
			}
		}

		coins.setPosition(getX() + coinImage.getWidth() - 20, getY() + coinImage.getHeight() / 2
				- coins.getHeight() / 2 - 3);
		coinImage.setPosition(getX(), getY());
		coinButton.setPosition(getX() + coinImage.getWidth() - coinButton.getWidth(), getY());

		coins.draw(batch, parentAlpha);
		coinImage.draw(batch, parentAlpha);
		coinButton.draw(batch, parentAlpha);
	}

}
