package com.jumpbuttonstudio.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
	float alpha = 1;
	float counter = 999f, cap = 0.05f;
	float x = 1;

	public Header(Image avatar, String username) {
		this.avatar = avatar;
		this.username = username;

		border = new Image(new Texture(Gdx.files.internal("login/avatar.png")));
		background = new Image(new Texture(Gdx.files.internal("login/welcomeback.png")));
		avatar.setSize(avatar.getWidth() * 0.5f, avatar.getHeight() * 0.5f);
		avatar.setPosition(10 + 15, Constants.HEIGHT - avatar.getHeight() - 10 - 5);
		border.setScale(1.0f);
		background.setScale(1.0f);
		border.setPosition(avatar.getX() - 10, avatar.getY() - 10);
		background.setPosition(0, Constants.HEIGHT - background.getHeight());

		font = new BitmapFont(Gdx.files.internal("ui/highscore/font.fnt"));

		LabelStyle style = new LabelStyle(font, Color.WHITE);
		text = new Label("Welcome back, " + username, style);
		text.setPosition(Constants.WIDTH / 2 - text.getWidth() / 2,
				Constants.HEIGHT - (130 - text.getHeight()) / 2 - text.getHeight());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		if (counter > cap) {

			if (alpha > 0) {
				x++;
				alpha -= ((x / 100) * (x / 100));
			}
			if (alpha < 0) {
				alpha = 0;
			}
			counter = 0;
		} else {
			counter += Gdx.graphics.getDeltaTime();
		}

		background.draw(batch, alpha);
		avatar.draw(batch, alpha);
		border.draw(batch, alpha);
		text.draw(batch, alpha);

	}

}
