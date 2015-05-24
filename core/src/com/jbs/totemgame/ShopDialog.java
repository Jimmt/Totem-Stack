package com.jbs.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class ShopDialog extends Dialog {
	Array<Image> images = new Array<Image>();
	Array<ImageButton> upgradeButtons = new Array<ImageButton>();
	boolean positionSet, changed;
	Image text = new Image(new Texture(Gdx.files.internal("ui/shop/retryinfo.png"))), black;
	ImageButton backButton, buyCoinButton;
	Skin skin;
	GameScreen gs;
	AbstractScreen as;
	String currentText = "retryinfo";
	ShapeRenderer sr = new ShapeRenderer();

	public ShopDialog(String title, Skin skin, GameScreen gs, AbstractScreen as) {
		super(title, skin);

		this.gs = gs;
		this.as = as;

		this.skin = skin;

		text.setScale(1);

		Image panel = new Image(new Texture(Gdx.files.internal("shop/boardNew1.png")));
		setBackground(panel.getDrawable());
		setSize(panel.getWidth(), panel.getHeight());

		final int[] coinNumbers = { 100, 300, 200, 500 };
		final String[] buttonNames = { "retry", "freeze", "slow", "wind" };

		ImageButtonStyle buyCoinStyle = new ImageButtonStyle();
		buyCoinStyle.up = new Image(new Texture(Gdx.files.internal("ui/shop/buyjbs.png")))
				.getDrawable();
		buyCoinStyle.down = new Image(new Texture(Gdx.files.internal("ui/shop/buyjbs_pressed.png")))
				.getDrawable();
		buyCoinButton = new ImageButton(buyCoinStyle);

		ImageButtonStyle backStyle = new ImageButtonStyle();
		backStyle.up = new Image(new Texture(Gdx.files.internal("ui/highscore/back.png")))
				.getDrawable();
		backStyle.down = new Image(new Texture(Gdx.files.internal("ui/highscore/back_pressed.png")))
				.getDrawable();
		backButton = new ImageButton(backStyle);

		setupListeners();

		ButtonGroup group = new ButtonGroup();

		CoinLabel coinLabel = new CoinLabel(skin, null);

		getContentTable().add(new Image(new Texture(Gdx.files.internal("blank.png")))).expand()
				.bottom().left().colspan(4).padBottom(coinLabel.coins.getHeight());

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

			getContentTable().add(button).padLeft(5f);
		}

		getContentTable().row();

		for (int i = 0; i < 4; i++) {
			ImageButtonStyle ibs = new ImageButtonStyle();
			ibs.up = new Image(new Texture(Gdx.files.internal("shop/button_normal.png")))
					.getDrawable();
			ibs.down = new Image(new Texture(Gdx.files.internal("shop/button_pressed.png")))
					.getDrawable();

			ImageButton button = new ImageButton(ibs);
			final int index = i;
			button.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					super.clicked(event, x, y);
					TotemGame.soundManager.play("button");

					if (GamePrefs.prefs.getInteger("coins") >= coinNumbers[index]) {
						GamePrefs.putInteger("coins", GamePrefs.prefs.getInteger("coins")
								- coinNumbers[index]);
						GamePrefs.putInteger(buttonNames[index] + "Uses", GamePrefs.prefs.getInteger(buttonNames[index] + "Uses") + 1);
					}
				}
			});

			Group imageButtonGroup = new Group();
			imageButtonGroup.addActor(button);
			button.setPosition(-button.getWidth() / 2, 0);
			Image price = new Image(Icons.getTex("ui/shop/" + coinNumbers[i] + "coins.png"));
			price.setTouchable(Touchable.disabled);
			imageButtonGroup.addActor(price);
			price.setPosition(-price.getWidth() / 2, price.getHeight() / 2);

			getContentTable().add(imageButtonGroup).padLeft(5f).height(button.getWidth())
					.height(button.getHeight());
		}

		for (int i = 0; i < coinNumbers.length; i++) {

			Image img = new Image(new Texture(Gdx.files.internal("shop/paper.png")));
			images.add(img);

		}
		getContentTable().row();
		Group textGroup = new Group();
		black = new Image(new Texture(Gdx.files.internal("shop/textbg.png")));
		black.setSize(text.getWidth() + 37 * 2, text.getHeight() + 17 * 2);
		black.setPosition(-37, -17);
		textGroup.addActor(black);
		textGroup.addActor(text);
		textGroup.setSize(text.getWidth(), text.getHeight());
		getContentTable().add(textGroup).colspan(4).height(115).padBottom(75).center();

		getContentTable().row();
		getButtonTable().add(buyCoinButton).colspan(2).expandX().padRight(37);
		getButtonTable().add(backButton).colspan(2).expandX().padLeft(37);
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

				as.pause(false);

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

		black.setSize(text.getWidth() + 37 * 2, text.getHeight() + 17 * 2);

// for (int i = 0; i < upgradeButtons.size; i++) {
// Image img = images.get(i);
// img.setPosition(getX() +
// getContentTable().getCell(upgradeButtons.get(i)).getActorX()
// + 3f, getY() + getContentTable().getCell(upgradeButtons.get(i)).getActorY()
// - getContentTable().getCell(upgradeButtons.get(i)).getActorHeight() / 4f);
// }
	}
}
