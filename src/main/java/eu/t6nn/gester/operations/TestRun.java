package eu.t6nn.gester.operations;

import eu.t6nn.gester.operations.impl.ParallelTestRunStrategy;

public class TestRun {
	private TestRun() {
		
	}
	
	public static ParallelTestRunStrategy parallel(int populationSize) {
		return new ParallelTestRunStrategy(populationSize);
	}
}
