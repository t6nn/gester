package eu.t6nn.gester.utils;

public class GrayCode {
	public static long toBinary(long value) {
		long result = value;
		for (int shift = 1; shift < 64; shift *= 2) {
			result = result ^ (result >>> shift);
		}
		return result;
	}

	public static long fromBinary(long value) {
		return (value >>> 1) ^ value;
	}
}
