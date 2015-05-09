package com.jumpbuttonstudio.totemgame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class BuyCoinsDialog extends Dialog {
	ImageButton x;
	Array<ImageButton> imageButtons = new Array<ImageButton>();
	Image[] textImages = new Image[3];
	Image[] coinImages = new Image[3];
	Image[] dollarImages = new Image[3];
	ImageButton buyButton;

	public BuyCoinsDialog(Skin skin) {
		super("", skin);
		setTransform(true);

		final int[] jbsNumbers = { 500, 1750, 4000 };
		int[] dollarNumbers = { 1, 3, 7 };

		Image window01 = new Image(Icons.getTex("ui/shop/window01.png"));
		setBackground(window01.getDrawable());
		setWidth(window01.getWidth());
		setHeight(window01.getHeight());

		ImageButtonStyle buttonStyle = new ImageButtonStyle();
		buttonStyle.up = new Image(Icons.getTex("ui/options/close.png")).getDrawable();
		x = new ImageButton(buttonStyle);

		ImageButtonStyle buyStyle = new ImageButtonStyle();
		buyStyle.up = new Image(Icons.getTex("ui/shop/buyitem.png")).getDrawable();
		buyStyle.down = new Image(Icons.getTex("ui/shop/buyitem_pressed.png")).getDrawable();
		buyButton = new ImageButton(buyStyle);

		buyButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");

				for (int i = 0; i < imageButtons.size; i++) {
					if (imageButtons.get(i).isChecked()) {
						TotemGame.services.buyCoins(jbsNumbers[i]);
					}
				}
			}

		});

// getContentTable().row();

		ButtonGroup group = new ButtonGroup();

		for (int i = 0; i < 3; i++) {
			ImageButtonStyle style = new ImageButtonStyle();
			style.up = new Image(Icons.getTex("ui/shop/unselected.png")).getDrawable();
			style.checked = new Image(Icons.getTex("ui/shop/selected.png")).getDrawable();
			ImageButton button = new ImageButton(style);
			group.add(button);
			getContentTable().add(button).padLeft(8f);
			imageButtons.add(button);
		}

		getContentTable().add(x).expand().top().width(x.getWidth() / 10 * 8)
				.height(x.getHeight() / 10 * 8).right();

		getContentTable().row();
		getContentTable().add(buyButton).colspan(4).padTop(30f).padBottom(15f);

		// TODO textures should probably be in an atlas
		// 3 textures loaded 3 times from disk = 9 times lag
		// for now caching works
		for (int i = 0; i < 3; i++) {
			Image img = new Image(Icons.getTex("ui/shop/jbscoin_000" + i + "_Layer-" + (3 - i)
					+ ".png"));
			img.setScale(1);
			coinImages[3 - i - 1] = img;

			img = new Image(Icons.getTex("ui/shop/" + dollarNumbers[i] + "dollar.png"));
			img.setScale(1);
			dollarImages[i] = img;

			img = new Image(Icons.getTex("ui/shop/" + jbsNumbers[i] + "jbs.png"));
			img.setScale(1);
			textImages[i] = img;
		}

		x.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
				setVisible(false);
			}

		});

	}

	public float getCenterX(ImageButton ibutton, Image image) {
		return getX() + ibutton.getX() + ibutton.getWidth() / 2 - image.getWidth() / 2;
	}

	public float getCenterY(ImageButton ibutton, Image image) {
		return getY() + ibutton.getY() + ibutton.getHeight() / 2 - image.getHeight() / 2;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		for (int i = 0; i < 3; i++) {
			ImageButton ibutton = imageButtons.get(i);
			coinImages[i].setPosition(getCenterX(ibutton, coinImages[i]),
					getCenterY(ibutton, coinImages[i]) - 3);
			textImages[i].setPosition(getCenterX(ibutton, textImages[i]), getY() + ibutton.getY()
					+ ibutton.getHeight() - textImages[i].getHeight() - 3);
			dollarImages[i].setPosition(getCenterX(ibutton, dollarImages[i]),
					getY() + ibutton.getY() - dollarImages[i].getHeight() / 5 * 3);

			coinImages[i].draw(batch, parentAlpha);
			textImages[i].draw(batch, parentAlpha);
			dollarImages[i].draw(batch, parentAlpha);
		}
	}
}
