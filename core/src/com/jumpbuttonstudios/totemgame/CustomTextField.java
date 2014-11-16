package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class CustomTextField extends TextField {

	public CustomTextField(String text, TextFieldStyle style) {
		super(text, style);
		setStyle(style);
		setSize(getPrefWidth(), getPrefHeight());
	}

	@Override
	public void setStyle(TextFieldStyle style) {
		super.setStyle(style);

		textHeight = 200;
	}

}
