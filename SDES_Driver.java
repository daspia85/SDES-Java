import java.util.Scanner;

public class SDES_Driver {
	public static void main(String[] args) {
		SDES sdes = new SDES();
		String plaintext_string = "h";
		System.out.println("Plaintext: " + plaintext_string);
		byte[] ciphertext = sdes.encrypt(plaintext_string);
		String ciphertext_string = sdes.byteArrayToCharString(ciphertext);
		System.out.println("Ciphertext: " + ciphertext_string);
		System.out.print("Ciphertext Bytes: ");
		sdes.show(ciphertext);
		byte[] plaintext = sdes.decrypt(ciphertext);
		String decrypt_string = sdes.byteArrayToCharString(plaintext);
		System.out.println("Decrypted Message: " + decrypt_string);
	}
}
