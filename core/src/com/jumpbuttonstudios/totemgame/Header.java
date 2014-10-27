package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Header extends Actor {
	Image border, avatar, background;
	BitmapFont font;
	String username;
	Label text;

	public Header(Image avatar, String username) {
		this.avatar = avatar;
		this.username = username;

		border = Icons.getImage("login/avatar.png");
		background = Icons.getImage("login/welcomeback.png");
		avatar.setSize(avatar.getWidth() * 0.5f, avatar.getHeight() * 0.5f);
		avatar.setPosition(10 + 15, Constants.HEIGHT - avatar.getHeight() - 10 - 5);
		border.setScale(1.0f);
		background.setScale(1.0f);
		border.setPosition(avatar.getX() - 10, avatar.getY() - 10);
		background.setPosition(0, Constants.HEIGHT - background.getHeight());

		font = new BitmapFont(Gdx.files.internal("ui/highscore/font.fnt"));

		LabelStyle style = new LabelStyle(font, Color.WHITE);
		text = new Label("Welcome back, " + username, style);
		System.out.println((140 - text.getHeight()) / 2);
		text.setPosition(Constants.WIDTH / 2 - text.getWidth() / 2,
				Constants.HEIGHT - (130 - text.getHeight()) / 2 - text.getHeight());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		background.draw(batch, parentAlpha);
		avatar.draw(batch, parentAlpha);
		border.draw(batch, parentAlpha);

		text.draw(batch, parentAlpha);

// String message = "Welcome back, " + username;

// font.draw(batch, message, Constants.WIDTH / 2 - message.length() * 40f / 4,
// Constants.HEIGHT - 40f);

	}

}
