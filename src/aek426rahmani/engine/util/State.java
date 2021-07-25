/*
 * Copyright (c) 2021.  Created by aek426rahmani
 */

package aek426rahmani.engine.util;

import aek426rahmani.engine.Renderer;

public abstract class State {
	public abstract void update(float dt);
	public abstract void render(Renderer r);
}
