package eu.t6nn.gester.operations.impl;

import eu.t6nn.gester.Population;

public class MinCostConvergenceStrategy extends
		AbstractConvergenceDetectionStrategy {

	private double minCost;

	public MinCostConvergenceStrategy(double maxCost) {
		this.minCost = maxCost;
	}
	
	@Override
	public boolean detect(Population pop, int genIndex) {
		return pop.get(0).lastCost() < minCost;
	}

}
