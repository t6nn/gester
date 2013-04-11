package eu.t6nn.gester;

import org.apache.commons.math3.analysis.function.Gaussian;
import org.testng.annotations.Test;

import eu.t6nn.gester.operations.Converge;
import eu.t6nn.gester.variables.GrayEncodedIntegerVariable;

public class GesterTest {

	@Test
	public void testOneVariableMinimizer() {

		final Gaussian dist = new Gaussian(555, 0.2);

		MapIdentityDef def = new MapIdentityDef();
		def.addTrait("var0", new GrayEncodedIntegerVariable(0, 10000));

		TestCase test = new TestCase() {

			@Override
			public double test(Identity identity) {
				return -100 * dist.value(val(identity));
			}

			private long val(Identity identity) {
				GrayEncodedIntegerVariable var = (GrayEncodedIntegerVariable) identity
						.getTrait("var0");
				return var.getValue();
			}

		};

		Gester.test(test, def).apply(Converge.maxCost(-95 * dist.value(555))).run();
	}

	@Test
	public void testNVariableMinimizer() {

		final int N = 10;
		final Gaussian dist = new Gaussian(555, 100);

		MapIdentityDef id = new MapIdentityDef();
		for (int i = 0; i < N; i++) {
			id.addTrait("var" + i, new GrayEncodedIntegerVariable(500, 600));
		}

		TestCase test = new TestCase() {

			@Override
			public double test(Identity identity) {
				double cost = 1d;
				for (long val : val(identity, N)) {
					cost *= dist.value(val);
				}
				return -cost;
			}
		};

		Gester.test(test, id).apply(Converge.maxCost(-0.95d * Math.pow(dist.value(555), N))).run();
	}

	private long[] val(Identity identity, int varCount) {
		long[] values = new long[varCount];
		for (int i = 0; i < varCount; i++) {
			GrayEncodedIntegerVariable var = (GrayEncodedIntegerVariable) identity
					.getTrait("var" + i);
			values[i] = var.getValue();
		}

		return values;
	}

}
