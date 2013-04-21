package eu.t6nn.gester.operations.impl;

import eu.t6nn.gester.Population;

public class MaxAgeConvergenceStrategy extends AbstractConvergenceDetectionStrategy {

	private long maxAge;
	private long firstTick = 0;

	public MaxAgeConvergenceStrategy(long maxAgeInMilliseconds) {
		this.maxAge = maxAgeInMilliseconds;
	}
	
	@Override
	public boolean detect(Population pop, int genIndex) {
		if(firstTick == 0) {
			firstTick = System.nanoTime();
		}
		
		return ((System.nanoTime() - firstTick) / 1000000L) > maxAge;
	}

}
