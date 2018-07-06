package com.raffus.httpserver.client;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.raffus.httpserver.server.Server;

public class Response {
	private Socket client;
	private BufferedWriter out;
	DataOutputStream dos;

	public Response(Socket client) throws IOException {
		this.setClient(client);
		out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		dos = new DataOutputStream(client.getOutputStream());
	}

	public void sendHeaders(long contentLength, String type, String httpCode) throws IOException {
		out.write("HTTP/1.0 " + httpCode + "\r\n");
		out.write("Content-Type: " + type + "\r\n");
		out.write("Content-Length: " + contentLength + "\r\n");
		out.write("Cache-Control: " + Server.cacheHeaderVal + "\r\n");
	}

	public void send200Binary(File f, String contentType) throws IOException {
		sendHeaders(f.length(), contentType, "200 OK");
		out.write("\r\n");
		out.flush();
		FileInputStream fis = new FileInputStream(f);
		int i;
		byte[] b = new byte[4096];
		while ((i = fis.read(b)) != -1) {
			client.getOutputStream().write(b, 0, i);
		}
		fis.close();
		client.getOutputStream().flush();
		client.close();
	}

	public void send200(String content, String type) throws IOException {
		sendHeaders(content.length(), type, "200 OK");
		out.write("\r\n");
		out.write(content);
		out.flush();
		client.close();
	}

	public void send404(String content) throws IOException {
		sendHeaders(content.length(), "text/html", "404 Not Found");
		out.write("\\r\\n");
		out.write(content);
		out.flush();
		client.close();
	}

	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}
}