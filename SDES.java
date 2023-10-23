import java.util.Scanner;

public class SDES {
	public SDES() {}

	public byte[] encrypt(String plaintext) {
		byte[] ascii = new byte[plaintext.length()];
		for (int i = 0; i < plaintext.length(); i++)
			ascii[i] = (byte) plaintext.charAt(i);
		byte[] encrypted_array = encrypt(ascii);
		return encrypted_array;
	}

	public byte[] encrypt(byte[] plaintext) {
		return new byte[1];
	}

	public byte[] decrypt(byte[] ciphertext) {
		return new byte[1];
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

	private boolean[] lh(boolean[] inp) {
		return new boolean[1];
	}

	private boolean[] rh(boolean[] inp) {
		return new boolean[1];
	}

	private boolean[] xor(boolean[] x, boolean[] y) {
		return new boolean[1];
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
