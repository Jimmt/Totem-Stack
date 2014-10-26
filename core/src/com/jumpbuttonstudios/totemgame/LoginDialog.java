package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

	public LoginDialog(String title, final Skin skin) {
		super(title, skin);

		setTransform(true);

		BitmapFont font = new BitmapFont(Gdx.files.internal("ui/highscore/font.fnt"));
		Image panel = new Image(new Texture(Gdx.files.internal("login/window.png")));

		setBackground(panel.getDrawable());

		TextFieldStyle textStyle = new TextFieldStyle();
		textStyle.font = font;
		textStyle.fontColor = Color.WHITE;

		username = new TextField("", textStyle);
		password = new TextField("", textStyle);
		password.setPasswordMode(true);

		ImageButtonStyle signInStyle = new ImageButtonStyle();
		signInStyle.up = Icons.getImage("login/signin.png").getDrawable();
		signInStyle.down = Icons.getImage("login/signin_pressed.png").getDrawable();

		ImageButton signIn = new ImageButton(signInStyle);
		signIn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
				boolean success = signIn(username.getText(), password.getText());

				if (success) {
// getStage().addActor(errorDialog);
				} else {
					ErrorDialog e = new ErrorDialog("", skin);
					e.setPosition(Constants.WIDTH / 2 - e.getWidth() / 2,
							Constants.HEIGHT / 2 - e.getHeight() / 2);
					getStage().addActor(e);
				}

			}
		});

		ImageButtonStyle registerStyle = new ImageButtonStyle();
		registerStyle.up = Icons.getImage("login/register.png").getDrawable();
		registerStyle.down = Icons.getImage("login/register_pressed.png").getDrawable();

		ImageButton register = new ImageButton(registerStyle);
		register.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");

			}
		});

		ImageButtonStyle xStyle = new ImageButtonStyle();
		xStyle.up = Icons.getImage("ui/options/close.png").getDrawable();
		xb = new ImageButton(xStyle);
		xb.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
				setVisible(false);
				
			}

		});
		getContentTable().add(xb).expandX().right().colspan(2).padBottom(xb.getHeight() / 2);
		getContentTable().row();
		getContentTable().add(username).width(panel.getWidth() - 50).colspan(2).padBottom(27f);
		getContentTable().row();
		getContentTable().add(password).width(panel.getWidth() - 50).colspan(2).padTop(10f);
		getContentTable().row();
		getContentTable().add(signIn).padTop(15);
		getContentTable().add(register).padTop(15);

		pack();
//		xb.setPosition(Constants.WIDTH / 2 - getWidth() / 2 + getWidth() - xb.getWidth() / 2 - 5,
//				Constants.HEIGHT / 2 - getHeight() / 2 + getHeight() - xb.getHeight() / 2 - 5);
		sr = new ShapeRenderer();

	}

	public boolean signIn(String username, String password) {

		return JBSApi.api.checkLogin(username, password);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

//		if (getStage() != null) {
//			getStage().addActor(xb);
//		}

		sr.setProjectionMatrix(getStage().getCamera().combined);
		sr.begin(ShapeType.Line);
		for (int i = 0; i < this.getContentTable().getCells().size; i++) {
// sr.box(getX() + getContentTable().getCells().get(i).getActorX(), getY()
// + getContentTable().getY() + getContentTable().getCells().get(i).getActorY(),
// 0, getContentTable().getCells().get(i).getActorWidth(), getContentTable()
// .getCells().get(i).getActorHeight(), 0);
		}
		sr.end();
	}
}
