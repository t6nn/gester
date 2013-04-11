package eu.t6nn.gester.operations;

import eu.t6nn.gester.operations.impl.RandomCrossoverMatingStrategy;
import eu.t6nn.gester.operations.impl.SinglePointCrossoverMatingStrategy;

public class Mate {
	private Mate() {
		
	}
	
	public static RandomCrossoverMatingStrategy uniform() {
		return new RandomCrossoverMatingStrategy();
	}
	
	public static SinglePointCrossoverMatingStrategy singlePoint() {
		return new SinglePointCrossoverMatingStrategy();
	}
}
