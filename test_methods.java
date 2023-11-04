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
	}
}
