package eu.t6nn.gester.variables;

import eu.t6nn.gester.utils.ByteUtils;
import eu.t6nn.gester.utils.GrayCode;

public class GrayEncodedIntegerVariable implements Variable {

	private final int bits;
	private final long minVal;
	private final long maxVal;
	private long value;

	public GrayEncodedIntegerVariable(long minVal, long maxVal) {
		assert maxVal - minVal < Long.MAX_VALUE : "Value range cannot exceed Long.MAX_VALUE";
		this.minVal = minVal;
		this.maxVal = maxVal;
		
		this.bits = Long.SIZE - Long.numberOfLeadingZeros(maxVal - minVal);
	}

	public long getValue() {
		return minVal + Math.abs(GrayCode.fromBinary(value) % (maxVal - minVal + 1));
	}

	@Override
	public int size() {
		return bits;
	}

	@Override
	public byte[] encode() {
		return ByteUtils.toBytes(value);
	}

	@Override
	public GrayEncodedIntegerVariable clone(byte[] newState) {
		GrayEncodedIntegerVariable var = new GrayEncodedIntegerVariable(minVal,
				maxVal);
		var.value = ByteUtils.toLong(newState);

		return var;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bits;
		result = prime * result + (int) (maxVal ^ (maxVal >>> 32));
		result = prime * result + (int) (minVal ^ (minVal >>> 32));
		result = prime * result + (int) (value ^ (value >>> 32));
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
		GrayEncodedIntegerVariable other = (GrayEncodedIntegerVariable) obj;
		if (bits != other.bits)
			return false;
		if (maxVal != other.maxVal)
			return false;
		if (minVal != other.minVal)
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.valueOf(getValue());
	}

}
