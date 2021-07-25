/*
 * Copyright (c) 2021.  Created by aek426rahmani
 */

package aek426rahmani.engine.util;

public class IniFormatException extends Exception {
	private String filePath;
	public IniFormatException(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String getMessage() {
		return "Error in ini file format, file: " + filePath;
	}
}
