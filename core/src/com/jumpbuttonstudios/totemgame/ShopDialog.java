package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
	boolean positionSet;
	Image text = Icons.getImage("ui/shop/retryinfo.png");
	ImageButton buyButton, buyCoinButton;
	ImageButton x;

	public ShopDialog(String title, Skin skin) {
		super(title, skin);

		text.setScale(1);

		Image panel = new Image(new Texture(Gdx.files.internal("ui/shop/window.png")));
		setBackground(panel.getDrawable());
		setSize(panel.getWidth(), panel.getHeight());

		int[] dollarNumbers = { 1, 3, 7 };
		int[] coinNumbers = { 1, 2, 3, 5 };
		int[] jbsNumbers = { 500, 1750, 4000 };
		String[] buttonNames = { "retry", "freeze", "slow", "wind" };

// for (int i = 0; i < dollarNumbers.length; i++) {
// getContentTable().add(Icons.getImage("ui/shop/" + dollarNumbers[i] +
// "dollars.png"));
// }
		ImageButtonStyle buyCoinStyle = new ImageButtonStyle();
		buyCoinStyle.up = Icons.getImage("ui/shop/buyjbs.png").getDrawable();
		buyCoinStyle.down = Icons.getImage("ui/shop/buyjbs_pressed.png").getDrawable();
		buyCoinButton = new ImageButton(buyCoinStyle);

		ImageButtonStyle buyStyle = new ImageButtonStyle();
		buyStyle.up = Icons.getImage("ui/shop/buyitem.png").getDrawable();
		buyStyle.down = Icons.getImage("ui/shop/buyitem_pressed.png").getDrawable();
		buyButton = new ImageButton(buyStyle);

		ImageButtonStyle buttonStyle = new ImageButtonStyle();
		buttonStyle.up = Icons.getImage("ui/options/close.png").getDrawable();
		x = new ImageButton(buttonStyle);

		setupListeners();

		ButtonGroup group = new ButtonGroup();

		getContentTable().add(x).expandX().right().colspan(4);
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
					
					text.setDrawable(Icons.returnImage("ui/shop/" + name + "info.png").getDrawable());
				}
			});

			group.add(button);
			upgradeButtons.add(button);
			getContentTable().add(button).padTop(5f);
		}
		for (int i = 0; i < coinNumbers.length; i++) {

			Image img = Icons.getImage("ui/shop/" + String.valueOf(coinNumbers[i]) + "00.png");
			img.setScale(1);
			images.add(Icons.getImage("ui/shop/" + coinNumbers[i] + "00.png"));

		}
		getContentTable().row();
		getContentTable().add(text).expandX().center().padTop(images.get(0).getHeight()).colspan(4)
				.padBottom((95 - text.getHeight()) / 2).width(text.getWidth())
				.height(text.getHeight());
		getContentTable().row();
		getContentTable().add(buyCoinButton).colspan(2).padTop(5f);
		getContentTable().add(buyButton).colspan(2).padTop(5f);

	}

	public void setupListeners() {
		buyCoinButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				TotemGame.soundManager.play("button");

			}
		});
		buyButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				TotemGame.soundManager.play("button");

			}
		});
		x.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TotemGame.soundManager.play("button");
				setVisible(false);
			}

		});
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		for (int i = 0; i < upgradeButtons.size; i++) {
			Image img = images.get(i);
			img.setPosition(getX() + getContentTable().getCell(upgradeButtons.get(i)).getActorX()
					+ 3f, getY() + getContentTable().getCell(upgradeButtons.get(i)).getActorY()
					- getContentTable().getCell(upgradeButtons.get(i)).getActorHeight() / 4f);
		}

		for (int i = 0; i < images.size; i++) {
			images.get(i).draw(batch, parentAlpha);
		}

	}

}
