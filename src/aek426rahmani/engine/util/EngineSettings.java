/*
 * Copyright (c) 2021.  Created by aek426rahmani
 */

package aek426rahmani.engine.util;

public class EngineSettings {

	private String title = "EngineEngine";
	private int width = 320, height = 240;
	private float scale = 2.0f;
	private double updateCap = 1.0 / 60.0;
	private boolean debug = true;
	private boolean lockFPS = false;

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getUpdateCap() {
		return updateCap;
	}

	public void setUpdateCap(double updateCap) {
		this.updateCap = updateCap;
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

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public boolean isLockFPS() {
		return lockFPS;
	}

	public void setLockFPS(boolean lockFPS) {
		this.lockFPS = lockFPS;
	}
}
