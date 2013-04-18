package eu.t6nn.gester.operations.impl;

import java.util.Collection;
import java.util.Queue;

import eu.t6nn.gester.Gester;
import eu.t6nn.gester.Identity;
import eu.t6nn.gester.Population;
import eu.t6nn.gester.SimplePopulation;
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
			pop.add(gester.getInitializationStrategy().initialize(gester.getIdentityDef()));
		}
		return pop;
	}
	
	@Override
	public void run(Gester gester) {
		Population pop = initializePopulation(gester);
		gester.getFeedbackStrategy().afterInitialization(pop);

		pop.update();
		int generation = 1;
		gester.getFeedbackStrategy().afterUpdate(pop, generation);
		while(!gester.getConvergenceStrategy().detect(pop, generation)) {
			gester.getPruningStrategy().prune(pop);
			Queue<Identity> pairs = gester.getPairingStrategy().pair(pop, (populationSize - pop.size()) / 2);
			while(!pairs.isEmpty()) {
				Collection<Identity> children = gester.getMatingStrategy().mate(pairs.poll(), pairs.poll());
				for (Identity child : children) {
					pop.add(child);
				}
			}
			gester.getMutationStrategy().mutate(pop);
			pop.update();
			generation++;
			gester.getFeedbackStrategy().afterUpdate(pop, generation);
		}
		gester.getFeedbackStrategy().afterConvergence(pop, generation);
	}

}
