package com.raffus.httpserver.server;

import java.util.HashMap;
import java.util.Map;

public class ServerOptions {
	Map<String, String> opts = new HashMap<String, String>();

	public void put(String opt, String value) {
		opts.put(opt, value);
	}

	public int getPort() {
		return toNumberVal("-p", 4343);
	}

	public String getDocBase() {
		return opts.get("-d") == null ? System.getProperty("user.dir") + "\\" : opts.get("-d");
	}

	public String getCacheHeaderValue() {
		int val = toNumberVal("-c", 0);
		if (val == -1) {
			return "no-cache, no-store, must-revalidate";
		} else {
			return "max-age=" + val;
		}
	}

	public boolean isLoggingEnabled() {
		return opts.get("-l") != null;
	}

	public boolean isVerboseEnabled() {
		return opts.get("-verb") != null;
	}

	public boolean needToPrintHelp() {
		return opts.get("-help") != null;
	}

	public boolean needToVersion() {
		return opts.get("--v") != null;
	}

	private int toNumberVal(String  _switch, int defaultValue) {
		try {
			return Integer.parseInt(opts.get(_switch)); 
		} catch (Exception e) {
			return defaultValue; 
		} 
	}

	public String toString() {
		return opts.toString();
	}
}