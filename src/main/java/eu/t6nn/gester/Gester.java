package eu.t6nn.gester;

import eu.t6nn.gester.operations.InitializationStrategy;
import eu.t6nn.gester.operations.MatingStrategy;
import eu.t6nn.gester.operations.MutationStrategy;
import eu.t6nn.gester.operations.NonElitistRandomMutationStrategy;
import eu.t6nn.gester.operations.PairingStrategy;
import eu.t6nn.gester.operations.PreserveBestPruningStrategy;
import eu.t6nn.gester.operations.PruningStrategy;
import eu.t6nn.gester.operations.RandomCrossoverMatingStrategy;
import eu.t6nn.gester.operations.RandomInitializationStrategy;
import eu.t6nn.gester.operations.RankWeightedRandomPairing;
import eu.t6nn.gester.operations.SynchronousTestRunStrategy;
import eu.t6nn.gester.operations.TestRunStrategy;

public class Gester {
	
	private TestCase testCase;
	
	private MatingStrategy matingStrategy = new RandomCrossoverMatingStrategy();
	
	private MutationStrategy mutationStrategy = new NonElitistRandomMutationStrategy(0.8d, 0.2d);
	
	private InitializationStrategy initializationStrategy = new RandomInitializationStrategy();
	
	private PairingStrategy pairingStrategy = new RankWeightedRandomPairing(0.5d);

	private PruningStrategy pruningStrategy = new PreserveBestPruningStrategy(0.5d);

	private TestRunStrategy runStrategy = new SynchronousTestRunStrategy(100);
	
	protected Gester(TestCase testCase) {
		this.testCase = testCase;
	}
	
	public Gester apply(MatingStrategy strategy) {
		assert strategy != null;
		this.matingStrategy = strategy;
		return this;
	}
	
	public Gester apply(MutationStrategy strategy) {
		assert strategy != null;
		this.mutationStrategy = strategy;
		return this;
	}
	
	public Gester apply(InitializationStrategy strategy) {
		assert strategy != null;
		this.initializationStrategy = strategy;
		return this;
	}
	
	public Gester apply(PairingStrategy strategy) {
		assert strategy != null;
		this.pairingStrategy = strategy;
		return this;
	}
	
	public Gester apply(PruningStrategy strategy) {
		assert strategy != null;
		this.pruningStrategy = strategy;
		return this;
	}
	
	public Gester apply(TestRunStrategy strategy) {
		assert strategy != null;
		this.runStrategy = strategy;
		return this;
	}
	
	public TestCase getTestCase() {
		return testCase;
	}

	public MatingStrategy getMatingStrategy() {
		return matingStrategy;
	}

	public MutationStrategy getMutationStrategy() {
		return mutationStrategy;
	}

	public InitializationStrategy getInitializationStrategy() {
		return initializationStrategy;
	}

	public PairingStrategy getPairingStrategy() {
		return pairingStrategy;
	}

	public PruningStrategy getPruningStrategy() {
		return pruningStrategy;
	}
	
	public void run() {
		this.runStrategy.run(this);
	}
	
	public static Gester test(TestCase testCase) {
		return new Gester(testCase);
	}
}
