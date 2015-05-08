package com.jumpbuttonstudio.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OptionsDialog extends Dialog {
	Image panel;
	ImageButton x;
	CheckBox bgMusic, soundEffects, tapControl, tiltControl;
	ShapeRenderer sr;
	AbstractScreen gs;
	Image tilt, tap;
	Stage xStage;
	Label tiltControlLabel, tapControlLabel, bgMusicLabel;

	public OptionsDialog(String title, Skin skin, AbstractScreen gs) {
		super(title, skin);

		this.gs = gs;

		sr = new ShapeRenderer();

		debug();

		setTransform(true);

		BitmapFont font = new BitmapFont(Gdx.files.internal("ui/highscore/font.fnt"));

		panel = new Image(Icons.getTex("ui/options/windowblank.png"));
		setBackground(panel.getDrawable());

		CheckBoxStyle checkBoxStyle = new CheckBoxStyle();
		checkBoxStyle.checkboxOn = new Image(new Texture(
				Gdx.files.internal("ui/options/checkbg.png"))).getDrawable();
		checkBoxStyle.checkboxOff = new Image(Icons.getTex("ui/options/xbg.png")).getDrawable();
		checkBoxStyle.font = font;

		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = font;

		bgMusic = new CheckBox("", checkBoxStyle);
		bgMusicLabel = new Label("Background Music", labelStyle);
		bgMusicLabel.pack();
		soundEffects = new CheckBox("", checkBoxStyle);
		Label soundEffectsLabel = new Label("Sound Effects", labelStyle);
		soundEffectsLabel.pack();
		tapControl = new CheckBox("", checkBoxStyle);
		tapControlLabel = new Label("Tap Controls", labelStyle);
		tapControlLabel.pack();
		tiltControl = new CheckBox("", checkBoxStyle);
		tiltControlLabel = new Label("Tilt Controls", labelStyle);
		tiltControlLabel.pack();
		bgMusic.setChecked(GamePrefs.prefs.getBoolean("bgMusic"));
		soundEffects.setChecked(GamePrefs.prefs.getBoolean("soundEffects"));
		tapControl.setChecked(GamePrefs.prefs.getBoolean("tap"));
		tiltControl.setChecked(!GamePrefs.prefs.getBoolean("tap"));

		ImageButtonStyle buttonStyle = new ImageButtonStyle();
		Texture closeTex = Icons.getTex("ui/options/close.png");
		closeTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		buttonStyle.up = new Image(closeTex).getDrawable();
		x = new ImageButton(buttonStyle);
		xStage = new Stage();
		xStage.addActor(x);
		setupListeners();

		tap = new Image(Icons.getTex("ui/options/tap.png"));
		tap.setScale(1f);
		tap.setOrigin(tap.getWidth() / 2, tap.getHeight() / 2);
		tilt = new Image(Icons.getTex("ui/options/tilt.png"));
		tilt.setScale(1);
		tilt.setOrigin(tilt.getWidth() / 2, tilt.getHeight() / 2);
		getContentTable().align(Align.left).padLeft(30f);
		getContentTable().add(bgMusic).padTop(Constants.HEIGHT / 8).left();
		getContentTable().add(bgMusicLabel).padTop(Constants.HEIGHT / 8).padBottom(9).left()
				.height(font.getCapHeight());
		getContentTable().row();
		getContentTable().add(soundEffects).left();
		getContentTable().add(soundEffectsLabel).padBottom(9).left().height(font.getCapHeight());
		getContentTable().row();
		getContentTable().add(tapControl).left();
		getContentTable().add(tapControlLabel).padBottom(9).left().height(font.getCapHeight());
		getContentTable().row();
		getContentTable().add(tiltControl).padBottom(54).left();
		getContentTable().add(tiltControlLabel).padBottom(54).left().height(font.getCapHeight())
				.width(tiltControlLabel.getWidth());
		getContentTable().row();
		getContentTable().debug();

		pack();

		xStage.addActor(tap);
		xStage.addActor(tilt);

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		x.draw(batch, parentAlpha);
		tap.draw(batch, parentAlpha);
		tilt.draw(batch, parentAlpha);
// Table.drawDebug(gs.hudStage);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		tap.setPosition(getX() + tapControlLabel.getX() + tapControlLabel.getWidth() + 10, getY()
				+ tapControlLabel.getY());
		tilt.setPosition(getX() + tiltControlLabel.getX() + tiltControlLabel.getWidth() + 10,
				getY() + tiltControlLabel.getY());

		x.setPosition(getX() + panel.getWidth() - x.getWidth() / 5 * 4,
				getY() + panel.getHeight() - x.getHeight() / 5 * 4);

		xStage.act(delta);

		if (tilt.getActions().size == 0) {
			tilt.addAction(Actions.sequence(Actions.rotateBy(45, 0.5f),
					Actions.rotateBy(-90, 1.0f), Actions.rotateBy(45, 0.5f)));
		}
		if (tap.getActions().size == 0) {
			tap.addAction(Actions.sequence(Actions.delay(1.0f), Actions.scaleTo(0.5f, 0.5f, 0.5f),
					Actions.scaleTo(1.5f, 1.5f, 0.5f)));
		}

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
				TotemGame.soundManager.setPlayMusic(bgMusic.isChecked());
			}

		});
		soundEffects.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("check");
				GamePrefs.putBoolean("soundEffects", soundEffects.isChecked());
				TotemGame.soundManager.setPlayEffects(soundEffects.isChecked());
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
					gs.pause(false);
				}
			}

		});
	}

}
