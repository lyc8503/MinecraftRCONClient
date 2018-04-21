package me.lyc8503.rconClient.gui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ConnectFrame {
	public static JFrame frame;
	
	public static void start(String server, String port) {
		frame = new JFrame("MinecraftRCONClient");
		frame.setLayout(new GridLayout(3, 1));
		frame.getContentPane().add(new JLabel("Connecting to the server... Please Wait"));
		frame.getContentPane().add(new JLabel("Server Address: " + server));
		frame.getContentPane().add(new JLabel("Server Port: " + port));
		frame.setSize(350, 150);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public static void stop() {
		frame.dispose();
		frame = null;
	}
}
