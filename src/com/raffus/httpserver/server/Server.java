package com.raffus.httpserver.server;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
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
			ServerSocket server = new ServerSocket(port, 50, InetAddress.getLocalHost());
			System.out.println("Server running at: " + "http://" + server.getInetAddress().getHostAddress() + ":" + port + "/\n\n");
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
			if(e instanceof BindException) {
				System.out.println("Port " + this.port + " is already in use. Restart the server with a different port.");
			} else {
				System.out.println("Something went wrong. " + e);
			}
		}
	}
}