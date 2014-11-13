package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
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

		int[] jbsNumbers = { 500, 1750, 4000 };
		int[] dollarNumbers = { 1, 3, 7 };

		setBackground(Icons.getImage("ui/shop/window01.png").getDrawable());
		setWidth(Icons.getImage("ui/shop/window01.png").getWidth());
		setHeight(Icons.getImage("ui/shop/window01.png").getHeight());

		ImageButtonStyle buttonStyle = new ImageButtonStyle();
		buttonStyle.up = Icons.getImage("ui/options/close.png").getDrawable();
		x = new ImageButton(buttonStyle);

		ImageButtonStyle buyStyle = new ImageButtonStyle();
		buyStyle.up = Icons.getImage("ui/shop/buyitem.png").getDrawable();
		buyStyle.down = Icons.getImage("ui/shop/buyitem_pressed.png").getDrawable();
		buyButton = new ImageButton(buyStyle);
		
		buyButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
			}

		});

		
//		getContentTable().row();

		ButtonGroup group = new ButtonGroup();

		for (int i = 0; i < 3; i++) {
			ImageButtonStyle style = new ImageButtonStyle();
			style.up = Icons.getImage("ui/shop/unselected.png").getDrawable();
			style.checked = Icons.getImage("ui/shop/selected.png").getDrawable();
			ImageButton button = new ImageButton(style);
			group.add(button);
			getContentTable().add(button).padLeft(8f);
			imageButtons.add(button);
		}
		
		getContentTable().add(x).expand().top().width(x.getWidth() / 10 * 8).height(x.getHeight() / 10 * 8).right();

		getContentTable().row();
		getContentTable().add(buyButton).colspan(4).padTop(30f).padBottom(15f);

		for (int i = 0; i < 3; i++) {
			Image img = Icons.getImage("ui/shop/jbscoin_000" + i + "_Layer-" + (3 - i) + ".png");
			img.setScale(1);
			coinImages[3 - i - 1] = img;

			img = Icons.getImage("ui/shop/" + dollarNumbers[i] + "dollar.png");
			img.setScale(1);
			dollarImages[i] = img;

			img = Icons.getImage("ui/shop/" + jbsNumbers[i] + "jbs.png");
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