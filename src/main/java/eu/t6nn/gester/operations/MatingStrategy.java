package eu.t6nn.gester.operations;

import eu.t6nn.gester.Identity;

public interface MatingStrategy {
	
	Identity mate(Identity parent1, Identity parent2);
}
