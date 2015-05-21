package com.jumpbuttonstudio.totemgame;

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
	boolean checkingFirst = true;
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
		
		float xRatio = Constants.WIDTH / 900;

		onePointLeft = new Rectangle(xRatio * 219 * Constants.SCALE, 94 * Constants.SCALE,
				85 * Constants.SCALE, 20 * Constants.SCALE);
		onePointRight = new Rectangle(xRatio * 599 * Constants.SCALE, 94 * Constants.SCALE,
				85 * Constants.SCALE, 20 * Constants.SCALE);
		twoPointLeft = new Rectangle(xRatio * 313 * Constants.SCALE, 91 * Constants.SCALE,
				78 * Constants.SCALE, 20 * Constants.SCALE);
		twoPointRight = new Rectangle(xRatio * 508 * Constants.SCALE, 91 * Constants.SCALE,
				78 * Constants.SCALE, 20 * Constants.SCALE);
		threePoint = new Rectangle(xRatio * 382 * Constants.SCALE, 92 * Constants.SCALE,
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
					totem.getY() - totem.getHeight() / 2, 0.1f, totem.getHeight());

			scoreX = totem.getX() + totem.getWidth();
			scoreY = totem.getY() - totem.getHeight() / 2;

			if (onePointLeft.overlaps(totemRect) || onePointRight.overlaps(totemRect)) {
				
				stage.addActor(Icons.getImage("ui/gameplay/+1.png"));
				Icons.normalPoints[0].setPosition(scoreX, scoreY);
				Icons.normalPoints[0].addAction(Actions.sequence(Actions.alpha(1, 0.5f), Actions.alpha(0, 0.5f)));
				checkingFirst = false;
				game.score += 1;
			} else
			if (twoPointLeft.overlaps(totemRect) || twoPointRight.overlaps(totemRect)) {
				stage.addActor(Icons.getImage("ui/gameplay/+2.png"));
				Icons.normalPoints[1].setPosition(scoreX, scoreY);
				Icons.normalPoints[1].addAction(Actions.sequence(Actions.alpha(1, 0.5f), Actions.alpha(0, 0.5f)));
				checkingFirst = false;
				game.score += 2;
			} else
			if (threePoint.overlaps(totemRect)) {
				stage.addActor(Icons.getImage("ui/gameplay/+3.png"));
				Icons.normalPoints[2].setPosition(scoreX, scoreY);
				Icons.normalPoints[2].addAction(Actions.sequence(Actions.alpha(1, 0.5f), Actions.alpha(0, 0.5f)));
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
