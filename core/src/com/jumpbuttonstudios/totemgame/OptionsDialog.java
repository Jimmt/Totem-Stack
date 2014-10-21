package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class OptionsDialog extends Dialog {
	Image panel;

	public OptionsDialog(String title, Skin skin) {
		super(title, skin);

		debug();

		setTransform(true);

		BitmapFont font = new BitmapFont(Gdx.files.internal("ui/highscore/font.fnt"));

		panel = new Image(new Texture(Gdx.files.internal("ui/options/windowblank.png")));
		setBackground(panel.getDrawable());

		CheckBoxStyle checkBoxStyle = new CheckBoxStyle();
		checkBoxStyle.checkboxOn = Icons.getImage("ui/options/checkbg.png").getDrawable();
		checkBoxStyle.checkboxOff = Icons.getImage("ui/options/xbg.png").getDrawable();
		checkBoxStyle.font = font;

		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = font;
		

		CheckBox bgMusic = new CheckBox("", checkBoxStyle);
		Label bgMusicLabel = new Label("Background Music", labelStyle);

		CheckBox soundEffects = new CheckBox("", checkBoxStyle);
		Label soundEffectsLabel = new Label("Sound Effects", labelStyle);

		CheckBox tapControl = new CheckBox("", checkBoxStyle);
		Label tapControlLabel = new Label("Tap Controls", labelStyle);

		CheckBox tiltControl = new CheckBox("", checkBoxStyle);
		Label tiltControlLabel = new Label("Tilt Controls", labelStyle);

		getContentTable().add(bgMusic).padTop(30f);
		getContentTable().add(bgMusicLabel).padTop(30f).padBottom(9).left().height(font.getCapHeight());
		getContentTable().row();
		getContentTable().add(soundEffects);
		getContentTable().add(soundEffectsLabel).padBottom(9).left().height(font.getCapHeight());
		getContentTable().row();
		getContentTable().add(tapControl);
		getContentTable().add(tapControlLabel).padBottom(9).left().height(font.getCapHeight());
		getContentTable().row();
		getContentTable().add(tiltControl);
		getContentTable().add(tiltControlLabel).padBottom(9).left().height(font.getCapHeight());
		getContentTable().row();
		
				pack();

	}

}
