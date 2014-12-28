package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class TweetedDialog extends Dialog {

	public TweetedDialog(String text, Skin skin) {
		super("", skin);

		setBackground(new Image(new Texture(Gdx.files.internal("ui/shop/window01.png"))).getDrawable());
		setWidth(new Image(new Texture(Gdx.files.internal("ui/shop/window01.png"))).getWidth());
		setHeight(new Image(new Texture(Gdx.files.internal("ui/shop/window01.png"))).getHeight());

		LabelStyle lStyle = new LabelStyle();
		lStyle.font = new BitmapFont(Gdx.files.internal("ui/highscore/font.fnt"));

		Label label = new Label(text, lStyle);
		label.setWrap(true);
		label.setAlignment(Align.center);

		getContentTable().add(label).width(getWidth());
		
		ImageButtonStyle buttonStyle = new ImageButtonStyle();
		buttonStyle.up = new Image(new Texture(Gdx.files.internal("login/ok.png"))).getDrawable();
		buttonStyle.down = new Image(new Texture(Gdx.files.internal("login/ok_pressed.png"))).getDrawable();
		ImageButton ok = new ImageButton(buttonStyle);
		button(ok).padBottom(30f);
	}

}