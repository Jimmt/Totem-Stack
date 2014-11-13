package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class CoinsDialog extends Dialog {
	CoinLabel coinLabel;

	public CoinsDialog(String text, Skin skin) {
		super(text, skin);
		
		Image panel = new Image(new Texture(Gdx.files.internal("blank.png")));
		setBackground(panel.getDrawable());
		coinLabel = new CoinLabel();
		setSize(305, coinLabel.coinImage.getHeight() + 7);
		
		
		
		getContentTable().add(coinLabel).expand().left().bottom();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		super.draw(batch, parentAlpha);

	}
}
