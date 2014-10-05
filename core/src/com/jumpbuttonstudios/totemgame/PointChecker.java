package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PointChecker extends Actor {
	private boolean checkingFirst = true;
	Rectangle onePointLeft, onePointRight;
	Rectangle twoPointLeft, twoPointRight;
	Rectangle threePoint;
	private Totem totem;
	Rectangle totemRect;
	Stage stage;
	float scoreX, scoreY;
	GameScreen game;

	public PointChecker(Totem totem, Stage stage, GameScreen game) {
		this.totem = totem;
		this.stage = stage;
		this.game = game;
		

		onePointLeft = new Rectangle(219 * Constants.SCALE, 94 * Constants.SCALE,
				85 * Constants.SCALE, 20 * Constants.SCALE);
		onePointRight = new Rectangle(599 * Constants.SCALE, 94 * Constants.SCALE,
				85 * Constants.SCALE, 20 * Constants.SCALE);
		twoPointLeft = new Rectangle(304 * Constants.SCALE, 91 * Constants.SCALE,
				78 * Constants.SCALE, 20 * Constants.SCALE);
		twoPointRight = new Rectangle(520 * Constants.SCALE, 91 * Constants.SCALE,
				78 * Constants.SCALE, 20 * Constants.SCALE);
		threePoint = new Rectangle(382 * Constants.SCALE, 92 * Constants.SCALE,
				138 * Constants.SCALE, 20 * Constants.SCALE);
		totemRect = new Rectangle();
	}

	public void setTotem(Totem totem) {
		this.totem = totem;
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (totem != null && checkingFirst) {
			totemRect.set(totem.getX() + totem.getWidth() / 2 - 0.01f / 2,
					totem.getY() - totem.getHeight() / 2, 0.01f, totem.getHeight());

			scoreX = totem.getX() + totem.getWidth();
			scoreY = totem.getY() - totem.getHeight() / 2;

			if (onePointLeft.overlaps(totemRect) || onePointRight.overlaps(totemRect)) {
				stage.addActor(Icons.getImage("ui/gameplay/+1.png"));
				Icons.getImage("ui/gameplay/+1.png").setPosition(scoreX, scoreY);
				Icons.getImage("ui/gameplay/+1.png").addAction(Actions.alpha(0, 0.5f));
				checkingFirst = false;
				game.score += 1;
			} 
			if (twoPointLeft.overlaps(totemRect) || twoPointRight.overlaps(totemRect)) {
				stage.addActor(Icons.getImage("ui/gameplay/+2.png"));
				Icons.getImage("ui/gameplay/+2.png").setPosition(scoreX, scoreY);
				Icons.getImage("ui/gameplay/+2.png").addAction(Actions.alpha(0, 0.5f));
				checkingFirst = false;
				game.score += 2;
			}
			if (threePoint.overlaps(totemRect)) {
				stage.addActor(Icons.getImage("ui/gameplay/+3.png"));
				Icons.getImage("ui/gameplay/+3.png").setPosition(scoreX, scoreY);
				Icons.getImage("ui/gameplay/+3.png").addAction(Actions.alpha(0, 0.5f));
				checkingFirst = false;
				game.score += 3;
			}
		} else {
			
		}

	}

	public void setCheckingFirst(boolean checking) {
		this.checkingFirst = checking;
	}

	public boolean isChecking() {
		return checkingFirst;
	}
}
