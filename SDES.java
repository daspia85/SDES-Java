import java.util.Scanner;
import java.util.Arrays;

public class SDES {
	public SDES() {}

	/**
	 * @param plaintext a String as plaintext
	 * @return an encrypted message as an array of bytes
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
	 * @param plaintext array of bytes as plaintext
	 * @return an encrypted message as an array of bytes
	 * @author Pial Das
	 */
	public byte[] encrypt(byte[] plaintext) {
		byte[] encrypted_bytes = new byte[plaintext.length];
		for (int i = 0; i < plaintext.length; i++)
			encrypted_bytes[i] = encryptByte(plaintext[i]);
		return encrypted_bytes;
	}

	/**
	 * @param ciphertext array of bytes as ciphertext
	 * @return a decrypted message as an array of bytes
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
	 * @param inp an array of booleans
	 * @return the left half of the inputted array
	 * @author Pial Das
	 */
	private boolean[] lh(boolean[] inp) {
		return Arrays.copyOfRange(inp, 0, inp.length / 2);
	}

	/**
	 * @param inp an array of booleans
	 * @return the right half of the inputted array
	 * @author Pial Das
	 */
	private boolean[] rh(boolean[] inp) {
		return Arrays.copyOfRange(inp, inp.length / 2, inp.length);
	}

	/**
	 * @param x an array of booleans
	 * @param y an array of booleans
	 * @return an array of booleans that is the exclusive OR of the inputted arrays
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

	/**
	 * @param inp an array of booleans
	 * @return an array of booleans as the output of the s0 function
	 * @author Pial Das
	 */
	private boolean[] s0(boolean[] inp) {
		boolean[] output = new boolean[2];

		// To make the variables clear in the SOP equation
		boolean a = inp[0];
		boolean b = inp[1];
		boolean c = inp[2];
		boolean d = inp[3];
		
		output[0] = !a & !b & !c & d | !a & !b & c & d | !a & b & !c & !d | !a & b & c & !d | a & !b & !c & d | a & !b & c & !d | a & b & !c & d | a & b & c & !d | a & b & c & d;  // First bit
		output[1] = !a & !b & !c & !d | !a & !b & !c & d | !a & b & !c & !d | !a & b & !c & d | a & !b & !c & d | a & !b & c & d | a & b & !c & !d | a & b & !c & d | a & b & c & !d;  // Second bit
		return output;
	}

	/**
	 * @param inp an array of booleans
	 * @return an array of booleans as the output of the s1 function
	 * @author Pial Das
	 */
	private boolean[] s1(boolean[] inp) {
		boolean[] output = new boolean[2];

		// To make the variables clear in the SOP equation
		boolean a = inp[0];
		boolean b = inp[1];
		boolean c = inp[2];
		boolean d = inp[3];

		output[0] = !a & !b & !c & d | !a & b & !c & !d | !a & b & !c & d | !a & b & c & !d | !a & b & c & d | a & !b & !c & !d | a & !b & !c & d | a & b & c & d;  // First bit
		output[1] = !a & !b & c & !d | !a & b & !c & d | !a & b & c & !d | !a & b & c & d | a & !b & !c & !d | a & !b & c & d | a & b & !c & !d | a & b & c & d;  // Second bit
		return output;
	}

	/**
	 * @param x the input array of bits
	 * @param k the array of bits as the key
	 * @return an output array of bits as a result of the f function
	 * @author Pial Das
	 */
	private boolean[] f(boolean[] x, boolean[] k) {
		boolean[] ep_x = expPerm(x, new int[]{3, 0, 1, 2, 1, 2, 3, 0});
		boolean[] key_xor = xor(k, ep_x);
		boolean[] left_key_xor = lh(key_xor);
		boolean[] right_key_xor = rh(key_xor);
		boolean[] s_left = s0(left_key_xor);
		boolean[] s_right = s1(right_key_xor);
		boolean[] concat_array = concat(s_left, s_right);
		boolean[] p4_x = expPerm(concat_array, new int[]{1,3,2,0});
		return p4_x;
	}

	private boolean[] feistel(boolean[] x, boolean[] k) {
		return new boolean[8];
	}

	private boolean[] feistelInv(boolean[] y, boolean[] k) {
		return new boolean[8];
	}
}
