package eu.t6nn.gester.operations;

import eu.t6nn.gester.operations.impl.ElitisticRandomMutationStrategy;

public class Mutate {
	private Mutate() {
		
	}
	
	public static ElitisticRandomMutationStrategy preserveElite(double bottomRatio, double mutationRatio) {
		return new ElitisticRandomMutationStrategy(bottomRatio, mutationRatio);
	}
}
