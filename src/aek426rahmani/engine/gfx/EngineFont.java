/*
 * Copyright (c) 2021.  Created by aek426rahmani
 */

package aek426rahmani.engine.gfx;

import aek426rahmani.engine.util.EngineFontGenerator;

import java.awt.*;
import java.util.ArrayList;

public class EngineFont {

	public static final EngineFont STANDARD = new EngineFont("src/res/fonts/sfont.png");

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int CENTER = 2;

	private EngineImage[] charEngineImages;
	private int height;
	public EngineFont(String path) {
		this(new EngineImage(path));
	}

	public EngineFont(EngineImage EngineImage) {
		int start = 0;
		ArrayList<EngineImage> tempEngineImages = new ArrayList<>();
		for (int i = 0; i < EngineImage.getWidth(); i++) {
			if (EngineImage.getPixels()[i] == 0xff0000ff) {
				start = i;
			} else if (EngineImage.getPixels()[i] == 0xffffff00) {
				int width = i - start;
				int[] p = new int[width * (EngineImage.getHeight() - 1)];
				for (int y = 0; y < EngineImage.getHeight() - 1; y++) {
					for (int x = start; x < start + width; x++) {
						p[(x - start) + y * width] = EngineImage.getPixels()[x + (y + 1) * EngineImage.getWidth()];
					}
				}
				tempEngineImages.add(new EngineImage(p, width, EngineImage.getHeight() - 1));
			}
		}
		charEngineImages = new EngineImage[tempEngineImages.size()];
		tempEngineImages.toArray(charEngineImages);
		height = getChar(0).getHeight();
	}

	public EngineImage getChar(int unicode) {
		if (unicode > charEngineImages.length || unicode < 0) {
			return charEngineImages[0];
		} else {
			return charEngineImages[unicode];
		}
	}

	public int getStringWidth(String text) {
		int res = 0;
		for(int i = 0; i < text.length(); i++) {
			res += getChar(text.codePointAt(i)).getWidth();
		}
		return res;
	}

	public int getMaxHeight() {
		return height;
	}
}
