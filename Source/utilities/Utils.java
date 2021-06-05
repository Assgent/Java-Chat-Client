//Client

package utilities;

import java.util.*;

import javax.swing.JOptionPane;

public class Utils 
{
	/**
	 * Displays a pop-up box on the computer screen.	
	*/
	public static void showBox(String message, String title, int type)
    {
        JOptionPane.showMessageDialog(null, message, title, type);
    }
	
	public static void showInfoBox(String message, String title)
    {
		showBox(message, title, JOptionPane.INFORMATION_MESSAGE);
    }
	
	public static void showWarningBox(String message, String title)
    {
		showBox(message, title, JOptionPane.WARNING_MESSAGE);
    }
	
	public static void showErrorBox(String message, String title)
    {
		showBox(message, title, JOptionPane.ERROR_MESSAGE);
    }
	
	//====================================================================================================
	
	/**
	 * Displays a generic pop-up input prompt.
	*/
	public static String showInputDialog(String message)
    {
		return JOptionPane.showInputDialog(message);  
    }
	
	//====================================================================================================
	
	/**
	 * Displays a warning pop-up box containing	the program error and exits the program.
	*/
	public static void showCriticalError(Exception e)
    {
		e.printStackTrace();
		showErrorBox(String.format("%s\n\nThis program will now exit.", e.toString()), "Critical Error");
		System.exit(1);
    }
	
	//====================================================================================================
	
	/**
	 * Converts a List<Byte> to a byte[].
	*/
	public static byte[] byteListToArray(List<Byte> byteList) //Why is there no native support for this???
    {
		int size = byteList.size(); 
		
		byte[] byteArray = new byte[size];
		
		for (int i = 0; i < size; i++)
		{
			byteArray[i] = byteList.get(i);
		}
		
		return byteArray;
    }
	
	/**
	 * Converts a byte[] to a List<Byte>.
	*/
	public static List<Byte> byteArrayToList(byte[] byteArray) //Why is there no native support for this???
    {
		ArrayList<Byte> byteList = new ArrayList<Byte>();
		
		for (byte b : byteArray)
		{
			byteList.add(b);
		}
		
		return byteList;
    }
	
	/**
	 * Converts a Byte[] to a byte[].
	*/
	public static byte[] arrayToByteArray(Object[] array) //Why is there no native support for this???
	{
		int size = array.length; 
		
		byte[] byteArray = new byte[size];
		
		for (int i = 0; i < size; i++)
		{
			byteArray[i] = (Byte)array[i];
		}
		
		return byteArray;
	}
}
