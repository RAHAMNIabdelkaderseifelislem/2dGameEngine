/*
 * Copyright (c) 2021.  Created by aek426rahmani
 */

package aek426rahmani.engine;

import aek426rahmani.engine.util.INIFile;

import java.awt.event.*;
import java.util.HashMap;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

	private Window window;

	private static final int NUM_KEYS = 256;
	private static final int NUM_BUTTONS = 5;

	private final boolean[] keys = new boolean[NUM_KEYS];
	private final boolean[] keysLast = new boolean[NUM_KEYS];

	private final boolean[] buttons = new boolean[NUM_BUTTONS];
	private final boolean[] buttonsLast = new boolean[NUM_BUTTONS];

	private HashMap<String, Integer> keyBind = new HashMap<>();
	private HashMap<String, Integer> mouseBinds = new HashMap<>();

	private int mouseX, mouseY, prevMouseX, prevMouseY, mouseDX, mouseDY;
	private char typed = 0;
	private int wheel = 0, prevWheel = 0;

	public Input(Window window) {
		this.window = window;
		window.getFrame().addKeyListener(this);
		window.getFrame().addMouseListener(this);
		window.getFrame().addMouseMotionListener(this);
		window.getFrame().addMouseWheelListener(this);
	}

	public void update() {
		System.arraycopy(keys, 0, keysLast, 0, NUM_KEYS);
		System.arraycopy(buttons, 0, buttonsLast, 0, NUM_BUTTONS);
		typed = (char)0;
		prevWheel = wheel;
		prevMouseX = mouseX;
		prevMouseY = mouseY;
	}

	public boolean isBind(String name) {
		return isKey(name) || isButton(name);
	}

	public boolean isBindUp(String name) {
		return isKeyUp(name) || isButtonUp(name);
	}

	public boolean isBindDown(String name) {
		return isKeyDown(name) || isButtonDown(name);
	}

	public boolean isKey(String name) { return isKey(keyBind.get(name)); }

	public boolean isKey(int keycode) {
		return keys[keycode];
	}

	public boolean isKeyDown(String name) { return isKeyDown(keyBind.get(name)); }

	public boolean isKeyDown(int keycode) {
		return keys[keycode] && !keysLast[keycode];
	}

	public boolean isKeyUp(String name) { return isKeyUp(keyBind.get(name)); }

	public boolean isKeyUp(int keycode) {
		return !keys[keycode] && keysLast[keycode];
	}

	public boolean isButton(String name) { return isButton(mouseBinds.get(name)); }

	public boolean isButton(int button) {
		return buttons[button];
	}

	public boolean isButtonDown(String name) { return isButtonDown(mouseBinds.get(name)); }

	public boolean isButtonDown(int button) {
		return buttons[button] && !buttonsLast[button];
	}

	public boolean isButtonUp(String name) { return isButtonUp(mouseBinds.get(name)); }

	public boolean isButtonUp(int button) {
		return !buttons[button] && buttonsLast[button];
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public int getMouseDX() {
		return prevMouseX - mouseX;
	}

	public int getMouseDY() {
		return prevMouseY - mouseY;
	}

	public char getTyped() {
		return typed;
	}

	public void addKeyBind(String name, int character) {
		keyBind.put(name, character);
	}

	public void addMouseBind(String name, int character) {
		mouseBinds.put(name, character);
	}

	public void loadKeyBinds(INIFile file) {
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {
		typed = keyEvent.getKeyChar();
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		if(keyEvent.getKeyCode() < NUM_KEYS) {
			keys[keyEvent.getKeyCode()] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		if(keyEvent.getKeyCode() < NUM_KEYS) {
			keys[keyEvent.getKeyCode()] = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {

	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		if(mouseEvent.getButton() < NUM_BUTTONS) {
			buttons[mouseEvent.getButton()] = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		if(mouseEvent.getButton() < NUM_BUTTONS) {
			buttons[mouseEvent.getButton()] = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) {
		mouseMoved(mouseEvent);
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		int x = mouseEvent.getX();
		mouseX = (int) (((x - window.getInsets().left) /
				 (float)(window.getFrame().getWidth() - window.getInsets().left - window.getInsets().right)) *
					    (window.getImage().getWidth()));
		int y = mouseEvent.getY();
		mouseY = (int) (((y - window.getInsets().top) /
				 (float)(window.getFrame().getHeight() - window.getInsets().top - window.getInsets().bottom)) *
					    (window.getImage().getHeight()));
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
		wheel = mouseWheelEvent.getWheelRotation();
	}
}
