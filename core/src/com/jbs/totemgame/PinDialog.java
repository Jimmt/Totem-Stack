package com.jbs.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PinDialog extends Dialog {

	public PinDialog(final String score, final Skin skin, final GameScreen gs) {
		super("", skin);

		setBackground(new Image(Icons.getTex("ui/shop/window01.png")).getDrawable());
		setWidth(new Image(Icons.getTex("ui/shop/window01.png")).getWidth());
		setHeight(new Image(Icons.getTex("ui/shop/window01.png")).getHeight());

		ImageButtonStyle buttonStyle = new ImageButtonStyle();
		buttonStyle.up = new Image(Icons.getTex("login/ok.png")).getDrawable();
		buttonStyle.down = new Image(Icons.getTex("login/ok_pressed.png")).getDrawable();

		BitmapFont font = new BitmapFont(Gdx.files.internal("ui/highscore/font.fnt"));

		LabelStyle lStyle = new LabelStyle();
		lStyle.font = new BitmapFont(Gdx.files.internal("ui/highscore/font.fnt"));

		Label label = new Label("Enter PIN code: ", lStyle);

		TextFieldStyle textStyle = new TextFieldStyle();
		textStyle.font = font;
		textStyle.fontColor = Color.WHITE;
		textStyle.background = new Image(Icons.getTex("ui/gameover/textfield.png")).getDrawable();
		final TextField input = new TextField("", textStyle);

		getContentTable().add(label);
		getContentTable().row();
		getContentTable().add(input).width(new Image(Icons.getTex("ui/gameover/textfield.png")).getWidth())
				.height(new Image(Icons.getTex("ui/gameover/textfield.png")).getHeight());

		ImageButton ok = new ImageButton(buttonStyle);
		ok.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");

				TwitterUtil.setPin(input.getText());
				TwitterUtil.twitter.setOAuthAccessToken(TwitterUtil.accessToken);

				TweetedDialog tweeted = null;
				try {
					TwitterUtil.twitter.updateStatus("I just scored " + score
							+ " points in Totem Stack!");

					tweeted = new TweetedDialog("Your tweet has been posted!", skin);

				} catch (Exception e) {
					tweeted = new TweetedDialog(
							"Something went wrong - check your username, password, and PIN", skin);
					e.printStackTrace();
				}

				gs.hudStage.addActor(tweeted);
				tweeted.setPosition(Constants.WIDTH / 2 - tweeted.getWidth() / 2, Constants.HEIGHT
						/ 2 - tweeted.getHeight() / 2);
			}
		});

		button(ok).padBottom(30f);
	}
}
