//Client

package utilities;

import java.util.*;
import java.security.*;
import java.security.spec.*;

import javax.crypto.*;
import java.nio.charset.*;
import javax.crypto.spec.*;

/**
 * A collection of methods which can encrypts or decrypts a string using AES 256 Encryption
 */
public class Encryption //All of this code is from https://howtodoinjava.com/java/java-security/aes-256-encryption-decryption/. I have no idea how it works, but it works.
{
	private static final String SALT = "g54795t94n9flinetz4fli8yt4eg";
	 
	public static String encrypt(String strToEncrypt, String secret)
	{
		try 
		{
			final byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			IvParameterSpec ivspec = new IvParameterSpec(iv);
	 
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(secret.toCharArray(), SALT.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
	 
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			return Base64.getEncoder()
	          .encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
	    } 
		catch (Exception e) 
		{
			e.printStackTrace();
	    }
		
		return null;
	}
	
	public static String decrypt(String strToDecrypt, String secret) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
	{
		final byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		IvParameterSpec ivspec = new IvParameterSpec(iv);
 
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		KeySpec spec = new PBEKeySpec(secret.toCharArray(), SALT.getBytes(), 65536, 256);
		SecretKey tmp = factory.generateSecret(spec);
		SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
 
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
		return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
	}
}
