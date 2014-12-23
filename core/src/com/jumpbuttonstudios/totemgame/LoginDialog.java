package com.jumpbuttonstudios.totemgame;

import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LoginDialog extends Dialog {
	ShapeRenderer sr;
	TextField username, password;
	ImageButton xb;
	BitmapFont font;

	public LoginDialog(String title, final Skin skin) {
		super(title, skin);

		setTransform(true);

		Tween.registerAccessor(Image.class, new ImageAccessor());

		font = new BitmapFont(Gdx.files.internal("ui/highscore/font.fnt")); 
//		font = new BitmapFont(Gdx.files.internal("login/font.fnt")); 
		Image panel = new Image(new Texture(Gdx.files.internal("login/window.png")));

		font.getData().getGlyph('.').yoffset = -10;
		
		Tween.to(panel, 0, 1.0f).target(0, 0).start();

		setBackground(panel.getDrawable());

		TextFieldStyle textStyle = new TextFieldStyle();
		textStyle.font = font;
		textStyle.fontColor = Color.WHITE;

//		username = new CustomTextField("", textStyle);
//		password = new CustomTextField("", textStyle);
		username = new TextField("", textStyle);
		password = new TextField("", textStyle);
		password.setPasswordMode(true);

		ImageButtonStyle signInStyle = new ImageButtonStyle();

		signInStyle.up = new Image(new Texture(Gdx.files.internal("login/signin.png"))).getDrawable();
		signInStyle.down = new Image(new Texture(Gdx.files.internal("login/signin_pressed.png"))).getDrawable();

		ImageButton signIn = new ImageButton(signInStyle);
		signIn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");

				if (JBSApi.api != null && !JBSApi.loggedIn && JBSApi.api.isConnected()
						&& JBSApi.api.isAuthenticated()) {
					boolean success = signIn(username.getText(), password.getText());

					if (success) {
						setVisible(false);
						JBSApi.loggedIn = true;
						Header h = new Header(ImageDownloader.downloadImage(JBSApi.api
								.getUserAvatar()), username.getText());
						getStage().addActor(h);
					} else {
						ErrorDialog e = new ErrorDialog("", skin);
						e.setPosition(Constants.WIDTH / 2 - e.getWidth() / 2, Constants.HEIGHT / 2
								- e.getHeight() / 2);
						getStage().addActor(e);
						JBSApi.loggedIn = false;
					}
				}

			}
		});

		ImageButtonStyle registerStyle = new ImageButtonStyle();
		registerStyle.up = new Image(new Texture(Gdx.files.internal("login/register.png"))).getDrawable();
		registerStyle.down = new Image(new Texture(Gdx.files.internal("login/register_pressed.png"))).getDrawable();

		ImageButton register = new ImageButton(registerStyle);
		register.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");

			}
		});

		ImageButtonStyle xStyle = new ImageButtonStyle();
		xStyle.up = new Image(new Texture(Gdx.files.internal("ui/options/close.png"))).getDrawable();
		xb = new ImageButton(xStyle);
		xb.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
				setVisible(false);

			}

		});
		getContentTable().add(xb).expandX().right().colspan(2);
		getContentTable().row();
		
		getContentTable().add(username).colspan(2).width(panel.getWidth() - 50).padBottom(47f);
		getContentTable().row();
		getContentTable().add(password).colspan(2).width(panel.getWidth() - 50).padBottom(47f);
		getContentTable().row();
		getContentTable().add(signIn).padBottom(15f);
		getContentTable().add(register).padBottom(15f);

		pack();

		sr = new ShapeRenderer();
		password.setPasswordCharacter('.');
	}

	public boolean signIn(String username, String password) {

		return JBSApi.api.checkLogin(username, password);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
// if (getStage() != null) {
// getStage().addActor(xb);
// }


//		sr.setProjectionMatrix(getStage().getCamera().combined);
//		sr.begin(ShapeType.Line);
//		for (int i = 0; i < this.getContentTable().getCells().size; i++) {
//			sr.box(getX() + getContentTable().getCells().get(i).getActorX(), getY()
//					+ getContentTable().getY() + getContentTable().getCells().get(i).getActorY(),
//					0, getContentTable().getCells().get(i).getActorWidth(), getContentTable()
//							.getCells().get(i).getActorHeight(), 0);
//		}
//		sr.end();
	}
}