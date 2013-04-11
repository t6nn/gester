package eu.t6nn.gester.variables;

import org.testng.Assert;
import org.testng.annotations.Test;

import eu.t6nn.gester.utils.ByteUtils;
import eu.t6nn.gester.utils.GrayCode;
import eu.t6nn.gester.variables.GrayEncodedIntegerVariable;

public class GrayEncodedIntegerVariableTest {

	@Test
	public void testDecode() {
		GrayEncodedIntegerVariable var = new GrayEncodedIntegerVariable(-2, 3);

		int in[] = { -7, -6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7 };
		int out[] = { -1, -2, 3, 2, 1, 0, -1, -2, -1, 0, 1, 2, 3, -2, -1 };

		for (int i = 0; i < in.length; ++i) {
			Assert.assertEquals(
					var.clone(ByteUtils.toBytes(GrayCode.toBinary(in[i])))
							.getValue(), out[i], "In: " + in[i]);
		}
	}
}
