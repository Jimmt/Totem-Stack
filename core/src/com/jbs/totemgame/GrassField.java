package com.jbs.totemgame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class GrassField extends Actor {
	Array<Image> blades;

	public GrassField(boolean scaled) {
		blades = new Array<Image>();

		int num = 4 + (int) (Math.random() * 5);

		for (int i = 0; i < num; i++) {
			String path = (int) (Math.random() * 2) == 0 ? "grass/grass00.png"
					: "grass/grass01.png";

			Image grassBlade = new Image(Icons.getTex(path));
			if (scaled) {
				grassBlade.setSize(grassBlade.getWidth() * Constants.SCALE, grassBlade.getHeight()
						* Constants.SCALE);
			}

			float width = Constants.WIDTH;
			if (scaled) {
				width = Constants.SCLWIDTH;
			}
			grassBlade.setPosition(
					MathUtils.random(grassBlade.getWidth(),
							width - grassBlade.getWidth()),
					-0.5f * grassBlade.getHeight() - (float) Math.random() * grassBlade.getHeight()
							* 0.1f);
			blades.add(grassBlade);
			grassBlade.setOrigin(getWidth() / 2, 0);
			grassBlade.addAction(Actions.rotateBy(MathUtils.random(-20, 20)));
			grassBlade.setScaleX((int) (Math.random() * 2) == 0 ? -1 : 1);
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if(getStage().getCamera().position.y <= Constants.SCLHEIGHT / 2){
		for (Image blade : blades) {
			if (blade.getActions().size == 0) {
				int mag = MathUtils.random(0, 10);
				blade.addAction(Actions.sequence(Actions.rotateBy(-mag, MathUtils.random(1f, 3f)),
						Actions.rotateBy(mag, MathUtils.random(1f, 3f))));
			}
			blade.setPosition(blade.getX(), blade.getY());
			blade.act(delta);
		}
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		for (Image blade : blades) {
			blade.draw(batch, parentAlpha);
		}
	}
}
