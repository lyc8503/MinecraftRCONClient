package me.lyc8503.rconClient.gui;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import me.lyc8503.rconClient.socketUtilities.RCONUtilities;
import me.lyc8503.rconClient.start.StartRCON;

public class MainFrame {
	public static JFrame frame;
	public static JTabbedPane tabbedPane;
	public static InfoPanel info;
	public static CommandPanel command;
	public static AboutPanel about;
	public static void startMainFrame() {
		System.out.println("MainFrame Init");
		frame = new JFrame("Minecraft RCON Client By lyc8503");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(500, 300));
		frame.setLayout(new FlowLayout());
		frame.getContentPane().add(tabbedPane);
		
		info = new InfoPanel();
		tabbedPane.addTab("Server Info", info);
		command = new CommandPanel();
		tabbedPane.addTab("Execute Command", command);
		about = new AboutPanel();
		tabbedPane.addTab("About", about);
		
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setSize(510, 340);
	}
}

class InfoPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public InfoThread infoThread;
	public String playerList;
	public long ping;
	public Vector<Long> pings = new Vector<Long>();
	
	public InfoPanel() {
		super();
		infoThread = new InfoThread();
	}
	
	public void paint(Graphics g) {
		g.drawString("Server Info", 20, 20);
		g.drawString("Server Address: " + StartRCON.address + ":" + StartRCON.port, 20, 50);
		g.drawString("Player list:", 20, 80);
		g.drawString(playerList, 20, 110);
		g.drawString("Ping: " + ping + " ms", 20, 140);
		for(int i = 1; i < pings.size(); i ++) {
//			System.out.println("Test");
			g.drawLine(20 + i, 250, 20 + i, 250 - (int)(pings.get(i) * 0.33));
		}
	}
	
	public void setPing(long ping) {
		this.ping = ping;
		if(pings.size() > 300) {
			pings.removeElementAt(0);
		}
		pings.addElement(ping);
	}
}

class InfoThread extends Thread{

	public InfoThread() {
		super();
		this.start();
	}
	
	public void run() {
		while(true) {
			long start = System.nanoTime();
			String result = RCONUtilities.executeCommand("list");
			long end = System.nanoTime();
			long delta = (long) ((end - start) / 1000.0d / 1000.0d);
//			System.out.println(delta);
			MainFrame.info.setPing(delta);
			MainFrame.info.playerList = result;
			MainFrame.tabbedPane.updateUI();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				//Ignore
			}
		}
	}
}

class CommandPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public JTextArea messageArea;
	public JTextField commandTextArea;
	
	public CommandPanel() {
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(layout);
		ActionListener l = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String command = MainFrame.command.commandTextArea.getText();
				if(command == null) {
					return;
				}
				if(command.equals("")) {
					return;
				}
				String result = RCONUtilities.executeCommand(command);
				MainFrame.command.commandTextArea.setText("");
				System.out.println("ExecuteCommand:" + command);
				System.out.println("Receive:" + result);
				MainFrame.command.addMsg(result);
			}
		};
		JButton executeButton = new JButton("Execute");
		executeButton.addActionListener(l);
		commandTextArea = new JTextField();
		commandTextArea.addActionListener(l);
		messageArea = new JTextArea();
		messageArea.setEditable(false);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.weighty = 0.9;
		add(new JScrollPane(messageArea), constraints);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.weightx = 0.7;
		constraints.weighty = 0.1;
		add(commandTextArea, constraints);
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.weightx = 0.3;
		constraints.weighty = 0.1;
		add(executeButton, constraints);
	}
	
	public void addMsg(String msg) {
		this.messageArea.setText(this.messageArea.getText() + "\n" + msg);
		MainFrame.tabbedPane.updateUI();
	}
}

class AboutPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public AboutPanel() {
		setLayout(new GridLayout(7, 1));
		add(new JLabel("Minecraft Rcon Client written By lyc8503"));
		
		
		JLabel emailLabel = new JLabel("<html><font color=blue><u>" + "Bug Report: lyc8503@foxmail.com");
		emailLabel.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
			}
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR)); 
			}
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().mail(new URI("mailto:lyc8503@foxmail.com?subject=RCONClientBugReport"));
				} catch (IOException e1) {} catch (URISyntaxException e1) {} 
			}
		});
		add(emailLabel);
		
		
		
		add(new JLabel("Learn more about Minecraft RCON:"));
		JLabel link = new JLabel("<html><font color=blue><u>" + "https://minecraft.gamepedia.com/Server.properties");
		link.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
			}
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR)); 
			}
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new java.net.URI("https://minecraft.gamepedia.com/Server.properties"));
				} catch (IOException e1) {} catch (URISyntaxException e1) {} 
			}
		});
		add(link);
		add(new JLabel("How to use MinecraftRconClient:"));
		JLabel link2 = new JLabel("<html><font color=blue><u>" + "Click Here");
		link2.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
			}
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR)); 
			}
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "1.Enable the RCON Fuction in the Minecraft Server side(You can visit the website above to learn more)\n2.Open this client and switch to the second Tab\"Execute Command\"\n3.Enter the command you want to execute\nYou can also get the info of the server in the \"Server Info\"Tab", "Minecraft RCON Client - Tutorial", JOptionPane.PLAIN_MESSAGE);
			}
		});
		add(link2);
	}
}
