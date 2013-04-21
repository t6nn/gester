package eu.t6nn.gester.operations.impl;

import java.util.Collection;
import java.util.Queue;

import eu.t6nn.gester.Gester;
import eu.t6nn.gester.Identity;
import eu.t6nn.gester.Population;
import eu.t6nn.gester.SimplePopulation;
import eu.t6nn.gester.operations.ConvergenceDetectionStrategy;
import eu.t6nn.gester.operations.FeedbackStrategy;
import eu.t6nn.gester.operations.InitializationStrategy;
import eu.t6nn.gester.operations.MatingStrategy;
import eu.t6nn.gester.operations.MutationStrategy;
import eu.t6nn.gester.operations.PairingStrategy;
import eu.t6nn.gester.operations.PruningStrategy;
import eu.t6nn.gester.operations.TestRunStrategy;

public class ParallelTestRunStrategy implements TestRunStrategy {

	private int populationSize;
	
	private int poolSize;

	public ParallelTestRunStrategy(int populationSize) {
		this.populationSize = populationSize;
		this.poolSize = Math.min(populationSize, 10);
	}
	
	public ParallelTestRunStrategy poolSize(int poolSize) {
		assert poolSize > 0;
		this.poolSize = poolSize;
		return this;
	}
	
	private Population initializePopulation(Gester gester) {
		Population pop = new SimplePopulation(gester.getTestCase(), gester.getIdentityDef(), populationSize, poolSize);
		for(int i = 0; i < populationSize; ++i) {
			pop.add(gester.strategy(InitializationStrategy.class).initialize(gester.getIdentityDef()));
		}
		return pop;
	}
	
	@Override
	public void run(Gester gester) {
		Population pop = initializePopulation(gester);
		gester.strategy(FeedbackStrategy.class).afterInitialization(pop);

		pop.update();
		int generation = 1;
		gester.strategy(FeedbackStrategy.class).afterUpdate(pop, generation);
		while(!gester.strategy(ConvergenceDetectionStrategy.class).detect(pop, generation)) {
			gester.strategy(PruningStrategy.class).prune(pop);
			gester.strategy(FeedbackStrategy.class).afterPrune(pop, generation);
			Queue<Identity> pairs = gester.strategy(PairingStrategy.class).pair(pop, (populationSize - pop.size()) / 2);
			while(!pairs.isEmpty()) {
				Collection<Identity> children = gester.strategy(MatingStrategy.class).mate(pairs.poll(), pairs.poll());
				for (Identity child : children) {
					pop.add(child);
				}
			}
			gester.strategy(MutationStrategy.class).mutate(pop);
			pop.update();
			generation++;
			gester.strategy(FeedbackStrategy.class).afterUpdate(pop, generation);
		}
		gester.strategy(FeedbackStrategy.class).afterConvergence(pop, generation);
	}

}
