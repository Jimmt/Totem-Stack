package com.jbs.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.pay.Offer;
import com.badlogic.gdx.pay.OfferType;
import com.badlogic.gdx.pay.PurchaseManager;
import com.badlogic.gdx.pay.PurchaseManagerConfig;
import com.badlogic.gdx.pay.PurchaseObserver;
import com.badlogic.gdx.pay.PurchaseSystem;
import com.badlogic.gdx.pay.Transaction;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class BuyCoinsDialog extends Dialog {
	ImageButton x;
	Array<ImageButton> imageButtons = new Array<ImageButton>();
	Image[] textImages = new Image[2];
	Image[] coinImages = new Image[2];
	Image[] dollarImages = new Image[2];
	ImageButton buyButton;
	Image black;

	public BuyCoinsDialog(Skin skin) {
		super("", skin);
		setTransform(true);

		final int[] jbsNumbers = { 500, 1750};
		int[] dollarNumbers = { 1, 3 };

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
//				TotemGame.soundManager.play("buy");

				for (int i = 0; i < imageButtons.size; i++) {
					if (imageButtons.get(i).isChecked()) {
						TotemGame.services.buyCoins(jbsNumbers[i]);
						TotemGame.soundManager.play("buy");
					}
				}
			}

		});

// getContentTable().row();

		ButtonGroup group = new ButtonGroup();

		for (int i = 0; i < 2; i++) {
			ImageButtonStyle style = new ImageButtonStyle();
			style.up = new Image(Icons.getTex("ui/shop/unselected.png")).getDrawable();
			style.checked = new Image(Icons.getTex("ui/shop/selected.png")).getDrawable();
			ImageButton button = new ImageButton(style);
			group.add(button);
			getContentTable().add(button).padLeft(8f);
			imageButtons.add(button);
		}
//		getContentTable().setDebug(true);

		getTitleTable().add(x).expand().width(x.getWidth())
				.height(x.getHeight()).padTop(x.getHeight()).right();

	

		// TODO textures should probably be in an atlas
		// 3 textures loaded 3 times from disk = 9 times lag
		// for now caching works
		for (int i = 0; i < 2; i++) {
			Image img = new Image(Icons.getTex("ui/shop/jbscoin_000" + i + "_Layer-" + (3 - i)
					+ ".png"));
			img.setScale(1);
			coinImages[2 - i - 1] = img;

			img = new Image(Icons.getTex("ui/shop/" + dollarNumbers[i] + "dollar.png"));
			img.setScale(1);
			dollarImages[i] = img;

			img = new Image(Icons.getTex("ui/shop/" + jbsNumbers[i] + "jbs.png"));
			img.setScale(1);
			textImages[i] = img;
		}
		
		getContentTable().row();
		getContentTable().add(buyButton).colspan(4).padTop(textImages[0].getHeight() + 10f).padBottom(15f);

		x.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setVisible(false);
				black.setVisible(false);
			}

		});

		black = new Image(Icons.getTex("black.png"));
		black.setSize(Constants.HUD_WIDTH, Constants.HUD_HEIGHT);
		black.setColor(black.getColor().r, black.getColor().g, black.getColor().b, 0.65f);
		black.setVisible(false);
		black.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setVisible(false);
				black.setVisible(false);
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
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		

		for (int i = 0; i < 2; i++) {
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
