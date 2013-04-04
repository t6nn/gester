package eu.t6nn.gester.utils;

public class GrayCode {
	
	public static long toBinary(long value) {
		return toBinary(value, 64);
	}
	
	public static long toBinary(long value, int bits) {
		long result = value;
		for (int shift = 1; shift < bits; shift *= 2) {
			result = result ^ (result >>> shift);
		}
		return result;
	}

	public static long fromBinary(long value) {
		return (value >>> 1) ^ value;
	}
}
