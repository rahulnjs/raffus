package com.raffus.httpserver.client;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.raffus.httpserver.server.Server;

public class Request {
	private Socket client;
	private String raw;
	private String method;
	private String resourceURI;
	private Map<String, String> headers = new HashMap<String, String>();
	private Map<String, String> params = new HashMap<String, String>();
	private boolean isStaticReq = false;
	private String resourceType;

	public Request(Socket client) {
		this.client = client;
	}

	public void parseRequest() {
		try {
			byte[] b = new byte[client.getReceiveBufferSize()];
			client.getInputStream().read(b);
			raw = new String(b).trim();
			readHeaders();
			if (Server.opts.isVerboseEnabled())
				System.out.println("\n" + raw + "\n");
		} catch (IOException e) {
			System.out.println("ERROR parsing request.");
		}
	}

	private void readHeaders() { 
		String[] headers = raw.split("\n");
		readResourceDesc(headers[0]); 
		for(int i = 1; i <headers.length; i++) { 
			String[] parts = headers[i].split(": ");
			try { 
				this.headers.put(parts[0], parts[1]);
			} catch (ArrayIndexOutOfBoundsException e) { 
				parts = headers[i].split("=");
				if(parts.length > 1) { 
					this.params.put(parts[0], parts[1]); 
				} 
			} 
		} 
	}

	private void readResourceDesc(String resourceDesc) {
		try {
			String[] parts = resourceDesc.split(" ");
			this.method = parts[0];
			this.resourceURI = parts[1];
			readParamsFromURI();
			determineResourceType();
		} catch (Exception e) {
		}
	}

	private void readParamsFromURI() {
		String[] parts = this.resourceURI.split("/?\\?");
		this.resourceURI = parts[0];
		if (parts.length > 1) {
			String[] params = parts[1].split("&");
			for (String param : params) {
				parts = param.split("=");
				this.params.put(parts[0], parts[1]);
			}
		}
	}

	private void determineResourceType() { 
		determineMIME(); 
		setStaticReq(!isDynamicReq()); 
	}

	private boolean isDynamicReq() {
		return false;
	}
	
	private void determineMIME() { 
		String ext = this.resourceURI.substring(this.resourceURI.lastIndexOf(".") + 1);
		String images = "bmp_gif_jpg_png_jpeg_ico_svg";
		String application = "js_json ";
		String text = "html_css"; 
		if(images.indexOf(ext) >= 0) { 
			resourceType = "image/" + ext; 
			if(ext.equals("svg")) {
				resourceType += "+xml";
			}
		} else if(application.indexOf(ext) >= 0) { 
			resourceType = "application/" + (ext.equals("js") ? "javascript" : ext);
		} else if(text.indexOf(ext) >= 0) {
			resourceType = "text/" + ext;
		} else {
			resourceType = "text/plain";
		}
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getResourceURI() {
		return resourceURI;
	}

	public void setResourceURI(String resourceURI) {
		this.resourceURI = resourceURI;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public boolean isStaticReq() {
		return isStaticReq;
	}

	public void setStaticReq(boolean isStaticReq) {
		this.isStaticReq = isStaticReq;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	
	
	
}
	
	
	
	
	