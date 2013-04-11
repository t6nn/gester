package eu.t6nn.gester.utils;

import java.util.Arrays;

public class MutableBitBuffer implements Cloneable {

	private byte[] bytes;

	private int size = 0;
	
	private int writePtr = 0;

	public MutableBitBuffer(int size) {
		bytes = new byte[size / 8 + 1];
		this.size = size;
	}

	public void append(byte[] bytes, int numBits) {
		for (int i = 0; i < numBits; ++i) {
			set(writePtr + i, (bytes[i / 8] >> (i % 8)) & 1);
		}
		writePtr += numBits;
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
			arr[n / 8] = (byte) (arr[n / 8] & ~(1 << (n % 8)));
		} else {
			arr[n / 8] = (byte) (arr[n / 8] | (1 << (n % 8)));
		}
	}
	
	public int size() {
		return size;
	}
	
	@Override
	public String toString() {
		StringBuilder bits = new StringBuilder(size);
		for(int i = 0; i < size; ++i) {
			bits.append(get(size - i - 1));
		}
		return bits.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(bytes);
		result = prime * result + size;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MutableBitBuffer other = (MutableBitBuffer) obj;
		if (!Arrays.equals(bytes, other.bytes)) {
			return false;
		}
		if (size != other.size) {
			return false;
		}
		return true;
	}
	
	@Override
	public MutableBitBuffer clone() {
		MutableBitBuffer clone = new MutableBitBuffer(size);
		clone.writePtr = this.writePtr;
		clone.bytes = Arrays.copyOf(bytes, bytes.length);
		return clone;
	}

}
