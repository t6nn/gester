package eu.t6nn.gester.operations;

import eu.t6nn.gester.operations.impl.MaxAgeConvergenceStrategy;
import eu.t6nn.gester.operations.impl.MaxCostConvergenceStrategy;
import eu.t6nn.gester.operations.impl.MaxGenerationsConvergenceStrategy;
import eu.t6nn.gester.operations.impl.RepeatedBestPerformersConvergenceStrategy;

public class Converge {
	
	private Converge() {
		// Utility class constructor
	}
	
	public static MaxAgeConvergenceStrategy maxAge(long ageInMillis) {
		return new MaxAgeConvergenceStrategy(ageInMillis);
	}
	
	public static MaxGenerationsConvergenceStrategy maxGen(int maxGen) {
		return new MaxGenerationsConvergenceStrategy(maxGen);
	}
	
	public static RepeatedBestPerformersConvergenceStrategy topPerformer(int timesSeen) {
		return new RepeatedBestPerformersConvergenceStrategy(1, timesSeen);
	}
	
	public static RepeatedBestPerformersConvergenceStrategy topPerformers(int performerCount, int timesSeen) {
		return new RepeatedBestPerformersConvergenceStrategy(performerCount, timesSeen);
	}
	
	public static MaxCostConvergenceStrategy maxCost(double maxCost) {
		return new MaxCostConvergenceStrategy(maxCost);
	}
}
