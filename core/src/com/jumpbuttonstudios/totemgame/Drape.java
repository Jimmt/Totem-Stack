package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Drape extends ImageButton {
	Image powerup;
	BitmapFont font;
	int count = 0;
	TotemSpawner spawner;
	String type;

	public Drape(String path) {
		super(new Image(new Texture(Gdx.files.internal("drape.png"))).getDrawable());
		
		type = path;
		
		powerup = new Image(new Texture("ui/shop/" + path + ".png"));
		powerup.setSize(powerup.getWidth() / 2, powerup.getHeight() / 2);
		
		font = new BitmapFont(Gdx.files.internal("ui/shop/numbers.fnt"));
		font.setScale(0.75f);
	}
	
	public String getType(){
		return type;
	}
	
	public void setTotemSpawner(TotemSpawner spawner){
		this.spawner = spawner;
	}
	
	public void setCount(int count){
		this.count = count;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		powerup.draw(batch, parentAlpha);
		powerup.setPosition(getX() + getWidth() / 2 - powerup.getWidth() / 2, getY() + getHeight()
				/ 5 * 3 - powerup.getHeight() / 2);
		
		font.draw(batch, String.valueOf(count), powerup.getX() + powerup.getWidth() / 4 * 3, powerup.getY() + font.getCapHeight());

	}

}
