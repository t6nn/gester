package eu.t6nn.gester.utils;

public class ByteUtils {

	public static byte[] toBytes(long value) {
		byte[] result = new byte[8];
		for (int i = 0; i < 8; ++i) {
			result[i] = (byte) (value >>> (i * 8));
		}
		return result;
	}

	public static long toLong(byte[] bytes) {
		long result = 0;
		for (int i = 0; i < bytes.length; ++i) {
			result += ((long) bytes[i] & 0xffL) << (i * 8);
		}
		return result;
	}
}
