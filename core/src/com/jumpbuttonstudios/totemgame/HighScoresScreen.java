package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HighScoresScreen extends AbstractScreen {
	private int length = 10;
	private String[] globalNames = new String[length];
	private int[] globalScores = new int[length];
	private Label[] numberLabels = new Label[length];
	private Label[] scoreLabels = new Label[length];
	private ImageButton globalButton;
	private ImageButton personalButton;
	private ImageButton friendsButton;

	public HighScoresScreen(TotemGame game) {
		super(game);

	}

	@Override
	public void show() {
		super.show();
		Table table = super.getTable();
		table.setFillParent(true);

		HighScores.setScoreLength(length);

		getGlobalHighscores();

		ImageButtonStyle gStyle = new ImageButtonStyle();
		gStyle.up = Icons.getImage("ui/highscore/global.png").getDrawable();
		gStyle.down = Icons.getImage("ui/highscore/globaldisable.png").getDrawable();
		gStyle.checked = Icons.getImage("ui/highscore/globaldisable.png").getDrawable();
		
		
		globalButton = new ImageButton(gStyle);
		globalButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
				personalButton.setChecked(false);
				friendsButton.setChecked(false);
				getGlobalHighscores();
				for (int i = 0; i < length; i++) {
					scoreLabels[i].setText(globalNames[i] + " - " + globalScores[i]);
				}
			}

		});
		

		HighScores.read();
		
		ImageButtonStyle pStyle = new ImageButtonStyle();
		pStyle.up = Icons.getImage("ui/highscore/personal.png").getDrawable();
		pStyle.down = Icons.getImage("ui/highscore/personaldisable.png").getDrawable();
		pStyle.checked = Icons.getImage("ui/highscore/personaldisable.png").getDrawable();
		
		personalButton = new ImageButton(pStyle);
		personalButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
				globalButton.setChecked(false);
				friendsButton.setChecked(false);
				for (int i = 0; i < length; i++) {
					scoreLabels[i].setText(String.valueOf(i + 1) + ". " + HighScores.scores[i]);
				}
			}

		});
		personalButton.setChecked(true);
		
		ImageButtonStyle fStyle = new ImageButtonStyle();
		fStyle.up = Icons.getImage("ui/highscore/friends.png").getDrawable();
		fStyle.down = Icons.getImage("ui/highscore/friendsdisable.png").getDrawable();
		fStyle.checked = Icons.getImage("ui/highscore/friendsdisable.png").getDrawable();
		
		friendsButton = new ImageButton(fStyle);
		friendsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
				globalButton.setChecked(false);
				personalButton.setChecked(false);
			}

		});
		
		ImageButtonStyle bStyle = new ImageButtonStyle();
		bStyle.up = Icons.getImage("ui/highscore/back.png").getDrawable();
		bStyle.down = Icons.getImage("ui/highscore/back_pressed.png").getDrawable();
		
		
		ImageButton backButton = new ImageButton(bStyle);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MenuScreen(game));
			}
		});
		
		Image panel = Icons.getImage("ui/highscore/window.png");
		panel.setScale(1);
		panel.setSize(Constants.WIDTH, Constants.HEIGHT);
	    hudStage.addActor(panel);
		table.add(personalButton).width(Constants.WIDTH / 3);
		table.add(globalButton).width(Constants.WIDTH / 3);
		table.add(friendsButton).width(Constants.WIDTH / 3); 	
		table.row();

		BitmapFont font = new BitmapFont(Gdx.files.internal("ui/highscore/font.fnt"));
		
		for (int i = 0; i < length; i++) {
			table.row();
			LabelStyle style = new LabelStyle();
			style.font = font;
			style.fontColor = Color.WHITE;
			numberLabels[i] = new Label(String.valueOf(i + 1) + ".", style);
			scoreLabels[i] = new Label(String.valueOf(HighScores.scores[i]), style);
			table.add(numberLabels[i]).padTop(5f).left().padLeft(5f);
			table.add(scoreLabels[i]).padTop(5f).colspan(2).expandX().right().padRight(5f);
		}
		table.row();
		table.add(backButton).colspan(3);
		
		table.debug();

	}

	public void getGlobalHighscores() {
		
		for (int i = 0; i < length; i++) {
			if (i < JBSApi.api.getGlobalHighscores().size()) {
				globalNames[i] = JBSApi.api.getGlobalHighscores().get(i).username;
				globalScores[i] = JBSApi.api.getGlobalHighscores().get(i).score;
			} else {
				globalNames[i] = "none";
				globalScores[i] = 0;
			}
		}

	}

	@Override
	public void render(float delta) {
		super.render(delta);
		
		hudStage.act(delta);
		stage.act(delta);
		hudStage.draw();
		stage.draw();
		
		Table.drawDebug(stage);
	}

}
