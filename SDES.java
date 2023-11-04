import java.util.Scanner;
import java.util.Arrays;

public class SDES {
	static boolean[] key10;
	Scanner scanner;
	public SDES() {
		key10 = new boolean[10];
		scanner = new Scanner(System.in);
		getKey10(scanner);
	}

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
	 *
	 * @param plainByte the byte that's in plaintext
	 * @return A byte of ciphertext
	 * @author Riley Miller
	 */
	public byte encryptByte(byte plainByte) {
		boolean[] bitArray = byteToBitArray(plainByte, 8);  // Single byte converted to an array of bits
		
		// Inital setup with initial permutation vector (IP)
		bitArray = expPerm(bitArray, new int[]{1, 5, 2, 0, 3, 7, 4, 6});

		// Go through 2 rounds of the feistel function
		for (int i = 0; i < 2; i++) {
			bitArray = feistel(bitArray, key10);
		}

		// Post-setup with inverse of initial permutation vector (IP^{-1})
		bitArray = expPerm(bitArray, new int[]{3, 0, 2, 4, 6, 1, 7, 5});

		// Convert array of bits back to a byte
		byte cipherByte = bitArrayToByte(bitArray);

		return cipherByte;
	}
	
	/**
	 * Decrypts a single byte using SDES
	 *
	 * @param cipherByte the byte that's in ciphertext
	 * @return A byte of plainText
	 * @author Riley Miller
	 */
	public byte decryptByte(byte cipherByte) {
		boolean[] bitArray = byteToBitArray(cipherByte, 8);  // Single byte converted to an array of bits
		
		// Inital setup with initial permutation vector (IP)
		bitArray = expPerm(bitArray, new int[]{1, 5, 2, 0, 3, 7, 4, 6});

		// Go through 2 rounds of the feistelInv function
		for (int i = 0; i < 2; i++) {
			bitArray = feistelInv(bitArray, key10);
		}

		// Post-setup with inverse of initial permutation vector (IP^{-1})
		bitArray = expPerm(bitArray, new int[]{3, 0, 2, 4, 6, 1, 7, 5});

		// Convert array of bits back to a byte
		byte plainByte = bitArrayToByte(bitArray);

		return plainByte;
	}

