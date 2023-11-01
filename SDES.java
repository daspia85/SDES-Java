import java.util.Scanner;
import java.util.Arrays;

public class SDES {
	public SDES() {}

	/**
	 * Encrypt a given plaintext message that's a String and return the corresponding ciphertext. The characters of the string will be converted as ASCII (byte) values and be encrypted.
	 *
	 * @param plaintext a String as plaintext
	 * @return an encrypted message as an array of bytes
	 * @author Pial Das
	 */
	public byte[] encrypt(String plaintext) {
		byte[] ascii = new byte[plaintext.length()];
		for (int i = 0; i < plaintext.length(); i++)  // Take each character and convert it into a byte (its ASCII value) and store it into a byte array. The array will be passed to the other encrypt method.
			ascii[i] = (byte) plaintext.charAt(i);
		byte[] encrypted_array = encrypt(ascii);
		return encrypted_array;
	}

	/**
	 * Encrypt a given plaintext message that's an array of bytes and return the corresponding ciphertext.
	 *
	 * @param plaintext array of bytes as plaintext
	 * @return an encrypted message as an array of bytes
	 * @author Pial Das
	 */
	public byte[] encrypt(byte[] plaintext) {
		byte[] encrypted_bytes = new byte[plaintext.length];
		for (int i = 0; i < plaintext.length; i++)  // Individually, encrypt each byte (each element of the array) and return the concatenation of the results as an array.
			encrypted_bytes[i] = encryptByte(plaintext[i]);
		return encrypted_bytes;
	}

	/**
	 * Decrypt a given ciphertext message that's an array of bytes and return the corresponding plaintext.
	 *
	 * @param ciphertext array of bytes as ciphertext
	 * @return a decrypted message as an array of bytes
	 * @author Pial Das
	 */
	public byte[] decrypt(byte[] ciphertext) {
		byte[] decrypted_bytes = new byte[ciphertext.length];
		for (int i = 0; i < ciphertext.length; i++)  // Individually, decrypt each byte (each element of the array) and return the concatenation of the results as an array.
			decrypted_bytes[i] = decryptByte(ciphertext[i]);
		return decrypted_bytes;
	}

	/**
	 * Encrypts a single byte using SDES
	 * @param byte as plainByte
	 * @return A byte of cyphertext
	 * @author Riley Miller
	 */
	private byte encryptByte(byte plainByte) {
		boolean[] bitArray = byteToBitArray(plainByte, 8);
		
		int[] IP = {1,5,2,0,3,7,4,6};
		bitArray = expPerm(bitArray, IP);
		
		boolean[] left0 = lh(bitArray);
		boolean[] right0 = rh(bitArray);
		
		boolean[] left1 = right0;
		boolean[] right1 = xor(left0, feistel(right0, key10));
		
		boolean[] left2 = right1;
		boolean[] right2 = xor(left1, feistel(right1, key10));
		
		int[] iIP = {3,0,2,4,6,1,7,5};
		bitArray = expPerm(bitArray, iIP);
		
		plainByte = bitArrayToByte(bitArray);
		return plainByte;
	}
	
	/**
	 * Decrypts a single byte using SDES
	 * @param byte as cipherByte
	 * @return A byte of plainText
	 * @author Riley Miller
	 */
	private byte decryptByte(byte cipherByte) {
		boolean[] bitArray = byteToBitArray(cipherByte, 8);
		int[] IP = {1,5,2,0,3,7,4,6};
		bitArray = expPerm(bitArray, IP);
		
		boolean[] left0 = lh(bitArray);
		boolean[] right0 = rh(bitArray);
		
		boolean[] left1 = right0;
		boolean[] right1 = xor(left0, feistelInv(right0, key10));
		
		boolean[] left2 = right1;
		boolean[] right2 = xor(left1, feistelInv(right1, key10));
		
		int[] iIP = {3,0,2,4,6,1,7,5};
		bitArray = expPerm(bitArray, iIP);
		
		cipherByte = bitArrayToByte(bitArray);
		return cipherByte;
	}

