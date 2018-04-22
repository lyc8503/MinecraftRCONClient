package me.lyc8503.rconClient.start;

import javax.swing.JOptionPane;

import me.lyc8503.rconClient.gui.ConnectFrame;
import me.lyc8503.rconClient.gui.Login;
import me.lyc8503.rconClient.gui.MainFrame;
import me.lyc8503.rconClient.gui.RCONData;
import me.lyc8503.rconClient.socketUtilities.RCONUtilities;
import net.kronos.rkon.core.Rcon;

public class StartRCON {
	
	public static String address = "";
	public static String port = "";
	
	public static void main(String args[]){
		System.out.println("RCONClient By lyc8503");
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("Disconnect From server...");
				RCONUtilities.disconnect();
			}
		}));
		RCONData data = Login.showInput();
		ConnectFrame.start(data.getServer(), data.getPort());
		Rcon rcon = null;
		System.out.println("Connecting to server...");
		address = data.getServer();
		port = data.getPort();
		System.out.println(data.getServer() + " " + data.getPort() + " " + data.getPassword());
		try {
			rcon = new Rcon(data.getServer(), Integer.parseInt(data.getPort()), data.getPassword().getBytes());
		}catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error while Connecting to the Server:\n" + e.toString(), "MinecraftRCONClient - Error", JOptionPane.PLAIN_MESSAGE);
			System.exit(1);
		}
		ConnectFrame.stop();
		RCONUtilities.init(rcon);
		MainFrame.startMainFrame();
	}
}