	/**
	 * Outputs an array of bits as 1s and 0s
	 *
	 * @param binaries an array of bits
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
	 *
	 * @param digits an array of ints
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
	 *
	 * @param inp the input as a bit array
	 * @param epv the permutation vector
	 * @return a permuted/expanded/selected bitArray, or null if there is an error
	 * @author Riley Miller
	 */
	public boolean[] expPerm(boolean[] inp, int[] epv) {
		int pos_exception = -1;
		try {
			boolean[] newBitArray = new boolean[epv.length];
			for (int i = 0; i < epv.length; i++)
			{
				pos_exception = i;
				newBitArray[i] = inp[epv[i]];  // The resulting array would be the same size as epv allowing for expansion and selection.
			}
			return newBitArray;
		} catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Out of Bounds Occurred. Position '" + pos_exception + "' for given permutation vector is not valid.");
			return null;
		}
	}
	
	/**
	 * Takes a 10 character input of 1s and 0s and stores them as booleans
	 *
	 * @param scanner grab input from the user for their key
	 * @author Riley Miller
	 */
	public void getKey10(Scanner scanner) {
		String scanLine;
		boolean done = false;
		while (done == false)
		{
			scanLine = scanner.nextLine();
			if (scanLine.length() == 8)
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

	/**
	 * Convert the given byte array to a String
	 *
	 * @param inp an array of bytes
	 * @return the characters as a string 
	 * @author Luke Lachowicz
	 * @version 11/03/2023
	 */
	public String byteArrayToCharString(byte[] inp) {
		if (inp == null) {
		    return null;
		}

		// created an array named arrayToChar equal to the length of the byte array
		char[] arrayToChar = new char[inp.length];

		// for loop to loop through the bytes so each byte is stored
		// in the arrayToChar array and comes out as a string when returned
		for (int i = 0; i < inp.length; i++) {
		    arrayToChar[i] = (char) inp[i];
		}

		// returns the string
		return new String(arrayToChar);
	}

	/**
	 * Given an array of bits, return only the left helf of the array.
	 *
	 * @param inp an array of booleans
	 * @return the left half of the inputted array
	 * @author Pial Das
	 */
	public boolean[] lh(boolean[] inp) {
		return Arrays.copyOfRange(inp, 0, inp.length / 2);  // Take the index from 0 to the half-way point and copy the elements to another array to return.
	}

	/**
	 * Given an array of bits, return only the right half of the array.
	 *
	 * @param inp an array of booleans
	 * @return the right half of the inputted array
	 * @author Pial Das
	 */
	public boolean[] rh(boolean[] inp) {
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
	public boolean[] xor(boolean[] x, boolean[] y) {
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

        /**
	 * Concatenate the two bit arrays, x || y
	 *
	 * @param x an array of bits
	 * @param y an array of bits
	 * @return the concatenation of x and y
	 * @author Luke Lachowicz
	 * @version 11/03/2023
	 */
	public boolean[] concat(boolean[] x, boolean[] y) {
		// checks if x and y is null 
		if (x == null || y == null) {
		    return null;
		}

		// add the length of x and y and store it in concatxy
		int concatxy = x.length + y.length;

		// combines x and y into an array called list
		boolean[] list = new boolean[concatxy];

		// copy elements of array x into the list array
		for (int i = 0; i < x.length; i++) {
		    list[i] = x[i];
		}
		// copy elements of array y into the list array starting from 
		// where the last place in the x array was left off
		for (int i = 0; i < y.length; i++) {
		    list[x.length + i] = y[i];
		}
		//returns the list
		return list;
	}

        /**
	 * Convert the given bit array to a single byte
	 *
	 * @param inp an array of bits
	 * @return a byte corresponding to the given array of bits
	 * @author Luke Lachowicz
	 * @version 11/03/2023
	 */
	public byte bitArrayToByte(boolean[] inp) {
		// checks to see if inp is null or length is greater than 8
		// returns 0 if this is the case
		if (inp == null || inp.length != 8) {
		    return 0;
		}

		byte b = 0;
		// loops through the array inp to process each bit
		for (int i = 0; i < 8; i++) {
		    if (inp[i]) {
			// left shifts 1 to determine bit position. bitwise OR operator 
			// sets the bit in byte b based on current value. 
			b |= (1 << (7 - i));
		    }
		}

		// returns byte b after looping through the bytes and setting the value
		return b;
	}

	/**
	 * Convert the given byte to a bit array, of the given size
	 *
	 * @param b a byte
	 * @param size the size of the resulting array
	 * @return an array of bits corresponding to the given byte
	 * @author Luke Lachowicz
	 * @version 11/03/2023
	 */
	public boolean[] byteToBitArray(byte b, int size) {
		// checks to see if size of array is 0 or greater than 8 
		if (size <= 0 || size > 8) {
		    return null;
		}

		// create array equal to size
		boolean[] array = new boolean[size];

		// iterate through each bit starting from left to right
		// bitwise AND to set bits 
		for (int i = size -1; i >= 0; i--) {
		    array[i] = (b & ( 1 << i)) != 0;
		}

		return array;
	}

	/**
	 * This is the s0 function that the "round" function depends on for its calculations. The method takes in a 4-bit array of bits and returns a 2-bit array of bits. The 2-bit array is derived by representing the truth table for s0 as two Sum Of Products (SOP) equations. The derivation for these specific equations can be found in this repository at doc/sbox/.
	 *
	 * @param inp an array of booleans
	 * @return an array of booleans as the output of the s0 function
	 * @author Pial Das
	 */
	public boolean[] s0(boolean[] inp) {
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
	public boolean[] s1(boolean[] inp) {
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
	 * The SDES internal "f" function (the "round" function) is peformed by using a given 4-bit array and a given key as an 8-bit array and returns the output of the function "f" as a 4-bit array.
	 *
	 * @param x the input array of bits
	 * @param k the array of bits as the key
	 * @return an output array of bits as a result of the f function
	 * @author Pial Das
	 */
	public boolean[] f(boolean[] x, boolean[] k) {
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

	/**
	 * This method is called once on each round of encryption feistel(x,k) = R(x) || (L(x) xor f(R(x),k))
	 *
	 * @param x an array of bits (from the byte of the plaintext)
	 * @param k the key as an array of bits
	 * @return a bit array of length 8
	 * @author Luke Lachowicz
	 * @version 11/03/2023
	 */
	public boolean[] feistel(boolean[] x, boolean[] k) {
		// Return left and right halfs of x
		boolean[] left = lh(x);
		boolean[] right = rh(x);
		boolean[] f_result = f(right, k);  // Get result from the round function

		// apply XOR to the arrays of L and f(R(x),k) and stores result in new_right
		boolean[] new_right = xor(left, f_result);

		// concatenate the old right with the new right and return
		boolean[] result = concat(right, new_right);
		return result;
	}

        /**
	 * This method is called once on each round of decryption. It is the inverse of feistel. feistelInv(y,k) = (L(y) xor f(R(y),k)) || R(y)
	 *
	 * @param y an array of bits (from the byte of the ciphertext)
	 * @param k the key as an array of bits
	 * @return a bit array of length 8
	 * @author Luke Lachowicz
	 * @version 11/03/2023
	 */
	public boolean[] feistelInv(boolean[] y, boolean[] k) {
		// Return left and right halfs of y
		boolean[] left = lh(y);
		boolean[] right = rh(y);
		boolean[] f_result = f(left, k);  // Get result from the round function
		
		// apply XOR to the arrays of R and f(L(x),k) and stores result in new_right
		boolean[] new_left = xor(right, f_result);

		// concatenate the new left with the old left and return
		boolean[] result = concat(new_left, left);
		return result;
	}
}
