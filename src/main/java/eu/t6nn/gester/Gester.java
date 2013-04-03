package eu.t6nn.gester;

import java.util.Collection;
import java.util.Queue;

import eu.t6nn.gester.TestCase.Process;
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

public class Gester {
	
	private TestCase testCase;
	
	private int populationSize = 100;
	
	private MatingStrategy matingStrategy = new RandomCrossoverMatingStrategy();
	
	private MutationStrategy mutationStrategy = new NonElitistRandomMutationStrategy(0.8d, 0.2d);
	
	private InitializationStrategy initializationStrategy = new RandomInitializationStrategy();
	
	private PairingStrategy pairingStrategy = new RankWeightedRandomPairing(0.5d);

	private PruningStrategy pruningStrategy = new PreserveBestPruningStrategy(0.5d);
	
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
	
	public Gester population(int size) {
		assert size > 0;
		this.populationSize = size;
		return this;
	}
	
	private Population initializePopulation() {
		Population pop = new PopulationImpl(testCase, populationSize);
		for(int i = 0; i < populationSize; ++i) {
			pop.add(initializationStrategy.initialize(testCase));
		}
		return pop;
	}
	
	public void run() {
		Population pop = initializePopulation();
		pop.update();
		while(testCase.tick(pop) != Process.STOP) {
			pruningStrategy.prune(pop);
			Queue<Identity> pairs = pairingStrategy.pair(pop, (populationSize - pop.size()) / 2);
			while(!pairs.isEmpty()) {
				Collection<Identity> children = matingStrategy.mate(pairs.poll(), pairs.poll());
				for (Identity child : children) {
					pop.add(child);
				}
			}
			mutationStrategy.mutate(pop);
			pop.update();
		}
	}
	
	public static Gester test(TestCase testCase) {
		return new Gester(testCase);
	}
}
