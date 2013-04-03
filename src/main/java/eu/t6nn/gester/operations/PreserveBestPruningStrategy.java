package eu.t6nn.gester.operations;

import eu.t6nn.gester.Population;

public class PreserveBestPruningStrategy implements PruningStrategy {

	private double keepRatio;

	public PreserveBestPruningStrategy(double keepRatio) {
		this.keepRatio = keepRatio;
	}
	
	@Override
	public void prune(Population pop) {
		int keep = (int)(pop.size() * keepRatio);
		for(int i = pop.size() - 1; i >= keep; --i) {
			pop.remove(i);
		}
	}

}
