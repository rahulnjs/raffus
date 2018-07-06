package com.raffus.httpserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.raffus.httpserver.client.ClientProcessor;

public class Server {
	private int port;
	public static String root = "";
	private boolean running = true;
	public static String cacheHeaderVal;
	public static ServerOptions opts;

	public Server(ServerOptions opts) {
		Server.opts = opts;
		this.port = opts.getPort();
		root = opts.getDocBase();
		cacheHeaderVal = opts.getCacheHeaderValue();
	}

	public void start() {
		try {
			ServerSocket server = new ServerSocket(port);
			System.out.println("Server running at: " + "http://localhost:" + port + "/\n\n");
			if (!opts.isLoggingEnabled()) {
				System.out.println(
						"Logging is not enabled.\nFor logging" + " try restarting the server with '-l' switch.");
			}
			while (running) {
				Socket client = server.accept();
				new ClientProcessor(client).start();
			}
			server.close();
		} catch (IOException e) {
			System.out.println("ERROR: " + e);
		}
	}
}