/*
 * Copyright (c) 2021.  Created by aek426rahmani
 */

package aek426rahmani.engine;

import aek426rahmani.engine.gfx.EngineImage;
import aek426rahmani.engine.gfx.EngineFont;
import aek426rahmani.engine.gfx.Pixel;

import java.awt.image.DataBufferInt;

public class Renderer {

	private EngineFont font = EngineFont.STANDARD;

	private int[] Pixels;
	private int[] depth;
	private int pWidth, pHeight;

	private float alphaMod = 1f;
	private int colorOverlay = Pixel.WHITE;
	private int clearColor = 0xff000000;

	public Renderer(Window window) {
		this.pWidth = window.getImage().getWidth();
		this.pHeight = window.getImage().getHeight();
		this.Pixels = ((DataBufferInt)window.getImage().getRaster().getDataBuffer()).getData();
		this.depth = new int[Pixels.length];
	}

	public void clear() {
		for (int i = 0; i < Pixels.length; i++) {
			Pixels[i] = clearColor;
		}
	}

	public void setPixel(int x, int y, int value) {
		if(x < 0 || x >= pWidth || y < 0 || y >= pHeight)
			return;

		float alpha = Pixel.getAlpha(value) - (1f - alphaMod);
		if(colorOverlay != Pixel.WHITE) {
			value = Pixel.overlayColor(value, colorOverlay);
		}

		if(alpha == 1) {
			Pixels[x + y * pWidth] = value;
		} else if(alpha != 0) {
			Pixels[x + y * pWidth] = Pixel.alphaBlend(Pixels[x + y * pWidth],
													  value, alpha);
		}

	}

	public void draw2DString(String text, int offX, int offY, int justified) {
		int unicode;
		int offset = 0;
		if(justified == EngineFont.RIGHT) {
			offset -= font.getStringWidth(text);
		} else if(justified == EngineFont.CENTER) {
			offset -= font.getStringWidth(text) / 2;
		}
		for (int i = 0; i < text.length(); i++) {
			unicode = text.codePointAt(i);
			draw2DImage(offX + offset, offY, font.getChar(unicode));
			offset += font.getChar(unicode).getWidth();
		}
	}

	public void draw2DRect(int x, int y, int width, int height, int color) {
		for(int i = x; i < x + width; i++) {
			setPixel(i, y, color);
			setPixel(i, y + height - 1, color);
		}

		for(int i = y; i < y + height; i++) {
			setPixel(x, i, color);
			setPixel(x + width - 1, i, color);
		}
	}

	public void draw2DFillRect(int x, int y, int width, int height, int color) {
		for(int j = y ; j < y + height; j++) {
			for(int i = x; i < x + width; i++) {
				setPixel(i, j, color);
			}
		}
	}

	public void draw2DImage(int x, int y, EngineImage EngineImage) {
		for(int j = y; j < y + EngineImage.getHeight(); j++) {
			for (int i = x; i < x + EngineImage.getWidth(); i++) {
				setPixel(i, j, EngineImage.getPixels()[(i - x) + (j - y) * EngineImage.getWidth()]);
			}
		}
	}

	/**
	 * Sets the color that the screen is set to at the beginning of each frame.
	 * @param clearColor
	 */
	public void setClearColor(int clearColor) {
		this.clearColor = clearColor;
	}

	/**
	 * Sets the modfication to the alpha channel that is rendered.
	 * alphaMod == 1 is no change, alphaMod == 0 is fully transparent.
	 * @param alphaMod
	 */
	public void setAlphaMod(float alphaMod) {
		this.alphaMod = alphaMod;
	}

	public void setColorOverlay(int color) {
		this.colorOverlay = color;
	}
}
