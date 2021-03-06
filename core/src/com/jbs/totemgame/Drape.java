package com.jbs.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Drape extends ImageButton {
	Image powerup;
	BitmapFont font;
	int count = 0;
	TotemSpawner spawner;
	String type;
	Label text;

	public Drape(String path) {
		super(new Image(Icons.getTex("ui/top/itembanner.png")).getDrawable());
		
		type = path;
		
		powerup = new Image(Icons.getTex("ui/shop/" + path + ".png"));
		powerup.setSize(powerup.getWidth() * 0.9f, powerup.getHeight() * 0.9f);
		
		font = new BitmapFont(Gdx.files.internal("ui/shop/numbers.fnt"));
//		font.getData().setScale(0.75f);
		
		LabelStyle style = new LabelStyle();
		style.font = font;
		text = new Label("", style);
		
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
	public void act(float delta){
		super.act(delta);

		
		text.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		powerup.setPosition(getX() + getWidth() / 2 - powerup.getWidth() / 2, getY() + getHeight()
				/ 5 * 3 - powerup.getHeight() / 2);
		powerup.draw(batch, parentAlpha);
		
		
		
		text.setText(String.valueOf(count));
		text.pack();
		text.setPosition(getX() + getWidth() / 2 - text.getWidth() / 2, getY());
		
		text.draw(batch, parentAlpha);

	}

}
