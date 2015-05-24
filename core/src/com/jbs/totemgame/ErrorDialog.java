package com.jbs.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ErrorDialog extends Dialog {

	public ErrorDialog(String title, Skin skin) {
		super(title, skin);

		setTransform(true);
		
		

		Image panel = new Image(new Texture(Gdx.files.internal("login/wrong.png")));
		setBackground(panel.getDrawable());

		ImageButtonStyle buttonStyle = new ImageButtonStyle();
		buttonStyle.up = new Image(new Texture(Gdx.files.internal("login/ok.png"))).getDrawable();
		buttonStyle.down = new Image(new Texture(Gdx.files.internal("login/ok_pressed.png"))).getDrawable();

		ImageButton ok = new ImageButton(buttonStyle);
		ok.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");

			}
		});
		
		
		button(ok).padBottom(30f);

		
		
		pack();
	}

}
