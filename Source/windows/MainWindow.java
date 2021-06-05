package windows;

import utilities.*;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollBar;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextPane;

import main.*;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class MainWindow {

	private JFrame frame;
	private JTextField UserInputTextfield;
	private JButton OptionsButton;
	private JTextField UsernameTextfield;
	private JButton DisconnectButton;
	private JButton SendMessageButton;
	private JTextArea MessageTextArea;
	private JScrollPane TextAreaScrollPane;
	private JLabel EncryptedMessagesLabel;

	//============================================================================
	
	/**
	 * Creates a new instance of this class and returns a reference to it
	 */
	public static MainWindow createNew() 
	{
		MainWindow window = null;
		
		try 
		{
			window = new MainWindow();
			window.frame.setVisible(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return window;
	}
	
	//============================================================================

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}
	
	//--------

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Chat Window");
		frame.setResizable(false);
		frame.setBounds(100, 100, 487, 441);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		UserInputTextfield = new JTextField();
		UserInputTextfield.setBounds(23, 319, 335, 20);
		frame.getContentPane().add(UserInputTextfield);
		UserInputTextfield.setColumns(10);
		
		SendMessageButton = new JButton("Send");
		SendMessageButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		SendMessageButton.setBounds(368, 318, 89, 23);
		frame.getContentPane().add(SendMessageButton);
		
		OptionsButton = new JButton("Options");
		OptionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				OptionsWindow.createNew();
			}
		});
		OptionsButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		OptionsButton.setBounds(368, 352, 89, 23);
		frame.getContentPane().add(OptionsButton);
		
		UsernameTextfield = new JTextField();
		UsernameTextfield.setText("User");
		UsernameTextfield.setColumns(10);
		UsernameTextfield.setBounds(85, 28, 96, 20);
		frame.getContentPane().add(UsernameTextfield);
		
		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(24, 32, 81, 14);
		frame.getContentPane().add(lblNewLabel);
		
		DisconnectButton = new JButton("Disconnect");
		DisconnectButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		DisconnectButton.setBounds(368, 60, 89, 23);
		frame.getContentPane().add(DisconnectButton);
		
		TextAreaScrollPane = new JScrollPane();
		TextAreaScrollPane.setBounds(23, 60, 335, 247);
		frame.getContentPane().add(TextAreaScrollPane);
		
		MessageTextArea = new JTextArea();
		TextAreaScrollPane.setViewportView(MessageTextArea);
		MessageTextArea.setLineWrap(true);
		MessageTextArea.setEditable(false);
		
		EncryptedMessagesLabel = new JLabel("Note: Your messages are currently being encrypted.");
		EncryptedMessagesLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		EncryptedMessagesLabel.setBounds(24, 339, 259, 14);
		EncryptedMessagesLabel.setVisible(false);
		frame.getContentPane().add(EncryptedMessagesLabel);
	}
	
	/**
	 * Displays a message in the User's chat window
	 */
	public void showDisconnected()
	{
		frame.setTitle(frame.getTitle() + " (Disconnected)");
		
		SendMessageButton.setEnabled(false);
		DisconnectButton.setEnabled(false);
		
		displayMessage("[You have been disconnected from the server]");
	}
	
	/**
	 * Displays a message in the User's chat window
	 */
	public void displayMessage(String message)
	{
		JScrollBar vertical = TextAreaScrollPane.getVerticalScrollBar();
		TextAreaScrollPane.getVerticalScrollBar().setValue(vertical.getMaximum());
		
		MessageTextArea.append(String.format("%s\n", message));
	}
	
	/**
	 * Displays a notice label that the user's messages are being encrypted
	 */
	public void displayEncryptedNotice()
	{
		EncryptedMessagesLabel.setVisible(true);
	}
	
	/**
	 * Sets an ActionLister to the "Disconnect" button in the window
	 * 
	 * 
	 * ActionListener format:
	 	   new ActionListener() 
		   {
				public void actionPerformed(ActionEvent e) 
				{
				}
		   }	
	 */
	public void setDisconnectHandler(ActionListener function)
	{
		DisconnectButton.addActionListener(function);
	}
	
	/**
	 * Sets an ActionLister to the "Send Message" button in the window
	 * 
	 * 
	 * ActionListener format:
	 	   new ActionListener() 
		   {
				public void actionPerformed(ActionEvent e) 
				{
				}
		   }	
	 */
	public void setMessageSendHandler(ActionListener function)
	{
		SendMessageButton.addActionListener(function);
	}
	
	/**
	 * Grabs the String which is located within the User's chat input Textfield and returns it.
	 */
	public String getMessageText()
	{
		return UserInputTextfield.getText();
	}
	
	/**
	 * Grabs the User's specific username and returns it.
	 */
	public String getUsername()
	{
		return UsernameTextfield.getText();
	}
}
