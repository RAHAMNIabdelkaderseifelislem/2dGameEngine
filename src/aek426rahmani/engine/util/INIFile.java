/*
 * Copyright (c) 2021.  Created by aek426rahmani
 */

package aek426rahmani.engine.util;

import aek426rahmani.engine.Engine;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class INIFile {

	private HashMap<String, Object> m_data;
	private String m_path;
	private EngineLogger logger;

	public INIFile(HashMap<String, Object> m_data) {
		this.m_data = m_data;
		m_path = null;
		logger = Engine.getLogger();
	}

	public INIFile(String path) {
		this.m_path = path;
		logger = Engine.getLogger();
		m_data = new HashMap<String, Object>();
		load();
	}

	private Object getValue(String token) {
		try {
			return Integer.parseInt(token);
		} catch (Exception e1) {
			try {
				return Float.parseFloat(token);
			} catch (Exception e2) {
				if (token.equalsIgnoreCase("true")) {
					return true;
				} else if (token.equalsIgnoreCase("false")) {
					return false;
				} else {
					return token;
				}
			}
		}
	}

	private INIFile getAllInHeader(String header) {
		HashMap<String, Object> object = new HashMap<>();
		Set<String> keys = m_data.keySet();
		for(String k : keys) {
			if(k.split("\\.")[0].equals(header)) {
				object.put(k, m_data.get(k));
			}
		}
		return new INIFile(object);
	}

	private void load() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(m_path));
			String line;
			String header = "";
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(":");
				if (tokens.length == 1) {
					if (line.charAt(0) == '[') {
						header = line.substring(1, line.length() - 1) + ".";
					}
				} else if (tokens.length == 2) {
					m_data.put(header + tokens[0], getValue(tokens[1]));
				} else {
					throw new IniFormatException(m_path);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			System.exit(1);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				System.exit(1);
			}
		}
	}

	private String[] getOrganizedData() {
		ArrayList<String> results = new ArrayList<String>();
		Set<String> keys = m_data.keySet();
		for (String key : keys) {
			results.add(key + ":" + m_data.get(key));
		}
		String[] arrayResults = new String[results.size()];
		results.toArray(arrayResults);
		Arrays.sort(arrayResults);
		return arrayResults;
	}

	public void save() {
		BufferedWriter writer = null;
		String[] settings = getOrganizedData();
		try {
			writer = new BufferedWriter(new FileWriter(m_path));
			writer.write("");
			String header = "";
			for (String line : settings) {
				String[] tokens = line.split(":");

				String lineHeader = tokens[0].split("\\.")[0];
				if (!lineHeader.equals(header)) {
					writer.append("[").append(lineHeader).append("]").append('\n');
				}

				String lineName = tokens[0].substring(tokens[0].lastIndexOf('.'));
				writer.append(lineName).append(":").append(tokens[1]).append('\n');
			}
			writer.flush();
		} catch (Exception e) {
			logger.error(e.getMessage());
			System.exit(1);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
					System.exit(1);
				}
			}
		}
	}

	public String getString(String key, String defaultValue) {
		Object data = m_data.get(key);
		if (!(data instanceof String)) {
			logger.error("Not a String! File: " + m_path + ", Key: " + key);
			return defaultValue;
		} else {
			return (String) data;
		}
	}

	public int getInt(String key, int defaultValue) {
		Object data = m_data.get(key);
		if (!(data instanceof Integer)) {
			logger.error("Not an Int! File: " + m_path + ", Key: " + key);
			return defaultValue;
		} else {
			return (Integer) data;
		}
	}

	public float getFloat(String key, float defaultValue) {
		Object data = m_data.get(key);
		if (!(data instanceof Float)) {
			logger.error("Not a Float! File: " + m_path + ", Key: " + key);
			return defaultValue;
		} else {
			return (Float) data;
		}
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		Object data = m_data.get(key);
		if (!(data instanceof Boolean)) {
			logger.error("Not a Boolean! File: " + m_path + ", Key: " + key);
			return defaultValue;
		} else {
			return (Boolean) data;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("filePath: ").append(m_path).append('\n');
		builder.append("Contents:\n");
		String[] lines = getOrganizedData();
		for (String s : lines) {
			builder.append(s).append('\n');
		}
		return builder.toString();
	}
}
