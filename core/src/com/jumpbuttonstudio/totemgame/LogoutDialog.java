package com.jumpbuttonstudio.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LogoutDialog extends Dialog {
	ImageButton yes;
	Image panel;

	public LogoutDialog(String title, Skin skin) {
		super(title, skin);
		setTransform(true);

		panel = new Image(new Texture(Gdx.files.internal("login/logoutwindow.png")));
		setBackground(panel.getDrawable());

		ImageButtonStyle yesStyle = new ImageButtonStyle();
		yesStyle.up = new Image(new Texture(Gdx.files.internal("login/yes.png"))).getDrawable();
		yesStyle.down = new Image(new Texture(Gdx.files.internal("login/yes_pressed.png"))).getDrawable();

		yes = new ImageButton(yesStyle);
		
		yes.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
				
			}
		});

		button(yes).padBottom(30f);
		
		pack();
	}

}
