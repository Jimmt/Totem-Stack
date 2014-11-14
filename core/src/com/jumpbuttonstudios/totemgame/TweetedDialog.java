package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TweetedDialog extends Dialog {

	public TweetedDialog(String text, Skin skin) {
		super("", skin);

		setBackground(Icons.getImage("ui/shop/window01.png").getDrawable());
		setWidth(Icons.getImage("ui/shop/window01.png").getWidth());
		setHeight(Icons.getImage("ui/shop/window01.png").getHeight());

		LabelStyle lStyle = new LabelStyle();
		lStyle.font = new BitmapFont(Gdx.files.internal("ui/highscore/font.fnt"));

		Label label = new Label(text, lStyle);

		getContentTable().add(label);
		
		ImageButtonStyle buttonStyle = new ImageButtonStyle();
		buttonStyle.up = Icons.getImage("login/ok.png").getDrawable();
		buttonStyle.down = Icons.getImage("login/ok_pressed.png").getDrawable();
		ImageButton ok = new ImageButton(buttonStyle);
		button(ok).padBottom(30f);
	}

}
