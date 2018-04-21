package me.lyc8503.rconClient.socketUtilities;

import java.io.IOException;

import javax.swing.JOptionPane;

import net.kronos.rkon.core.Rcon;

public class RCONUtilities {
	
	private static Rcon rcon;
	
	public static void init(Rcon rcon) {
		RCONUtilities.rcon = rcon;
	}
	
	public static String executeCommand(String command){
		String result = null;
		try {
			result = rcon.command(command);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unexpected Error:" + e.toString(), "MinecraftRCONClient - Error", JOptionPane.PLAIN_MESSAGE);
			System.exit(1);
		}
		return result;
	}
	
	public static void disconnect() {
		try {
			rcon.disconnect();
		} catch (Exception e) {
			//Ignore
		}
		rcon = null;
	}
}
