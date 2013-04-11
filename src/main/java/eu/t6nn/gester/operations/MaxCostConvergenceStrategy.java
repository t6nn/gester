package eu.t6nn.gester.operations;

import eu.t6nn.gester.Population;

public class MaxCostConvergenceStrategy extends
		AbstractConvergenceDetectionStrategy {

	private double maxCost;

	public MaxCostConvergenceStrategy(double maxCost) {
		this.maxCost = maxCost;
	}
	
	@Override
	public boolean detect(Population pop, int genIndex) {
		return pop.get(0).lastCost() < maxCost;
	}

}
