import java.util.Scanner;
import java.util.Arrays;

public class SDES {
	public SDES() {}

	/**
	 * @author Pial Das
	 */
	public byte[] encrypt(String plaintext) {
		byte[] ascii = new byte[plaintext.length()];
		for (int i = 0; i < plaintext.length(); i++)
			ascii[i] = (byte) plaintext.charAt(i);
		byte[] encrypted_array = encrypt(ascii);
		return encrypted_array;
	}

	/**
	 * @author Pial Das
	 */
	public byte[] encrypt(byte[] plaintext) {
		byte[] encrypted_bytes = new byte[plaintext.length];
		for (int i = 0; i < plaintext.length; i++)
			encrypted_bytes[i] = encryptByte(plaintext[i]);
		return encrypted_bytes;
	}

	/**
	 * @author Pial Das
	 */
	public byte[] decrypt(byte[] ciphertext) {
		byte[] decrypted_bytes = new byte[ciphertext.length];
		for (int i = 0; i < ciphertext.length; i++)
			decrypted_bytes[i] = decryptByte(ciphertext[i]);
		return decrypted_bytes;
	}

	private byte encryptByte(byte plainByte) {
		return (byte) 1;
	}

	private byte decryptByte(byte cipherByte) {
		return (byte) 1;
	}

	public void show(byte[] digits) {
	}

	public void show(boolean[] binaries) {
	}

	private boolean[] expPerm(boolean[] inp, int[] epv) {
		return new boolean[1];
	}

	private void getKey10(Scanner scanner) {
	}

	public String byteArrayToString(byte[] inp) {
		return "";
	}

	/**
	 * @author Pial Das
	 */
	private boolean[] lh(boolean[] inp) {
		return Arrays.copyOfRange(inp, 0, inp.length / 2);
	}

	/**
	 * @author Pial Das
	 */
	private boolean[] rh(boolean[] inp) {
		return Arrays.copyOfRange(inp, inp.length / 2, inp.length);
	}

	/**
	 * @author Pial Das
	 */
	private boolean[] xor(boolean[] x, boolean[] y) {
		boolean[] shorter, longer;
		if (x.length < y.length) {
			shorter = x;
			longer = y;
		} else {
			shorter = y;
			longer = x;
		}
		boolean[] xor_array = new boolean[longer.length];
		int diff_length = longer.length - shorter.length;
		for (int index = 0; index < xor_array.length; index++) {
			if (index - diff_length < 0)
				xor_array[index] = longer[index] ^ false;
			else
				xor_array[index] = shorter[index - diff_length] ^ longer[index];
		}
		return xor_array;
	}

	private boolean[] concat(boolean[] x, boolean[] y) {
		return new boolean[1];
	}

	public byte bitArrayToByte(boolean[] bitArray) {
		return (byte) 1;
	}

	public boolean[] byteToBitArray(byte number, int size) {
		return new boolean[1];
	}

	private boolean[] f(boolean[] x, boolean[] k) {
		return new boolean[4];
	}

	private boolean[] feistel(boolean[] x, boolean[] k) {
		return new boolean[8];
	}
}
