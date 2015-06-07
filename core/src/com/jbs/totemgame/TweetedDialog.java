package com.jbs.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TweetedDialog extends Dialog {

	public TweetedDialog(String text, Skin skin) {
		super("", skin);

		setBackground(new Image(Icons.getTex("ui/shop/window01.png")).getDrawable());
		setWidth(new Image(Icons.getTex("ui/shop/window01.png")).getWidth());
		setHeight(new Image(Icons.getTex("ui/shop/window01.png")).getHeight());

		LabelStyle lStyle = new LabelStyle();
		lStyle.font = new BitmapFont(Gdx.files.internal("ui/highscore/font.fnt"));

		Label label = new Label(text, lStyle);
		label.setWrap(true);
		label.setAlignment(Align.center);

		getContentTable().add(label).width(getWidth());

		ImageButtonStyle buttonStyle = new ImageButtonStyle();
		buttonStyle.up = new Image(Icons.getTex("login/ok.png")).getDrawable();
		buttonStyle.down = new Image(Icons.getTex("login/ok_pressed.png")).getDrawable();
		ImageButton ok = new ImageButton(buttonStyle);
		button(ok).padBottom(30f);
	}

}
