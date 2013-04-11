package eu.t6nn.gester.operations;

import eu.t6nn.gester.Population;

public class NeverConvergeStrategy extends AbstractConvergenceDetectionStrategy {

	@Override
	public boolean detect(Population pop, int genIndex) {
		return false;
	}

}
