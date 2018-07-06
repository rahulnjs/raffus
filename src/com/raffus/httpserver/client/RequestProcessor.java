package com.raffus.httpserver.client;

import java.io.IOException;

public interface RequestProcessor {
	void process() throws IOException;
}