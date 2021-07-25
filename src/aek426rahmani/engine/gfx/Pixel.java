/*
 * Copyright (c) 2021.  Created by aek426rahmani
 */

package aek426rahmani.engine.gfx;

public class Pixel {

	public static final int WHITE = 0xFFFFFFFF;
	public static final int RED = 0xFFFF0000;
	public static final int GREEN = 0xFF00FF00;
	public static final int BLUE = 0xFF0000FF;
	public static final int YELLOW = 0xFFFFFF00;
	public static final int MAGENTA = 0xFFFF00FF;
	public static final int CYAN = 0xFF00FFFF;

	private static final float inv255 = 1 / 255f;

	public static int getColor(float a, float r, float g, float b) {
		return (int) (a * 255f) << 24 | (int) (r * 255f) << 16 | (int) (g * 255f) << 8 | (int) (b * 255f);
	}

	public static float getAlpha(int color) {
		return ((color >> 24) & 0xFF) * inv255;
	}

	public static float getRed(int color) {
		return ((color >> 16) & 0xFF) * inv255;
	}

	public static float getGreen(int color) {
		return ((color >> 8) & 0xFF) * inv255;
	}

	public static float getBlue(int color) {
		return (color & 0xFF) * inv255;
	}

	public static int mul(int color, float multiplier) {
		return getColor(1f,
				getRed(color) * multiplier,
				getGreen(color) * multiplier,
				getBlue(color) * multiplier
		);
	}

	public static int add(int color0, int color1) {
		return getColor(1f,
				getRed(color0) + getRed(color1),
				getGreen(color0) + getGreen(color1),
				getBlue(color0) + getBlue(color1));
	}

	public static int overlayColor(int Pixel, int color) {
		return getColor(getAlpha(Pixel) * getAlpha(color),
						getRed(Pixel) * getRed(color),
						getGreen(Pixel) * getGreen(color),
						getBlue(Pixel) * getBlue(color));
	}

	public static int alphaBlend(int dest, int source, float alpha) {
		return add(mul(dest, 1f - alpha), mul(source, alpha));
	}



	public static int alphaBlend(int dest, int source) {
		float a = getAlpha(source);
		return alphaBlend(dest, source, a);
	}
}
