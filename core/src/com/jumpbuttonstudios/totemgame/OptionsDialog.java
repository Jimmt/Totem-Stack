package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OptionsDialog extends Dialog {
	Image panel;
	ImageButton x;
	CheckBox bgMusic, soundEffects, tapControl, tiltControl;
	ShapeRenderer sr;
	GameScreen gs;

	public OptionsDialog(String title, Skin skin, GameScreen gs) {
		super(title, skin);

		this.gs = gs;

		sr = new ShapeRenderer();

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

		bgMusic.setChecked(GamePrefs.prefs.getBoolean("bgMusic"));
		soundEffects.setChecked(GamePrefs.prefs.getBoolean("soundEffects"));
		tapControl.setChecked(GamePrefs.prefs.getBoolean("tap"));
		tiltControl.setChecked(!GamePrefs.prefs.getBoolean("tap"));

		ImageButtonStyle buttonStyle = new ImageButtonStyle();
		buttonStyle.up = Icons.getImage("ui/options/close.png").getDrawable();
		x = new ImageButton(buttonStyle);
		getContentTable().add(x).expandX().right().colspan(3).padTop(x.getHeight() / 2);
		getContentTable().row();

		setupListeners();

		Image tap = Icons.getImage("ui/options/tap.png");
		tap.setScale(1);
		Image tilt = Icons.getImage("ui/options/tilt.png");
		tilt.setScale(1);

		getContentTable().add(bgMusic);
		getContentTable().add(bgMusicLabel).padBottom(9).left().height(font.getCapHeight());
		getContentTable().row();
		getContentTable().add(soundEffects);
		getContentTable().add(soundEffectsLabel).padBottom(9).left().height(font.getCapHeight());
		getContentTable().row();
		getContentTable().add(tapControl);
		getContentTable().add(tapControlLabel).padBottom(9).left().height(font.getCapHeight());
		getContentTable().add(tap).padBottom(9);
		getContentTable().row();
		getContentTable().add(tiltControl).padBottom(54);
		getContentTable().add(tiltControlLabel).padBottom(54).left().height(font.getCapHeight());
		getContentTable().add(tilt).padBottom(54);
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

// sr.setProjectionMatrix(getStage().getCamera().combined);
// sr.begin(ShapeType.Line);
// for (int i = 0; i < this.getContentTable().getCells().size; i++) {
// sr.box(getX() + getContentTable().getCells().get(i).getActorX(), getY()
// + getContentTable().getY() + getContentTable().getCells().get(i).getActorY(),
// 0, getContentTable().getCells().get(i).getActorWidth(), getContentTable()
// .getCells().get(i).getActorHeight(), 0);
// }
// sr.end();
	}

	public void setupListeners() {
		bgMusic.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("check");
				GamePrefs.putBoolean("bgMusic", bgMusic.isChecked());
			}

		});
		soundEffects.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("check");
				GamePrefs.putBoolean("soundEffects", soundEffects.isChecked());
			}

		});
		tapControl.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("check");
				GamePrefs.putBoolean("tap", tapControl.isChecked());
				tiltControl.setChecked(!tapControl.isChecked());
			}

		});
		tiltControl.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("check");
				GamePrefs.putBoolean("tap", !tiltControl.isChecked());
				tapControl.setChecked(!tiltControl.isChecked());
			}

		});
		x.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
				setVisible(false);
				if (gs != null) {
					gs.pause();
				}
			}

		});
	}

}
