/*
 * Copyright (c) 2021.  Created by aek426rahmani
 */

package aek426rahmani.engine.util;

import aek426rahmani.engine.Engine;
import aek426rahmani.engine.gfx.EngineImage;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;

public class EngineFontGenerator {
	private static final int NUM_CHAR = 256;

	private EngineFontGenerator() {}

	public static EngineImage genFontImage(String fontName, int size, int type) {
		if (!fontExist(fontName)) {
			Engine.getLogger().error("Font: " + fontName + " does not exist!");
			System.exit(1);
		}
		Font font = new Font(fontName, type, size);
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = img.createGraphics();

		int width = calcWidth(font);
		FontRenderContext frc = graphics.getFontRenderContext();
		String alphabet = buildAlphabet();
		GlyphVector vec = font.createGlyphVector(frc, alphabet);
		Rectangle bounds = vec.getPixelBounds(null, 0, 0);
		int height = bounds.height + 1;
		size = (size + calMaxFontSize(font, frc)) / 2;
		return createFontImage(font, width, height, size);
	}

	private static EngineImage createFontImage(Font f, int width, int height, int fontSize) {
		BufferedImage sprite = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = sprite.createGraphics();
		graphics.setColor(new Color(0, true));
		graphics.fillRect(0, 0, width, height);
		int x = 0, y = 0;
		graphics.setFont(f);
		FontMetrics metrics = new JPanel().getFontMetrics(f);
		for (char c = 0; c < NUM_CHAR; c++) {
			y = 0;
			graphics.setColor(new Color(0f, 0f, 1f, 1f));
			graphics.drawLine(x, y, x, y);
			x++;
			y = 1;
			graphics.setColor(Color.WHITE);
			graphics.drawString(String.valueOf(c), x, y + fontSize);
			x += metrics.charWidth(c);
			y = 0;
			graphics.setColor(new Color(1f, 1f, 0f, 1f));
			graphics.drawLine(x, y, x, y);
			x++;
		}
		int[] p = sprite.getRGB(0, 0, width, height, null, 0, width);
		return new EngineImage(p, width, height);
	}

	private static int calMaxFontSize(Font font, FontRenderContext frc) {
		int max = 0;
		for (int i = 0; i < NUM_CHAR; i++) {
			GlyphVector gv = font.createGlyphVector(frc, String.valueOf(i));
			max = Math.max(gv.getPixelBounds(null, 0, 0).height, max);
		}
		return max;
	}

	private static String buildAlphabet() {
		StringBuilder builder = new StringBuilder(NUM_CHAR);
		for (char i = 0; i < NUM_CHAR; i++) {
			builder.append(i);
		}
		return builder.toString();
	}

	private static int calcWidth(Font font) {
		int width = 0;
		FontMetrics metrics = new JPanel().getFontMetrics(font);
		for (char i = 0; i < NUM_CHAR; i++) {
			width += metrics.charWidth(i) + 2;
		}
		return width;
	}

	private static boolean fontExist(String name) {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = env.getAllFonts();
		for (Font f : fonts) {
			if (f.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
}
