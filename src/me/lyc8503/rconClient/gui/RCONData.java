package me.lyc8503.rconClient.gui;

public class RCONData {
	
	private String server;
	private String port;
	private String password;
	
	public RCONData(String server, String port, String password) {
		this.server = server;
		this.port = port;
		this.password = password;
	}

	public String getServer() {
		return server;
	}

	public String getPort() {
		return port;
	}

	public String getPassword() {
		return password;
	}
	
	
}
