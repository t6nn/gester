package eu.t6nn.gester;

import eu.t6nn.gester.utils.ByteUtils;
import eu.t6nn.gester.utils.GrayCode;


public class GrayEncodedLongVariable implements Variable {
	
	private static final int VAR_SIZE = 64;
	private final long minVal;
	private final long maxVal;
	private long normalizedValue;

	public GrayEncodedLongVariable(long minVal, long maxVal) {
		assert maxVal - minVal < Long.MAX_VALUE : "Value range cannot exceed Long.MAX_VALUE";
		this.minVal = minVal;
		this.maxVal = maxVal;
	}
	
	public long getValue() {
		return minVal + normalizedValue;
	}

	@Override
	public int size() {
		return VAR_SIZE;
	}

	@Override
	public byte[] encode() {
		return ByteUtils.toBytes(GrayCode.toBinary(normalizedValue));
	}

	@Override
	public GrayEncodedLongVariable clone(byte[] newState) {
		GrayEncodedLongVariable var = new GrayEncodedLongVariable(minVal, maxVal);
		
		long value = GrayCode.fromBinary(ByteUtils.toLong(newState));
		var.normalizedValue = Math.abs(value % (maxVal - minVal + 1));
		
		return var;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (maxVal ^ (maxVal >>> 32));
		result = prime * result + (int) (minVal ^ (minVal >>> 32));
		result = prime * result
				+ (int) (normalizedValue ^ (normalizedValue >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrayEncodedLongVariable other = (GrayEncodedLongVariable) obj;
		if (maxVal != other.maxVal)
			return false;
		if (minVal != other.minVal)
			return false;
		if (normalizedValue != other.normalizedValue)
			return false;
		return true;
	}
	
	
}
