package com.raffus.httpserver.client;

import java.net.Socket;
import java.util.Date;

public class ClientProcessor extends Thread {
	private Socket client;
	private RequestProcessor rp;

	public ClientProcessor(Socket client) {
		this.client = client;
	}

	public void run() {
		long startTime = new Date().getTime();
		try {
			Request req = new Request(this.client);
			Response res = new Response(this.client);
			req.parseRequest();
			if (req.isStaticReq()) {
				rp = new StaticRequestProcessor(req, res, startTime);
			}
			rp.process();
		} catch (Exception e) {
		}
	}
}
