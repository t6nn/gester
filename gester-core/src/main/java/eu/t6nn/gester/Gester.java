package eu.t6nn.gester;

import eu.t6nn.gester.operations.ConvergenceDetectionStrategy;
import eu.t6nn.gester.operations.FeedbackStrategy;
import eu.t6nn.gester.operations.InitializationStrategy;
import eu.t6nn.gester.operations.MatingStrategy;
import eu.t6nn.gester.operations.MutationStrategy;
import eu.t6nn.gester.operations.PairingStrategy;
import eu.t6nn.gester.operations.PruningStrategy;
import eu.t6nn.gester.operations.TestRunStrategy;
import eu.t6nn.gester.operations.impl.ElitisticRandomMutationStrategy;
import eu.t6nn.gester.operations.impl.LoggerFeedbackStrategy;
import eu.t6nn.gester.operations.impl.NeverConvergeStrategy;
import eu.t6nn.gester.operations.impl.ParallelTestRunStrategy;
import eu.t6nn.gester.operations.impl.PreserveBestPruningStrategy;
import eu.t6nn.gester.operations.impl.RandomCrossoverMatingStrategy;
import eu.t6nn.gester.operations.impl.RandomInitializationStrategy;
import eu.t6nn.gester.operations.impl.RankWeightedRandomPairing;

public class Gester {

	private TestCase testCase;

	private MatingStrategy matingStrategy = new RandomCrossoverMatingStrategy();

	private MutationStrategy mutationStrategy = new ElitisticRandomMutationStrategy(
			0.8d, 0.2d);

	private InitializationStrategy initializationStrategy = new RandomInitializationStrategy();

	private PairingStrategy pairingStrategy = new RankWeightedRandomPairing();

	private PruningStrategy pruningStrategy = new PreserveBestPruningStrategy(
			0.5d);

	private TestRunStrategy runStrategy = new ParallelTestRunStrategy(100);

	private ConvergenceDetectionStrategy convergenceStrategy = new NeverConvergeStrategy();
	
	private FeedbackStrategy feedbackStrategy = new LoggerFeedbackStrategy();
	
	private IdentityDef idDef;

	protected Gester(TestCase testCase, IdentityDef idDef) {
		this.testCase = testCase;
		this.idDef = idDef;
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
	
	public Gester apply(ConvergenceDetectionStrategy strategy) {
		assert strategy != null;
		this.convergenceStrategy = strategy;
		return this;
	}
	
	public Gester apply(FeedbackStrategy strategy) {
		assert strategy != null;
		this.feedbackStrategy = strategy;
		return this;
	}

	public TestCase getTestCase() {
		return testCase;
	}

	public IdentityDef getIdentityDef() {
		return idDef;
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

	public ConvergenceDetectionStrategy getConvergenceStrategy() {
		return convergenceStrategy;
	}

	public FeedbackStrategy getFeedbackStrategy() {
		return feedbackStrategy;
	}

	public void run() {
		this.runStrategy.run(this);
	}

	public static Gester test(TestCase testCase, IdentityDef idDef) {
		return new Gester(testCase, idDef);
	}
}
