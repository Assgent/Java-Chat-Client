//Client

package main;

import windows.*;
import utilities.*;
import networking.*;

import java.io.*;
import java.net.*;
import java.util.concurrent.CompletableFuture;
import java.awt.event.*;

public class Client extends Thread
{
	private String destination;
	private int port;
	private String encryptionSecret;
	
	public boolean encryptionEnabled;
	private boolean stopRecv = false;
	
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private PacketReader reciever;
	
	private MainWindow mainWindow;
	
	public Client(String IPin, int portIn)
	{
		destination = IPin;
		port = portIn;
		encryptionEnabled = false;
	}
	
	public Client(String IPin, int portIn, String secret)
	{
		destination = IPin;
		port = portIn;
		encryptionSecret = secret;
		encryptionEnabled = true;
	}
	
	@Override
	public void run() 
	{
		LoadingWindow loadingWindow = LoadingWindow.createNew(destination);
		
		boolean connectionStarted = startConnection();
		
		loadingWindow.setVisible(false);
		
		if (connectionStarted)
		{
			mainWindow = MainWindow.createNew();
			
			if (encryptionEnabled)
				mainWindow.displayEncryptedNotice();
			
			mainWindow.setMessageSendHandler(new ActionListener() {
				public void actionPerformed(ActionEvent e) 
				{
					sendMessage(String.format("<%s> %s", mainWindow.getUsername(), mainWindow.getMessageText()));
				}
			});
			
			mainWindow.setDisconnectHandler(new ActionListener() {
				public void actionPerformed(ActionEvent e) 
				{
					disconnect(false);
				}
			});
			
			recieveMessages();
		}
		else if (Main.numberOfClients() <= 1) //If this client fails to connect and this is the only running client, don't launch connection window
		{
			OptionsWindow.createNew();
		}
	}
	
	//--------
	
	/**
	 * Starts the connection between this instance of Client and the Server. Returns true if successful, returns false if unsuccessful.
	 */
	private boolean startConnection() 
	{
		try
		{
			socket = new Socket(destination, port);
			
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			reciever = new PacketReader(in);
		}
		catch (UnknownHostException e)
		{
			Utils.showErrorBox(String.format("The server \"%s\" could not be found.", destination), "Connection Error: Unknown Host");
			return false;
		}
		catch (ConnectException e)
		{
			Utils.showErrorBox("A connection to the server could not be established.", "Connection Error");
			return false;
		}
		catch (IllegalArgumentException e)
		{
			Utils.showErrorBox(String.format("\"%d\" is an invalid port. Ports range from 1 to 65335. For more information, please check with the server owner!", port), "Connection Error: Invalid Port");
			return false;
		}
		catch (Exception e)
		{
			return false;
		}
		
		return true;
	}
	
	public void disconnect(boolean isExitingProgram)
	{
		stopReciever();
		
		try
		{
			out.write(Packet.getDisconnectionPacket().getBytes());
			
			if (!isExitingProgram)
				mainWindow.showDisconnected();
		}
		catch (Exception e)
		{
			//pass
		}
	}
	
	private void sendMessage(String message)
	{
		Packet messageToSend = encryptionEnabled ? new Packet(message, encryptionSecret) : new Packet(message);
		
		try
		{
			out.write(messageToSend.getBytes());
		}
		catch (IOException e)
		{
			mainWindow.displayMessage("ERROR: Message failed to send. See error below:\n" + e.toString());
		}
	}
	
	private void recieveMessages()
	{
		CompletableFuture.runAsync(reciever);
		
		while (!stopRecv)
		{
			Packet newPacket = reciever.next();
			
			if (newPacket == null)
			{
				continue;
			}
			else if (newPacket.getType() == Packet.Type.DISCONNECT_FROM_SERVER)
			{
				mainWindow.showDisconnected();
				
				reciever.stop();
			}
			else
			{
				if (encryptionEnabled)
					newPacket.setEncryptionSecret(encryptionSecret);
				
				mainWindow.displayMessage(newPacket.toString());
			}
		}
	}
	
	private void stopReciever()
	{
		stopRecv = true;
		reciever.stop();
	}
}
