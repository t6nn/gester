package eu.t6nn.gester.operations.impl;

import eu.t6nn.gester.Population;

public class MaxGenerationsConvergenceStrategy extends
		AbstractConvergenceDetectionStrategy {

	private int maxGenerations;

	public MaxGenerationsConvergenceStrategy(int maxGenerations) {
		this.maxGenerations = maxGenerations;
	}

	@Override
	public boolean detect(Population pop, int genIndex) {
		return genIndex > this.maxGenerations;
	}

}
