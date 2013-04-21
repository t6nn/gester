package eu.t6nn.gester.operations;

import eu.t6nn.gester.Population;

public interface MutationStrategy extends OperationStrategy {
	void mutate(Population population);
}
