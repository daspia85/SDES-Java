public class test_methods {
	public static void main(String[] args) {
		SDES sdes = new SDES();

		System.out.println();
		boolean[] bits = new boolean[]{true, false, true, true, false, true, false, false};
		boolean[] second_bits = new boolean[]{true, true, true, false, false, false, true};
		boolean[] key = new boolean[]{true, false, false, true, true, false, false, true};

		System.out.println("Testing expPerm:");
		int[] epv_array = new int[]{0, 1, 4, 2, 7};
		sdes.show(sdes.expPerm(bits, epv_array));
		System.out.println();

		System.out.println("Testing lh & rh:");
		sdes.show(sdes.lh(bits));
		sdes.show(sdes.rh(bits));
		System.out.println();

		System.out.println("Testing XOR:");
		sdes.show(sdes.xor(bits, second_bits));
		System.out.println();

		System.out.println("Testing concat:");
		sdes.show(sdes.concat(bits, second_bits));
		System.out.println();

		System.out.println("Testing f:");
		boolean[] f_inp = new boolean[]{false, false, true, true};
		boolean[] f_key = new boolean[]{true, false, false, true, false, true, false, true};
		sdes.show(sdes.f(f_inp, f_key));
		System.out.println();

		System.out.println("Testing feistel:");
		sdes.show(bits);
		boolean[] feistel_encode = sdes.feistel(bits, key);
		sdes.show(feistel_encode);
		System.out.println();

		System.out.println("Testing feistel inverse:");
		sdes.show(sdes.feistelInv(feistel_encode, key));
		System.out.println();

		String sample_string = "hello";
		byte[] byte_string = new byte[]{104, 101, 108, 108, 111};

		System.out.println("Testing byteArrayToCharString:");
		System.out.println("Sample String: " + sample_string);
		System.out.println(sdes.byteArrayToCharString(byte_string));
		System.out.println();

		System.out.println("Testing byteToBitArray:");
		System.out.println("Sample String: " + sample_string);
		for (int i = 0; i < byte_string.length; i++)
			sdes.show(sdes.byteToBitArray(byte_string[i], 8));
		System.out.println();	

		System.out.println("Testing new bitArrayToByte:");
		String bit_string = "01101000 01100101 01101100 01101100 01101111";
		boolean[] bit_array = new boolean[]{false, true, true, false, true, true, false, false};
		byte converted = 0;
		for (int i = 0; i < bit_array.length; i++)
			if (bit_array[bit_array.length - 1 - i])
				converted += Math.pow(2, i);
		System.out.println("Byte: " + converted);
		System.out.println();
		
		// This also works
		System.out.println("Testing bitArrayToByte:");
		System.out.println("Byte: " + sdes.bitArrayToByte(bit_array));
		System.out.println();
	}
}
