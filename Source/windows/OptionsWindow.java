//Client

package windows;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.*;

import javax.swing.JTextField;


import javax.swing.JButton;
import java.awt.Color;

import main.*;
import settings.*;
import utilities.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class OptionsWindow 
{
	
	//============================================================================
	
	/**
	 * Creates a new instance of this class and returns a reference to it
	 */
	public static OptionsWindow createNew() 
	{
		OptionsWindow window = null;
		
		try 
		{
			window = new OptionsWindow();
			window.frame.setVisible(true);
		} 
		catch (Exception e) 
		{
			Utils.showCriticalError(e);
		}
		
		return window;
	}
		
	//============================================================================

	private JFrame frame;
	private JTextField IPTextField;
	private JTextField PortTextField;
	
	private Map<String, String> initialSettings;
	private JTextField KeyTextField;

	/**
	 * Create the application.
	 */
	public OptionsWindow() 
	{
		initialize();
		
		try
		{
			initialSettings = Main.settings.read();
			
			String IP = initialSettings.get(Settings.IP);
			String port = initialSettings.get(Settings.PORT);
			
			IPTextField.setText(!IP.equals("null") ? IP : "");
			PortTextField.setText(!port.equals("null") ? port : "");
			
			JLabel lblServerPort = new JLabel("Server Port: (1-65535)");
			lblServerPort.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblServerPort.setBounds(21, 82, 152, 14);
			frame.getContentPane().add(lblServerPort);
			
			KeyTextField = new JTextField();
			KeyTextField.setText((String) null);
			KeyTextField.setColumns(10);
			KeyTextField.setBounds(21, 169, 179, 20);
			frame.getContentPane().add(KeyTextField);
			
			JTextArea txtrIfYouProvide = new JTextArea();
			txtrIfYouProvide.setBackground(UIManager.getColor("CheckBox.background"));
			txtrIfYouProvide.setLineWrap(true);
			txtrIfYouProvide.setFont(new Font("Tahoma", Font.PLAIN, 8));
			txtrIfYouProvide.setText("If you provide an Encryption key, the program will attempt to encrypt each one of your messages and decrypt all messages sent by others.");
			txtrIfYouProvide.setEditable(false);
			txtrIfYouProvide.setBounds(21, 191, 292, 30);
			frame.getContentPane().add(txtrIfYouProvide);
			
			JButton CloseWindowButton = new JButton("Close Window");
			CloseWindowButton.setBackground(Color.LIGHT_GRAY);
			CloseWindowButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
			CloseWindowButton.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					if (Main.numberOfClients() == 0) //If this is the only window open, close the program
						System.exit(0);
					
					close();
				}
			});
			CloseWindowButton.setBounds(212, 249, 101, 23);
			frame.getContentPane().add(CloseWindowButton);
		}
		catch (Exception e)
		{
			Utils.showCriticalError(e);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("New Connection");
		frame.setResizable(false);
		frame.setBounds(100, 100, 347, 413);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblServerIP = new JLabel("Server IP / Domain:");
		lblServerIP.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblServerIP.setBounds(21, 23, 132, 14);
		frame.getContentPane().add(lblServerIP);
		
		IPTextField = new JTextField();
		IPTextField.setBounds(20, 48, 179, 20);
		frame.getContentPane().add(IPTextField);
		IPTextField.setColumns(10);
		
		JLabel lblEncryptionKey = new JLabel("Encryption Key: (optional)");
		lblEncryptionKey.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEncryptionKey.setBounds(21, 138, 192, 20);
		frame.getContentPane().add(lblEncryptionKey);
		
		PortTextField = new JTextField();
		PortTextField.setColumns(10);
		PortTextField.setBounds(21, 107, 40, 20);
		frame.getContentPane().add(PortTextField);
		
		JButton NewConnectionButton = new JButton("Connect to Server");
		NewConnectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String encryptionKey = KeyTextField.getText();
				
				Client client = null;
				
				if (encryptionKey.equals(""))
					client = new Client(getIP(), getPort()); 
				else
					client = new Client(getIP(), getPort(), encryptionKey);
				
				Main.launchNewClient(client); //Starts an instance of the chat client with the specified parameters
				
				close();
			}
		});
		NewConnectionButton.setBackground(Color.LIGHT_GRAY);
		NewConnectionButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		NewConnectionButton.setBounds(21, 312, 292, 41);
		frame.getContentPane().add(NewConnectionButton);
		
		JButton SaveSettingsButton = new JButton("Save Settings");
		SaveSettingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				initialSettings.put(Settings.IP, getIP());
				initialSettings.put(Settings.PORT, PortTextField.getText());
				
				try 
				{
					Main.settings.write(initialSettings);
				} 
				catch (Exception e1) 
				{
					Utils.showErrorBox("The program encountered an error when attempting to save the current settings.", "Unable to save settings");
				}
				
				Utils.showInfoBox("The current connection settings have been saved!", "Settings Saved");
			}
		});
		SaveSettingsButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		SaveSettingsButton.setBackground(Color.LIGHT_GRAY);
		SaveSettingsButton.setBounds(21, 278, 292, 25);
		frame.getContentPane().add(SaveSettingsButton);
	}
	
	public String getIP()
	{
		return IPTextField.getText();
	}
	
	public int getPort()
	{
		return Integer.parseInt(PortTextField.getText());
	}
	
	public void close()
	{
		frame.dispose();
	}
}
