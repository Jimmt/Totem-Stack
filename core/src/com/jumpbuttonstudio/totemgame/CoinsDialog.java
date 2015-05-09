package com.jumpbuttonstudio.totemgame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class CoinsDialog extends Dialog {
	CoinLabel coinLabel;

	public CoinsDialog(String text, Skin skin, Stage hudStage) {
		super(text, skin);

		Image panel = new Image(Icons.getTex("blank.png"));
		setBackground(panel.getDrawable());
		coinLabel = new CoinLabel(skin, hudStage);
		coinLabel.setPosition(0, 0);
		hudStage.addActor(coinLabel);
		//

// getContentTable().add(coinLabel).expand().left().bottom();
	}

	@Override
	public void act(float delta) {
		super.act(delta);
//		coinLabel.act(delta);
		coinLabel.setPosition(getX(),getY());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
//		coinLabel.setPosition(getX(), getY());
//		coinLabel.draw(batch, parentAlpha);
	}
}
