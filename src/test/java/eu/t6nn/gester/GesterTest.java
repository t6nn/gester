package eu.t6nn.gester;

import org.apache.commons.math3.analysis.function.Gaussian;
import org.testng.annotations.Test;

public class GesterTest {
	@Test
	public void testOneVariableMinimizer() {

		final Gaussian dist = new Gaussian(555, 0.2);

		TestCase test = new TestCase() {

			private int iteration = 0;

			@Override
			public double calculateCost(Identity identity) {
				return -100 * dist.value(val(identity));
			}

			private long val(Identity identity) {
				GrayEncodedLongVariable var = (GrayEncodedLongVariable) identity
						.getTrait("var");
				return var.getValue();
			}

			@Override
			public Identity newIdentity() {
				MapIdentity id = new MapIdentity();
				id.addTrait("var", new GrayEncodedLongVariable(0, 10000));
				return id;
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

		Gester.test(test).run();
	}

	@Test
	public void testNVariableMinimizer() {

		final int N = 5;
		final Gaussian dist = new Gaussian(555, 100);

		TestCase test = new TestCase() {

			private int iteration = 0;
			private Identity lastBest = null;

			@Override
			public double calculateCost(Identity identity) {
				double cost = 1d;
				for (long val : val(identity)) {
					cost *= dist.value(val);
				}
				return -cost;
			}

			private long[] val(Identity identity) {
				long[] values = new long[N];
				for (int i = 0; i < N; i++) {
					GrayEncodedLongVariable var = (GrayEncodedLongVariable) identity
							.getTrait("var" + i);
					values[i] = var.getValue();
				}

				return values;
			}

			@Override
			public Identity newIdentity() {
				MapIdentity id = new MapIdentity();
				for (int i = 0; i < N; i++) {
					id.addTrait("var" + i,
							new GrayEncodedLongVariable(0, 10000));
				}
				return id;
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

		Gester.test(test).run();
	}

}
