package eu.t6nn.gester.operations;

public class Converge {
	
	private Converge() {
		// Utility class constructor
	}
	
	public static ConvergenceDetectionStrategy maxAge(long ageInMillis) {
		return new MaxAgeConvergenceStrategy(ageInMillis);
	}
	
	public static ConvergenceDetectionStrategy maxGen(int maxGen) {
		return new MaxGenerationsConvergenceStrategy(maxGen);
	}
	
	public static ConvergenceDetectionStrategy topPerformer(int timesSeen) {
		return new RepeatedBestPerformersConvergenceStrategy(1, timesSeen);
	}
	
	public static ConvergenceDetectionStrategy topPerformers(int performerCount, int timesSeen) {
		return new RepeatedBestPerformersConvergenceStrategy(performerCount, timesSeen);
	}
	
	public static ConvergenceDetectionStrategy maxCost(double maxCost) {
		return new MaxCostConvergenceStrategy(maxCost);
	}
}
