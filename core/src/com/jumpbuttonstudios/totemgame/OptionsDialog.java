package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OptionsDialog extends Dialog {
	Image panel;
	ImageButton x;
	CheckBox bgMusic, soundEffects, tapControl, tiltControl;

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

		bgMusic = new CheckBox("", checkBoxStyle);
		Label bgMusicLabel = new Label("Background Music", labelStyle);

		soundEffects = new CheckBox("", checkBoxStyle);
		Label soundEffectsLabel = new Label("Sound Effects", labelStyle);

		tapControl = new CheckBox("", checkBoxStyle);
		Label tapControlLabel = new Label("Tap Controls", labelStyle);

		tiltControl = new CheckBox("", checkBoxStyle);
		Label tiltControlLabel = new Label("Tilt Controls", labelStyle);

		ImageButtonStyle buttonStyle = new ImageButtonStyle();
		buttonStyle.up = Icons.getImage("ui/options/close.png").getDrawable();
		x = new ImageButton(buttonStyle);
		getContentTable().add(x).expandX().right().colspan(2).padTop(x.getHeight() / 2);
		getContentTable().row();
		
		setupListeners();

		getContentTable().add(bgMusic);
		getContentTable().add(bgMusicLabel).padBottom(9).left().height(font.getCapHeight());
		getContentTable().row();
		getContentTable().add(soundEffects);
		getContentTable().add(soundEffectsLabel).padBottom(9).left().height(font.getCapHeight());
		getContentTable().row();
		getContentTable().add(tapControl);
		getContentTable().add(tapControlLabel).padBottom(9).left().height(font.getCapHeight());
		getContentTable().row();
		getContentTable().add(tiltControl).padBottom(54);
		getContentTable().add(tiltControlLabel).padBottom(54).left().height(font.getCapHeight());
		getContentTable().row();

		pack();

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	public void setupListeners() {
		bgMusic.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("check");

			}

		});
		soundEffects.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("check");

			}

		});
		tapControl.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("check");

			}

		});
		tiltControl.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("check");

			}

		});
		x.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
				setVisible(false);
			}

		});
	}

}
