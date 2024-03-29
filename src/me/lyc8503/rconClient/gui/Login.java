package me.lyc8503.rconClient.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Login {
	
	public static GridBagLayout layout;
	public static GridBagConstraints constraints;
	public static JTextField addressTextField;
	public static JTextField portTextField;
	public static JTextField passwordTextField;
	public static JButton submitButton;
	public static JButton exitButton;
	public static boolean waitFlag;
	
	public static RCONData showInput() {
	    waitFlag = true;

	    String savedServer = "";
        String savedPort = "25575";
        String savedPassword = "";
        try {
            File tempFile = new File(System.getProperty("java.io.tmpdir") + "rcon-server.dat");
            FileReader reader = new FileReader(tempFile);
            Scanner sc = new Scanner(reader);
            savedServer = sc.nextLine();
            savedPort = sc.nextLine();
            savedPassword = sc.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

		JFrame frame = new JFrame("Minecraft RCON Client - Login");
		frame.setSize(350, 150);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		frame.setLayout(layout);
		
		JLabel addressLabel = new JLabel("Server Address:");
		JLabel passwordLabel = new JLabel("Password:");
		JLabel portLabel = new JLabel("Server Port");
		addressTextField = new JTextField(savedServer);
		portTextField = new JTextField(savedPort.equals("") ? "25575" : savedPort);
		passwordTextField = new JTextField(savedPassword);
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				waitFlag = false;
			}
		});
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.ipadx = 80;
		constraints.gridx = 0;
		constraints.gridy = 0;
		frame.getContentPane().add(addressLabel, constraints);
		constraints.gridx = 1;
		constraints.gridy = 0;
		frame.getContentPane().add(addressTextField, constraints);
		constraints.gridx = 0;
		constraints.gridy = 1;
		frame.getContentPane().add(portLabel, constraints);
		constraints.gridx = 1;
		constraints.gridy = 1;
		frame.getContentPane().add(portTextField, constraints);
		constraints.gridx = 0;
		constraints.gridy = 2;
		frame.getContentPane().add(passwordLabel, constraints);
		constraints.gridx = 1;
		constraints.gridy = 2;
		frame.getContentPane().add(passwordTextField, constraints);
		constraints.gridx = 0;
		constraints.gridy = 3;
		frame.getContentPane().add(submitButton, constraints);
		constraints.gridx = 1;
		constraints.gridy = 3;
		frame.getContentPane().add(exitButton, constraints);
		
		frame.setVisible(true);
		while(waitFlag) {
			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}
		}

        try {
            File tempFile = new File(System.getProperty("java.io.tmpdir") + "rcon-server.dat");
            tempFile.createNewFile();
            FileWriter writer = new FileWriter(tempFile);
            writer.write(addressTextField.getText() + "\n" + portTextField.getText() + "\n" + passwordTextField.getText());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

		frame.dispose();
		return new RCONData(addressTextField.getText(), portTextField.getText(), passwordTextField.getText());
	}
	
}
