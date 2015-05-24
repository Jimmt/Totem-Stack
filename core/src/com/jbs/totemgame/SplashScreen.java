package com.jbs.totemgame;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;

public class SplashScreen extends AbstractScreen {
	Image splashImage;

	public SplashScreen(final TotemGame game) {
		super(game);
		
		Action switchScreenAction = new Action() {

			@Override
			public boolean act(float delta) {
				game.setScreen(new MenuScreen(game));
				return true;
			}

		};

		splashImage = new Image(Icons.getTex("ui/JBSLogo.png"));
		splashImage.setSize(Constants.WIDTH, Constants.HEIGHT);
		splashImage.setScaling(Scaling.fit);
		
		hudStage.addActor(splashImage);
		splashImage.setColor(1, 1, 1, 0);
		splashImage.addAction(Actions.sequence(Actions.fadeIn(0.6f), Actions.delay(0.7f),
				Actions.fadeOut(0.3f), switchScreenAction));
	}
	
	@Override
	public void render(float delta){
		super.render(delta);
		
		hudStage.draw();
	}

}
