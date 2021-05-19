//Daniel Strauch 300206805
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class encryptFile {


	// The crypt() function encrypts or decrypts a byte array input, using
	// an 16-byte initialization vector (init), 16-byte password (pass),
	// and an integer mode (either Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE)
	public static byte[] crypt(byte[] input, byte[] init, byte[] pass, int mode) {
		byte[] output = null;
		try {
			Cipher cip = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cip.init(mode, new SecretKeySpec(pass, "AES"), new IvParameterSpec(init));
			output = cip.doFinal(input);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}

	//encryption same as crypt but using DES
	public static byte[] cryptDES(byte[] input, byte[] init, byte[] pass, int mode) {
		byte[] output = null;
		try {
			Cipher cip = Cipher.getInstance("DES/CBC/PKCS5PADDING");
			cip.init(mode, new SecretKeySpec(pass, "DES"), new IvParameterSpec(init));
			output = cip.doFinal(input);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}


	// The cryptFile() function opens a file at a specified string path,
	// then passes in the init, pass, and mode values to the crypt() function
	// to either encrypt or decrypt the contents of the file. It then writes
	// the encrypted or decrypted data back to the file.
	public static void cryptFile(String path, byte[] init, byte[] pass, int mode) {
		byte[] filebyte = null;
		try {
			filebyte = Files.readAllBytes(Paths.get(path));
			filebyte = crypt(filebyte, init, pass, mode);
			Files.write(Paths.get(path), filebyte);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//encryption File same as cryptFile but using DES
	public static void cryptFileDES(String path, byte[] init, byte[] pass, int mode) {
		byte[] filebyte = null;
		try {
			filebyte = Files.readAllBytes(Paths.get(path));
			filebyte = cryptDES(filebyte, init, pass, mode);
			Files.write(Paths.get(path), filebyte);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// The menu() function provides a user interface for the script. It should
	// prompt the user to enter a file path, 16-byte initialization vector,
	// 16-byte password, and a mode (encrypt or decrypt). If the password or
	// initialization vector are too short or too long, the function should
	// re-prompt the user to re-enter the value.
	public static void menu() {
		Scanner input = new Scanner(System.in);
		String init = null;
		String pass = null;
		String modeString = null;
		String algorithm = null;
		int numChars = 0;

		System.out.println("Enter File Path:");  
		String path = input.next();

		do {
			System.out.println("Enter Encryption Algorithm (AES or DES):");
			algorithm = input.next();
		} while(!algorithm.equals("AES") && !algorithm.equals("DES"));

		if(algorithm.equals("AES")) numChars = 16;
		if(algorithm.equals("DES")) numChars = 8;

		do {
			System.out.println("Enter Initialization Vector (" + numChars +" chars):");
			init = input.next();
		} while(init.length() != numChars);

		do {
			System.out.println("Enter Password (" + numChars +" chars):");
			pass = input.next();
		} while(pass.length() != numChars);

		do {
			System.out.println("Enter mode (encrypt or decrypt):");
			modeString = input.next();
		} while(!modeString.equals("encrypt") && !modeString.equals("decrypt"));

		int mode = 0;
		if(modeString.equals("encrypt")) mode = Cipher.ENCRYPT_MODE;
		if(modeString.equals("decrypt")) mode = Cipher.DECRYPT_MODE;

		if(algorithm.equals("AES")) cryptFile(path, init.getBytes(), pass.getBytes(), mode);
		if(algorithm.equals("DES")) cryptFileDES(path, init.getBytes(), pass.getBytes(), mode);

		System.out.println("Finished");
	}

	// Just need to call the menu() function here
	public static void main(String[] args) {
		// Tests for crypt();
//		byte[] plain = "Hello World".getBytes();
//		byte[] pass = "aaaabbbbccccdddd".getBytes();
//		byte[] init = "gggggggggggggggg".getBytes();
//		byte[] cipher = crypt(plain, init, pass, Cipher.ENCRYPT_MODE);
//		byte[] decrypted = crypt(cipher, init, pass, Cipher.DECRYPT_MODE);
		// This should print "Hello World"
//		System.out.println(new String(decrypted));

		//cryptFile("src/test.txt", init, pass, Cipher.ENCRYPT_MODE);
		//cryptFile("src/test.txt", init, pass, Cipher.DECRYPT_MODE);

		// Uncomment below to test menu section once complete
		menu();
	}

}
