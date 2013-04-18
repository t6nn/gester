package eu.t6nn.gester.operations;

import eu.t6nn.gester.operations.impl.PreserveBestPruningStrategy;

public class Prune {
	
	private Prune() {
		
	}
	
	public static PreserveBestPruningStrategy preserveBest(double keepRatio) {
		return new PreserveBestPruningStrategy(keepRatio);
	}
}
