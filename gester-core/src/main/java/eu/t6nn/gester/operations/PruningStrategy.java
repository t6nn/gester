package eu.t6nn.gester.operations;

import eu.t6nn.gester.Population;

public interface PruningStrategy extends OperationStrategy {
	void prune(Population pop);
}
