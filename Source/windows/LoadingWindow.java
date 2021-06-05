package windows;

import javax.swing.JFrame;
import java.awt.Window.Type;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class LoadingWindow {

	private JFrame frame;

	//============================================================================
	
	/**
	 * Creates a new instance of this class and returns a reference to it
	 */
	public static LoadingWindow createNew(String serverIP) 
	{
		LoadingWindow window = null;
		
		try 
		{
			window = new LoadingWindow(serverIP);
			window.setVisible(true);
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
	public LoadingWindow(String serverIP) {
		initialize(serverIP);
	}
	
	//--------

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String serverIP) {
		frame = new JFrame();
		frame.setType(Type.POPUP);
		frame.setResizable(false);
		frame.setBounds(100, 100, 281, 143);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblConnectingToServer = new JLabel("Connecting to server...");
		lblConnectingToServer.setHorizontalAlignment(SwingConstants.CENTER);
		lblConnectingToServer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblConnectingToServer.setBounds(10, 11, 246, 38);
		frame.getContentPane().add(lblConnectingToServer);
		
		JLabel ServerNameLabel = new JLabel("");
		ServerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ServerNameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		ServerNameLabel.setBounds(10, 48, 246, 38);
		ServerNameLabel.setText(serverIP);
		frame.getContentPane().add(ServerNameLabel);
	}
	
	public void setVisible(boolean value)
	{
		frame.setVisible(value);
	}
}
