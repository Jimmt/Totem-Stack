package com.jumpbuttonstudios.totemgame;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.StreamUtils;

public class ImageDownloader {
	static TextureRegion image;

	public static Image downloadImage(String url) {
		byte[] bytes = new byte[200 * 1024]; // assuming the content is not
// bigger than 200kb.
		int numBytes = download(bytes, url);
		if (numBytes != 0) {
			// load the pixmap, make it a power of two if necessary (not needed
// for GL ES 2.0!)
			Pixmap pixmap = new Pixmap(bytes, 0, numBytes);
			final int originalWidth = pixmap.getWidth();
			final int originalHeight = pixmap.getHeight();
			int width = MathUtils.nextPowerOfTwo(pixmap.getWidth());
			int height = MathUtils.nextPowerOfTwo(pixmap.getHeight());
			final Pixmap potPixmap = new Pixmap(width, height, pixmap.getFormat());
			potPixmap.drawPixmap(pixmap, 0, 0, 0, 0, pixmap.getWidth(), pixmap.getHeight());
			pixmap.dispose();
			image = new TextureRegion(new Texture(potPixmap), 0, 0, originalWidth, originalHeight);
			return new Image(image);
			
		} else {
			return null;
		}
	}

	private static int download(byte[] out, String url) {
		InputStream in = null;
		try {
			HttpURLConnection conn = null;
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(false);
			conn.setUseCaches(true);
			conn.connect();
			in = conn.getInputStream();
			int readBytes = 0;
			while (true) {
				int length = in.read(out, readBytes, out.length - readBytes);
				if (length == -1)
					break;
				readBytes += length;
			}
			return readBytes;
		} catch (Exception ex) {
			return 0;
		} finally {
			StreamUtils.closeQuietly(in);
		}
	}
}
