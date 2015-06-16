package com.jbs.totemgame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class GameOverSign extends Image {
	Array<Image> chains = new Array<Image>();
	Image panel;
	boolean startGen;

	public GameOverSign(Image panel) {
		super(Icons.getTex("gameover/gameover.png"));

		this.panel = panel;

			Image chain = new Image(Icons.getTex("gameover/chain.png"));
			chain.setPosition(panel.getX() + getX(), panel.getY() + getY() + getHeight());
			chains.add(chain);
		
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		for (Image image : chains) {
			image.act(delta);
		}

		for (int i = 0; i < chains.size; i++) {
			chains.get(i).setPosition(panel.getX() + getX(),
					panel.getY() + getY() + getHeight() + i * chains.get(0).getHeight());
		}

		if (!startGen) {
			if (getY() + getHeight() < panel.getY() + panel.getHeight()) {
				startGen = true;
			}
		}

		if (chains.get(chains.size - 1).getY() + chains.get(0).getHeight() < panel.getY()
						+ panel.getHeight()) {
			Image chain = new Image(Icons.getTex("gameover/chain.png"));
			chains.add(chain);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		for (Image image : chains) {
			image.draw(batch, parentAlpha);
		}
	}
}
