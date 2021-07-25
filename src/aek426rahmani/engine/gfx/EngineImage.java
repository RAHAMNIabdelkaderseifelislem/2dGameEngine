/*
 * Copyright (c) 2021.  Created by aek426rahmani
 */

package aek426rahmani.engine.gfx;

import aek426rahmani.engine.Engine;
import aek426rahmani.engine.util.EngineLogger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EngineImage {
	private int[] Pixels;
	private int width, height;

	public EngineImage(String path) {
		try {
			BufferedImage image = ImageIO.read(new File(path));
			width = image.getWidth();
			height = image.getHeight();
			Pixels = image.getRGB(0, 0, width, height, null, 0, width);
		} catch (IOException e) {
			Engine.getLogger().error(e.getMessage());
			System.exit(1);
		}
	}

	public EngineImage(int[] Pixel, int width, int height) {
		this.Pixels = Pixel;
		this.width = width;
		this.height = height;
	}

	public void saveImage(String path, String format) throws IOException {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		image.setRGB(0, 0, width, height, Pixels, 0, width);
		ImageIO.write(image, format, new File(path));
	}

	public int[] getPixels() {
		return Pixels;
	}

	public void setPixels(int[] Pixels) {
		this.Pixels = Pixels;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
