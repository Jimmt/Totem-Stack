package com.jumpbuttonstudio.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParticleEffectActor extends Actor {
	ParticleEffect effect;

	public ParticleEffectActor(String path, String dir) {
		effect = new ParticleEffect();
		effect.load(Gdx.files.internal(path), Gdx.files.internal(dir));
		
	}

	@Override
	public void act(float delta){
		super.act(delta);
		effect.update(delta);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		effect.draw(batch);
	
	}
}
