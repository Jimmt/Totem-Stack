package com.jbs.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PauseDialog extends Dialog {

	public PauseDialog(Skin skin, final AbstractScreen gs) {
		super("", skin);

		setTransform(true);
		
		Image window = new Image(new Texture(Gdx.files.internal("ui/paused/resumewindow.png")));
		Image blank = new Image(new Texture(Gdx.files.internal("blank.png")));
		background(blank.getDrawable());
		setSize(window.getWidth(), 300);

		ImageButtonStyle removeAdStyle = new ImageButtonStyle();
		removeAdStyle.up = new Image(new Texture(Gdx.files.internal("ui/paused/removead.png")))
				.getDrawable();
		removeAdStyle.down = new Image(new Texture(Gdx.files.internal("ui/paused/removead.png")))
				.getDrawable();
		ImageButton removeAds = new ImageButton(removeAdStyle);
		removeAds.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				TotemGame.soundManager.play("button");
				TotemGame.services.removeAds();
			}
		});
		
		ImageButtonStyle style = new ImageButtonStyle();
		style.up = new Image(new Texture(Gdx.files.internal("ui/paused/resumebutton.png")))
				.getDrawable();
		style.down = new Image(new Texture(Gdx.files.internal("ui/paused/resume_pressed.png")))
				.getDrawable();
		ImageButton resume = new ImageButton(style);
		resume.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
				gs.pause(false);
				setVisible(false);
			}
		});
		
		Group group = new Group();
		group.addActor(window);
		group.addActor(resume);
		resume.setPosition(window.getWidth() / 2 - resume.getWidth() / 2, window.getHeight() / 2 - resume.getHeight() / 2);

		getContentTable().add(group).width(window.getWidth()).height(window.getHeight());
		getContentTable().row();
		getContentTable().add(removeAds);

	}

}
