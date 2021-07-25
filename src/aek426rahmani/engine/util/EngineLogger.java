/*
 * Copyright (c) 2021.  Created by aek426rahmani
 */

package aek426rahmani.engine.util;

import aek426rahmani.engine.Engine;

public class EngineLogger {

	private final StringBuilder log = new StringBuilder();

	private final String INFO = "<INFO>: ";
	private final String ERROR = "<ERROR>: ";

	public void info(String message) {
		log.append(INFO).append(message).append('\n');
		if(Engine.getSettings().isDebug()) {
			System.out.println(INFO + message);
		}
	}

	public void error(String message) {
		log.append(ERROR).append(message).append('\n');
		if(Engine.getSettings().isDebug()) {
			System.out.println(ERROR + message);
		}
	}
}
