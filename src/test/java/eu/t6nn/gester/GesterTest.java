package eu.t6nn.gester;

import org.apache.commons.math3.analysis.function.Gaussian;
import org.testng.annotations.Test;

import eu.t6nn.gester.variables.GrayEncodedIntegerVariable;

public class GesterTest {
	
	@Test
	public void testOneVariableMinimizer() {

		final Gaussian dist = new Gaussian(555, 0.2);
		
		MapIdentityDef def = new MapIdentityDef();
		def.addTrait("var", new GrayEncodedIntegerVariable(0, 10000));

		TestCase test = new TestCase() {

			private int iteration = 0;

			@Override
			public double test(Identity identity) {
				return -100 * dist.value(val(identity));
			}

			private long val(Identity identity) {
				GrayEncodedIntegerVariable var = (GrayEncodedIntegerVariable) identity
						.getTrait("var");
				return var.getValue();
			}

			@Override
			public Process tick(Population pop) {
				System.out.println("Iteration " + (++iteration) + " - best: "
						+ val(pop.get(0)));
				if (Math.abs(val(pop.get(0)) - 555) < 2) {
					return Process.STOP;
				}
				return Process.CONTINUE;
			}

		};

		Gester.test(test, def).run();
	}

	@Test
	public void testNVariableMinimizer() {

		final int N = 10;
		final Gaussian dist = new Gaussian(555, 100);
		
		MapIdentityDef id = new MapIdentityDef();
		for (int i = 0; i < N; i++) {
			id.addTrait("var" + i,
					new GrayEncodedIntegerVariable(500, 600));
		}

		TestCase test = new TestCase() {

			private int iteration = 0;
			private Identity lastBest = null;

			@Override
			public double test(Identity identity) {
				double cost = 1d;
				for (long val : val(identity)) {
					cost *= dist.value(val);
				}
				return -cost;
			}

			private long[] val(Identity identity) {
				long[] values = new long[N];
				for (int i = 0; i < N; i++) {
					GrayEncodedIntegerVariable var = (GrayEncodedIntegerVariable) identity
							.getTrait("var" + i);
					values[i] = var.getValue();
				}

				return values;
			}

			@Override
			public Process tick(Population pop) {
				Identity best = pop.get(0);
				iteration++;
				if(!best.equals(lastBest)) {
					StringBuilder msg = new StringBuilder("Iteration ");
					msg.append(iteration);
					msg.append(" - best: ");
					for (long val : val(best)) {
						msg.append(val).append(" ");
					}
					System.out.println(msg);
					lastBest = best;
				}
				
				for (long val : val(best)) {
					if(Math.abs(val - 555) > 10) {
						return Process.CONTINUE;
					}
				}
				return Process.STOP;
			}

		};

		Gester.test(test, id).run();
	}

}
