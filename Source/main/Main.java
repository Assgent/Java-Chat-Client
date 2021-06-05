package main;

import windows.*;
import settings.*;

import java.util.*;
import java.util.concurrent.*;

public class Main 
{
	public static final Settings settings = new Settings(Settings.FILENAME);
	
	//-----
	
	private static final Set<Client> clients = ConcurrentHashMap.newKeySet(); //Thread-safe HashSet
	
	/**
	 * Function that searches for and removes inactive Client threads
	 */
	private final static TimerTask removeDeadThreads = new TimerTask() {
		@Override
		public void run() 
		{
			for (Iterator<Client> i = clients.iterator(); i.hasNext();) 
			{
				Client client = i.next();
			    
			    if (!client.isAlive()) 
			    {
			    	clients.remove(client);
			    }
			}
		}
	};
	
	//-----
	
	public static void main(String[] args) 
	{
		(new Timer()).schedule(removeDeadThreads, 20 * 1000, 40 * 1000); //Run function that deletes dead clients every 40 seconds
		
		Runtime.getRuntime().addShutdownHook(new Thread()
	    {
			@Override
	    	public void run()
	    	{
				for (Iterator<Client> i = clients.iterator(); i.hasNext();) 
				{
					Client client = i.next();
				    
				    client.disconnect(true);
				}
	    	}
	    });
		
		OptionsWindow.createNew();
	}
	
	public static void launchNewClient(Client newClient)
	{
		clients.add(newClient);
		
		newClient.start();
	}
	
	public static int numberOfClients()
	{
		return clients.size();
	}
}
