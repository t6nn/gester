package eu.t6nn.gester.operations;

import eu.t6nn.gester.operations.impl.RankWeightedRandomPairing;

public class Pair {
	private Pair() {
		
	}
	
	public static RankWeightedRandomPairing weighted(double keepRatio) {
		return new RankWeightedRandomPairing(keepRatio);
	}
}
