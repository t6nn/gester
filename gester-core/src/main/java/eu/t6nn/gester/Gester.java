package eu.t6nn.gester;

import java.util.HashMap;
import java.util.Map;

import eu.t6nn.gester.operations.ConvergenceDetectionStrategy;
import eu.t6nn.gester.operations.FeedbackStrategy;
import eu.t6nn.gester.operations.InitializationStrategy;
import eu.t6nn.gester.operations.MatingStrategy;
import eu.t6nn.gester.operations.MutationStrategy;
import eu.t6nn.gester.operations.OperationStrategy;
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

	private Map<Class<? extends OperationStrategy>, OperationStrategy> strategies = new HashMap<Class<? extends OperationStrategy>, OperationStrategy>();

	/*
	 * private MatingStrategy matingStrategy = new
	 * RandomCrossoverMatingStrategy();
	 * 
	 * private MutationStrategy mutationStrategy = new
	 * ElitisticRandomMutationStrategy( 0.8d, 0.2d);
	 * 
	 * private InitializationStrategy initializationStrategy = new
	 * RandomInitializationStrategy();
	 * 
	 * private PairingStrategy pairingStrategy = new
	 * RankWeightedRandomPairing();
	 * 
	 * private PruningStrategy pruningStrategy = new
	 * PreserveBestPruningStrategy( 0.5d);
	 * 
	 * private TestRunStrategy runStrategy = new ParallelTestRunStrategy(100);
	 * 
	 * private ConvergenceDetectionStrategy convergenceStrategy = new
	 * NeverConvergeStrategy();
	 * 
	 * private FeedbackStrategy feedbackStrategy = new LoggerFeedbackStrategy();
	 */

	private IdentityDef idDef;

	protected Gester(TestCase testCase, IdentityDef idDef) {
		this.testCase = testCase;
		this.idDef = idDef;

		registerDefaults();
	}

	private void registerDefaults() {
		register(MatingStrategy.class, new RandomCrossoverMatingStrategy());
		register(MutationStrategy.class, new ElitisticRandomMutationStrategy(
				0.8d, 0.2d));
		register(InitializationStrategy.class,
				new RandomInitializationStrategy());
		register(PairingStrategy.class, new RankWeightedRandomPairing());
		register(PruningStrategy.class, new PreserveBestPruningStrategy(0.5d));
		register(TestRunStrategy.class, new ParallelTestRunStrategy(100));
		register(ConvergenceDetectionStrategy.class,
				new NeverConvergeStrategy());
		register(FeedbackStrategy.class, new LoggerFeedbackStrategy());
	}

	private <T extends OperationStrategy> Gester register(
			Class<T> strategyClass, T strategy) {
		assert strategy != null;
		strategies.put(strategyClass, strategy);

		return this;
	}

	public Gester apply(MatingStrategy strategy) {
		return register(MatingStrategy.class, strategy);
	}

	public Gester apply(MutationStrategy strategy) {
		return register(MutationStrategy.class, strategy);
	}

	public Gester apply(InitializationStrategy strategy) {
		return register(InitializationStrategy.class, strategy);
	}

	public Gester apply(PairingStrategy strategy) {
		return register(PairingStrategy.class, strategy);
	}

	public Gester apply(PruningStrategy strategy) {
		return register(PruningStrategy.class, strategy);
	}

	public Gester apply(TestRunStrategy strategy) {
		return register(TestRunStrategy.class, strategy);
	}

	public Gester apply(ConvergenceDetectionStrategy strategy) {
		return register(ConvergenceDetectionStrategy.class, strategy);
	}

	public Gester apply(FeedbackStrategy strategy) {
		return register(FeedbackStrategy.class, strategy);
	}

	public TestCase getTestCase() {
		return testCase;
	}

	public IdentityDef getIdentityDef() {
		return idDef;
	}

	@SuppressWarnings("unchecked")
	public <T extends OperationStrategy> T strategy(Class<T> strategyClass) {
		return (T) strategies.get(strategyClass);
	}

	public void run() {
		beforeRun();
		this.strategy(TestRunStrategy.class).run(this);
		afterRun();
	}

	public static Gester test(TestCase testCase, IdentityDef idDef) {
		return new Gester(testCase, idDef);
	}

	private void beforeRun() {
		for (OperationStrategy listener : this.strategies.values()) {
			if (listener instanceof TestLifecycleListener) {
				((TestLifecycleListener) listener).beforeRun(this);
			}
		}
	}

	private void afterRun() {
		for (OperationStrategy listener : this.strategies.values()) {
			if (listener instanceof TestLifecycleListener) {
				((TestLifecycleListener) listener).afterRun(this);
			}
		}
	}
}
