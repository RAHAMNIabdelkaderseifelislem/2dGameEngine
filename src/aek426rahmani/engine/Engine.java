/*
 * Copyright (c) 2021.  Created by aek426rahmani
 */

package aek426rahmani.engine;

import aek426rahmani.engine.gfx.EngineFont;
import aek426rahmani.engine.gfx.Pixel;
import aek426rahmani.engine.util.EngineLogger;
import aek426rahmani.engine.util.EngineSettings;
import aek426rahmani.engine.util.EngineUtils;
//import com.sun.management.GarbageCollectionNotificationInfo;



public class Engine {
	private static final EngineLogger logger = new EngineLogger();

	private static Game game;
	private static EngineSettings settings;
	private static Window window;
	private static Renderer renderer;
	private static Input input;

	private static volatile boolean running = false;

	public Engine(Game game, EngineSettings settings) {
		Engine.setGame(game);
		Engine.setSettings(settings);
	}

	public void start() {
		if (running)
			return;
		run();
	}

	public static void stop() {
		Engine.running = false;
	}

	private static void init() {
		window = new Window(settings);
		renderer = new Renderer(window);
		input = new Input(window);
		game.init();
		Engine.running = true;
	}

	private void run() {
		Engine.init();

		double firstTime;
		double lastTime = System.nanoTime() / 1e9d;
		double passedTime;
		double unprocessedTime = 0;
		double frameTime = 0;
		int frames = 0;
		int fps = 0;

		boolean render;


		while(Engine.isRunning()) {
			firstTime = System.nanoTime() / 1e9d;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			unprocessedTime += passedTime;
			frameTime += passedTime;
			render = false;
			double cap = Engine.getSettings().isLockFPS() ? Engine.getSettings().getUpdateCap() : passedTime;
			while(unprocessedTime >= cap && unprocessedTime != 0) {
				unprocessedTime -= cap;
				getGame().getState().update((float)cap);
				render = true;
			}

			if(frameTime >= 1.0) {
				frameTime -= frameTime;
				fps = frames;
				frames = 0;
			}

			if(render) {
				renderer.clear();
				getGame().getState().render(renderer);
				if(Engine.getSettings().isDebug())
					renderDebug(fps);
				window.update();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
		cleanUp();
	}

	private static void cleanUp() {
		window.dispose();
	}

	// Temporary until a UI system is implemented.
	private void renderDebug(int fps) {
		renderer.setAlphaMod(0.5f);
		EngineFont f = EngineFont.STANDARD;
		renderer.draw2DString("FPS: " + fps, 0, 0, EngineFont.LEFT);
		renderer.draw2DString("MouseX: " + input.getMouseX(),  0, f.getMaxHeight(), EngineFont.LEFT);
		renderer.draw2DString("MouseY: " + input.getMouseY(), 0, f.getMaxHeight() * 2, EngineFont.LEFT);

		int twothirds = (int) (Engine.getSettings().getWidth() * (3f / 4f));

		renderer.draw2DString("Memory Usage", twothirds, 0, EngineFont.CENTER);
		double memRatio = (double)EngineUtils.getUsedMemory() / EngineUtils.getTotalMemory();
		int barWidth = (int) (Engine.getSettings().getWidth() / 3f);
		renderer.draw2DFillRect((int) (twothirds - barWidth / 2f), f.getMaxHeight(),
								 barWidth, f.getMaxHeight(), Pixel.GREEN);
		renderer.draw2DFillRect((int) (twothirds - barWidth / 2f), f.getMaxHeight(),
				(int) (barWidth * memRatio), f.getMaxHeight(), Pixel.RED);

		renderer.setColorOverlay(Pixel.RED);
		renderer.draw2DString("Used: " + EngineUtils.getUsedMemory() / 1048576 + " Mib", twothirds, f.getMaxHeight() * 2, EngineFont.CENTER);
		renderer.setColorOverlay(Pixel.GREEN);
		renderer.draw2DString("Free: " + (EngineUtils.getTotalMemory() - EngineUtils.getUsedMemory()) / 1048576  + " Mib", twothirds, f.getMaxHeight() * 3, EngineFont.CENTER);
		renderer.setColorOverlay(Pixel.WHITE);
		renderer.draw2DString("Max: " + Runtime.getRuntime().maxMemory() / 1048576 + " Mib", twothirds, f.getMaxHeight() * 4, EngineFont.CENTER);
		renderer.setAlphaMod(1f);
	}

	public static Game getGame() {
		return game;
	}

	public static void setGame(Game game) {
		Engine.game = game;
	}

	public static EngineSettings getSettings() {
		return settings;
	}

	public static void setSettings(EngineSettings settings) {
		Engine.settings = settings;
	}

	public static Window getWindow() {
		return window;
	}

	public static Renderer getRenderer() {
		return renderer;
	}

	private static boolean isRunning() {
		return running;
	}

	public static EngineLogger getLogger() {
		return logger;
	}

	public static Input getInput() {
		return input;
	}
	public static void main(String[] args) {
		
		Engine eng = new Engine(game, settings);
		eng.start();
	}
}
