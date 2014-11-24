package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver.Resolution;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SplashScreen extends AbstractScreen {
	private AssetManager assetManager;
	private Image splashImage;
	private boolean startLoad;

	public SplashScreen(TotemGame game) {
		super(game);

		GamePrefs.initialize();

		if (JBSApi.api != null) {

		} else {

			CheckNetResponseListener responseListener = new CheckNetResponseListener();
			HttpRequest request = new HttpRequest(HttpMethods.GET);
			request.setUrl("http://google.com");
			Gdx.net.sendHttpRequest(request, responseListener);
		}

		splashImage = new Image(new Texture(Gdx.files.internal("ui/JBSLogo.png")));
		splashImage.setFillParent(true);

		hudStage.addActor(splashImage);

// splashImage.addAction(Actions.sequence(Actions.fadeIn(0.5f),
// Actions.delay(0.5f),
// Actions.fadeOut(0.5f)));

		Resolution[] resolutions = { new Resolution(320, 480, ".320480"),
				new Resolution(480, 800, ".480800"), new Resolution(480, 856, ".480854") };
		ResolutionFileResolver resolver = new ResolutionFileResolver(
				new InternalFileHandleResolver(), resolutions);
		assetManager = new AssetManager();
		assetManager.setLoader(Texture.class, new TextureLoader(resolver));
		Icons.assetManager = assetManager;

		for (int i = 0; i < Icons.paths.length; i++) {
			assetManager.load(Icons.paths[i], Texture.class);
		}
		load();
	}

	public void load() {
		Icons.loadIcons();
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		if (assetManager.update()) {
			game.setScreen(new MenuScreen(game));
		}
	}

}
