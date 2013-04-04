package eu.t6nn.gester.operations;

import java.util.Collection;
import java.util.Queue;

import eu.t6nn.gester.Gester;
import eu.t6nn.gester.Identity;
import eu.t6nn.gester.Population;
import eu.t6nn.gester.SimplePopulation;
import eu.t6nn.gester.TestCase.Process;

public class SynchronousTestRunStrategy implements TestRunStrategy {
	
	private int populationSize;

	public SynchronousTestRunStrategy(int populationSize) {
		assert populationSize > 0;
		this.populationSize = populationSize;
	}
	
	private Population initializePopulation(Gester gester) {
		Population pop = new SimplePopulation(gester.getTestCase(), gester.getIdentityDef(), populationSize, 1);
		for(int i = 0; i < populationSize; ++i) {
			pop.add(gester.getInitializationStrategy().initialize(gester.getIdentityDef()));
		}
		return pop;
	}
	
	@Override
	public void run(Gester gester) {
		Population pop = initializePopulation(gester);
		pop.update();
		while(gester.getTestCase().tick(pop) != Process.STOP) {
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
		}
	}
	
}