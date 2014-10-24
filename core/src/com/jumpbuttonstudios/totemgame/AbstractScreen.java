package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class AbstractScreen implements Screen {
	private Table table;
	public World world;
	public Stage stage;
	protected TotemGame game;
	protected Box2DDebugRenderer debugRenderer;
	protected OrthographicCamera camera;
	protected Skin skin;
	protected Stage hudStage;
	protected TotemGameComparator comparator;
	protected static InputMultiplexer multiplexer;
	protected boolean paused;

	public AbstractScreen(TotemGame game) {
		this.game = game;

		comparator = new TotemGameComparator();

		debugRenderer = new Box2DDebugRenderer();
		world = new World(new Vector2(0, -4f), false);
		stage = new Stage();
		hudStage = new Stage();

		camera = (OrthographicCamera) stage.getCamera();
		multiplexer = new InputMultiplexer(stage, hudStage);
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		stage.act(delta);
		hudStage.act(delta);
		stage.draw();
		hudStage.draw();

// stage.getActors().sort(comparator);

// Table.drawDebug(hudStage);

		if (!paused) {
			world.step(1f / 60f, 5, 8);
		}

// debugRenderer.render(world, stage.getCamera().combined);

	}

	protected Skin getSkin() {
		if (skin == null) {
			FileHandle skinFile = Gdx.files.internal("skin/uiskin.json");
			skin = new Skin(skinFile);
		}
		return skin;
	}

	protected Table getTable() {
		if (table == null) {
			table = new Table();
			table.setFillParent(true);

			stage.addActor(table);
		}
		return this.table;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		dispose();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}
