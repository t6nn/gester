package eu.t6nn.gester.utils;

import java.util.Arrays;

public class MutableBitBuffer {

	private byte[] bytes;

	private int size = 0;

	public MutableBitBuffer(int size) {
		bytes = new byte[size / 8 + 1];
	}

	public void append(byte[] bytes, int numBits) {
		for (int i = 0; i < numBits; ++i) {
			set(size + i, (bytes[i / 8] >> (i % 8)) & 1);
		}
		size += numBits;
	}
	
	public byte[] getBytes() {
		return Arrays.copyOf(bytes, bytes.length);
	}

	public int get(int n) {
		return (bytes[n / 8] >> (n % 8)) & 1;
	}
	
	public byte[] range(int start, int size) {
		byte[] result = new byte[size / 8 + 1];
		for(int i = 0; i < size; i++) {
			set(result, i, get(start + i));
		}
		return result;
	}

	public void set(int n, int bit) {
		set(bytes, n, bit);
	}
	
	private static void set(byte[] arr, int n, int bit) {
		if (bit == 0) {
			arr[n / 8] = (byte) (((int) arr[n / 8]) & ~(1 << (n % 8)));
		} else {
			arr[n / 8] = (byte) (((int) arr[n / 8]) | (1 << (n % 8)));
		}
	}
	
	public int size() {
		return size;
	}

}
