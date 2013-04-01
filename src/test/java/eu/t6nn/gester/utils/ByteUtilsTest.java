package eu.t6nn.gester.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ByteUtilsTest {
	@Test
	public void testInverse() {
		long[] testValues = { Long.MIN_VALUE, -42, -1, 0, 1, 42, Long.MAX_VALUE };
		for (long l : testValues) {
			Assert.assertEquals(ByteUtils.toLong(ByteUtils.toBytes(l)), l);
		}
	}
}
