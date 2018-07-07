package com.raffus.httpserver.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import com.raffus.httpserver.server.Server;

public class StaticRequestProcessor implements RequestProcessor {
	private Request req;
	private Response res;
	private long sT;

	public StaticRequestProcessor(Request req, Response res, long sT) {
		this.req = req;
		this.res = res;
		this.sT = sT;
	}

	public void process() throws IOException {
		String resource = Server.root + req.getResourceURI();
		int resCode = 0;
		File f = new File(resource);
		if (f.exists()) {
			String cont = getContent(f);
			if (req.getResourceType().charAt(0) == 'i') {
				res.send200Binary(f, req.getResourceType());
			} else {
				res.send200(cont, req.getResourceType());
			}
			resCode = 200;
		} else {
			res.send404(get404Content());
			resCode = 404;
		}
		if (Server.opts.isLoggingEnabled())
			System.out.println(req.getMethod() + "\t" +
					req.getResourceURI() + "\t" + req.getResourceType() + "\t" + resCode + "\t" + processingTime());
	}

	private String processingTime() {
		double diff = new Date().getTime() - sT;
		return diff + "ms";
	}

	private String getContent(File f) {
		String content = "";
		try {
			FileInputStream fis = new FileInputStream(f);
			byte[] b = new byte[fis.available()];
			fis.read(b);
			content = new String(b);
			fis.close();
		} catch (IOException e) {
		}
		return content;
	}

	private String get404Content() {
		return "404";
	}
}