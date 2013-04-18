package eu.t6nn.gester.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

public class GrayCodeTest {
	
	@Test
	public void testInverse() {
		long[] testValues = {Long.MIN_VALUE, -42, -1, 0, 1, 100, 42, Long.MAX_VALUE };
		for (long l : testValues) {
			Assert.assertEquals(GrayCode.fromBinary(GrayCode.toBinary(l)), l);
		}
	}
}
