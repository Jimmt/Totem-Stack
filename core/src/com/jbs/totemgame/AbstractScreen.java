package com.jbs.totemgame;

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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class AbstractScreen implements Screen {
	private Table table;
	public World world;
	public Stage stage;
	public TotemGame game;
	protected Box2DDebugRenderer debugRenderer;
	protected OrthographicCamera camera;
	protected Skin skin;
	protected Stage hudStage;
	protected Image black;
	protected FitViewport viewport;
	protected StretchViewport hudViewport;
	protected TotemGameComparator comparator;
	protected static InputMultiplexer multiplexer;
	protected boolean paused;
	private float step = 1 / 60f;
	private float slowTime = 0, slowCap = 30;
	private boolean slowChanged;
	public PauseDialog pauseDialog;

	public AbstractScreen(TotemGame game) {
		this.game = game;

		comparator = new TotemGameComparator();

		debugRenderer = new Box2DDebugRenderer();
		world = new World(new Vector2(0, -4f), false);

		viewport = new FitViewport(Constants.SCLWIDTH, Constants.SCLHEIGHT);
		hudViewport = new StretchViewport(Constants.HUD_WIDTH, Constants.HUD_HEIGHT);
		stage = new Stage();
		hudStage = new Stage(hudViewport);

		camera = (OrthographicCamera) stage.getCamera();
		multiplexer = new InputMultiplexer(stage, hudStage);
		Gdx.input.setInputProcessor(multiplexer);

		black = new Image(Icons.getTex("black.png"));
		black.setSize(Constants.WIDTH, Constants.HEIGHT);
		black.setColor(black.getColor().r, black.getColor().g, black.getColor().b, 0.65f);
		stage.addActor(black);

		pauseDialog = new PauseDialog(getSkin(), this);
		pauseDialog.show(hudStage);
		pauseDialog.setVisible(false);
	}

	public void setStep(float step) {
		this.step = step;
		slowChanged = true;
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT
				| GL20.GL_DEPTH_BUFFER_BIT
				|
				// for android MSAA filtering
				(Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV
						: 0));

		stage.act(delta);
		hudStage.act(delta);
		stage.draw();
//		hudStage.draw();
		


// stage.getActors().sort(comparator);

// Table.drawDebug(hudStage);

		if (slowChanged) {
			if (slowTime > slowCap) {
				step = 1 / 60f;
				slowChanged = false;
				slowTime = 0;
			} else {
				slowTime += delta;
			}
		}

		if (!paused) {
			world.step(step, 5, 8);
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
		hudViewport.update(width, height);
		viewport.update(width, height);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		dispose();

	}

	public void pause(boolean showDialog) {
		paused = !paused;
		black.setVisible(paused);
		black.setPosition(camera.position.x - Constants.WIDTH / 2, camera.position.y
				- Constants.HEIGHT / 2);
		black.toFront();

		if (showDialog) {
			pauseDialog.setVisible(true);
			hudStage.getActors().removeValue(pauseDialog, false);
			hudStage.addActor(pauseDialog);

		}

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}
}