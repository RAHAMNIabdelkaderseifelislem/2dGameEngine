/*
 * Copyright (c) 2021.  Created by aek426rahmani
 */

package aek426rahmani.engine;

import aek426rahmani.engine.util.State;

public abstract class Game {
	private State state;

	public abstract void init();
	public abstract void dispose();

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
