package com.jumpbuttonstudios.totemgame;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jumpbuttonstudio.Friend;
import com.jumpbuttonstudio.HighscoreResult;

public class HighScoresScreen extends AbstractScreen {
	private int length = 15;
	private String[] globalNames = new String[length];
	private int[] globalScores = new int[length];
	private Label[] numberLabels = new Label[length];
	private Label[] nameLabels = new Label[length];
	private Label[] scoreLabels = new Label[length];
	private Image[] avatars = new Image[length];
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

		Table scrollTable = new Table();
//		scrollTable.setFillParent(true);

		HighScores.setScoreLength(length);

		ImageButtonStyle gStyle = new ImageButtonStyle();
		gStyle.up = new Image(new Texture(Gdx.files.internal("ui/highscore/global.png"))).getDrawable();
		gStyle.down = new Image(new Texture(Gdx.files.internal("ui/highscore/globaldisable.png"))).getDrawable();
		gStyle.disabled = new Image(new Texture(Gdx.files.internal("ui/highscore/globaldisable.png"))).getDrawable();

		globalButton = new ImageButton(gStyle);
		globalButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
				getGlobalHighscores();
				for (int i = 0; i < length; i++) {
					numberLabels[i].setText(String.valueOf(i + 1) + ". ");
					scoreLabels[i].setText(String.valueOf(globalScores[i]));
					avatars[i].setVisible(true);
					nameLabels[i].setText(globalNames[i]);
				}
			}

		});

		HighScores.read();

		ImageButtonStyle pStyle = new ImageButtonStyle();
		pStyle.up = new Image(new Texture(Gdx.files.internal("ui/highscore/personal.png"))).getDrawable();
		pStyle.down = new Image(new Texture(Gdx.files.internal("ui/highscore/personaldisable.png"))).getDrawable();

		personalButton = new ImageButton(pStyle);
		personalButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
				HighScores.read();
				for (int i = 0; i < length; i++) {
					numberLabels[i].setText(String.valueOf(i + 1) + ".");
					scoreLabels[i].setText(String.valueOf(HighScores.scores[i]));
					nameLabels[i].setText("");
					avatars[i].setVisible(false);

				}
			}

		});
// personalButton.setChecked(true);

		ImageButtonStyle fStyle = new ImageButtonStyle();
		fStyle.up = new Image(new Texture(Gdx.files.internal("ui/highscore/friends.png"))).getDrawable();
		fStyle.down = new Image(new Texture(Gdx.files.internal("ui/highscore/friendsdisable.png"))).getDrawable();
		fStyle.disabled = new Image(new Texture(Gdx.files.internal("ui/highscore/friendsdisable.png"))).getDrawable();

		friendsButton = new ImageButton(fStyle);
		friendsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
				getFriendsHighscores();
			}

		});

		ImageButtonStyle bStyle = new ImageButtonStyle();
		bStyle.up = new Image(new Texture(Gdx.files.internal("ui/highscore/back.png"))).getDrawable();
		bStyle.down = new Image(new Texture(Gdx.files.internal("ui/highscore/back_pressed.png"))).getDrawable();

		ImageButton backButton = new ImageButton(bStyle);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MenuScreen(game));
			}
		});

		Image panel = new Image(new Texture(Gdx.files.internal("ui/highscore/window.png")));
		panel.setScale(1);
		panel.setSize(Constants.WIDTH, Constants.HEIGHT);
		table.setBackground(panel.getDrawable());
		table.add(personalButton).width(Constants.WIDTH / 3).colspan(3);
		table.add(globalButton).width(Constants.WIDTH / 3).colspan(1);
		table.add(friendsButton).width(Constants.WIDTH / 3).colspan(1);
		table.row();

		if(JBSApi.api != null){
			
		
		if (!(JBSApi.api.isConnected() && JBSApi.api.isAuthenticated())) {
			globalButton.setDisabled(true);
			friendsButton.setDisabled(true);
		}
		if (!JBSApi.loggedIn) {
			friendsButton.setDisabled(true);
		}
		}

		BitmapFont font = new BitmapFont(Gdx.files.internal("ui/highscore/font.fnt"));
//		font.getData().getGlyph('.').yoffset = 30;

		for (int i = 0; i < length; i++) {
			scrollTable.row();
			LabelStyle style = new LabelStyle();
			style.font = font;
			style.fontColor = Color.WHITE;
			numberLabels[i] = new Label(String.valueOf(i + 1) + ".", style);
			scoreLabels[i] = new Label(String.valueOf(HighScores.scores[i]), style);
			nameLabels[i] = new Label("", style);
			avatars[i] = new Image();
			scrollTable.add(numberLabels[i]).padTop(5f).left().padLeft(5f);
			scrollTable.add(avatars[i]).padTop(5f).left().width(40).height(40).padTop(5f).bottom();
			scrollTable.add(nameLabels[i]).padTop(5f).left().padLeft(15f);
			scrollTable.add(scoreLabels[i]).padTop(5f).colspan(2).expandX().right().padRight(25f);
		}

//		table.debug();
//		scrollTable.debug();

		ScrollPane scrollPane = new ScrollPane(scrollTable, getSkin());
		scrollPane.setScrollingDisabled(true, false);
		scrollPane.setFadeScrollBars(false);
		super.getTable().add(scrollPane).colspan(6).fillX();

		table.row();

		table.add(backButton).colspan(7);
	}

	public void getFriendsHighscores() {
		if (JBSApi.api.isConnected() && JBSApi.api.isAuthenticated() && JBSApi.loggedIn) {

			ArrayList<Friend> friends = JBSApi.api.getFriends();

			for (int i = 0; i < length; i++) {
				if (i < friends.size() - 1) {
					nameLabels[i].setText(friends.get(i).username);
					scoreLabels[i].setText(String.valueOf(JBSApi.api.getHighscore(JBSApi.api
							.getFriends().get(i).user_id)));

					if (avatars[i] != null) {
						avatars[i].setVisible(true);
						Image image = ImageDownloader.downloadImage(friends.get(i).avatar_url);
						image.setSize(100f, 100f);
						avatars[i].setDrawable(image.getDrawable());
					}
				} else {
					nameLabels[i].setText("none");
					scoreLabels[i].setText("0");
					if (avatars[i] != null) {
						avatars[i].setVisible(false);
					}
				}
			}

		}
	}

	public void getGlobalHighscores() {

		if (JBSApi.api.isConnected() && JBSApi.api.isAuthenticated()) {
			ArrayList<HighscoreResult> scores = JBSApi.api.getGlobalHighscores();

			for (int i = 0; i < length; i++) {
				if (i < scores.size()) {
					globalNames[i] = scores.get(i).username;
					globalScores[i] = scores.get(i).score;

					if (avatars[i] != null) {
						Image image = ImageDownloader.downloadImage(JBSApi.api.getUserAvatar(scores
								.get(i).user_id));
						avatars[i].setDrawable(image.getDrawable());
					}
				} else {
					globalNames[i] = "none";
					globalScores[i] = 0;
				}
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