	/**
	 * Outputs an array of bits as 1s and 0s
	 * @param booleanArray as binaries
	 * @author Riley Miller
	 */
	public void show(boolean[] binaries) {
		for (int i = 0; i < binaries.length; i++)
		{
			System.out.print(binaries[i] == true ? "1" : "0");
		}
		System.out.println("");
	}
	
	/**
	 * Outputs an array of bytes as standard output
	 * @param byteArray as digits
	 * @author Riley Miller
	 */
	public void show(byte[] digits) {
		for (int i = 0; i < digits.length-1; i++)
		{
			System.out.print(digits[i] + " ");
		}
		System.out.println(digits[digits.length-1]);
	}

	/**
	 * Expands or permutes from the inp bitArray, producing an expanded/permuted/selected bitArray
	 * @param booleanArray as input, booleanArray as epv
	 * @return a permuted/expanded/selected bitArray, or null if there is an error
	 * @author Riley Miller
	 */
	private boolean[] expPerm(boolean[] inp, int[] epv) {
		try {
			boolean[] newBitArray = inp;
			for (int i = 0; i < inp.length; i++)
			{
				newBitArray[i] = inp[epv[i]];
			}
			return newBitArray;
		} catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Out of Bounds Occurred");
			return null;
		}
		return null;
	}
	
	static boolean[] key10;
	/**
	 * Takes a 10 character input of 1s and 0s and stores them as booleans in key10 
	 * @param Scanner object as scanner
	 * @author Riley Miller
	 */
	private void getKey10(Scanner scanner) {
		
		String scanLine;
		boolean done = false;
		while (done == false)
		{
			scanLine = scanner.nextLine();
			if (scanLine.length() == 10)
			{
				done = true;
				for (char letter : scanLine.toCharArray())
				{
					if (letter != '0' && letter != '1')
						done = false;
				}
			}
			
			if (done == true)
			{
				char[] temp = scanLine.toCharArray();
				for (int i = 0 ; i < temp.length; i++)
				{
					if (temp[i] == '0')
					{
						key10[i] = false;
					}
					else
					{
						key10[i] = true;
					}
				}
			}
		}
	}

	public String byteArrayToString(byte[] inp) {
		return "";
	}

	/**
	 * Given an array of bits, return only the left helf of the array.
	 *
	 * @param inp an array of booleans
	 * @return the left half of the inputted array
	 * @author Pial Das
	 */
	private boolean[] lh(boolean[] inp) {
		return Arrays.copyOfRange(inp, 0, inp.length / 2);  // Take the index from 0 to the half-way point and copy the elements to another array to return.
	}

	/**
	 * Given an array of bits, return only the right half of the array.
	 *
	 * @param inp an array of booleans
	 * @return the right half of the inputted array
	 * @author Pial Das
	 */
	private boolean[] rh(boolean[] inp) {
		return Arrays.copyOfRange(inp, inp.length / 2, inp.length);  // Take the index from the half-way point to the end of the array and copy the elements to another array to return.
	}

	/**
	 * Perform the exclusive OR (XOR) boolean operation bitwise between two arrays of bits. The two bit arrays can be of different sizes of each other. The method will behave as if it's prepending the shorter array with 0's (assuming higher-to-lower order of bits going left to right).
	 *
	 * @param x an array of booleans
	 * @param y an array of booleans
	 * @return an array of booleans that is the exclusive OR of the inputted arrays
	 * @author Pial Das
	 */
	private boolean[] xor(boolean[] x, boolean[] y) {
		boolean[] shorter, longer;
		if (x.length < y.length) {  // Determine which array is shorter/longer.
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
				xor_array[index] = longer[index] ^ false;  // Essentially, we right-hand align our two arrays and perform the bitwise XOR on each index. If the index is outside the shorter array than the bit will be assumed to be 0.
			else
				xor_array[index] = shorter[index - diff_length] ^ longer[index];  // This is the proper alignment for when the index exists in both arrays.
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
	 * This is the s0 function that the "round" function depends on for its calculations. The method takes in a 4-bit array of bits and returns a 2-bit array of bits. The 2-bit array is derived by representing the truth table for s0 as two Sum Of Products (SOP) equations. The derivation for these specific equations can be found in this repository at doc/sbox/.
	 *
	 * @param inp an array of booleans
	 * @return an array of booleans as the output of the s0 function
	 * @author Pial Das
	 */
	private boolean[] s0(boolean[] inp) {
		boolean[] output = new boolean[2];

		// To make the variables clear in the SOP equation.
		boolean a = inp[0];
		boolean b = inp[1];
		boolean c = inp[2];
		boolean d = inp[3];
		
		output[0] = !a & !b & !c & d | !a & !b & c & d | !a & b & !c & !d | !a & b & c & !d | a & !b & !c & d | a & !b & c & !d | a & b & !c & d | a & b & c & !d | a & b & c & d;  // First bit
		output[1] = !a & !b & !c & !d | !a & !b & !c & d | !a & b & !c & !d | !a & b & !c & d | a & !b & !c & d | a & !b & c & d | a & b & !c & !d | a & b & !c & d | a & b & c & !d;  // Second bit
		return output;
	}

	/**
	 * This is the s1 function that the "round" function depends on for its calculations. The method takes in a 4-bit array of bits and returns a 2-bit array of bits. The 2-bit array is derived by representing the truth table for s1 as two Sum Of Products (SOP) equations. The derivation for these specific equations can be found in this repository at doc/sbox/.
	 *
	 * @param inp an array of booleans
	 * @return an array of booleans as the output of the s1 function
	 * @author Pial Das
	 */
	private boolean[] s1(boolean[] inp) {
		boolean[] output = new boolean[2];

		// To make the variables clear in the SOP equation.
		boolean a = inp[0];
		boolean b = inp[1];
		boolean c = inp[2];
		boolean d = inp[3];

		output[0] = !a & !b & !c & d | !a & b & !c & !d | !a & b & !c & d | !a & b & c & !d | !a & b & c & d | a & !b & !c & !d | a & !b & !c & d | a & b & c & d;  // First bit
		output[1] = !a & !b & c & !d | !a & b & !c & d | !a & b & c & !d | !a & b & c & d | a & !b & !c & !d | a & !b & c & d | a & b & !c & !d | a & b & c & d;  // Second bit
		return output;
	}

	/**
	 * The SDES internal "f" function (the "round" function) is peformed by using a given array of bits and a given key as an array of bits and returns the output of the function "f" as a 4-bit array of bits.
	 *
	 * @param x the input array of bits
	 * @param k the array of bits as the key
	 * @return an output array of bits as a result of the f function
	 * @author Pial Das
	 */
	private boolean[] f(boolean[] x, boolean[] k) {
		boolean[] ep_x = expPerm(x, new int[]{3, 0, 1, 2, 1, 2, 3, 0});  // Permutate with the EP permutation vector.
		boolean[] key_xor = xor(k, ep_x);  // Peform the XOR operation between the key and the array that has been permutated with the EP vector.
		boolean[] left_key_xor = lh(key_xor);  // Take the left half of the array.
		boolean[] right_key_xor = rh(key_xor);  // Take the right half of the array.
		boolean[] s_left = s0(left_key_xor);  // Input the left half of the array into s0.
		boolean[] s_right = s1(right_key_xor);  // Input the right half of the array into s1.
		boolean[] concat_array = concat(s_left, s_right);  // Concatenate the results from s0 and s1.
		boolean[] p4_x = expPerm(concat_array, new int[]{1,3,2,0});  // Permutate the resulted array with the P4 permutation vector.
		return p4_x;
	}

	private boolean[] feistel(boolean[] x, boolean[] k) {
		return new boolean[8];
	}

	private boolean[] feistelInv(boolean[] y, boolean[] k) {
		return new boolean[8];
	}
}
