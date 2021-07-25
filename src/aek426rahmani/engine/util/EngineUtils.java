/*
 * Copyright (c) 2021.  Created by aek426rahmani
 */

package aek426rahmani.engine.util;


public class EngineUtils {
	public static long getTotalMemory() {
		return Runtime.getRuntime().totalMemory();
	}

	public static long getUsedMemory() {
		Runtime t = Runtime.getRuntime();
		return t.totalMemory() - t.freeMemory();
	}
}
