package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class ShopDialog extends Dialog {
	Array<Image> images = new Array<Image>();
	Array<ImageButton> upgradeButtons = new Array<ImageButton>();
	boolean positionSet, changed;
	Image text = new Image(new Texture(Gdx.files.internal("ui/shop/retryinfo.png")));
	ImageButton backButton, buyCoinButton;
	Skin skin;
	GameScreen gs;
	String currentText = "retryinfo";
	ShapeRenderer sr = new ShapeRenderer();

	public ShopDialog(String title, Skin skin, GameScreen gs) {
		super(title, skin);

		this.gs = gs;

		this.skin = skin;

		text.setScale(1);

		Image panel = new Image(new Texture(Gdx.files.internal("shop/boardNew.png")));
		setBackground(panel.getDrawable());
		setSize(panel.getWidth(), panel.getHeight());

		int[] coinNumbers = { 1, 2, 3, 5 };
		String[] buttonNames = { "retry", "freeze", "slow", "wind" };

		ImageButtonStyle buyCoinStyle = new ImageButtonStyle();
		buyCoinStyle.up = new Image(new Texture(Gdx.files.internal("ui/shop/buyjbs.png"))).getDrawable();
		buyCoinStyle.down = new Image(new Texture(Gdx.files.internal("ui/shop/buyjbs_pressed.png"))).getDrawable();
		buyCoinButton = new ImageButton(buyCoinStyle);

		ImageButtonStyle backStyle = new ImageButtonStyle();
		backStyle.up = new Image(new Texture(Gdx.files.internal("ui/highscore/back.png"))).getDrawable();
		backStyle.down = new Image(new Texture(Gdx.files.internal("ui/highscore/back_pressed.png"))).getDrawable();
		backButton = new ImageButton(backStyle);


		setupListeners();

		ButtonGroup group = new ButtonGroup();

		CoinLabel coinLabel = new CoinLabel();

		getContentTable().add(new Image(new Texture(Gdx.files.internal("blank.png")))).expand().bottom().left().colspan(4).padBottom(coinLabel.coins.getHeight());

		getContentTable().row();

		for (int i = 0; i < buttonNames.length; i++) {
			ImageButtonStyle style = new ImageButtonStyle();
			style.up = new Image(new Texture(Gdx.files.internal("ui/shop/" + buttonNames[i]
					+ "Button.png"))).getDrawable();
			style.checked = new Image(new Texture(Gdx.files.internal("ui/shop/" + buttonNames[i]
					+ "Selected.png"))).getDrawable();

			ImageButton button = new ImageButton(style);

			final String name = buttonNames[i];

			button.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					super.clicked(event, x, y);
					TotemGame.soundManager.play("button");

					currentText = name;

					text.setDrawable(Icons.returnImage("ui/shop/" + name + "info.png")
							.getDrawable());

				}
			});

			group.add(button);
			upgradeButtons.add(button);

			getContentTable().add(button);
		}

		getContentTable().row();

		for (int i = 0; i < 4; i++) {
			ImageButtonStyle ibs = new ImageButtonStyle();
			ibs.up = new Image(new Texture(Gdx.files.internal("shop/button_normal.png"))).getDrawable();
			ibs.down = new Image(new Texture(Gdx.files.internal("shop/button_pressed.png"))).getDrawable();

			ImageButton button = new ImageButton(ibs);

			getContentTable().add(button);
		}

		for (int i = 0; i < coinNumbers.length; i++) {

			Image img = new Image(new Texture(Gdx.files.internal("shop/paper.png")));
			images.add(img);

		}
		getContentTable().row();
		getContentTable().add(text).colspan(4).height(95).padBottom(95);

		getContentTable().row();
		getButtonTable().add(buyCoinButton).colspan(2).expandX().padRight(75);
		getButtonTable().add(backButton).colspan(2).expandX();
	}

	public void setupListeners() {
		buyCoinButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				TotemGame.soundManager.play("button");

				BuyCoinsDialog bcd = new BuyCoinsDialog(skin);
				bcd.setPosition(Constants.WIDTH / 2 - bcd.getWidth() / 2, Constants.HEIGHT / 2
						- bcd.getHeight() / 2);
				getStage().addActor(bcd);

			}
		});
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				TotemGame.soundManager.play("button");
				setVisible(false);
				if (gs != null) {
					gs.pause(false);
				}
			}
		});
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		if (currentText.equals("freeze")) {
			text.setHeight(95);
		} else {
			text.setHeight(63);
		}

// for (int i = 0; i < upgradeButtons.size; i++) {
// Image img = images.get(i);
// img.setPosition(getX() +
// getContentTable().getCell(upgradeButtons.get(i)).getActorX()
// + 3f, getY() + getContentTable().getCell(upgradeButtons.get(i)).getActorY()
// - getContentTable().getCell(upgradeButtons.get(i)).getActorHeight() / 4f);
// }
	}
}
